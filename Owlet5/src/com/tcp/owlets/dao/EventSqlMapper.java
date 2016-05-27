package com.tcp.owlets.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.bean.CouponBean;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.extractor.CouponInfoRowMapper;
import com.tcp.owlets.extractor.EventExtractor;
import com.tcp.owlets.extractor.EventRowMapper;
import com.tcp.owlets.extractor.NoticeRowMapper;
import com.tcp.util.CalendarFactory;
import com.tcp.util.FileManager;
import com.tcp.util.NumberCouponFactory;
import com.tcp.util.ResManager;
import com.tcp.util.SessionManager;

@Repository
public class EventSqlMapper {
	@Autowired
	DataSource dataSource;

	private int calIndex=0;
	private int couponIndex=0;

	public void insertCoupon(CouponBean bean) {
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into couponInfos ");
		sql.append("(genIndex , storeCode , name ) ");
		sql.append("VALUES(? ,? ,? ) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int code = SessionManager.getInstance().getSessionCode();

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {getCalIndex(), code, bean.getName() });
	}


	public void insertCouponKind(CouponBean bean) {
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into couponKinds ");
		sql.append("(genIndex ,type ,ImagePath,codePath, count ) ");
		sql.append("VALUES(? ,? ,?,?,? ) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String imageName=null;
		if(bean.getOriginType()==0){
			imageName = FileManager.getInstance().getFileName() + FileManager.getInstance().getFileType();
		}

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {getCalIndex(), bean.getOriginType(), imageName,
					bean.getCodePath() , bean.getCount()});
	}

