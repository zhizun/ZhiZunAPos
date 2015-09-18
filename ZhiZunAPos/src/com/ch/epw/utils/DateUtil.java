package com.ch.epw.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.util.Log;

/**
 * 日期处理帮助类
 * 创建日期：2014-5-12  上午11:59:35
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */
public class DateUtil {
	
	
	//格式化时间字符串
	@SuppressWarnings("unused")
	/**
	 * 格式化字符串 获取：年-月-日 yyyy-MM-dd
	 */
	
	public static final String date_sampleFormat="yyyy-MM-dd";
	public static final String dateFormat_yyyyMMdd="yyyyMMdd";
	public static final String datesFormat_yyyyMMddHH24mmss="yyyyMMddHHmmss";
	public static final String PATTERN_CHINESE_NO_SECOND = "yyyy年MM月dd日 HH:mm";

	@SuppressWarnings("unused")
	/**
	 * 格式化字符串 获取：年-月-日 时：分：秒  yyyy-MM-dd HH:mm:ss
	 */
	public static final String date_fullFormat="yyyy-MM-dd HH:mm:ss";
	/**
	 * 返回 yyyy-MM-dd HH:mm:ss 格式的SimpleDateFormat对象
	 */
	public static final SimpleDateFormat sdf=new SimpleDateFormat(date_fullFormat);
	@SuppressWarnings("unused")
   	/**
	 * 格式化字符串 获取：年-月-日 星期 yyyy-MM-dd EEEE
	 */
	public static final String weekdate_sampleFormat="yyyy-MM-dd EEEE";
	@SuppressWarnings("unused")
   	/**
	 * 格式化字符串 获取： 年-月-日 时：分：秒 星期 yyyy-MM-dd HH:mm:ss EEEE
	 */
	public static final String Weekdate_fullFormat="yyyy-MM-dd HH:mm:ss EEEE"; 
	@SuppressWarnings("unused")
    	/**
	 * 格式化字符串 只获取星期 EEEE
	 */
	public static final String week_fromat="EEEE";
	@SuppressWarnings("unused")
    	/**
	 * 格式化字符串 只获取年 yyyy
	 */
	public static final String year_format="yyyy";
	@SuppressWarnings("unused")
   	/**
	 * 格式化字符串 只获取月 MM
	 */
	public static final String moth_format="MM";
	@SuppressWarnings("unused")
    /**
	 * 格式化字符串 只获取天 dd
	 */
	public static final String date_fromat="dd";
	@SuppressWarnings("unused")
    	/**
	 * 格式化字符串 只获取小时 HH
	 */
	public static final String hour_format="HH";
	@SuppressWarnings("unused")
   	/**
	 * 格式化字符串 只获取分钟 mm
	 */
	public static final String min_format="mm";
	@SuppressWarnings("unused")
    /**
	 * 格式化字符串 只获取秒 ss
	 */
	public static final String second_fromt="ss";
	@SuppressWarnings("unused")
   	/**
	 * 格式化字符串 只获取时间 HH:mm:ss
	 */
	public static final String time_fromat = "HH:mm:ss";

