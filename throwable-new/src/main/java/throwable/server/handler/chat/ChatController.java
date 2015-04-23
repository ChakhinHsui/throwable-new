package throwable.server.handler.chat;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import seava.tools.ProxyChannel;
import seava.tools.RedisTool;
import throwable.server.bean.ChatInfo;
import throwable.server.bean.User;
import throwable.server.bean.UserShowInfo;
import throwable.server.handler.user.UserServer;
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
	private static String user_room_key = "user_room_";
	
	/**
	 * 房间信息的key
	 */
	private static String room_key = "room_";
	
	private static String room_speak_key = "room_speak_";
	
	@Inject
	private SsdbHandler ssdbHandler;
	@Inject
	private UserServer userServer;
	@Inject
	private RedisTool redisTool;

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
		String userRoomKey = user_room_key + userId + "_" + roomId;
		ssdbHandler.getSSDB().multi_hset(userRoomKey, "roomId", roomId, "userId", userId, "uuid", uuid, "loginTime", System.currentTimeMillis()).check();
		String roomKey = room_key + roomId;
		ssdbHandler.getSSDB().hincr(roomKey, "factCount", 1).check();
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("fc", "l");
		message.put("msgCode", 1);
		User user = userServer.queryUserInfo(userId);
		UserShowInfo userShowInfo = null == user ? UserShowInfo.getVisitor(userId) : new UserShowInfo(user);
		message.put("fuo", userShowInfo);
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
		String userRoomKey = user_room_key + userId + "_" + roomId;
		ssdbHandler.getSSDB().multi_hset(userRoomKey, "uuid", "", "loginTime", 0).check();
		String roomKey = room_key + roomId;
		ssdbHandler.getSSDB().hincr(roomKey, "factCount", -1).check();
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
}
