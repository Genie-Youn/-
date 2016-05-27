package com.tcp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.tcp.owlets.bean.CalendarInfo;

/**
 * 
 * 자료구조매니저
 * Calendar나 다른 SqlMapper에서 계산해야 하는 상황이 많을 경우
 * 여기서 처리해서 넘기자.
 * 
 * 다양한 자료구조 활용할 사항이 있으면
 * 
 * 여기서 getter setter만들어서 활용하기 바람.
 * @author captainahn
 *
 */
public class DataStructureManager {

	private static DataStructureManager instance;
	
	private ArrayList<CalendarInfo> list = new ArrayList<CalendarInfo>();

	public static DataStructureManager getInstance(){
		if(instance==null){
			instance= new DataStructureManager();
		}
		return instance;
	}
	
	public ArrayList<CalendarInfo> mergeCalendarData(
			HashMap<Integer, CalendarInfo> map, ArrayList<CalendarInfo> sub) {
		for(CalendarInfo bean : sub){
			if(map.containsKey(bean.getnIndex())){
				map.get(bean.getnIndex()).setContents(bean.getContents());
			}
		}
		setCalendarList(map);
		
		return getList();
	}
	
	private void setCalendarList(HashMap<Integer, CalendarInfo> dataMap){
		list.clear();
		
		Iterator<Integer> it = dataMap.keySet().iterator();
		
		while(it.hasNext()){
			int key = it.next();
			
			list.add(dataMap.get(key));
		}
	}
	public ArrayList<CalendarInfo> getList() {
		return list;
	}
	
	
}
