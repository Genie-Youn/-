package com.tcp.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import com.tcp.owlets.bean.AdderInfo;

public class DataParser {

	private static DataParser instance=null;
	
	private String addrName;
	
	public static DataParser getInstance(){
		if(instance==null){
			instance=new DataParser();
		}
		return instance;
	}
	
	public void setAddressName(int addrCode){
		String name;
		switch(addrCode){
		default:
			name=ResManager.getString("Store.addr.Seoul");
			break;
		case 1:
			name=ResManager.getString("Store.addr.Seoul");
			break;
		case 2:
			name=ResManager.getString("Store.addr.Kyeonggi");
			break;
		case 3:
			name=ResManager.getString("Store.addr.kwangwon");
			break;
		case 4:
			name=ResManager.getString("Store.addr.Chung");
			break;
		case 5:
			name=ResManager.getString("Store.addr.Jeolla");
			break;
		case 6:
			name=ResManager.getString("Store.addr.Gyeong");
			break;
		case 7:
			name=ResManager.getString("Store.addr.Jeju");
			break;
		}
		this.addrName=name;
	}
	public String getAddressName(){
		return this.addrName;
	}
	
	/**
	 * 이것도 허접한 방법이지만... 이방법 밖에 떠오르질 안하 ㅠㅠ
	 * @param data
	 * @return
	 */
	public int[] configData(String data){
		int[] array;
		
		String[] datas=data.split(",");
		
		array=new int[datas.length];
		
		for(int i=0; i< datas.length-1 ;i++){
			array[i]=Integer.parseInt(datas[i]);
		}
		
		return array;
	}
	
	/**
	 * 허접한 방법이지만... GroupIndex를 가져올 수 있는 방법
	 * 지금 떠오르는게 이거밖에 없어....
	 * @param data
	 * @return
	 */
	public int configGroupIndex(String data){
		String[] datas=data.split(",");
		
		int index=Integer.parseInt(datas[datas.length-1]);
		
		return index;
	}
	
	/*public static void main(String[] args) throws UnsupportedEncodingException {
//		DataParser.getInstance().setAddressName(1);
//		
//		String addr = DataParser.getInstance().getAddressName();
//		
//		System.out.println(addr);
//		
//		System.out.println(DataParser.getInstance().getAddressName());
		
		HashMap<Integer, ArrayList<AdderInfo>> map=new HashMap<Integer, ArrayList<AdderInfo>>();
		
		ArrayList<AdderInfo> list = new ArrayList<AdderInfo>();
		
		for(int i=0; i < 10; i++){
			AdderInfo bean=new AdderInfo();
			
			list.clear();
			
			bean.setnIndex(i+1);
			
			list.add(bean);
			
			map.put(i, list );
		}
		
		for(int data : map.keySet()){
			System.out.println(data + " : " + map.get(data).get(0).getnIndex());
		}
		
	}*/
}
