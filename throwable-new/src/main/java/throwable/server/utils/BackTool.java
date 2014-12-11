package throwable.server.utils;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.Lang;

import throwable.server.ThrowableConf;
import throwable.server.framework.mvc.SystemError;
import throwable.server.framework.rpc.ResultCode;


public class BackTool {

	public static final String msgCode = "msgCode";
	public static final String errorMsg = "errorMsg";
	public static final String successMsgCode = "1";
	
	public static Map<String,Object> errorInfo(String msgCode,String errorMsg){
		if(!successMsgCode.equals(msgCode)){
			throw Lang.wrapThrow(new SystemError(ResultCode.PARAMS_ERROR, msgCode, errorMsg));
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put(BackTool.msgCode, msgCode);
		res.put(BackTool.errorMsg, errorMsg);
		return res;
	}
	
	public static Map<String,Object> errorInfo(String msgCode,PropertiesProxy errorMsg){
		if(!successMsgCode.equals(msgCode)){
			throw Lang.wrapThrow(new SystemError(ResultCode.PARAMS_ERROR, msgCode,errorMsg.get(msgCode)));
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put(BackTool.msgCode, msgCode);
		res.put(BackTool.errorMsg, errorMsg.get(msgCode));
		return res;
	}
	
	public static Map<String,Object> errorInfo(String msgCode){
		if(!successMsgCode.equals(msgCode)){
			throw Lang.wrapThrow(new SystemError(ResultCode.PARAMS_ERROR, msgCode,ThrowableConf.errorMsg.get(msgCode)));
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put(BackTool.msgCode, msgCode);
		res.put(BackTool.errorMsg, ThrowableConf.errorMsg.get(msgCode));
		return res;
	}
	
	public static Map<String,Object> successInfo(){
		Map<String, Object> res = new HashMap<String, Object>();
		res.put(BackTool.msgCode, successMsgCode);
		return res;
	}
	
	public static Map<String,Object> successInfo(String successKey,Object successMsg){
		Map<String, Object> res = new HashMap<String, Object>();
		res.put(BackTool.msgCode, successMsgCode);
		if(null != successKey && null != successMsg){
			res.put(successKey, successMsg);
		}
		return res;
	}
	
	/**
	 * 处理多返回值
	 * @param successKey 存储多个返回值的key
	 * @param successMsg 存储多个返回值的value
	 */
	public static Map<String, Object> successInfo(String[] successKey,Object[] successMsg) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put(BackTool.msgCode, successMsgCode);
        if(null != successKey && null != successMsg && successKey.length == successMsg.length){
    		for (int i = 0; i < successKey.length; i++) {
    			if(null != successKey[i] && !"".equals(successKey[i]) && null != successMsg[i])
                res.put(successKey[i], successMsg[i]);
    		}
        }
		return res;
	}
}
