package com.tcp.owlets.handler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.AppInfo;
import com.tcp.owlets.bean.AppSettingInfo;
import com.tcp.owlets.dao.AppSqlMapper;
import com.tcp.owlets.dao.ReportSqlMapper;

@Service
public class AdderHandler{
	@Autowired
	AppSqlMapper adderDao;
	
	@Autowired
	ReportSqlMapper reportDao;
	
	
	public void insertFrame(AdderInfo adderInfo, int type) {
		adderDao.insertFrame(adderInfo,type);
		adderDao.insertFrameKind(adderInfo, type);
		adderDao.insertPageReport();
	}

	public void updateFrame(AdderInfo adderInfo) {
		adderDao.updateFrame(adderInfo);
	}

	public AdderInfo getFrame(int frameindex) {
		return adderDao.getFrame(frameindex);
	}

	public void deleteFrame(int codeIndex) {
		adderDao.deleteFrame(codeIndex);
		adderDao.deleteFrameKinds(codeIndex);
		adderDao.deletePageReports(codeIndex);
	}

	public ArrayList<AdderInfo> getFrameList(int codeIndex) {
		// TODO Auto-generated method stub
		return adderDao.getFrameList(codeIndex);
	}
	
	public void insertAppInfo(AppInfo info , int storeCode){
		adderDao.insertAppInfo(info, storeCode);
		
	}
	
	public ArrayList<AppInfo> getAppList(){
		return adderDao.getAppList();
	}
	
	public boolean isAppBasicExist(){
		return adderDao.isAppBasicExist();
	}
	
	public int isStoreFrameExist(){
		return adderDao.isStoreFrameExist();
	}
	
	public boolean isNoticeListExist(){
		return adderDao.isNoticeListFrameExist();
	}
	public boolean isCouponListExist(){
		return adderDao.isCouponListFrameExist();
	}
	
	public ArrayList<AdderInfo> getUrlList(int codeIndex) {
		// TODO Auto-generated method stub
		return adderDao.getUrlList(codeIndex);
	}
	
	public void insertAppSettingInfo(AppSettingInfo info , int storeCode){
		adderDao.insertAppSettingInfo(info, storeCode);
	}
	
	public int insertGroupInfo(int storeCode,String groupName){
		return adderDao.insertGroupData(storeCode, groupName);
	}
	
	public void updateGroupInfo(int index, String groupName){
		adderDao.updateGroupData(index, groupName);
		
	}
	public void deleteGroupInfo(int index){
		adderDao.deleteGroupData(index);
	}
	public ArrayList<AdderInfo> getGroupFrame(int code){
		return adderDao.getGroupFrame(code);
	}
	public ArrayList<AdderInfo> getGroupInfo(int code){
		return adderDao.getGroupMenu(code);
	}
	
	public void insertDataConfig(int[] data , int groupIndex){
		adderDao.insertGroupConfigData(data, groupIndex);
	}
	
	public void initDataConfig(int storeCode){
		adderDao.initGroupConfig(storeCode);
	}
	
	public ArrayList<AdderInfo> getTotalList(int codeIndex) {
		// TODO Auto-generated method stub
		return adderDao.getTotalFrameList(codeIndex);
	}
	
	public AppInfo getAppInfo(int storeCode){
		return adderDao.selectAppInfo(storeCode);
	}
	
	public int isMobileAppFrameExist(){
		return adderDao.isWebAppFrameExist();
	}
	
	public void updateAppInfo(AppInfo bean , int code){
		adderDao.updateAppInfo(bean, code);
	}
	
	public void updateAppConfirm(AppInfo bean){
		adderDao.updateAppInfo(bean);
	}
	
	public boolean isAppExist(){
		return adderDao.isAppExist();
	}
}
