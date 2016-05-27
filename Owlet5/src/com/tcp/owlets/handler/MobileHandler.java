package com.tcp.owlets.handler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.bean.MobileBean;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.dao.MobileSqlMapper;

@Service
public class MobileHandler {
	@Autowired
	MobileSqlMapper mobileDao;
	
	
	public void updateNumberCoupon(String couponNum, int genIndex) {
		mobileDao.updateUseCoupons(couponNum, genIndex);
	}

	public void updateImageCoupon(int genIndex) {
		mobileDao.updateUseCoupons(genIndex);
	}
	
	public void updateCheckOut(String couponNum , int genIndex){
		mobileDao.updateCouponCheckOut(couponNum, genIndex);
	}
	
	public void updatePageReport(int frameIndex){
		mobileDao.updatePageCount(frameIndex);
	}
	
	public void updateIncImgCouponCount(int frameIndex){
		mobileDao.updateIncImgCouponCount(frameIndex);
	}
	
	public void updateIncNumCouponEanble(int frameIndex){
		mobileDao.updateIncNumCouponEnable(frameIndex);
	}
	
	public ArrayList<CalendarInfo> getNoticeList(int storeCode){
		return mobileDao.getNoticeList(storeCode);
	}
	
	public EventNoticeBean getNotice(int index, int type){
		return mobileDao.getNotice(index , type);
	}
	
	public MobileBean getAppInfo(int code){
		return mobileDao.selectMobileInfo(code);
	}
	
	public ArrayList<AdderInfo> getMenuList(int code){
		return mobileDao.selectMenuList(code);
	}
	
	public ArrayList<AdderInfo> getGroupInfo(int groupIndex){
		return mobileDao.getGroupFrame(groupIndex);
	}
}
