package throwable.server.handler.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.mvc.annotation.At;
import org.nutz.ssdb4j.spi.Response;

import seava.tools.DateUtils;
import seava.tools.ProxyChannel;
import seava.tools.cache.RedisTool;
import throwable.server.bean.ChatInfo;
import throwable.server.bean.Kind;
import throwable.server.bean.User;
import throwable.server.bean.UserShowInfo;
import throwable.server.handler.user.UserServer;
import throwable.server.service.KindService;
import throwable.server.service.SsdbHandler;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

import com.alibaba.fastjson.JSON;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年4月23日
 */
@At("chat")
@IocBean
public class ChatController {
	
	/**
	 * 用户房间信息的key
	 */
	private static final String user_room_key = "user_room_";
	
	/**
	 * 房间信息的key
	 */
	private static final String room_key = "room_";
	
	/**
	 * 发言记录
	 */
	private static final String room_speak_key = "room_speak_";
	
	/**
	 * 房间用户的key
	 */
	private static final String room_user_key = "room_users_";
	
	@Inject
	private SsdbHandler ssdbHandler;
	@Inject
	private UserServer userServer;
	@Inject
	private RedisTool redisTool;
	
	@Inject
	private KindService kindService;

	/**
	 * 查询所有的房间信息
	 * @return
	 */
	@At("/queryAllRooms")
	public List<Map<String, Object>> queryAllRooms() {
		List<Map<String, Object>> retList = new ArrayList<>();
		List<Kind> kindList = kindService.queryAllKinds();
		if (StringTool.isEmpty(kindList)) {
			return retList;
		}
		//填充房间详细信息
		Map<String, Object> retMap;
		for (Kind kind : kindList) {
			Map<String, String> map = getRoomInfo(kind.id);
			retMap = new HashMap<>();
			//如果ssdb中取不到数据 则重新new一个
			if (!StringTool.isEmpty(map)) {
				retMap = new HashMap<>();
				retMap.put("factCount", map.get("factCount"));
				retMap.put("speakCount", map.get("speakCount"));
			}
			retMap.put("roomId", kind.id);
			retMap.put("roomName", kind.kind_name);
			retMap.put("description", kind.description);
			List<ChatInfo> list = getRoomChatInfo(kind.id, 0, 1);
			if (!StringTool.isEmpty(list)) {
				retMap.put("activeTime", DateUtils.getNewTime(list.get(0).time, 2));
			}
			retList.add(retMap);
		}
		return retList;
	}
	
	/**
	 * 加入房间
	 * @param userId
	 * @param roomId
	 * @param fromId
	 * @param uuid
	 */
	@At("/joinRoom")
	public void joinRoom(long userId, long roomId, int fromId, String uuid) {
		if(userId < 1 || roomId < 1 || fromId < 1 || StringTool.isEmpty(uuid)) {
			BackTool.errorInfo("120001", "参数错误");
		}
		//获取房间的基本信息
		Kind kind = kindService.queryKindById(roomId);
		//判断房间是否存在
		if (null == kind) {
			BackTool.errorInfo("120010", "房间不存在");
		}
		//设置用户房间信息
		String userRoomKey = user_room_key + userId + "_" + roomId;
		ssdbHandler.getSSDB().multi_hset(userRoomKey, "roomId", roomId, "userId", userId, "uuid", uuid, "loginTime", System.currentTimeMillis()).check();
		//增加房间人数
		String roomKey = room_key + roomId;
		ssdbHandler.getSSDB().hincr(roomKey, "factCount", 1).check();
		
		//添加房间的用户列表
		String roomUserKey = room_user_key + roomId;
		ssdbHandler.getSSDB().zset(roomUserKey, userId, System.currentTimeMillis());
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("fc", "l");
		message.put("msgCode", 1);
		//获取用户基本信息
		User user = userServer.queryUserInfo(userId);
		UserShowInfo userShowInfo = null == user ? UserShowInfo.getVisitor(userId) : new UserShowInfo(user);
		message.put("fuo", userShowInfo);
		//获取房间基本信息
		Map<String, Object> roomMap = new HashMap<>();
		roomMap.put("roomId", kind.id);
		roomMap.put("roomName", kind.kind_name);
		roomMap.put("roomDesc", kind.description);
		Map<String, String> map = getRoomInfo(roomId);
		if (!StringTool.isEmpty(map)) {
			roomMap.put("factCount", map.get("factCount"));
			roomMap.put("speakCount", map.get("speakCount"));
		}
		message.put("roomInfo", roomMap);
		List<Long> userList = getRoomUsers(roomId);
		//获取用户列表
		List<UserShowInfo> userShowInfoList = new ArrayList<>();
		for (Long id : userList) {
			User tempUser = userServer.queryUserInfo(userId);
			UserShowInfo us = null == tempUser ? UserShowInfo.getVisitor(id) : new UserShowInfo(tempUser);
			userShowInfoList.add(us);
		}
		message.put("userList", userShowInfoList);
		//进入房间返回20条最新的聊天记录
		List<ChatInfo> list = getRoomChatInfo(roomId, 0, 20);
		if (!StringTool.isEmpty(list)) {
			//房间的最后发言时间作为房间的最后活动时间
			message.put("activeTime", DateUtils.getNewTime(list.get(0).time, 2));
		}
		for (ChatInfo chatInfo : list) {
			User n_f_user = userServer.queryUserInfo(chatInfo.fromUserId);
			User n_t_user = userServer.queryUserInfo(chatInfo.toUserId);
			if (null == n_f_user) {
				chatInfo.fromUserName = "游客";
			} else {
				chatInfo.fromUserName = n_f_user.username;
			}
			if (null == n_t_user) {
				chatInfo.toUserName = "游客";
			} else {
				chatInfo.toUserName = n_t_user.username;
			}
		}
		message.put("chatHistory", list);
		ProxyChannel.room(roomId, message, redisTool);
	}
	
