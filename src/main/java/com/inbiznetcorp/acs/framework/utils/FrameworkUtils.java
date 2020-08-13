package com.inbiznetcorp.acs.framework.utils;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

import com.inbiznetcorp.acs.framework.beans.FrameworkBeans;

/**
 * @FileName : FrameworkUtils.java
 * @Project  : o2o-ui-auth-request
 * @Date     : 2016. 6. 12.
 * @작성자   : hsjeon1224(효성)
 * @변경이력 :
 * @프로그램 설명 :
 */
public class FrameworkUtils extends StringUtils{

	public final static String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";

	/// Constants
    final static LocalDateTime kBaseTime = LocalDateTime.of(2015, 1, 1, 0, 0, 0);
    private static int mSequenceNumber = 0;

    private static int   mCallCnt				= 0;

    public synchronized static int getCallCnt() {
    	System.out.println("mCallCnt == " +mCallCnt);
    	return mCallCnt++;
    }

    public static Long iCallCookieTime()
    {
		SimpleDateFormat f  	= new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
		Date 			 d1 	= null;
		Date 			 d2 	= null;
		long 			 diff 	= 0l;
		long 			 sec 	= 0l;

		try
		{
			d1 		= f.parse("23:59:59");
			d2 		= f.parse(FrameworkUtils.getCurrentDateHour("HH:mm:ss"));
			diff 	= d1.getTime() - d2.getTime();
			sec 	= diff / 1000;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return sec;
    }

	public static String dateDate(int aGo,  String strDateFormat)
	{
		SimpleDateFormat sdformat = new SimpleDateFormat(strDateFormat);
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());
		cal.add(Calendar.DATE, aGo);

		return sdformat.format(cal.getTime());
	}


    public static synchronized String generateSessionID()
    {
        String sessionID = "";
        LocalDateTime currentDate = LocalDateTime.now();
        long diffInSeconds = ChronoUnit.SECONDS.between(kBaseTime, currentDate);
        int currentSequenceNumber = mSequenceNumber;

        diffInSeconds &= 0xffffffff;

        mSequenceNumber++;
        mSequenceNumber %= 10000;

        sessionID = String.format("%08x", diffInSeconds)
                + String.format("%04d", currentSequenceNumber)
                + String.valueOf((char)((int)(Math.random()*26)+97));

        return sessionID;
    }

//	String[] mInnerIPAddress = new String[]{"182.172.49.24", "192.168.0.44", "192.168.3.45", "192.168.3.16", "192.168.3.1", "220.73.45.183",  "192.168.10.27"};
	String[] mInnerIPAddress = new String[]{};
	/**
	 * @Method Name  : convCamelCase
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 :
	 * @param _str
	 * @return
	 */
	public static String convCamelCase(String _str){
		String newColumnName=null;
		if(_str.indexOf("_")==-1){
			if(_str.matches("(.+)?[A-Z](.+)?")&&_str.matches("(.+)?[a-z](.+)?")) return _str;
			return _str.toLowerCase();
		}else{
			StringBuffer sb=new StringBuffer();
			boolean isFirst=true;
			StringTokenizer tokenizer=new StringTokenizer(_str,"_");
			while(tokenizer.hasMoreTokens()){
				if(isFirst) sb.append(tokenizer.nextToken().toLowerCase());
				else{
					sb.append(StringUtils.capitalize(tokenizer.nextToken().toLowerCase()));
				}
				isFirst=false;
			}
			newColumnName=sb.toString();
		}
		return newColumnName;
	}

	/**
	 * <pre>
	 * @Method Name  : unescapeHtml
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 :
	 * @param values
	 * @return
	 * </pre>
	 */
	public static String unescapeHtml(String values){
		if(values==null) return "";
		values=values.replaceAll("&lt;","<");
		values=values.replaceAll("&gt;",">");
		values=values.replaceAll("&amp;","&");
		values=values.replaceAll("&#38;","&");
		values=values.replaceAll("&quot;","\"");
		values=values.replaceAll("&#34;","\"");
		values=values.replaceAll("&#39;","'");
		values=values.replaceAll("&#36;","\\$");
		return values;
	}

	/**
	 * <pre>
	 * @Method Name  : escapeHtml
	 * @작성일    : 2016. 6. 12.
	 * @작성자    : hsjeon1224(효성)
	 * @변경이력  :
	 * @Method 설명 :
	 * @param values
	 * @return
	 * </pre>
	 */
	public static String escapeHtml(String values){
		if(values==null) return "";
		values=values.replaceAll("<(no)?script[^>]*>.*?</(no)?script>","");
		values=values.replaceAll("<style[^>]*>.*</style>","");
		values=values.replaceAll("&(?![#]?[a-z0-9]+;)","&#38;");
		values=values.replaceAll("<","&lt;");
		values=values.replaceAll(">","&gt;");
		values=values.replaceAll("\"","&#34;");
		values=values.replaceAll("'","&#39;");
		values=values.replaceAll("\\$","&#36;");
		return values;
	}

