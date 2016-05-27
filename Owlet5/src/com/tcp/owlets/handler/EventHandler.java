package com.tcp.owlets.handler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.bean.CouponBean;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.dao.CalendarSqlMapper;
import com.tcp.owlets.dao.EventSqlMapper;
import com.tcp.util.TaskManager;


@Service
public class EventHandler{
	@Autowired
	EventSqlMapper eventDao;
	
	@Autowired
	CalendarSqlMapper calDao;
	
	public void insertCoupon(CouponBean bean , CalendarInfo info) {
		eventDao.insertCalendarInfo(bean , info);
		eventDao.insertCalendarKind(info);
		eventDao.insertCalendarStoreMaps();
		eventDao.insertCoupon(bean);
		eventDao.insertCouponKind(bean);
		TaskManager.getInstance().addCoupon(bean);
	}

	public void updateCoupon(CouponBean bean, CalendarInfo info , int index, int storeCode) {
		eventDao.updateCoupon(bean);
		eventDao.updateCouponKind(bean);
		calDao.updateCalendar(storeCode, index, info);
	}
	
	public void deleteCoupon(int genIndex) {
		eventDao.deleteCoupon(genIndex);
	}

	public void insertActualCoupon() {
		eventDao.insertActualCoupons();
	}

	public void insertUseImageCoupon() {
		eventDao.insertEventActualCoupon();
	}
	
	public ArrayList<CouponBean> getCouponList(){
		return eventDao.getCouponList();
	}
	
	public CouponBean getCoupon(int couponIndex){
		return eventDao.getCoupon(couponIndex);
	}
	
	public ArrayList<CouponBean> getMonthCouponList(int storeCode){
		return eventDao.getCouponList(storeCode);
	}
	
	public CouponBean getImageCoupon(int storeCode, int frameIndex){
		return eventDao.getImgCoupon(storeCode, frameIndex);
	}
	
	public CouponBean getNumberCoupon(int storeCode, int frameIndex){
		return eventDao.getNumCoupon(storeCode, frameIndex);
	}
	
	public ArrayList<EventNoticeBean> getEventNoticeList(){
		return eventDao.getEventList();
	}
}
