package com.tcp.owlets.dao;

import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.extractor.CalendarInfoRowMapper;
import com.tcp.owlets.extractor.CalendarRowMapper;
import com.tcp.owlets.extractor.CalendarkindsRowMapper;
import com.tcp.util.CalendarFactory;
import com.tcp.util.DataStructureManager;

@Repository
public class CalendarSqlMapper {

	@Autowired
	DataSource dataSource;

	/**
	 * @param storeCode
	 * @param calID
	 * @return
	 * @using -- calendar 에서 데이터 삽입 후 데이터 요청
	 */
	public CalendarInfo getCalendarInfo(int storeCode, int calID) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ci.nIndex, ci.title, ci.startDate, ci.endDate,ci.updateDate, ");
		sql.append("ck.type,ck.popupEnable,ck.alarmEnable,ck.alarmMethod,ck.alarmClock,ck.alarmPeriod ");
		sql.append("FROM calendarInfos ci ");
		sql.append("INNER JOIN calendarKinds ck ");
		sql.append("ON ci.nIndex = ck.nIndex ");
		sql.append(" AND ci.nIndex = ");
		sql.append("(SELECT cs.nIndex FROM calendar_store_maps cs WHERE cs.nIndex =");
		sql.append(calID);
		sql.append(" AND cs.code=");
		sql.append(storeCode);
		sql.append(");");

		LoggerConfig.getDBLogger().debug(sql.toString());

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		List<CalendarInfo> list = jdbcTemplate.query(sql.toString(), new CalendarRowMapper());