	@SuppressWarnings("unused")
	private static int weeks = 0;
	@SuppressWarnings("unused")
	private static int MaxDate;// 一月最大天数
	@SuppressWarnings("unused")
	private static int MaxYear;// 一年最大天数
	private static String TAG="com.ktv.app.common.DateUtil";
	public static void main(String args[]){
		
	/*	System.out.println("获取当前日期:" +DateTimeUtil.getCurrntDate());
		System.out.println("获取当前格式化日期:" +DateTimeUtil.getFromatCurrentDate(Weekdate_fullFormat));
		System.out.println("获取本周一日期:" + DateTimeUtil.getMondayOFWeek(Weekdate_fullFormat));
		System.out.println("获取本周日的日期:" + DateTimeUtil.getCurrentWeekSunday(Weekdate_fullFormat));
		System.out.println("获取本周一和周日的日期:" + DateTimeUtil.getCurrntWeek(Weekdate_fullFormat)[0] +"  "+DateTimeUtil.getCurrntWeek(Weekdate_fullFormat)[1]);
		System.out.println("获取上周一日期:" + DateTimeUtil.getPreviousWeekday(Weekdate_fullFormat));
		System.out.println("获取上周日日期:" + DateTimeUtil.getPreviousWeekSunday(Weekdate_fullFormat));
		System.out.println("获取下周一日期:" + DateTimeUtil.getNextMonday(Weekdate_fullFormat));
		System.out.println("获取下周日日期:" + DateTimeUtil.getNextSunday(Weekdate_fullFormat));
		System.out.println("获取本月第一天日期:" + DateTimeUtil.getFirstDayOfMonth(Weekdate_fullFormat));
		System.out.println("获取本月最后一天日期:" + DateTimeUtil.getLastDayOfMonth(Weekdate_fullFormat));
	
		System.out.println("获取上月第一天日期:" + DateTimeUtil.getPreviousMonthFirst(Weekdate_fullFormat));
		System.out.println("获取上月最后一天的日期:" + DateTimeUtil.getPreviousMonthEnd(Weekdate_fullFormat));
		System.out.println("获取下月第一天日期:" + DateTimeUtil.getNextMonthFirst(Weekdate_fullFormat));
		System.out.println("获取下月最后一天日期:" + DateTimeUtil.getNextMonthEnd(Weekdate_fullFormat));
		System.out.println("获取本年的第一天日期:" + DateTimeUtil.getCurrentYearFirst(Weekdate_fullFormat));
		System.out.println("获取本年最后一天日期:" + DateTimeUtil.getCurrentYearEnd(Weekdate_fullFormat));
		System.out.println("获取去年的第一天日期:" + DateTimeUtil.getPreviousYearFirst(Weekdate_fullFormat));
		System.out.println("获取去年的最后一天日期:" + DateTimeUtil.getPreviousYearEnd(Weekdate_fullFormat));
		System.out.println("获取明年第一天日期:" + DateTimeUtil.getNextYearFirst(Weekdate_fullFormat));
		System.out.println("获取明年最后一天日期:" + DateTimeUtil.getNextYearEnd(Weekdate_fullFormat));
        System.out.println(getTimestampString());
		System.out.println("获得本年有多少天:" + DateTimeUtil.getMaxYear());
		System.out.println("根据一个日期，返回是星期几的字符串:" + DateTimeUtil.getWeekWithDate("2010-12-31", date_sampleFormat));
		System.out.println("获取两个日期之间间隔天数:"+ DateTimeUtil.getIntervalWithTwoDate("2010-12-1", "2010-12-29",date_sampleFormat));
       	System.out.println("String字符格式化为日期:" + DateTimeUtil.strToDate("2010-8-9", date_sampleFormat));
		System.out.println("获取当前时间与月初相差数:" + DateTimeUtil.getMonthPlus() );
		System.out.println("判断是否是闰年:" + DateTimeUtil.isLeapYear(2010));
		System.out.println("获取所选月份所在季度第一天到最后一天:" + DateTimeUtil.getThisSeasonTime(1,Weekdate_fullFormat));
		System.out.println("获取近两天日期："+DateUtil.getLastTwoDays(Weekdate_fullFormat)[0]+"-"+DateUtil.getLastTwoDays(Weekdate_fullFormat)[1]);
		System.out.println("格式化日期为字符串:" + DateTimeUtil.format(new Date()));
		System.out.println("格式化日期为字符串:" + DateTimeUtil.format(new Date(),Weekdate_fullFormat));
		
		System.out.println("获取本月起止日期:" + DateUtil.getCurrentMonth(Weekdate_fullFormat)[0]+"  "+DateUtil.getCurrentMonth(Weekdate_fullFormat)[1]);
		*/
	}
	