	 public static boolean isNull(String str) {
        if (str == null || "".equals(str) || str.trim().length() == 0)
            return true;
        else
            return false;
    }
	/////////////////////////////////////////////////////////////////////////////////////////
	//	   Array
    /////////////////////////////////////////////////////////////////////////////////////////
	public static int inArray(String[] arrStr,String str)
	 {
		if(str==null) return -1;

		for(int i=0;i<arrStr.length;i++)
		{
			if(arrStr[i].trim().equals(str.trim())) return i;
		}
		return -1;
	}
    /////////////////////////////////////////////////////////////////////////////////////////
	//		Date
	/////////////////////////////////////////////////////////////////////////////////////////

	public static String getDateToStr(Date date, String dateFormat)
	{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
	}

	public static String getDateToStr(String dateFormat)
	{
		return getDateToStr(new Date(), dateFormat);
	}

	public static String getDateToStr()
	{
		return getDateToStr(new Date(), DEFAULT_DATE_FORMAT);
	}


    public static boolean isNotNull(String str) {
        return !isNull(str);
    }
	public static int nvl(String i,int def){
		return(i==null?def:FrameworkUtils.toInt(i));
	}
	public static int nvl(Object i,int def){
		return(i==null?def:FrameworkUtils.toInt(i));
	}
	public static int nvl(int i,int def){
		return(i==0?def:i);
	}
	public static long nvl(long i,long def){
		return(i==0?def:i);
	}
	public static String nvl(String str,String def){
		str=str==null?"":str;
		return(str.equals("")?def:str.trim());
	}
	public static String nvl(Object object,String def){
		String str=object==null?"":object.toString();
		return(str.equals("")?def:str.trim());
	}
	public static String nvl(int object,String def){
		return object==0?def:FrameworkUtils.toString(object);
	}
	public static Object NVL(Object object,Object def){
		return object==null?def:object;
	}
	public static int toInt(String s){
		try{s=FrameworkUtils.nvl(s,"0");return Integer.parseInt(s);}catch(Exception e){return -1;}
	}
	public static int toInt(Long s){
		return Integer.parseInt(FrameworkUtils.toString(Math.round(s)));
	}
	public static int toInt(Double s){
		return Integer.parseInt(FrameworkUtils.toString(Math.round(s)));
	}
	public static int toInt(Object s){
		s=FrameworkUtils.nvl(s,"0");return toInt(s.toString());
	}
	public static int toInt(Object s,int def){
		s=FrameworkUtils.nvl(s,def);return toInt(s.toString());
	}
	public static String toString(Object i){
		try{return String.valueOf(i);}catch(Exception e){return "";}
	}
	public static String toString(Object i,String def){
		try{return String.valueOf(i);}catch(Exception e){return def;}
	}
	public static List<String> diffDates(String startDate, String endDate)
	{
		List<String> list = new ArrayList<String>();
		String s1=startDate.replaceAll("[^\\d]", "");
		String s2=endDate.replaceAll("[^\\d]", "");

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat df1 = new SimpleDateFormat("yyyyMMdd");

		//Date타입으로 변경

		Date d1 = null;
		Date d2 = null;
		try
		{
			d1 = df.parse( s1 );
			d2 = df.parse( s2 );
		}
		catch (java.text.ParseException e)
		{
			e.printStackTrace();
		}

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime( d1 );
		c2.setTime( d2 );

		while( c1.compareTo( c2 ) !=1 )
		{
			list.add(df1.format(c1.getTime()));
			//시작날짜 + 1 일
			c1.add(Calendar.DATE, 1);
		}
		return list;
	}

	public boolean isInner()
	{
		String ip = null;
		ip = FrameworkBeans.findHttpServletBean().getHttpServletRequest().getRemoteAddr();
        if ( FrameworkUtils.inArray(mInnerIPAddress, ip) > -1 ){return true;}
        return false;

	}