		return list.get(0);
	}

	public String getContents(int storeCode, int calID){
		StringBuffer sql = new StringBuffer();

		sql.append("Select a.content");
		sql.append(" from calendarInfos cal , ");
		int type = getCalendarType(calID); //자료구조에서 처리해서 불러올 수 있도록 변경 바람. 꼭!
		switch(type){
		case 0:
			sql.append("  notices a ");
			break;
		case 1:
			sql.append("  events a ");
			break;
		}
		sql.append("where (cal.nIndex=a.nIndex) ");
		sql.append("AND cal.nIndex = (select nIndex from calendar_store_maps ");
		sql.append(" where code=");sql.append(storeCode);
		sql.append(" AND nIndex=");sql.append(calID);sql.append(")");

		LoggerConfig.getDBLogger().debug(sql.toString());

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String data = jdbcTemplate.query(sql.toString(), new ResultSetExtractor<String>(){
			@Override
			public String extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				String content=null;
				if(rs.next()){
					content  = rs.getString("content");
				}
				return content;
			}
		});
		return data;
	}

	public EventNoticeBean getType(int storeCode, int calID){
		StringBuffer sql = new StringBuffer();

		sql.append("Select type from events where nIndex=");
		sql.append(calID);


		LoggerConfig.getDBLogger().debug(sql.toString());
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		EventNoticeBean bean = jdbcTemplate.query(sql.toString(), new ResultSetExtractor<EventNoticeBean>(){
			public EventNoticeBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {

				EventNoticeBean bean = new EventNoticeBean();
				if(rs.next()){
					bean.setType(rs.getString("type")); 
				}
				return bean;
			}
		});
		return bean;
	}

	public int getMaxIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(nIndex) as maxIndex ");
		sql.append("from calendarInfos");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int index = jdbcTemplate.query(sql.toString(),
				new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				if (rs.next()) {
					int maxIndex = rs.getInt("maxIndex");

					maxIndex = maxIndex + 1;

					return maxIndex;
				}
				return null;
			}
		});
		return index;
	}

	private boolean insertCalendarInfo(CalendarInfo bean, int nIndex) {
		try{
			StringBuffer sql = new StringBuffer();

			sql.append("INSERT INTO calendarInfos ");
			sql.append("(nIndex,title,startDate,endDate,updateDate) ");
			sql.append("VALUES(?,?,?,?,?)");

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(sql.toString(),
					new Object[] { nIndex, bean.getTitle(), bean.getStartDate(),
				bean.getEndDate(), bean.getUpdateDate() });

			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	private void insertCalendarKinds(CalendarInfo bean, int nIndex) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO calendarKinds ");
		sql.append("(nIndex,type,popupEnable,alarmEnable,alarmMethod,alarmClock,alarmPeriod) ");
		sql.append("VALUES(?,?,?,?,?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { nIndex, bean.getType(), bean.getPopupEnable(),
					bean.getAlarmEnable(), bean.getAlarmMethod(),
					bean.getAlarmClock(), bean.getAlarmPeriod() });
	}

	private boolean insertCoupon(CalendarInfo bean, int nIndex, int storeCode) {

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO couponInfos(genIndex,storeCode,name) ");
		sql.append("VALUES(?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(sql.toString(), new Object[] { nIndex, storeCode,
			bean.getTitle() });

		return true;

	}

	private boolean insertNotice(CalendarInfo bean, int nIndex) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO notices(nIndex,name,content) ");
		sql.append("VALUES(?,?,?); ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(sql.toString(),
				new Object[] { nIndex, bean.getTitle(), bean.getContents() });

		return true;
	}

	private boolean insertEvents(CalendarInfo bean, int nIndex) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO events(nIndex,name,content,type) ");
		sql.append("VALUES(?,?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(sql.toString(),
				new Object[] { nIndex, bean.getTitle(), bean.getContents(), bean.getEventType() });

		return true;
	}

	public synchronized CalendarInfo insertCalendar(CalendarInfo bean,
			int storeCode) {
		int nIndex = this.getMaxIndex();
		int type = bean.getType();
		boolean flag=false;

		String now = CalendarFactory.getInstance().setCurrentDateTime(3);
		bean.setUpdateDate(now);

		String startDate = CalendarFactory.getInstance()
				.setParseDateFormat(bean.getStartDate(), 3, 2);

		String endDate = CalendarFactory.getInstance()
				.setParseDateFormat(bean.getEndDate(), 3, 2);

		bean.setStartDate(startDate);
		bean.setEndDate(endDate);

		flag = insertCalendarInfo(bean, nIndex);

		if(flag){
			insertCalendarKinds(bean, nIndex);
			insertCalendarStoreMap(storeCode, nIndex);

			if (type == 0)
				this.insertNotice(bean, nIndex);
			else if (type == 1)
				this.insertEvents(bean, nIndex);
			else if (type == 2)
				this.insertCoupon(bean, nIndex, storeCode);

			// DB Insert 작업 이후에 Return 되는 Bean값을 위해서 Setting
			bean.setnIndex(nIndex);
		}else{
			bean = new CalendarInfo(); //Exception 의 경우니까 Bean 객체 새로 만들어서 Return
		}

		return bean;
	}

	public void deleteCalendarInfo(int storeCode, int calID) {
		StringBuffer sql = new StringBuffer();

		int type = this.getCalendarType(calID);

		sql.append("DELETE ci,ck FROM calendarInfos ci ");
		sql.append("JOIN calendarKinds ck ON ci.nIndex=ck.nIndex ");
		sql.append("WHERE ci.nIndex=");
		sql.append(calID);
		sql.append(" AND ck.nIndex=");
		sql.append(calID);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());

		if (type == 0)
			this.deleteNotices(storeCode, calID);
		else if (type == 1)
			this.deleteEvents(storeCode, calID);
		else if (type == 2){
			this.deleteCouponInfos(storeCode, calID);
			this.deleteCouponKinds(storeCode, calID);
		}
		this.deleteCalendarStoreMaps(storeCode, calID);
	}

	private boolean deleteNotices(int storeCode, int calID) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM notices WHERE nIndex=");
		sql.append("(SELECT cs.nIndex FROM calendar_store_maps cs WHERE cs.nIndex=");
		sql.append(calID);
		sql.append(" AND code=");
		sql.append(storeCode);
		sql.append(");");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());

		return true;
	}

	private int getCalendarType(int calID) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ci.type FROM calendarKinds ci WHERE ci.nIndex=");
		sql.append(calID);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int type = jdbcTemplate.query(sql.toString(),
				new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				if (rs.next()) {
					int type = rs.getInt("type");

					return type;
				}
				return null;
			}
		});
		return type;
	}

	private boolean deleteEvents(int storeCode, int calID) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM events WHERE nIndex=");
		sql.append("(SELECT nIndex FROM calendar_store_maps cs WHERE cs.nIndex=");
		sql.append(calID);
		sql.append(" AND code=");
		sql.append(storeCode);
		sql.append(");");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());

		return true;
	}

	private boolean deleteCouponInfos(int storeCode, int calID) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM couponInfos WHERE genIndex=");
		sql.append("(SELECT nIndex FROM calendar_store_maps cs WHERE cs.nIndex=");
		sql.append(calID);
		sql.append(" AND code=");
		sql.append(storeCode);
		sql.append(");");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());

		return true;
	}

	private boolean deleteCouponKinds(int storeCode , int calID){
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM couponKinds WHERE genIndex=");
		sql.append("(SELECT nIndex FROM calendar_store_maps cs WHERE cs.nIndex=");
		sql.append(calID);
		sql.append(" AND code=");
		sql.append(storeCode);
		sql.append(");");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());


		return true;
	}

	private boolean deleteCalendarStoreMaps(int storeCode, int calID) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM calendar_store_maps ");
		sql.append("WHERE nIndex=");
		sql.append(calID);
		sql.append(" AND ");
		sql.append("code=");
		sql.append(storeCode);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());

		// Debug :: 쿼리가 올바른지 확인
		LoggerConfig.getDBLogger().debug(sql.toString());

		return true;
	}

	public ArrayList<CalendarInfo> getAllCalendarInfo(int storeCode) {

		ArrayList<CalendarInfo> retCalendar = new ArrayList<CalendarInfo>();

		HashMap<Integer, CalendarInfo> calDataMap = new HashMap<Integer, CalendarInfo>();
		ArrayList<CalendarInfo> sub = new ArrayList<CalendarInfo>();

		calDataMap = this.getCalendarInfo(storeCode);
		sub = this.getCalendarkinds(storeCode);

		retCalendar = DataStructureManager.getInstance().mergeCalendarData(calDataMap, sub);

		return retCalendar;
	}

	private HashMap<Integer, CalendarInfo> getCalendarInfo(int storeCode) {
		HashMap<Integer, CalendarInfo> calDataMap = new HashMap<Integer, CalendarInfo>();

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT ci.nIndex, ci.title, ci.startDate, ci.endDate, ck.popupEnable, ck.alarmEnable, ck.alarmPeriod, ck.type ");
		sql.append("FROM calendarInfos ci, calendarKinds ck ");
		sql.append("WHERE ci.nIndex IN ");
		sql.append("(SELECT sm.nIndex FROM calendar_store_maps sm ");
		sql.append("WHERE code = ");
		sql.append(storeCode);
		sql.append(") ");
		sql.append("AND ci.nIndex = ck.nIndex;");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		List<CalendarInfo> list = (ArrayList<CalendarInfo>) jdbcTemplate.query(
				sql.toString(), new CalendarInfoRowMapper());

		for(CalendarInfo info : list){
			calDataMap.put(info.getnIndex(), info);
		}

		LoggerConfig.getDBLogger().debug(sql.toString());

		return calDataMap;
	}

	private ArrayList<CalendarInfo> getCalendarkinds(int storeCode) {
		ArrayList<CalendarInfo> subList = new ArrayList<CalendarInfo>();

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT n.nIndex, ck.type, n.content as contents FROM calendarKinds ck,notices n WHERE n.nIndex IN ");
		sql.append(" (SELECT n.nIndex FROM notices n WHERE n.nIndex IN ");
		sql.append(" (SELECT nIndex FROM calendar_store_maps cs WHERE cs.code=");
		sql.append(storeCode);sql.append(" )) GROUP BY contents ");

		sql.append(" UNION ");

		sql.append(" SELECT e.nIndex, ck.type, e.content as contents FROM calendarKinds ck,events e WHERE e.nIndex IN ");
		sql.append(" (SELECT e.nIndex FROM events e WHERE e.nIndex IN ");
		sql.append(" (SELECT nIndex FROM calendar_store_maps cs WHERE cs.code=");
		sql.append(storeCode);sql.append(" )) GROUP BY contents ");

		sql.append(" UNION ");

		sql.append(" SELECT c.genIndex, ck.type, c.name as contents FROM calendarKinds ck,couponInfos c WHERE c.genIndex IN ");
		sql.append(" (SELECT c.genIndex FROM couponInfos c WHERE c.genIndex IN ");
		sql.append(" (SELECT nIndex FROM calendar_store_maps cs WHERE cs.code=");
		sql.append(storeCode);sql.append(" )) GROUP BY contents ");


		// Debug :: 쿼리가 올바른지 로그를 찍자
		//		LoggerConfig.getDBLogger().debug(sql.toString());

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		subList = (ArrayList<CalendarInfo>) jdbcTemplate.query(sql.toString(),
				new CalendarkindsRowMapper());

		return subList;
	}

	private void insertCalendarStoreMap(int storeCode, int nIndex) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO calendar_store_maps(nIndex,code) ");
		sql.append("VALUES(?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(sql.toString(), new Object[] { nIndex, storeCode });
	}

	public boolean updateCalendar(int storeCode, int calID, CalendarInfo bean) {

		/*
		 *  Method :(private) isAccessAble(int storeCode, int calID)
		 *      사용 이유 :  한번의 쿼리가 아닌 복수의 쿼리를 이용하여 업데이트를 처리
		 *            이때, 다수의 서브쿼리가 발생. 따라서 하나의 select 쿼리로 다수의 서브쿼리를 처리.
		 */
		if (this.isAccessAble(storeCode, calID)) {
			int type = this.getCalendarType(calID);

			this.updateCalendarInfo(calID, bean);
			this.updateCalendarkinds(calID, bean);

			switch (type) {
			case 0:
				this.updateNotices(calID, bean);
				break;

			case 1:
				this.updateEvents(calID, bean);
				break;

			case 2:
				//				this.updateCoupon(calID, bean);
				break;

			case 3: // 일정인 경우.
				break;

			default:
				LoggerConfig.getDBLogger().debug(
						"Debug :: [updateCalendar] type miss match", type);
			}
		}

		return false;
	}

	private void updateNotices(int calID, CalendarInfo bean) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE notices n SET n.name=?, n.content=? ");
		sql.append("WHERE n.nIndex =");
		sql.append(calID);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString(), new Object[] { bean.getTitle(),
			bean.getContents() });
	}

	private void updateEvents(int calID, CalendarInfo bean) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE events e SET e.name=?, e.content=? , e.type=? ");
		sql.append("WHERE e.nIndex =");
		sql.append(calID);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString(), new Object[] { bean.getTitle(),
			bean.getContents(), bean.getEventType() });

	}

	private void updateCoupon(int calID, CalendarInfo bean) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE couponInfos c SET c.name=? ");
		sql.append("WHERE c.genIndex =");
		sql.append(calID);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString(), new Object[] { bean.getTitle() });
	}

	/**
	 * @Table calendarinfos
	 * @param storeCode
	 * @param calID
	 * @param bean
	 * @return boolean -- 쿼리가 잘 시행되었는지 확인한다.
	 */
	private void updateCalendarInfo(int calID, CalendarInfo bean) {
		StringBuffer sql = new StringBuffer();

		/*
		 * 날짜를 수정했을 경우 Date가 mysql에서 받아들일 수 없게 들어온다..
		 * 이걸 해결하기 위해 Check하나 둔다...
		 */
		boolean isStartAdmit = CalendarFactory.getInstance().isAdmitDate(bean.getStartDate());

		boolean isEndAdmit = CalendarFactory.getInstance().isAdmitDate(bean.getEndDate());


		if(!isStartAdmit){
			bean.setStartDate(CalendarFactory.getInstance().setParseDateFormat(bean.getStartDate(), 3, 2));
		}

		if(!isEndAdmit){
			bean.setEndDate(CalendarFactory.getInstance().setParseDateFormat(bean.getEndDate(), 3, 2));
		}

		bean.setUpdateDate(CalendarFactory.getInstance().setCurrentDateTime(3));

		sql.append("UPDATE calendarInfos ci SET ci.title = ?, ci.startDate = ?, ci.endDate = ?, ci.updateDate = ?");
		sql.append(" WHERE ci.nIndex =");
		sql.append(calID);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(sql.toString(), new Object[] { bean.getTitle(),
			bean.getStartDate(), bean.getEndDate(), bean.getUpdateDate() });
	}

	/**
	 * @Table calendarkinds
	 * @param storeCode
	 * @param calID
	 * @param bean
	 * @return boolean -- 쿼리가 잘 시행되었는지 확인한다
	 */
	private void updateCalendarkinds(int calID, CalendarInfo bean) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE calendarKinds ck SET ck.type = ?, ck.popupEnable = ?, ck.alarmEnable = ?, ");
		sql.append("ck.alarmMethod = ?, ck.alarmClock = ?, ck.alarmPeriod = ? ");
		sql.append("WHERE ck.nIndex =");
		sql.append(calID);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { bean.getType(), bean.getPopupEnable(),
					bean.getAlarmEnable(), bean.getAlarmMethod(),
					bean.getAlarmClock(), bean.getAlarmPeriod() });
	}

	/**
	 * @param storeCode
	 * @param calID
	 * @return
	 * @Table calendar_store_maps // storeCode와 calendarID의 접근권한을 검사.. 혹시 필요할까
	 *        싶어서 만들었는데, 쓸일이 있을까..
	 */
	private boolean isAccessAble(int storeCode, int calID) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT cs.nIndex FROM calendar_store_maps cs");
		sql.append(" WHERE cs.nIndex =");
		sql.append(calID);
		sql.append(" AND code =");
		sql.append(storeCode);
		sql.append(";");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		boolean check = jdbcTemplate.query(sql.toString(),
				new ResultSetExtractor<Boolean>(){

			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()){
					return true;
				}
				return false;
			}
		});
		return check;
	}

	public void bulkDelete(int storeCode, int[] calArr) {
		StringBuffer sql = new StringBuffer(this.makeDeleteQuery(calArr));

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private String makeDeleteQuery(int[] calArr) {
		StringBuffer sql = new StringBuffer();

		StringBuffer subQuery = new StringBuffer();

		subQuery.append("(");
		for (int i = 0, len = calArr.length; i < len; i++) {
			subQuery.append(calArr[i]);
			sql.append(",");
		}
		subQuery.deleteCharAt(subQuery.length() - 1);
		subQuery.append(")");

		sql.append("DELETE ci,ck FROM calendarInfos ci ");
		sql.append("JOIN calendarKinds ck ON ci.nIndex = ck.nIndex ");
		sql.append("WHERE ci.nIndex IN ");
		sql.append(subQuery.toString());
		sql.append(" AND ck.nIndex IN ");
		sql.append(subQuery.toString());

		LoggerConfig.getDBLogger().debug("쿼리확인[bulkDelete] " + sql.toString());

		return sql.toString();

	}
}
