package com.tcp.owlets.handler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.AppSettingInfo;
import com.tcp.owlets.dao.OptionSqlMapper;

@Service
public class OptionHandler {

	@Autowired
	OptionSqlMapper optionDao;
	
	public void insertMenu(int num , int index){
		optionDao.insertMenu(num , index);
	}
	public AppSettingInfo getSetData(int storeCode){
		return optionDao.selectSettingInfo(storeCode);
	}
	
	public ArrayList<AdderInfo> selectMenu(int storeCode){
		return optionDao.selectMenuData(storeCode);
	}
	
	public void initConfig(int storeCode){
		optionDao.initMenuConfig(storeCode);
	}
	
	public void updateSettingInfo(String fColor, String bColor, MultipartFile file , int change){
		optionDao.updateSettingInfo(bColor, fColor, file , change);
	}
}
