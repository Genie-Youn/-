package com.tcp.owlets.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.AppSettingInfo;
import com.tcp.owlets.handler.OptionHandler;
import com.tcp.util.SessionManager;

@Controller
@RequestMapping("/option")
public class OptionController {

	@Autowired
	OptionHandler optionService;
	
	@RequestMapping("/insertMenu")
	public @ResponseBody int insertMenu(@RequestParam(value="menus" , required=false) String[] data){
		try{
			int storeCode=SessionManager.getInstance().getSessionCode();
			
			optionService.initConfig(storeCode);
			
			for(int i=0; i < data.length ; i++){
				String[] array=data[i].split("\\s");
				
				int index=Integer.parseInt(array[0]);
				
				optionService.insertMenu(i+1, index);
			}
			return 1;
		}
		catch(Exception e){
			LoggerConfig.getWebLogger().debug(e.getMessage());
			
			return 0;
		}
	}
	
	@RequestMapping(value="/selectMenu" , produces="application/json")
	public @ResponseBody ArrayList<AdderInfo> getAppMenu(){
		int code= SessionManager.getInstance().getSessionCode();
		
		ArrayList<AdderInfo> list=optionService.selectMenu(code);
		
		return list;
	}
	
	@RequestMapping(value="/getOptionInfo" , produces="application/json")
	public @ResponseBody AppSettingInfo getAppOption(){
		int storeCode=SessionManager.getInstance().getSessionCode();
		
		AppSettingInfo info=optionService.getSetData(storeCode);
		
		return info;
	}
	
	@RequestMapping(value="/updateOption" , method=RequestMethod.POST)
	public @ResponseBody int updateOption(@RequestParam(value="fcolor" , required=false) String color
			, @RequestParam(value="bcolor" , required=false) String bColor 
			, @RequestParam(value="image" , required=false) MultipartFile file
			, @RequestParam(value="ch" , required=false) int change){
		try{
			optionService.updateSettingInfo(color, bColor, file , change);
			
			return 1;
		}
		catch(Exception e){
			LoggerConfig.getWebLogger().debug(e.getMessage());
			
			return 0;
		}
		
	}
}
