package throwable.server.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtils {
	
	private static Log logger = LogFactory.getLog(DateUtils.class);
	public static long getLongTime(int timeType, int time) {
		Calendar cal = Calendar.getInstance();
		switch (timeType) {
			case 1:
				cal.set(Calendar.MINUTE, time > 0 ? time : 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				break;
			case 2:
				cal.set(Calendar.HOUR_OF_DAY, time > 0 ? time : 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				break;
			case 3:
				cal.set(Calendar.DAY_OF_WEEK, time > 0 ? time : 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				break;
			case 4:
				cal.set(Calendar.DAY_OF_MONTH, time > 0 ? time : 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				break;
		}
		return cal.getTimeInMillis();
	}
	public static Date parseDatetime(String date){
		SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date s = t.parse(date);
			return s;
		} catch (ParseException e) {
			logger.error("method(parseDatetime) param="+date+"  error \r\n"+ StringTool.getExceptionStack(e));
			return null;
		} 
	}
	
	public static long getyyyyMMddHHmmssSSS(long date){
		SimpleDateFormat t = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		try {
			String s = t.format(date);
			return Long.parseLong(s);
		} catch (Exception e) {
			logger.error(e);
			return 0;
		} 
	}
	
	/**
	 * 获取一个月的第一天的时间戳
	 * @return
	 */
	public static long getFirstDayOfMonth(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		long re = c.getTimeInMillis();
		
		return re;
	}
	
	/**
	 * 获取当月的最后一天末的时间戳
	 * @return
	 */
	public static long getLastTimeOfMonth(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		c.add(Calendar.MONTH, 1);
		long re = c.getTimeInMillis();		
		re -= 1;
		
		return re;
	}
	
	public static String getDatetimeSSS(long time){
		SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			Date s =  new Date();
			s.setTime(time);
			String re = t.format(s);
			return re;
		} catch (Exception e) {
			logger.error("method(parseDatetime) param="+time+"  error \r\n"+ StringTool.getExceptionStack(e));
			return "";
		}
	}
	
	/**
	 * 根据时间得到天 格式yyyyMMdd
	 * @param time 时间
	 * @return
	 */
	public static int getDay(long time){
		String date = new SimpleDateFormat("yyyyMMdd").format(time);
		return Integer.parseInt(date);
	}
	
	/**
	 * 获取昨天最后时间的时间戳    结束时间yyyy-MM-dd 23:59:59
	 * 
	 * @return
	 */
	public static long getToDayLastTime() {
		Calendar cal = Calendar.getInstance();
		// 设置时间
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH,1);
		cal.add(Calendar.SECOND, -1);
		return cal.getTime().getTime();
	}
	
	/**
	 * 得到yyyy-MM-dd格式的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getYear_Month_Day(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	/**
	 * 得到yyyy-MM-dd HH:mm:ss格式的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 * 比较当前时间与给定时间的大小
	 * @param time
	 * @param ms
	 * @return
	 */
	public static boolean compareDate(String time,int minutes){
		boolean b = false;
	    Date   nows=new   Date();  
	    Date date = parseDatetime(time);		
		long   minms=nows.getTime()-(date.getTime()+minutes*60*1000); 
		if (minms >= 0){
		   b=true;
		}			   
		 return b;
	}
	/**
	 * 根据传入的时间戳取得当天凌晨的时间戳 
	 * 如    ：2013-09-30 14:32:58.687  1380522778687(参数)
	 * 得到：2013-09-30 00:00:00.000  1380470400000(返回值)
	 * @param currentstamp
	 * @return
	 */
	public static long getZeroTimestamp(long currentstamp){
		Date date12=new Date(currentstamp);
		SimpleDateFormat f= new SimpleDateFormat("yyyyMMdd");
		try{
			String yyyyMMdd=f.format(date12);//20130923
			Date date = f.parse(yyyyMMdd);//
			return date.getTime();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	/**
	 * 根据传入的时间戳取得当天午夜的时间戳 
	 * 如    ：2013-09-30 14:32:58.687  1380522778687(参数)
	 * 得到：2013-09-30 23:59:59.999  1380556799999(返回值)
	 * @param currentstamp
	 * @return
	 */
	public static long getMidnightTimestamp(long currentstamp){
		Date date12=new Date(currentstamp);
		SimpleDateFormat f= new SimpleDateFormat("yyyyMMdd");
		try{
			String yyyyMMdd=f.format(date12);//20130923
			Date date = f.parse(yyyyMMdd);//凌晨时间戳
			return date.getTime()+24*60*60*1000L-1;//午夜时间戳
		}catch (Exception e) {
		}
		return 0;
	}
	
	
	/**
	 * 获取上周一到周日的时间段。返回日期类型
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date[] getLastWeek() {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis());
		int week = startDate.getDay();
		week = week == 0 ? 6 : week - 1;
		int oneDay = 1000 * 60 * 60 * 24;
		endDate.setTime(startDate.getTime() - week * oneDay - oneDay);
		startDate.setTime(endDate.getTime() - 6 * oneDay);
		return new Date[] { startDate, endDate };
	}
	
	
	@SuppressWarnings("deprecation")
	public static Date[] getThisWeek(){
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis());
		int week = startDate.getDay();
		week = week == 0 ? 6 : week - 1;
		int oneDay = 1000 * 60 * 60 * 24;
		endDate.setTime(startDate.getTime() + (6 - week) * oneDay);
		startDate.setTime(endDate.getTime() - 6 * oneDay);
		return new Date[] { startDate, endDate };
	}
	
	@SuppressWarnings("deprecation")
	public static Date[] getLastWeek(Date date){
		Date startDate = new Date(date.getTime());
		Date endDate = new Date(date.getTime());
		int week = startDate.getDay();
		week = week == 0 ? 6 : week - 1;
		int oneDay = 1000 * 60 * 60 * 24;
		endDate.setTime(startDate.getTime() - week * oneDay - oneDay);
		startDate.setTime(endDate.getTime() - 6 * oneDay);
		return new Date[] { startDate, endDate };
	}
	
	
	@SuppressWarnings("deprecation")
	public static Date[] getThisWeek(Date date){
		Date startDate = new Date(date.getTime());
		Date endDate = new Date(date.getTime());
		int week = startDate.getDay();
		week = week == 0 ? 6 : week - 1;
		int oneDay = 1000 * 60 * 60 * 24;
		endDate.setTime(startDate.getTime() + (6 - week) * oneDay);
		startDate.setTime(endDate.getTime() - 6 * oneDay);
		return new Date[]{startDate, endDate};
	}
	
	/**
	 * 传入日期  返回这个星期的第一天后最后一天  以yyyyMMdd形式的int类型返回
	 */
	@SuppressWarnings("deprecation")
	public static int[] getThisWeekInt(Date date){
		Date startDate = new Date(date.getTime());
		Date endDate = new Date(date.getTime());
		int week = startDate.getDay();
		week = week == 0 ? 6 : week - 1;
		int oneDay = 1000 * 60 * 60 * 24;
		endDate.setTime(startDate.getTime() + (6 - week) * oneDay);
		startDate.setTime(endDate.getTime() - 6 * oneDay);
		return new int[]{getDay(startDate.getTime()), getDay(endDate.getTime())};
	}
	
	/**
	 * 返回具体某个月的00 - 32  
	 * yyyymm00 - yyyymm32
	 * @param date
	 * @return
	 */
	public static int[] getThisMonth(Date date){
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
		String mm = dayFormat.format(date); 
		String yyyy = mm.substring(0, 4);
		mm = mm.substring(4, 6);
		return new int[]{Integer.parseInt(yyyy + mm + "00"), Integer.parseInt(yyyy + mm + "32")};
	}
	
	/**
	 * 返回具体某年的00 00 - 13 32
	 * yyyy0000 - yyyy1332
	 * @param date
	 * @return
	 */
	public static int[] getThisYear(Date date){
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
		String yyyy = dayFormat.format(date).substring(0, 4);
		return new int[]{Integer.parseInt(yyyy + "00" + "00"), Integer.parseInt(yyyy + "13" + "32")};
	}
	
	public static int[] getTotal(){
		return new int[]{10000000, 99999999};
	}
	
	/**
	 * 根据时间 获得 几分钟前，几小时前，几天前， 2014年3月15日等的时间格式
	 * @param time   时间戳
	 * @param day    几天前的截止  如10 则10天内的时间用几天前的格式  10天后的用 xxxx年xx月xx天
	 * @return
	 */
	public static String getNewTime(long time, int day){
		String retTime = "";
		if(System.currentTimeMillis() - time > 1000 * 60 * 60 * 24 * day){
			retTime = getYear_Month_Day(new Date(time));
			String[] temp = retTime.split("-");
			retTime = temp[0] + "年" + temp[1] + "月" + temp[2] + "日";
		}else if(System.currentTimeMillis() - time >= 1000 * 60 * 60 * 24){
			retTime = ((System.currentTimeMillis() - time) / (1000 * 60 * 60 * 24)) + "天前";
		}else if(System.currentTimeMillis() - time >= 1000 * 60 * 60){
			retTime = ((System.currentTimeMillis() - time) / (1000 * 60 * 60)) + "小时前";
		}else if(System.currentTimeMillis() - time >= 1000 * 60){
			retTime = "" + ((System.currentTimeMillis() - time) / (1000 * 60)) + "分钟前";
		}else{
			retTime = "1分钟前";
		}
		return retTime;
	}
  
	public static void main(String[] args) {
//		int[] retInt = getThisMonth(new Date());
//		for(int tt : retInt){
//			System.out.println(tt);
//		}
//		getNewTime(System.currentTimeMillis(), 20);
		System.out.println("你好阿".length());
		System.out.println("nihaoa".length());
		System.out.println("Hello World".length());
		System.out.println("HelloWorld".length() + " " + "HelloWorld".getBytes().length);
		System.out.println("Hello你好".length() + " " + "Hello你好".getBytes().length);
	}
	

}
