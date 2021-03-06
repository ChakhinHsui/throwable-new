/**
 * 秀吧网络科技有限公司版权所有
 * Copyright (C) xiu8 Corporation. All Rights Reserved
 */
package throwable.server.user;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import throwable.server.AbstractThritfController;
import throwable.server.framework.rpc.ResultMsg;
import throwable.server.framework.utils.GeneratorID;


/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
public class UserTest extends AbstractThritfController {

	@Test
	public void testRegister() throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("username", "loveet5");
		paramMap.put("password", "5");
		paramMap.put("email", "et5@seava.com");
		paramMap.put("nickname", "seava5");
		
		ResultMsg retMsg = call(GeneratorID.getID(), "/user/register", paramMap);
		System.out.println(retMsg);
	}
	
	@Test
	public void testQueryUserInfo() throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", "29");
		ResultMsg retMsg = call(GeneratorID.getID(), "/user/queryUserInfo", paramMap);
		System.out.println(retMsg);
	}
}
