package com.tcp.util;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class ResManager {
	public static ResourceBundle resText = ResourceBundle.getBundle("config");

	public static String getString(String name){
		try{
			String str = new String(resText.getString(name).getBytes("UTF-8") , "UTF-8");

			return str;
		}
		catch(Exception e){
			return e.getMessage();
		}
	}
}
