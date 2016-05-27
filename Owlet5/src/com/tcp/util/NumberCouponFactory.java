package com.tcp.util;

import java.util.ArrayList;
import java.util.UUID;

public class NumberCouponFactory {

	private static NumberCouponFactory instance=null;
	
	private ArrayList<String> couponList = new ArrayList<String>();
	
	public static NumberCouponFactory getInstance(){
		if(instance==null){
			instance= new NumberCouponFactory();
		}
		return instance;
	}
	
	/**
	 * 수량만큼 Coupon 숫자쿠폰 발급함
	 * @param quantity
	 */
	public void setCouponGenerator(int quantity){
//		System.out.println(quantity);
		couponList.clear();
		
		for(int i=0; i <quantity ; i++){
			UUID uid = UUID.randomUUID();
			
			String coupon = setCouponNumber(uid);
			
			couponList.add(coupon);
		}
		setCouponList(couponList);
	}
	
	/**
	 * 쿠폰 넘버 생성 
	 * @param uid
	 * @return
	 */
	private String setCouponNumber(UUID uid){
		String initNum= uid.toString().replaceAll("-", "").substring(0,16);
		
		char[] arr = initNum.toCharArray();
		
		StringBuffer codeNum = new StringBuffer();
		
		for(int i=0; i <arr.length ; i++){
			codeNum.append(arr[i]);
			if(i%4==3 && i < arr.length-1){
				codeNum.append("-");
			}
		}
		return codeNum.toString();
	}

	public ArrayList<String> getCouponList() {
		return couponList;
	}

	public void setCouponList(ArrayList<String> couponList) {
		this.couponList = couponList;
	}
	
	
}