	/**
	 * 离开房间
	 * @param userId
	 * @param roomId
	 * @param fromId
	 */
	@At("/leaveRoom")
	public void leaveRoom(long userId, long roomId, int fromId) {
		if(userId < 1 || roomId < 1 || fromId < 1) {
			BackTool.errorInfo("120001", "参数错误");
		}
		//清空用户房间信息
		String userRoomKey = user_room_key + userId + "_" + roomId;
		ssdbHandler.getSSDB().multi_hset(userRoomKey, "uuid", "", "loginTime", 0).check();
		//减少房间人数
		String roomKey = room_key + roomId;
		ssdbHandler.getSSDB().hincr(roomKey, "factCount", -1).check();
		//删除房间用户列表
		String roomUserKey = room_user_key + roomId;
		ssdbHandler.getSSDB().zdel(roomUserKey, userId);
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("fc", "lo");
		message.put("msgCode", 1);
		User user = userServer.queryUserInfo(userId);
		UserShowInfo userShowInfo = null == user ? UserShowInfo.getVisitor(userId) : new UserShowInfo(user);
		message.put("fuo", userShowInfo);
		ProxyChannel.room(roomId, message, redisTool);
	}
	
	/**
	 * 聊天
	 * @param userId
	 * @param ruid
	 * @param roomId
	 * @param fromId
	 */
	@At("/speak")
	public void speak(long userId, long ruid, long roomId, int fromId, String message) {
		if(userId < 1 || roomId < 1 || fromId < 1 || ruid < 0 || StringTool.isEmpty(message)) {
			BackTool.errorInfo("120001", "参数错误");
		}
		ChatInfo chatInfo = new ChatInfo();
		chatInfo.roomId = roomId;
		chatInfo.fromUserId = userId;
		chatInfo.toUserId = ruid;
		chatInfo.time = System.currentTimeMillis();
		chatInfo.content = message;
		String roomSpeakKey = room_speak_key + roomId;
		ssdbHandler.getSSDB().qpush_front(roomSpeakKey, JSON.toJSON(chatInfo));
		String roomKey = room_key + roomId;
		ssdbHandler.getSSDB().hincr(roomKey, "speakCount", 1).check();
		Map<String, Object> backMap = new HashMap<String, Object>();
		backMap.put("fc", "c");
		backMap.put("msgCode", 1);
		User user = userServer.queryUserInfo(userId);
		UserShowInfo userShowInfo = null == user ? UserShowInfo.getVisitor(userId) : new UserShowInfo(user);
		if(ruid > 0) {
			User ruser = userServer.queryUserInfo(ruid);
			UserShowInfo ruserShowInfo = null == ruser ? UserShowInfo.getVisitor(ruid) : new UserShowInfo(ruser);
			backMap.put("ruo", ruserShowInfo);
		}
		backMap.put("fuo", userShowInfo);
		backMap.put("message", message);
		ProxyChannel.room(roomId, backMap, redisTool);
	}
	
	/**
	 * 获取房间信息
	 * @param roomId
	 * @return
	 */
	private Map<String, String> getRoomInfo(long roomId) {
		String roomKey = room_key + roomId;
		Response rp = ssdbHandler.getSSDB().hgetall(roomKey);
		return rp.mapString();
	}
	
	/**
	 * 获取房间的发言记录
	 * @param roomId  房间id
	 * @param start   起始位置
	 * @param limit   限制条数
	 * @return
	 */
	private List<ChatInfo> getRoomChatInfo(long roomId, int start, int limit) {
		String key = room_speak_key + roomId;
		//从ssdb中获取数据
		Response rp = ssdbHandler.getSSDB().qslice(key, start, limit);
		List<ChatInfo> list = new ArrayList<>();
		//如果没有数据 返回空
		if (null == rp) {
			return list;
		}
		List<String> rList = rp.listString();
		if (StringTool.isEmpty(rList)) {
			return list;
		}
		//转化数据
		ChatInfo chatInfo;
		for (String temp : rList) {
			chatInfo = Json.fromJson(ChatInfo.class, temp);
			list.add(chatInfo);
		}
		return list;
	}
	
	/**
	 * 获取房间的用户列表 所有用户
	 * @param roomId
	 * @return
	 */
	private List<Long> getRoomUsers(long roomId) {
		String key = room_user_key + roomId;
		int size = 0;
		Response rp1 = ssdbHandler.getSSDB().zsize(key);
		if (null != rp1 ) {
			size = rp1.asInt();
		}
		Response rp = ssdbHandler.getSSDB().zrrange(key, 0, size);
		List<Long> userList = new ArrayList<>();
		if (null == rp) {
			return userList;
		} 
		for (String temp : rp.listString()) {
			userList.add(Long.parseLong(temp));
		}
		return userList;
	}
}