	public static String vmTableQueryHour(String getsStartDate, String getsEndDate) {
		boolean isAsName   = false;
		int		listInex   = 0;
		List<String> listStrDates  = diffDates2(getsStartDate, getsEndDate);
		List<String> listSelects   = new ArrayList<>();
		StringBuffer vmTablesQuery = new StringBuffer();

		vmTablesQuery.append(" select DATE_FORMAT(TARGETDATE,'%Y-%m-%d %H') AS  TARGETDATE  from ( ");

		for (String strDate : listStrDates)
		{
			listInex++;
			for (int i = 0; i < 24; i++)
			{
				String hour = String.format("%02d", i);
				String fullDate = strDate+" "+hour;


				int targetDate = Integer.valueOf(strDate.replaceAll("-", "").replaceAll("\\s", "")+hour);
				int currentDate = Integer.valueOf(aGoHourDate(-1));

				if ( targetDate > currentDate )
				{
					break;
				}

				if ( targetDate == currentDate )
				{
					if ( 25 > Integer.valueOf(getCurrentDateHour("mm")) )
					{
						break;
					}
				}

				if(isAsName == false)
				{
					listSelects.add(" select '"+fullDate+"' as TARGETDATE union");
					isAsName = true;
				}
				else
				{
					listSelects.add(" select '"+fullDate+"'  union ");
				}
			}
		}

		for (int i = 0; i < listSelects.size(); i++)
		{
			String selectDate = listSelects.get(i);
			if ( i ==  listSelects.size() -1 )
			{
				selectDate = selectDate.replaceAll("union", "");
			}
			vmTablesQuery.append(selectDate);
		}


		vmTablesQuery.append(" ) as b");

		return vmTablesQuery.toString();
	}
	public static String getCurrentDateHour(String strFormat)
	{
		SimpleDateFormat dayTime = new SimpleDateFormat(strFormat);
		return dayTime.format(new Date());
	}

	public static String getCurrentDate(String strFormat)
	{
		SimpleDateFormat dayTime = new SimpleDateFormat(strFormat);
		return dayTime.format(new Date());
	}

	public static String aGoHourDate(int aGo)
	{
    	SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMddHH");
    	Calendar cal = Calendar.getInstance();

    	cal.setTime(new Date());
    	cal.add(Calendar.HOUR, aGo);

    	return sdformat.format(cal.getTime());
	}

	public static String minuteDate(int aGo)
	{
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, aGo);

		return sdformat.format(cal.getTime());
	}

	public static String secondDate(int aGo, String strDate)
	{
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal = Calendar.getInstance();

		try
		{
			cal.setTime(sdformat.parse(strDate));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		cal.add(Calendar.SECOND, aGo);

		return sdformat.format(cal.getTime());
	}

	public static List<String> diffDates2(String startDate, String endDate)
	{
		List<String> list = new ArrayList<String>();
		String s1=startDate;
		String s2=endDate;

		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

		//Date타입으로 변경

		Date d1 = null;
		Date d2 = null;
		try
		{
			d1 = df.parse( s1 );
			d2 = df.parse( s2 );
		}
		catch (java.text.ParseException e)
		{
			e.printStackTrace();
		}

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime( d1 );
		c2.setTime( d2 );

		while( c1.compareTo( c2 ) !=1 )
		{
			list.add(df1.format(c1.getTime()));
			//시작날짜 + 1 일
			c1.add(Calendar.DATE, 1);
		}
		return list;
	}


	public static String timestampToStrDate(Long strTimestamp)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		Timestamp ts=new Timestamp((strTimestamp*1000));
		Date date=new Date(ts.getTime());

		return simpleDateFormat.format(date);
	}

	public static String getDateDay(String date, String dateType)
	{


	    String day = "" ;

	    SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
	    Date nDate = null;
		try {
			nDate = dateFormat.parse(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    Calendar cal = Calendar.getInstance() ;
	    cal.setTime(nDate);

	    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;

	    switch(dayNum){
	        case 1:
	            day = "일";
	            break ;
	        case 2:
	            day = "월";
	            break ;
	        case 3:
	            day = "화";
	            break ;
	        case 4:
	            day = "수";
	            break ;
	        case 5:
	            day = "목";
	            break ;
	        case 6:
	            day = "금";
	            break ;
	        case 7:
	            day = "토";
	            break ;

	    }
	    return day ;
	}

	public static String vmTableQueryCheck(String[] arr) {
		StringBuffer vmTablesQuery = new StringBuffer();

		vmTablesQuery.append(" select SEQIVRLOG from ( ");

		if( arr.length == 0)
		{
			vmTablesQuery.append(" select 0 as SEQIVRLOG ");
		}
		else
		{
			for (int i = 0; i < arr.length; i++)
			{
				if( i == (arr.length -1 ))
				{
					vmTablesQuery.append(" select 	"+arr[i]+" as SEQIVRLOG ");
				}
				else
				{
					vmTablesQuery.append(" select 	"+arr[i]+" as SEQIVRLOG union ");
				}
			}
		}

		vmTablesQuery.append(" ) as b");

		return vmTablesQuery.toString();
	}
	
	
	public static String exceptionToString(Throwable e)
	{
		StringBuffer 		sb 	 = new StringBuffer();
		StackTraceElement[] elem = e.getStackTrace();
		
        for ( int i = 0; i < elem.length; i++ )
        {
        	sb.append("\n"+elem[i]);
        }
        return sb.toString();
	}

}