	/**
	 * 将指定日期格式转换成不带秒的中文日期格式
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static String getSimpleChineseDateTimeWithoutSec(String sdate, String format){
		Date date = StringconvertDate(sdate, format);
		if(date != null){
			SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_CHINESE_NO_SECOND);
			return sdf.format(date);
		}
		return sdate;
	}
	
	
	public static List<String> getBetweenDate(String d1,String d2){
		List<String> list=new ArrayList<String>();
		
		try {
			GregorianCalendar[]	ga = getBetweenDate(d1, d2,date_sampleFormat);
			 for(GregorianCalendar e:ga)   
		        {   
				  String m="";
				  String d="";
		          int year=e.get(Calendar.YEAR);
		          int month=(e.get(Calendar.MONTH)+1);
		          int day=e.get(Calendar.DAY_OF_MONTH);
		          if(month<10){
		        	  m="0"+month;
		          }else{
		        	  m=""+month;
		          }
		          if(day<10){
		        	  d="0"+day;
		          }else{
		        	  d=""+day;
		          }
				 list.add(year+"-"+m+"-"+d );   
		        }   
		} catch (ParseException e1) {
			Log.i(TAG, "获取两个时间点之间的日期出错："+e1.getMessage());
			//e1.printStackTrace();
		}   
       
		
	  return list;	
	}
	// 把date类型转换成String类型
		public static String Date2String(java.util.Date date,String dateFormat) {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.format(date);
		}

	 public static GregorianCalendar[]  getBetweenDate(String d1,String d2,String format) throws ParseException   
	    {   
	        Vector<GregorianCalendar> v=new Vector<GregorianCalendar>();   
	        SimpleDateFormat  sdf=new SimpleDateFormat(format);   
	        GregorianCalendar gc1=new GregorianCalendar(),gc2=new GregorianCalendar();   
	        gc1.setTime(sdf.parse(d1));   
	        gc2.setTime(sdf.parse(d2));   
	        do{   
	            GregorianCalendar gc3=(GregorianCalendar)gc1.clone();   
	            v.add(gc3);   
	            gc1.add(Calendar.DAY_OF_MONTH, 1);                
	         }while(!gc1.after(gc2));   
	        return v.toArray(new GregorianCalendar[v.size()]);   
	    }       
	       
	 

	public static String[] getLastTwoDays(String format) {
		String[] str=new String[2];
		 Calendar c=Calendar.getInstance();
		 Date date=c.getTime();
		 str[0]=format(date,format);//当天时间
		 date = new Date(date.getTime() - 1000 * 60 * 60 * 24); 
		 str[1]=format(date,format); //昨天时间
		return str;
	}


	//获得本年有多少天  
	private static int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天  
		cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。  
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}
	//获得上年第一天的日期 *  
	public static String getPreviousYearFirst(String format) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		year--;
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DAY_OF_YEAR, 1);
		String str =format(c.getTime(),format);
		return str;
	}

	//获得上年最后一天的日期  
	public static String getPreviousYearEnd(String format) {
		weeks--;
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks+ (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		String preYearDay = format(yearDay,format);
		return preYearDay;
	}
	//获得明年最后一天的日期  
	public static String getNextYearEnd(String format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);//加一个年  
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = format(lastDate.getTime(),format);
		return str;
	}

	//获得明年第一天的日期  
	public static String getNextYearFirst(String format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);//加一个年  
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str =format(lastDate.getTime(),format);
		return str;

	}
	
	/**
	 * 获得本季度  起止日期
	 * @param int 月份
	 * @param String 格式化字符串
	 * @return String 
	 * 
	 */
	public static  String getThisSeasonTime(int month,String format) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];
		Calendar c = Calendar.getInstance();
		int years_value =c.get(Calendar.YEAR);
		int start_days = 1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);  
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/** 
	 * 获取某年某月的最后一天 
	 * @param year 年 
	 * @param month 月 
	 * @return 最后一天 
	 */
	private static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}
	/** 
	 * 是否闰年 
	 * @param year 年 
	 * @return  
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	//获得本年最后一天的日期 *  
	public static String getCurrentYearEnd(String format) {
		Calendar c=Calendar.getInstance();
		c.add(Calendar.YEAR, 1);
		c.set(Calendar.DAY_OF_YEAR, 0);
		String years =format(c.getTime(),format);
		return years;
	}
	//获得本年第一天的日期  
	public static String getCurrentYearFirst(String format) {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天 
		String preYearDay = format(cd.getTime(),format);
		return preYearDay;
	}
	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天  
		cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天  
		cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。  
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}
	
	//获得下个月第一天的日期  
	public static String getNextMonthFirst(String format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天   
		str = format(lastDate.getTime(),format);
		return str;
	}

	//获得下个月最后一天的日期  
	public static String getNextMonthEnd(String format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);//加一个月  
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天   
		lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天   
		str = format(lastDate.getTime(),format);
		return str;
	}
	//获得上月最后一天的日期  
	public static String getPreviousMonthEnd(String format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);//减一个月  
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天   
		lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天   
		str = format(lastDate.getTime(),format);
		return str;
	}
	// 上月第一天  
	public static String getPreviousMonthFirst(String format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号  
		lastDate.add(Calendar.MONTH, -1);//减一个月，变为下月的1号  
		//lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天  
		str = format(lastDate.getTime(),format);
		return str;
	}
	
	
	
	
	// 计算当月最后一天,返回字符串  
	public static String getLastDayOfMonth(String format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号  
		lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1号  
		lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天  
		str = format(lastDate.getTime(),format);
		return str;
	}
	//获取当月第一天  
	public static String getFirstDayOfMonth(String format) {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号  
		str = format(lastDate.getTime(),format);
		return str;
	}

	// 获得下周星期一的日期  
	public static String getNextMonday(String format) {
		weeks++;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		String preMonday = format(monday,format);
		return preMonday;
	}
	// 获得下周星期日的日期  
	public static String getNextSunday(String format) {

		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		String preMonday = format(monday,format);
		return preMonday;
	}

	// 获得上周星期一的日期  
	public static String getPreviousWeekday(String format) {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		String preMonday = format(monday,format);
		return preMonday;
	}
	// 获得上周星期日的日期  
	public static String getPreviousWeekSunday(String format) {
		weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		String preMonday = format(monday,format);
		return preMonday;
	}
	// 获得本周星期日的日期    
	public static String getCurrentWeekSunday(String format) {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		String preMonday = format(monday,format);
		return preMonday;
	}

	
	/**
	 * 获取当天时间
	 * 
	 */
	public static Date getCurrntDate(){
		Calendar c=Calendar.getInstance();
		return c.getTime();
	}

	/**
	 * 获取指定格式的当前时间
	 * @param String format 格式化字符串 例如:yyyy-MM-dd 
	 * @return string
	 */
	public  static String getFromatCurrentDate(String format){
		 Calendar c=Calendar.getInstance();
		 Date date=c.getTime();
		 return  format(date,format);
	}
	/**
	 * 获取本周起止时间
	 * @param String format 格式化字符串 例如:yyyy-MM-dd
	 * @return string[]
	 */
 public static String[] getCurrntWeek(String format){
	     String[] str=new String[2];
		Calendar c=Calendar.getInstance();
		int xq=c.get(Calendar.DAY_OF_WEEK);

		if(xq==1)
		{
			c.add(Calendar.DATE,-1);		
		}
		xq=c.get(Calendar.DAY_OF_WEEK);
		
		c.set(Calendar.DAY_OF_WEEK, 1);
		c.add(Calendar.DATE, 1);
		
		str[0]=format(c.getTime(),format);
		c.add(Calendar.DATE, 6);
		str[1]=format(c.getTime(),format);
		return str;
	}

	/**
	 * 获取上周起止时间
	 * @param String format 格式化字符串 例如:yyyy-MM-dd 
	 * @return string[]
	 */
	public static String[] getLastWeek(String format)
	{
		String[] str=new String[2];
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DATE,-7);
		int xq=c.get(Calendar.DAY_OF_WEEK);
		if(xq==1)
		{
			c.add(Calendar.DATE,-1);		
		}
		xq=c.get(Calendar.DAY_OF_WEEK);
		
		c.set(Calendar.DAY_OF_WEEK, 1);
		c.add(Calendar.DATE, 1);
		
		str[0]=format(c.getTime(),format);

		c.add(Calendar.DATE, 6);
		str[1]=format(c.getTime(),format);
		return str;
	}
	
	/**
	 * 获取近两周起止时间
	 * @param String format 格式化字符串 例如:yyyy-MM-dd 
	 * @return string[]
	 */
	public static String[] getRecentTwoWeek(String format)
	{
		String[] str=new String[2];
		Calendar c=Calendar.getInstance();
		int xq=c.get(Calendar.DAY_OF_WEEK);
		if(xq==1)
		{
			c.add(Calendar.DATE,-1);		
		}
		xq=c.get(Calendar.DAY_OF_WEEK);
		
		c.set(Calendar.DAY_OF_WEEK, 1);
		c.add(Calendar.DATE, -6);
		
		str[0]=format(c.getTime(),format);

		c.add(Calendar.DATE, 13);
		str[1]=format(c.getTime(),format);

		return str;
	}
	/**
	 * 获取本月起止时间
	 * @param String format 格式化字符串 例如:yyyy-MM-dd
	 * @return string[]
	 */
	public static String[] getCurrentMonth(String format){
		String[] str=new String[2];
		Calendar c=Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);		
		str[0]=format(c.getTime(),format);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		str[1]=format(c.getTime(),format);

		return str;
	}
	/**
	 * 获取上月起止时间
	 * @param String format 格式化字符串 例如:yyyy-MM-dd
	 * @return String[]
	 */
	public static String[] getLastMonth(String format)
	{
		String[] str=new String[2];
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		str[0]=format(c.getTime(),format);

		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		str[1]=format(c.getTime(),format);
		return str;
	}
	
	/**
	 * 格式化时间公共方法
	 * @param Date date 日期 按照yyyy-MM-dd格式进行格式化
	 * @return String
	 */
	public static String format(Date date){
		return format(date,null);
	}
	/**
	 * 格式化时间公共方法
	 *  @param Date date 日期 
	 *  @param String format 格式化字符串 例如:yyyy-MM-dd
	 *  @return String
	 */
	public static String format(Date date,String format){
		if(date!=null&&!date.equals(null)&&!isBlank(format)){
			format=date_sampleFormat;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null&&!date.equals(null)){
			return sdf.format(date);
		}else{
			return null;
		}
	
	}
	/**
	 * 校验空串,只校验null,""两中情况
	 * @param String 
     * @return boolean
	 */
	public static boolean isBlank(String v){
		if(v!=null&&!v.equals("")){
			return true;
		}else{
			return false;
		}
	}
	//获得本周一的日期  
	public static  String getMondayOFWeek(String format) {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		String preMonday = format(monday,format);
		return preMonday;
	}
	private static int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);//把日期设置为当月第一天   
		cd.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天   
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	
	/**
	 * 获得当前日期与本周日相差的天数 
	 */ 
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......  
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; //因为按中国礼拜一作为第一天所以这里减1  
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}
	 /**
     * 根据一个日期，返回是星期几的字符串
     * 说明： 参数传入要对应，传入什么格式的日期字符，后面跟的格式化字符串要和日期对应
     * @param String sdate
     * @param String fromat
     * @return String
     */  
	public static String getWeekWithDate(String sdate,String fromat) {   
	    // 再转换为时间   
	     Date date = strToDate(sdate,fromat);   
	     Calendar c = Calendar.getInstance();   
	     c.setTime(date);   
	    // int hour=c.get(Calendar.DAY_OF_WEEK);   
	    // hour中存的就是星期几了，其范围 1~7   
	    // 1=星期日 7=星期六，其他类推   
	    return new SimpleDateFormat("EEEE").format(c.getTime());   
	}

	/**
	 * 根据传入的传入日期和日期格式，得到指定日期的星期X
	 * @param sdate
	 * @param srcFmt
	 * @return
	 */
	public static String getWeekDay(String sdate,String srcFmt){
		if(!isBlank(srcFmt)){
			srcFmt=date_sampleFormat;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(week_fromat,Locale.CHINESE);
		try{
			Date date = strToDate(sdate, srcFmt);
			return sdf.format(date);
		} catch (Exception e) {
			return sdate;
		}
	}
	   /**
     * 得到二个日期间的间隔天数
     * @param String 日期1
     * @param String 日期2
     * @return String 
     */  
	public static String getIntervalWithTwoDate(String sj1, String sj2,String format) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(format);
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (mydate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}   
	 /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param String strDate
     * @param String format
     * @return Date
     */  
	public static Date strToDate(String strDate,String format) {   
	     SimpleDateFormat formatter = new SimpleDateFormat(format);   
	     ParsePosition pos = new ParsePosition(0);   
	     Date strtodate = formatter.parse(strDate, pos);   
	    return strtodate;   
	 }   

	
	public static Date getDelayTime(){
		Calendar calendar = Calendar.getInstance();
	      calendar.add(Calendar.MINUTE,1);
	      Date nextDate=calendar.getTime();
	      return nextDate;
	}
	// 获取当前时间
	public static String getyyyyMMddCurrentTime() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		return s.format(date);
	}

	public static String getyyyyMMddCurrentTime(java.util.Date date) {

		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		return s.format(date);
	}
	/**
	 * 
	 * getCurrntDate:获取当前时间延时多少分钟的时间
	 * @return 
	 * @return Date
	 */
	public  String getLaterMinuteTime(int min){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MINUTE, min);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());

	} 
	 
	public static java.util.Date getDateCurrentTime() {
		java.util.Date date = new java.util.Date();
		return date;
	}

	public static String getCurrentTime() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return s.format(date);
	}

	// 把String类型转换成java.util.date
	public static java.util.Date StringToDate(String str) {
		DateFormat df = DateFormat.getDateInstance();
		java.util.Date date = null;
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// 把String类型转换成java.util.Date类型
	public static java.util.Date StringconvertDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		java.util.Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return date;
	}
	// 把String类型转换成java.util.Date类型
	public static java.util.Date StringconvertDate(String str,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date date = null;
		try {
			if(isNotBlank(str)){
				date = sdf.parse(str);
			}
		
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return date;
	}
	// 把date类型转换成String类型
	public static String DateConvertString(java.util.Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}

	// 把date类型转换成String类型
	public static String Convert() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(date);
	}
	/**
	 * 
	 * getCurrentTimeStam:获取当前时间戳
	 * @return 
	 * @return String
	 */
	public static String getCurrentTimeStam(){
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmssms");
		return dateFm.format(new java.util.Date()) + "_"+ System.nanoTime();
	}
	/**
	 * 
	 * isNotBlank:判断一个字符串不为空
	 * @param p
	 * @return 
	 * @return boolean
	 */
	public  static boolean isNotBlank(String p){
		if (p != null && p != "" && !p.equals(null) && !p.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getTimestampString()
	{
		return new Timestamp(System.currentTimeMillis()).toString().replace(" ", "").replace("-", "").replace(":", "").replace(".", "");
	}
	//获取当月第一天  
    public static Date lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
   
    /** 
	   * 得到几天前的时间 
	   * @param d 
	   * @param day 
	   * @return 
	   */  
	  public  static Date getDateBefore(Date d,int day){  
	   Calendar now =Calendar.getInstance();  
	   now.setTime(d);  
	   now.set(Calendar.DATE,now.get(Calendar.DATE)-day);  
	   return now.getTime();  
	  }  
	    
	  /** 
	   * 得到几天后的时间 
	   * @param d 
	   * @param day 
	   * @return 
	   */  
	  public static Date getDateAfter(Date d,int day){  
	   Calendar now =Calendar.getInstance();  
	   now.setTime(d);  
	   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);  
	   return now.getTime();  
	  }  
	  
	/**
	 * 
	 * @param day 日期
	 * @param x 加多少小时
	 * @return
	 */
			public static String addDateMinut(String day, int x,String formatStr)//返回的是字符串型的时间，输入的
			//是String day, int x
			 {   
			        SimpleDateFormat format = new SimpleDateFormat(formatStr);// 24小时制  
			//引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
			//量day格式一致
			        Date date = null;   
			        try {   
			            date = format.parse(day);   
			        } catch (Exception ex) {   
			            ex.printStackTrace();   
			        }   
			        if (date == null)   
			            return "";   
			        System.out.println("front:" + format.format(date)); //显示输入的日期  
			        Calendar cal = Calendar.getInstance();   
			        cal.setTime(date);   
			        cal.add(Calendar.HOUR, x);  
			        date = cal.getTime();   
			        System.out.println("after:" + format.format(date));  //显示更新后的日期 
			        cal = null;   
			        return format.format(date);   
			  
			    }  
			
			   /**
		     * 得到二个日期间的间隔小时数
		     * @param String 日期1
		     * @param String 日期2
		     * @return String 
		     */  
			public static Double getIntervalHourWithTwoDate(String sj1, String sj2,String format) {
				SimpleDateFormat myFormatter = new SimpleDateFormat(format);
				double day = 0;
				try {
					Date date = myFormatter.parse(sj1);
					Date mydate = myFormatter.parse(sj2);
					day = (mydate.getTime() - date.getTime()) / ( 60.0 * 60 * 1000);
				} catch (Exception e) {
					return null;
				}
				return day;
			}

}
