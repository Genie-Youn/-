package com.tcp.util;

import java.util.HashMap;
import java.util.Hashtable;

import com.tcp.owlets.bean.AdderInfo;

public class AppManager {

	private static AppManager instance=null;
	
	/**Frame Type 값  Defalut :0**/
	public static final int FRAME_TYPE_DEFAULT=0; 
	/**Frame Type 값  FrameList :1**/
	public static final int FRAME_TYPE_FRAMELIST=1;
	/**Frame Type 값  Store :2**/
	public static final int FRAME_TYPE_STORE=2;
	/**Frame Type 값  Coupon :3**/
	public static final int FRAME_TYPE_COUPON=3;

	/**Report Type Visit :0 **/
	public static final int REPORT_VISITCOUNT=0;
	/**Report Type Visitor :1 **/
	public static final int REPORT_VISITORCOUNT=1;
	/**Report Type DownCount :2 **/
	public static final int REPORT_DOWNCOUNT=2;

	/**App 정보 담을  App Key , AppIndex**/
	private HashMap<Integer, Integer> appHashMap= new HashMap<Integer, Integer>();
	
	public static AppManager getInstance(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	
	public void addApp(int storeCode , int appIndex){
		appHashMap.put(storeCode, appIndex);
	}
	
	public HashMap<Integer, Integer> getAppMap(){
		return this.appHashMap;
	}
	
	
	public int findAppIndex(int storeCode){
		int code=0;
		if(appHashMap.containsKey(storeCode)){
			code=appHashMap.get(storeCode);
		}
		return code;
	}
}