	public void insertCalendarInfo(CouponBean info, CalendarInfo bean) {
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into calendarInfos ");
		sql.append("(nIndex , title , startDate, endDate , updateDate ) ");
		sql.append("VALUES(? ,? ,? ,?,?) ");

		int index = getMaxIndex();

		setCalIndex(index);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		bean.setUpdateDate(CalendarFactory.getInstance().setCurrentDateTime(1));

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {index, info.getName(), bean.getStartDate(),
					bean.getEndDate() , bean.getUpdateDate()});
	}


	public void insertCalendarKind(CalendarInfo bean) {
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into calendarKinds ");
		sql.append("(nIndex , type , popupEnable , alarmEnable , alarmMethod, ");
		sql.append("alarmClock , alarmPeriod) ");
		sql.append("VALUES(? ,? ,? ,?,?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {getCalIndex(), bean.getType(), bean.getPopupEnable(),
					bean.getAlarmEnable() , bean.getAlarmMethod(),
					bean.getAlarmClock() , bean.getAlarmPeriod()});
	}

	public void insertCalendarStoreMaps(){
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into calendar_store_maps ");
		sql.append(" (nIndex, code) ");
		sql.append("VALUES(? ,?)");

		int code = SessionManager.getInstance().getSessionCode();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {getCalIndex(), code});

	}

	private int getMaxIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(nIndex) as maxIndex ");
		sql.append("from calendarInfos ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int index = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if(rs.next()){
					int maxIndex = rs.getInt("maxIndex");

					maxIndex= maxIndex+1;

					return maxIndex;
				}
				return null;
			}
		});
		return index;
	}

	private int getCouponMaxIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(nIndex) as maxIndex ");
		sql.append("from useCoupons");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int index = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if(rs.next()){
					int maxIndex = rs.getInt("maxIndex");

					maxIndex= maxIndex+1;

					return maxIndex;
				}
				return null;
			}
		});
		return index;
	}

	public void updateCoupon(CouponBean bean) {
		StringBuffer sql = new StringBuffer();

		sql.append("Update couponInfos set name=? ");
		sql.append(" where genIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {bean.getName(), bean.getGenIndex() });
	}

	public void updateCouponKind(CouponBean bean){
		StringBuffer sql =new StringBuffer();

		sql.append("Update couponKinds set type=? , ");
		sql.append(" imagePath=?, codePath=? ");
		sql.append(" where genIndex=?");

		String imageName = FileManager.getInstance().getFileName() + FileManager.getInstance().getFileType();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {bean.getOriginType(), imageName, bean.getCodePath(), bean.getGenIndex() });
	}

	public void deleteCoupon(int genIndex) {
	}

	public void insertActualCoupons() {

		final ArrayList<String> couponList = NumberCouponFactory.getInstance().getCouponList();

		LoggerConfig.getWebLogger().debug("List Size  : " + couponList.size());

		int index = getCouponMaxIndex();

		setCouponIndex(index);

		StringBuffer sql = new StringBuffer();

		sql.append("Insert into useCoupons ");
		sql.append("(nIndex , genIndex , number , count , checkOut ,nEnable ) ");
		sql.append("VALUES(? ,? ,? ,?, ?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.batchUpdate(sql.toString() , new BatchPreparedStatementSetter() {
			int index= getCouponIndex();
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {

				String code = couponList.get(i);
				ps.setInt(1, index++);
				ps.setInt(2, getCalIndex());
				ps.setString(3, code);
				ps.setInt(4, 0);
				ps.setInt(5, 0);
				ps.setInt(6, 0);
			}
			@Override
			public int getBatchSize() {
				return couponList.size();
			}
		});
	}

	public void insertEventActualCoupon() {
		StringBuffer sql = new StringBuffer();

		int index = getCouponMaxIndex();

		setCouponIndex(index);

		sql.append("Insert into useCoupons ");
		sql.append("(nIndex , genIndex , number , count , checkout , nEnable ) ");
		sql.append("VALUES(? ,? ,? ,?, ?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {getCouponIndex(), getCalIndex(), null,
					0, 0 , 0});
	}

	public ArrayList<CouponBean> getCouponList(){
		int code = SessionManager.getInstance().getSessionCode();

		StringBuffer sql = new StringBuffer();

		sql.append("select info.genIndex , info.storeCode , info.name , kind.type, ");
		sql.append("kind.imagePath, kind.codePath, cal.startDate , cal.endDate, kind.count ");
		sql.append(" from couponInfos info , couponKinds kind , calendarInfos cal ");
		sql.append(" where (info.genIndex=kind.genIndex) ");
		sql.append(" AND (kind.genIndex=cal.nIndex)" );
		sql.append(" AND info.storeCode=");sql.append(code);

		List<CouponBean> list = new ArrayList<CouponBean>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list =jdbcTemplate.query(sql.toString(), new CouponInfoRowMapper());

		return (ArrayList<CouponBean>) list;
	}

	public CouponBean getCoupon(int couponIndex){
		int code = SessionManager.getInstance().getSessionCode();

		StringBuffer sql = new StringBuffer();

		sql.append("Select info.genIndex , info.storeCode ,info.name , ck.type ,");
		sql.append("ck.imagePath , ck.codePath, cal.startDate, cal.endDate, ck.count ");
		sql.append(" from couponInfos info , couponKinds ck , calendarInfos cal ");
		sql.append(" where (info.genIndex=cal.nIndex) ");
		sql.append(" AND (info.genIndex=ck.genIndex) ");
		sql.append(" AND info.storeCode=");sql.append(code);
		sql.append(" AND info.genIndex=");sql.append(couponIndex);

		List<CouponBean> list = new ArrayList<CouponBean>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list =jdbcTemplate.query(sql.toString(), new CouponInfoRowMapper());

		return list.get(0);
	}

	/**
	 * 30일로 쿠폰 가져오는 것 고정
	 * @param storeCode
	 * @return
	 * @throws ParseException 
	 */
	public ArrayList<CouponBean> getCouponList(int storeCode){
		StringBuffer sql = new StringBuffer();

		String endDate = CalendarFactory.getInstance().setCurrentDateTime(2);

		//		String startDate= CalendarFactory.getInstance().setCalcDateTime(endDate, 2);

		sql.append("select info.genIndex , info.storeCode , info.name , kind.type, ");
		sql.append("kind.imagePath, kind.codePath, cal.startDate , cal.endDate, kind.count ");
		sql.append(" from couponInfos info , couponKinds kind , calendarInfos cal ");
		sql.append(" where (info.genIndex=kind.genIndex) ");
		sql.append(" AND (kind.genIndex=cal.nIndex)" );
		sql.append(" AND info.storeCode=");sql.append(storeCode);
		sql.append(" AND cal.endDate >= '");sql.append(endDate);sql.append("'");

		LoggerConfig.getDBLogger().debug(sql.toString());

		List<CouponBean> list = new ArrayList<CouponBean>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list =jdbcTemplate.query(sql.toString(), new CouponInfoRowMapper());

		return (ArrayList<CouponBean>) list;
	}

	public CouponBean getImgCoupon(int storeCode, int frameIndex){
		StringBuffer sql=new StringBuffer();

		sql.append("select info.genIndex, info.storeCode, info.name, uc.nIndex, kind.imagePath , cal.startDate, cal.endDate ");
		sql.append("from couponInfos info, couponKinds kind , useCoupons uc , calendarInfos cal ");
		sql.append("where (info.genIndex=kind.genIndex) ");
		sql.append(" AND (info.genIndex=cal.nIndex) ");
		sql.append("AND (kind.genIndex=uc.genIndex) ");
		sql.append("AND info.storeCode=");sql.append(storeCode);
		sql.append(" AND info.genINdex=");sql.append(frameIndex);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		CouponBean bean=new CouponBean();

		bean= jdbcTemplate.query(sql.toString(), new ResultSetExtractor<CouponBean>() {
			@Override
			public CouponBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				CouponBean info=new CouponBean();
				if(rs.next()){

					String startDate = CalendarFactory.getInstance().
							setParseDateFormat(rs.getString("startDate"), 2, 0);

					String endDate = CalendarFactory.getInstance().
							setParseDateFormat(rs.getString("endDate"), 2, 0);

					info.setGenIndex(rs.getInt("genIndex"));
					info.setStoreCode(rs.getInt("storeCode"));
					info.setName(rs.getString("name"));
					info.setCodeIndex(rs.getInt("nIndex"));
					info.setImage(rs.getString("imagePath"));
					info.setStartDate(startDate);
					info.setEndDate(endDate);
				}
				return info;
			}
		});
		return bean;

	}

	public CouponBean getNumCoupon(int storeCode, int frameIndex){
		StringBuffer sql=new StringBuffer();

		sql.append("select info.genIndex, info.storeCode, info.name , uc.nIndex, uc.number , cal.startDate, cal.endDate ");
		sql.append("from useCoupons uc , couponInfos info, calendarInfos cal ");
		sql.append("where (uc.genIndex=info.genIndex) ");
		sql.append(" AND (info.genIndex=cal.nIndex) ");
		sql.append("AND uc.nIndex =( ");
		sql.append("select MIN(uc.nIndex) as num ");
		sql.append("from useCoupons uc, couponInfos info ");
		sql.append("where uc.genindex=");sql.append(frameIndex);
		sql.append(" AND (uc.genIndex=info.genIndex) ");
		sql.append(" AND info.storeCode=");sql.append(storeCode);
		sql.append(" AND uc.nEnable=0)");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		CouponBean bean=new CouponBean();

		bean= jdbcTemplate.query(sql.toString(), new ResultSetExtractor<CouponBean>() {
			@Override
			public CouponBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				CouponBean info=new CouponBean();
				if(rs.next()){
					String startDate = CalendarFactory.getInstance().
							setParseDateFormat(rs.getString("startDate"), 2, 0);

					String endDate = CalendarFactory.getInstance().
							setParseDateFormat(rs.getString("endDate"), 2, 0);

					info.setGenIndex(rs.getInt("genIndex"));
					info.setStoreCode(rs.getInt("storeCode"));
					info.setName(rs.getString("name"));
					info.setCodeIndex(rs.getInt("nIndex"));
					info.setCodePath(rs.getString("number"));
					info.setStartDate(startDate);
					info.setEndDate(endDate);
				}
				return info;
			}
		});
		return bean;
	}

	public ArrayList<EventNoticeBean> getEventList(){
		int code = SessionManager.getInstance().getSessionCode();
		
		List<EventNoticeBean> list = new ArrayList<EventNoticeBean>();

		StringBuffer sql = new StringBuffer();
		sql.append("Select event.nIndex , event.name , event.content , cal.startDate , cal.endDate , event.type ");
		sql.append("from events event , calendarInfos cal ");
		sql.append("where event.nIndex in (");
		sql.append(" select nIndex from calendar_store_maps ");
		sql.append(" where code = ");sql.append(code);
		sql.append(") ");
		sql.append(" AND (event.nIndex=cal.nIndex) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = jdbcTemplate.query(sql.toString(), new EventRowMapper());

		return (ArrayList<EventNoticeBean>) list;
	}

	public int getCalIndex() {
		return calIndex;
	}

	public void setCalIndex(int calIndex) {
		this.calIndex = calIndex;
	}
	public int getCouponIndex() {
		return couponIndex;
	}
	public void setCouponIndex(int couponIndex) {
		this.couponIndex = couponIndex;
	}
}
