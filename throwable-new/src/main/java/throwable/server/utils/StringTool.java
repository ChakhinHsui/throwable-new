package throwable.server.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import throwable.server.enums.Right;

/**
 * 公用的String工具类
 * @author WaterHsu@xiu8.com
 * @version 2015年2月12日
 */
public class StringTool {

	public static String repaymentDate2Str(int repaymentDate) {
		String s = String.valueOf(repaymentDate);
		return s.substring(0, 4) + "-" + s.substring(4, 6) + "-"
				+ s.substring(6, 8);
	}

	public static String float2Str1(float f) {
		return new DecimalFormat("#.0").format(f);
	}

	public static Set<Long> commaStringToLongSet(String commaString) {
		Set<Long> testUserIds = new HashSet<Long>();
		if (StringTool.isNotEmpty(commaString)) {
			String[] userIdArray = commaString.split(",");
			if (userIdArray.length > 0) {
				for (int i = 0; i < userIdArray.length; i++) {
					testUserIds.add(Long.parseLong(userIdArray[i]));
				}
			} else {
				testUserIds.add(Long.parseLong(commaString));
			}
		}
		return testUserIds;
	}
	
	public static Set<Integer> commaStringToIntegerSet(String commaString) {
		Set<Integer> testUserIds = new HashSet<Integer>();
		if (StringTool.isNotEmpty(commaString)) {
			String[] userIdArray = commaString.split(",");
			if (userIdArray.length > 0) {
				for (int i = 0; i < userIdArray.length; i++) {
					testUserIds.add(Integer.parseInt(userIdArray[i]));
				}
			} else {
				testUserIds.add(Integer.parseInt(commaString));
			}
		}
		return testUserIds;
	}

	public static BigDecimal str2BigDecimal(String str) {
		if (isEmpty(str)) {
			return new BigDecimal(0);
		}
		BigDecimal l;
		try {
			l = new BigDecimal(str);
		} catch (Exception e) {
			l = new BigDecimal(0);
		}
		return l;
	}

	public static float str2float0Null(String str) {
		if (isEmpty(str)) {
			return 0;
		}
		float l;
		try {
			l = Float.parseFloat(str);
		} catch (Exception e) {
			l = 0;
		}
		return l;
	}
	
	public static double str2double0Null(String str) {
		if (isEmpty(str)) {
			return 0;
		}
		double l;
		try {
			l = Double.parseDouble(str);
		} catch (Exception e) {
			l = 0;
		}
		return l;
	}

	public static int str2int0Null(String str) {
		if (isEmpty(str)) {
			return 0;
		}
		int l;
		try {
			l = Integer.parseInt(str);
		} catch (Exception e) {
			l = 0;
		}
		return l;
	}

	public static long str2long0Null(String str) {
		if (isEmpty(str)) {
			return 0;
		}
		long l;
		try {
			l = Long.parseLong(str);
		} catch (Exception e) {
			l = 0;
		}
		return l;
	}
	/**
	 * 把date转换成一个时间戳字符串yyyyMMddHHmmssSSS。
	 * @param date
	 * @return
	 */
	public static String date2long(Date date){
		if(date==null) return null;
		SimpleDateFormat t = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String s = t.format(date);
		return s;
	}
	
	/**
	 * 判断是否为空  String Map List Set Object
	 * @param object  对象
	 * @return
	 */
	public static boolean isEmpty(Object object) {
		if(null == object) {
			return true;
		}
		if(object instanceof String) {
			return "".equals(((String)object).trim());
		}
		if(object instanceof Map) {
			return ((Map<?,?>)object).isEmpty();
		}
		if(object instanceof List) {
			return ((List<?>)object).isEmpty();
		}
		if(object instanceof Set) {
			return ((Set<?>)object).isEmpty();
		}
		return false;
	}

	public static boolean isEmptyOrLong(String s, int length) {
		if (isEmpty(s) || s.length() > length) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String s) {
		if (null != s && !"".equals(s.trim())) {
			return true;
		}
		return false;
	}
	
	public static boolean isNumber(String number) {

		boolean isNumber = false;
		if(null==number || "".equals(number)){
			return false;
		}
		int index = number.indexOf(",");
		if (index >= 0) {
			// 有逗号等分隔符的数字
			isNumber = number.matches("[1-9]+[0-9]*(,[0-9]{3})+(\\.[0-9]+)?");
		} else {
			isNumber = number.matches("[1-9]+[0-9]*(\\.[0-9]+)?");

		}
		return isNumber;
	}

	/**
	 * 转换错误栈为为字符串。
	 */
	public static String getExceptionStack(Throwable e) {
		if (e == null)
			return "";
		OutputStream ou = new ByteArrayOutputStream();
		PrintStream o = new PrintStream(ou);
		e.printStackTrace(o);
		return ou.toString();
	}

