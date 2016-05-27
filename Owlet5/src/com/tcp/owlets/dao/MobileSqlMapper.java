package com.tcp.owlets.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.AppInfo;
import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.bean.MobileBean;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.extractor.NoticeRowMapper;
import com.tcp.util.AppManager;
import com.tcp.util.TaskManager;

@Repository
public class MobileSqlMapper {

	@Autowired
	DataSource dataSource;

	/**
	 * 숫자쿠폰의 사용 상태 변경
	 * @param number
	 * @param genIndex
	 */
	public void updateUseCoupons(String number, int genIndex) {
		StringBuffer sql = new StringBuffer();

		sql.append("Update useCoupons set nEnable=? ");
		sql.append(" where genIndex=? AND number=? ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {1 , genIndex , number});
	}

	/**
	 * 이미지 쿠폰 누적 Count 증가
	 * @param genIndex
	 */
	public void updateUseCoupons(int genIndex) {
		StringBuffer sql = new StringBuffer();

		sql.append("Update useCoupons set count=? ");
		sql.append(" where genIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int count = TaskManager.getInstance().getCount(genIndex);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {count , genIndex});

	}

	/**
	 * 숫자 쿠폰 발급 상태 변경
	 * @param genIndex
	 */
	public void updateCouponCheckOut(String number , int genIndex){
		StringBuffer sql = new StringBuffer();

		sql.append("Update useCoupons set checkOut=? ");
		sql.append(" where genIndex=? AND number=? ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {1 , genIndex , number});
	}

	/**
	 * page 접근시 PageReports FrameIndex의 Count수를 늘린다.
	 * @param frameIndex
	 */
	public void updatePageCount(int frameIndex){
		StringBuffer sql=new StringBuffer();

		sql.append("Update pageReports set visitCount=visitCount+1 ");
		sql.append(" where nIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {frameIndex});
	}

	public void updateIncImgCouponCount(int frameIndex){
		StringBuffer sql=new StringBuffer();

		sql.append("Update useCoupons set count=count+1 ");
		sql.append(" where nIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {frameIndex});
	}
	
	public void updateIncNumCouponEnable(int frameIndex){
		StringBuffer sql=new StringBuffer();

		sql.append("Update useCoupons set nEnable=1 ");
		sql.append(" where nIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {frameIndex});
	}
	
	public ArrayList<CalendarInfo> getNoticeList(int code){
		List<CalendarInfo> list = new ArrayList<CalendarInfo>();
		
		StringBuffer sql=new StringBuffer();

		sql.append(" Select info.nIndex, info.title, info.startDate, info.endDate, kind.type ");
		sql.append(" from calendarInfos info, calendarKinds kind ");
		sql.append(" where (info.nIndex=kind.nIndex) ");
		sql.append(" AND (kind.type=0 or kind.type=1) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = jdbcTemplate.query(sql.toString(), new ResultSetExtractor<ArrayList<CalendarInfo>>(){

			@Override
			public ArrayList<CalendarInfo> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				
				ArrayList<CalendarInfo> list =new ArrayList<CalendarInfo>();
				
				while(rs.next()){
					CalendarInfo info=new CalendarInfo();
					try{
						info.setnIndex(rs.getInt("nIndex"));
						info.setType(rs.getInt("type"));
						info.setTitle(rs.getString("title"));
						info.setStartDate(rs.getString("startDate"));
						info.setEndDate(rs.getString("startDate"));
					}
					catch(Exception e){
						LoggerConfig.getDBLogger().debug(e.getMessage());
					}
					list.add(info);
				}
				return list;
			}
		});
		return (ArrayList<CalendarInfo>) list;
	}
	
	public EventNoticeBean getNotice(int index, int type){
		EventNoticeBean info = new EventNoticeBean();
		
		StringBuffer sql=new StringBuffer();

		sql.append(" Select nIndex, name , content, type "); //type 추가  - 윤지수
		switch(type){
		case 0:
			sql.append(" from notices ");
			break;
		case 1:
			sql.append(" from events ");
			break;
		}
		sql.append(" where nIndex=");sql.append(index);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		info = jdbcTemplate.query(sql.toString(), new ResultSetExtractor<EventNoticeBean>(){

			@Override
			public EventNoticeBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				EventNoticeBean info=null;
				if(rs.next()){
					info=new EventNoticeBean();

					try{
						info.setnIndex(rs.getInt("nIndex"));
						info.setName(rs.getString("name"));
						info.setContext(rs.getString("content"));
						info.setType(rs.getString("type"));
					}
					catch(Exception e){
						LoggerConfig.getDBLogger().debug(e.getMessage());
					}
				}
				return info;
			}
		});
		return info;
	}
	
	public MobileBean selectMobileInfo(int storeCode){
		StringBuffer sql=new StringBuffer();
		
		sql.append("Select info.appIndex, info.name , setInfo.color, setInfo.bgColor, setInfo.image ");
		sql.append("from appInfos info , appSettingInfos setInfo ");
		sql.append("where (info.appIndex=setInfo.appIndex) ");
		sql.append("AND info.storeCode=");sql.append(storeCode);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		MobileBean info=new MobileBean();

		info = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<MobileBean>() {
			@Override
			public MobileBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				MobileBean bean=new MobileBean();

				while(rs.next()){
					bean.setAppIndex(rs.getInt("appIndex"));
					bean.setAppName(rs.getString("name"));
					bean.setfColor(rs.getString("color"));
					bean.setbColor(rs.getString("bgColor"));
					bean.setImage(rs.getString("image"));
				}
				return bean;
			}
		});
		return info;
	}
	
	public ArrayList<AdderInfo> selectMenuList(int storeCode){
		StringBuffer sql=new StringBuffer();
		
		sql.append("Select info.nIndex, info.title , info.path, kind.type ");
		sql.append(" from frameInfos info , frameKinds kind ");
		sql.append(" where (info.nIndex=kind.nIndex) ");
		sql.append(" AND info.appIndex=(select appIndex from ");
		sql.append(" appInfos where storeCode=");sql.append(storeCode);sql.append(") ");
		sql.append(" AND kind.menuEnable=1");
		sql.append(" ORDER BY kind.menuNum ASC");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<AdderInfo> list=new ArrayList<AdderInfo>();
		
		list = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<ArrayList<AdderInfo>>() {
			@Override
			public ArrayList<AdderInfo> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<AdderInfo> datas=new ArrayList<AdderInfo>();

				while(rs.next()){
					AdderInfo bean=new AdderInfo();
					bean.setnIndex(rs.getInt("nIndex"));
					bean.setTitle(rs.getString("title"));
					bean.setPath(rs.getString("path"));
					bean.setLevel(rs.getInt("type"));
					
					datas.add(bean);
				}
				return datas;
			}
		});
		return (ArrayList<AdderInfo>)list;
	}
	
	public ArrayList<AdderInfo> getGroupFrame(int groupIndex){

		ArrayList<AdderInfo> info=new ArrayList<AdderInfo>();

		StringBuffer sql=new StringBuffer();

		sql.append("Select frame.nIndex, frame.groupIndex, g.name, frame.title, frame.content ");
		sql.append(" from frameInfos frame , groupInfos g ");
		sql.append(" where (frame.groupIndex=g.nIndex) ");
		sql.append(" AND g.nIndex=");sql.append(groupIndex);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		info = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<ArrayList<AdderInfo>>() {
			@Override
			public ArrayList<AdderInfo> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<AdderInfo> list=new ArrayList<AdderInfo>();

				while(rs.next()){
					AdderInfo bean=new AdderInfo();

					bean.setnIndex(rs.getInt("nIndex"));
					bean.setGroupIndex(rs.getInt("groupIndex"));
					bean.setGroupName(rs.getString("name"));
					bean.setTitle(rs.getString("title"));
					bean.setContent(rs.getString("content"));

					list.add(bean);
				}

				return list;
			}
		});
		return info;
	}
}
