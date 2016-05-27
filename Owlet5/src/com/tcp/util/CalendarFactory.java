package com.tcp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import com.tcp.logger.LoggerConfig;

/**
 * Java Calendar 관련해서 만든 Util
 * 
 * 날짜를 집어넣어야 할 경우 여기서 작업해서 넣도록 하자.
 * 
 * @author blessldk
 *
 */
public class CalendarFactory {

	private static CalendarFactory instance=null;

	private SimpleDateFormat dateFormatWithTime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.S");

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");

	private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyMMdd");

	private SimpleDateFormat dateFormatHan = new SimpleDateFormat("E MM월 dd일");

	private SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");

	private SimpleDateFormat dateParser2 = new SimpleDateFormat("yyyyMMdd");

	private SimpleDateFormat dateParser3 = new SimpleDateFormat("MM/dd/yyyy");

	private SimpleDateFormat hipeDataFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private SimpleDateFormat dateFormatWithStop = new SimpleDateFormat("yyyy.MM.dd");

	public static CalendarFactory getInstance(){
		if(instance==null){
			instance= new CalendarFactory();
		}
		return instance;
	}

	/**
	 * 현재시간을 포멧에 맞춰 사용하고자 할 때 쓰는 메소드
	 * @param int index (0 :day 1:Time 2:dayCustom (yyMMdd) 3:dayCustom2 (yyyy-MM-dd) )
	 * @return String currentTime 
	 */
	public String setCurrentDateTime(int index){
		Date today = Calendar.getInstance().getTime();

		String now = "";

		switch(index){
		case 0:
			now = dateFormat.format(today);
			break;
		case 1:
			now = dateFormatWithTime.format(today);
			break;
		case 2:
			now = dateFormat2.format(today);
			break;
		case 3:
			now = hipeDataFormat.format(today);
			break;
		case 4:
			now = dateParser2.format(today);
			break;
		}
		return now;
	}

	/**
	 * 일자로 들어오는 Data 중
	 * 구간별로 구분하여 처리 한다.
	 * @param date yyyyMMdd
	 * @param period 0:1일 1:1주 2:1월
	 * @param index
	 * @return
	 * @throws ParseException 
	 */
	public String setCalcDateTime(String date, int period) {
		Calendar cal=Calendar.getInstance();
		try{
			Date calcDate=dateFormat2.parse(date);
			
			cal.setTime(calcDate);
			
			switch(period){
			case 0:
				cal.add(Calendar.DAY_OF_MONTH, -1);
				break;
			case 1:
				cal.add(Calendar.WEEK_OF_MONTH, -1);
				break;
			case 2: 
				cal.add(Calendar.MONTH,-1);
				break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		String resultDate=dateParser2.format(cal.getTime());

		return resultDate;
	}

	/**
	 * DB에 있는 Data Parser용 ..딴용으로 쓸수가 없어 이런 젠장..망할..우라질..
	 * @param int index (0 : day 1: Time 2:한글 (요일 월 일)
	 * @return String currentTime 
	 * @throws ParseException 
	 */
	public String setCustomDateTime(String date, int index){
		try{
			Date customDate=dateParser.parse(date);
			String resultDate="";

			switch(index){
			case 0:
				resultDate = dateFormat.format(customDate);
				break;
			case 1:
				resultDate = dateFormatWithTime.format(customDate);
				break;
			case 2:
				resultDate = dateFormatHan.format(customDate);
				break;
			case 3:
				resultDate = hipeDataFormat.format(customDate);
				break;
			case 4:
				resultDate = dateFormatWithStop.format(customDate);

			}
			return resultDate;
		}catch(Exception e){
			LoggerConfig.getEngineLogger().debug(e.getMessage());

			return "1970-01-01";
		}
	}

	/**
	 * 파서는 이걸로 끝내자..
	 * date값은 parseType에 허용되는 것으로 넣어줘야 한다.
	 *  
	 * @param date 
	 * @param index 0: yy/MM/dd 1: yyyy/MM/dd hh:mm:ss.S 2: E MM월 dd일 3: yyyy-MM-dd
	 * @param parseType 0: yyyy-MM-dd hh:mm:ss 1: yyyyMMdd 2: MM/dd/yyyy 
	 * @return
	 * @throws ParseException
	 */
	public String setParseDateFormat(String date, int index , int parseType) {
		try{
			Date customDate;
			switch(parseType){
			case 0:
				customDate=dateParser.parse(date);
				break;
			case 1:
				customDate=dateParser2.parse(date);
				break;
			case 2:
				customDate=dateParser3.parse(date);
				break;
			default:
				customDate=dateParser.parse(date);
				break;
			}
			String resultDate="";

			switch(index){
			case 0:
				resultDate = dateFormat.format(customDate);
				break;
			case 1:
				resultDate = dateFormatWithTime.format(customDate);
				break;
			case 2:
				resultDate = dateFormatHan.format(customDate);
				break;
			case 3:
				resultDate = hipeDataFormat.format(customDate);
				break;

			}
			return resultDate;
		}
		catch(Exception e){
			LoggerConfig.getEngineLogger().debug("ParseException : " + e.getMessage());

			return setCurrentDateTime(3);
		}
	}

	public boolean isAdmitDate(String date){
		char[] data = date.substring(0 ,3).toCharArray();

		int charSum = data[0] + data[1] +data[2];

		if(charSum < 145){
			LoggerConfig.getWebLogger().debug("Date ParseException : "+ date);

			return false;
		}else{
			return true;
		}
	}

	public Date getDate(String date , int type) throws ParseException{
		Date customDate=null;
		switch(type){
		case 0:
			customDate=dateParser2.parse(date);
			break;
		}

		return customDate;
	}

	public static void main(String[] args) throws ParseException {
		String date ="20141103";

		Date dates = CalendarFactory.getInstance().getDate(date, 0);

		Calendar cal = Calendar.getInstance();

		cal.setTime(dates);

		System.out.println(dates);

		cal.add(cal.MONTH, -1);

		System.out.println(CalendarFactory.getInstance().dateParser2.format(cal.getTime()));

	}
}