	/**
	 * 验证邮箱地址是否正确
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[_|-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 */
	public static boolean isMobileNo(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断对象是否为空，为空则返回传来的默认值，不是空则返回对象的字符串形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @param defStr
	 *            默认值
	 * @return String
	 */
	public static String getString(Object obj, Object defStr) {
		return obj == null ? String.valueOf(defStr) : obj.toString();
	}

	/**
	 * 判断对象是否为空，为空则返回传来的0，不是空则返回对象的Long形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @return Long
	 */
	public static long getLong(Object obj) {
		return getLong(obj, 0);
	}

	/**
	 * 判断对象是否为空，为空则返回传来的defArg，不是空则返回对象的Long形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @param defArg
	 *            默认的Long值
	 * @return Long
	 */
	public static long getLong(Object obj, long defArg) {
		return Long.parseLong(getString(obj, defArg));
	}

	/**
	 * 判断对象是否为空，为空则返回传来的0，不是空则返回对象的Long形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @return Long
	 */
	public static int getInt(Object obj) {
		return getInt(obj, 0);
	}

	/**
	 * 判断对象是否为空，为空则返回传来的defArg，不是空则返回对象的Long形式
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @param defArg
	 *            默认的Long值
	 * @return Long
	 */
	public static int getInt(Object obj, long defArg) {
		return Integer.parseInt(getString(obj, defArg));
	}

	/**
	 * 检查当前对象是否不为空 包括NULL和空字符串 如果不为空返回true
	 * 
	 * @param obj
	 *            要检查的对象
	 * @return boolean 返回true或false
	 */
	public static boolean isNotEmpty(Object obj) {
		boolean isTrue = false;
		if (null != obj) {
			isTrue = true;
		}
		if (obj instanceof String) {
			if (((String) obj).trim().length() > 0)
				isTrue = true;
			else
				isTrue = false;
		}
		if (obj instanceof List<?>) {
			if (((List<?>) obj).size() > 0) {
				isTrue = true;
			}
		}
		if (obj instanceof Map<?, ?>) {
			if (((Map<?, ?>) obj).size() > 0) {
				isTrue = true;
			}
		}
		return isTrue;
	}

	public static String formatuserName(String username){
    	if(isEmpty(username)) return "";
    	return username.length() <=12 ? username : username.substring(0, 12);
    }
	
	/**
	 * 用户名检测  不能是网站的保留字段  也不能是非法字符
	 * @param username
	 * @return
	 */
	public static boolean userNameCheck(String username){
		if(Right.general.getValue().equals(username) || Right.context.getValue().equals(username) || Right.web.getValue().equals(username) || Right.superU.getValue().equals(username)){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否是ip
	 * @param ip
	 * @return
	 */
	public static boolean isIp(String ip){
		if(ip.trim().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
			String[] s = ip.split("\\.");
			if(Integer.parseInt(s[0])<255)  
                if(Integer.parseInt(s[1])<255)  
                    if(Integer.parseInt(s[2])<255)  
                        if(Integer.parseInt(s[3])<255){
                        	return true;
                        }
		}
		return false;
	}
	
	/**
	 * 得到注册激活发邮件的邮件内容
	 * @param nick  用户名
	 * @param url   url 邮件点击后返回地址
	 * @return
	 */
	public static String getRegisterMailString(String nick, String url){
		StringBuffer context = new StringBuffer();
		context.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
		context.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
		context.append("<head>");
		context.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
		context.append("<title>Throwable技术问答社区</title>");
		context.append("<meta name='keywords' content='用户账号激活   throwable技术问答社区'/>");
		context.append("<meta name='description' content='用户账号激活   throwable技术问答社区' />");
		context.append("<style type='text/css'>");
		context.append(".email_center a { word-break: break-all;}");
		context.append(".email_logo { width:285px; height:40px;}");
		context.append(".email_logo img {border:none;}");
		context.append(".email_center { width:550px;  border-top:1px solid #e5d0b0; background:#fff1dd; margin-top:5px; padding:0px 10px 10px 10px;}");
		context.append(".email_center p { padding:0px; margin:11px 0px; font-size:14px; color:#333; line-height:18px;}");
		context.append(".email_center p font { color:#f00; font-size:14px; font-weight:bold;}");
		context.append(".email_bottom { width:550px; border-top:1px dashed #333; margin-left:10px; margin-top:20px; font-size:14px;}");
		context.append(".email_bottom p { width:550px; text-align:center; padding:0px; margin:11px 0px;}");
		context.append(".email_bottom p a { color:#1155cc; text-decoration:underline; padding-left:3px;}");
		context.append("</style>");
		context.append("</head>");
		context.append("<body>");
		context.append("<a href='http://www.xiu8.com' class='email_logo'><img src='http://static.xiu8.com/images1/myaccount/logo.png' /></a>");
		context.append("<div class='email_center'>");
		context.append("<p>亲爱的<span>");
		context.append(nick);
		context.append("</span>:</p>");
		context.append(" <p>您好，感谢您对throwable技术问答社区的支持。</p>");
		context.append("<p>您在throwable技术问答社区注册了账号，请使用以下链接进行激活：<a href='"+url+"' target='_blank'> "+ url +" </a></p> ");
		context.append("<p>如果链接无法点击，请您选择并复制整个链接，打开浏览器窗口并将其粘贴到地址栏中。然后单击'转到'按钮或按键盘上的 Enter 键。</p>");
		context.append("<p><font>注意：</font>此激活链接在24小时内有效，如地址已失效，请重新激活。</p>");
		context.append("</div>");
		context.append("<div class='email_bottom'>");
		context.append("<p>此信由throwable技术问答社区系统发出，系统不接收回信，因此请勿直接回复。</p>");
		context.append("<p><font>温馨提示：</font> 1、throwable技术问答社区统一回复邮箱为xxx@throwable.cn，请注意邮件发送者，谨防假冒！2、为了您帐号的安全，建议经常更新密码！</p>");
		context.append("<p>如有任何疑问，请联系<a href='http://sighttp.qq.com/msgrd?v=3&uin=97260007&site=qq&menu=yes'>throwable管理员</a>.</p>");
		context.append("<p>Copyright 2014-2054, 版权所有<a href='http://www.throwable.cn'>Throwable.cn</a></p>");
		context.append("</div>");
		context.append("</body>");
		context.append("</html>");
		return context.toString();
	}
}
