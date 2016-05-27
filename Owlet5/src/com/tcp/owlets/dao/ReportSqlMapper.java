package com.tcp.owlets.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;
import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.CouponBean;
import com.tcp.owlets.bean.CouponHistoryBean;
import com.tcp.owlets.bean.PageInfoBean;
import com.tcp.owlets.bean.ReplyBean;
import com.tcp.owlets.bean.ReportBean;
import com.tcp.owlets.bean.ReportListInfo;
import com.tcp.owlets.extractor.PageReportRowMapper;
import com.tcp.owlets.extractor.ReportHistoryRowMapper;
import com.tcp.owlets.extractor.ReportRowMapper;
import com.tcp.util.CalendarFactory;
import com.tcp.util.SessionManager;
import com.tcp.util.TaskManager;

@Repository
public class ReportSqlMapper {

	@Autowired
	DataSource dataSource;

	public ReportListInfo getDayReportInfo(int storeCode,String date , int period , boolean isDash) {
		ReportListInfo totalBean = new ReportListInfo();


		ArrayList<ReportBean> dayReportList = getDayReport(storeCode,date, period);
		ArrayList<ReplyBean> dayReplyList = getDayReplyReport(storeCode, date, period);
		ArrayList<ReplyBean> productReplyList = null;
		productReplyList = getProductReplyReport(storeCode, date, period);
//		ArrayList<PageInfoBean> pageReportList = getDayPageReport(storeCode,date, period);
//		ArrayList<CouponHistoryBean> couponReportList;
//		ArrayList<CouponBean> couponGenReportList = getCouponGenReport(storeCode);	// 대시보드일 경우 쿠폰 발급에 대한 정보도 알아야 함으로 추가
		
		//대시보드 일 경우 실시간 Data를 합친다. .쿠폰 내역을 달리 보여준다.  * reply db 스케줄링 요망
		if(isDash){
//			pageReportList=TaskManager.getInstance().getPageCountList(pageReportList , storeCode);
			dayReportList=TaskManager.getInstance().getVisitCountList(dayReportList, storeCode);
//			couponReportList = getDayDashBoardCoupon(storeCode, date, period);
		}else{
//			couponReportList = getDayCouponReport(storeCode, date, period);
		}

		totalBean.setStoreCode(storeCode);
		totalBean.setDayReport(dayReportList);
		totalBean.setDayReplyReport(dayReplyList);
		totalBean.setProductReplyReport(productReplyList);
//		totalBean.setPageReport(pageReportList);
//		totalBean.setCouponReport(couponReportList);
//		totalBean.setCouponGenReport(couponGenReportList);
		
		return totalBean;
	}

	public ReportListInfo getDayReportInfoRandom(int storeCode, String startDate,
			String endDate) {
		ReportListInfo totalBean = new ReportListInfo();


		ArrayList<ReportBean> dayReportList = getDayReport(storeCode,startDate, endDate);
		ArrayList<ReplyBean> dayReplyList = getDayReplyReport(storeCode, startDate, endDate);
		ArrayList<ReplyBean> productReplyList = getProductReplyReport(storeCode, startDate, endDate);
//		ArrayList<PageInfoBean> pageReportList = getDayPageReport(storeCode,startDate, endDate);
//		ArrayList<CouponHistoryBean> couponReportList = getDayCouponReport(storeCode, startDate, endDate);
//		ArrayList<CouponBean>couponGenReportList = getCouponGenReport(storeCode, startDate, endDate);
		
		totalBean.setStoreCode(storeCode);
		totalBean.setDayReport(dayReportList);
		totalBean.setDayReplyReport(dayReplyList);
		totalBean.setProductReplyReport(productReplyList);
//		totalBean.setPageReport(pageReportList);
//		totalBean.setCouponReport(couponReportList);
		
		return totalBean;
	}


	private ArrayList<ReportBean> getDayReport(int storeCode,String date , int period){
		ArrayList<ReportBean> list = new ArrayList<ReportBean>();

		String startDate = CalendarFactory.getInstance().setCalcDateTime(date, period);
		String endDate = date;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT dr.nIndex, dr.downCount, dr.visitCount, dr.visitorCount, dr.date ");
		sql.append("FROM dayReports dr ");
		sql.append("WHERE storeCode =");sql.append(storeCode);
		sql.append(" AND date between '");sql.append(startDate);sql.append("' ");
		sql.append(" AND '");sql.append(endDate);sql.append("' ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = (ArrayList<ReportBean>)jdbcTemplate.query(sql.toString(),new ReportRowMapper() );

		return list;
	}

	private ArrayList<ReplyBean> getDayReplyReport(int storeCode, String date, int period){
		String startDate = CalendarFactory.getInstance().setCalcDateTime(date, period);
		String endDate = date;
		
		return getDayReplyReport(storeCode, startDate, endDate);
	}
	
	private ArrayList<ReplyBean> getDayReplyReport(int storeCode, String startDate, String endDate){
		ArrayList<ReplyBean> list = new ArrayList<ReplyBean>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATE(replyDate) as date, COUNT(nIndex) as replyCount, AVG(starService) as starService, AVG(starMood) as starMood, AVG(starGoods) as starGoods");
		sql.append(" FROM Reply ");
		sql.append(" WHERE storeCode = "); sql.append(storeCode);
		sql.append(" AND DATE(replyDate) between '");	sql.append(startDate); sql.append("' ");
		sql.append(" AND '"); sql.append(endDate); sql.append("' ");
		sql.append(" GROUP BY date;");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		list = (ArrayList<ReplyBean>)jdbcTemplate.query(sql.toString(), new ResultSetExtractor<ArrayList<ReplyBean>>(){

			@Override
			public ArrayList<ReplyBean> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<ReplyBean> datas = new ArrayList<ReplyBean>();
				while(rs.next()){
					ReplyBean bean = new ReplyBean();
					bean.setDate(rs.getString(1));
					bean.setReplyCount(rs.getInt(2));
					bean.setStarService(rs.getFloat(3));
					bean.setStarMood(rs.getFloat(4));
					bean.setStarGoods(rs.getFloat(5));
					
					datas.add(bean);
				}
				return datas;
			}
		});
		return list;
	}
	
	private ArrayList<ReplyBean> getProductReplyReport(int storeCode, String date, int period) {
		String startDate = CalendarFactory.getInstance().setCalcDateTime(date, period);
		String endDate = date;
		
		return getProductReplyReport(storeCode, startDate, endDate);
	}
	private ArrayList<ReplyBean> getProductReplyReport(int storeCode, String startDate, String endDate) {
		ArrayList<ReplyBean> list = new ArrayList<ReplyBean>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT rp.pdIndex, pi.pdName, pi.pdPrice, COUNT(rp.nIndex) as replyCount, AVG(rp.starService) as starService, AVG(rp.starMood) as starMood, AVG(rp.starGoods) as starGoods");
		sql.append(" FROM Reply rp, productInfos pi ");
		sql.append(" WHERE rp.pdIndex=pi.pdIndex and storeCode="); sql.append(storeCode);
		sql.append(" AND DATE(replyDate) between '");	sql.append(startDate); sql.append("' ");
		sql.append(" AND '"); sql.append(endDate); sql.append("' ");
		sql.append(" GROUP BY pdIndex;");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		list = (ArrayList<ReplyBean>)jdbcTemplate.query(sql.toString(), new ResultSetExtractor<ArrayList<ReplyBean>>(){

			@Override
			public ArrayList<ReplyBean> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<ReplyBean> datas = new ArrayList<ReplyBean>();
				while(rs.next()){
					ReplyBean bean = new ReplyBean();
					bean.setPdIndex(rs.getInt(1));
					bean.setPdName(rs.getString(2));
					bean.setPdPrice(rs.getInt(3));
					bean.setReplyCount(rs.getInt(4));
					bean.setStarService(rs.getFloat(5));
					bean.setStarMood(rs.getFloat(6));
					bean.setStarGoods(rs.getFloat(7));
					
					datas.add(bean);
				}
				return datas;
			}
		});
		return list;
	}
	
	private ArrayList<PageInfoBean> getDayPageReport(int storeCode,String date , int period){
		ArrayList<PageInfoBean> list = new ArrayList<PageInfoBean>();

		String startDate = CalendarFactory.getInstance().setCalcDateTime(date, period);
		String endDate = date;

		StringBuffer sql = new StringBuffer();

		sql.append("Select fr.nIndex , fr.title , dp.count , dp.date ");
		sql.append("from dayPageReports dp , frameInfos fr, frameKinds kind ");
		sql.append("where (dp.pageIndex=fr.nIndex) ");
		sql.append(" AND (dp.pageIndex=kind.nIndex) ");
		sql.append(" AND kind.type=0 or kind.type=2 ");
		sql.append("AND fr.appIndex = (select appIndex ");
		sql.append("from appInfos where storeCode=");sql.append(storeCode);sql.append(") ");
		sql.append(" AND dp.date between '");sql.append(startDate);sql.append("' ");
		
		sql.append(" AND '");sql.append(endDate);sql.append("' ");
		

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = (ArrayList<PageInfoBean>)jdbcTemplate.query(sql.toString(),new PageReportRowMapper());

		return list;
	}

	private ArrayList<ReportBean> getDayReport(int storeCode,String startDate , String endDate){
		ArrayList<ReportBean> list = new ArrayList<ReportBean>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT dr.nIndex , dr.downCount, dr.visitCount, dr.visitorCount , dr.date ");
		sql.append("FROM dayReports dr ");
		sql.append("WHERE storeCode =");sql.append(storeCode);
		sql.append(" AND date between '");sql.append(startDate);sql.append("' ");
		sql.append(" AND '");sql.append(endDate);sql.append("' ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = (ArrayList<ReportBean>)jdbcTemplate.query(sql.toString(),new ReportRowMapper() );

		return list;
	}

	private ArrayList<PageInfoBean> getDayPageReport(int storeCode,String startDate , String endDate){
		ArrayList<PageInfoBean> list = new ArrayList<PageInfoBean>();

		StringBuffer sql = new StringBuffer();

		sql.append("Select fr.nIndex , fr.title , dp.count , dp.date ");
		sql.append("from dayPageReports dp , frameInfos fr ");
		sql.append("where (dp.pageIndex=fr.nIndex) ");
		sql.append(" AND fr.appIndex = (select appIndex ");
		sql.append(" from appInfos where storeCode=");sql.append(storeCode);sql.append(") ");
		sql.append(" AND dp.date between '");sql.append(startDate);sql.append("' ");
		sql.append(" AND '");sql.append(endDate);sql.append("' ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = (ArrayList<PageInfoBean>)jdbcTemplate.query(sql.toString(),new PageReportRowMapper());

		return list;
	}

	private ArrayList<CouponHistoryBean> getDayCouponReport(int storeCode,String startDate , String endDate){
		ArrayList<CouponHistoryBean> list = new ArrayList<CouponHistoryBean>();

		StringBuffer sql = new StringBuffer();

//		sql.append("select cp.genIndex , cp.name , ch.date , ch.count ");
		sql.append("select ch.date ,SUM(ch.count) as count ");
		sql.append("from couponInfos cp , useCouponHistory ch ");
		sql.append("where (cp.genIndex=ch.genIndex) ");
		sql.append(" AND cp.storeCode=");sql.append(storeCode);
		sql.append(" AND ch.date between '");sql.append(startDate);sql.append("' ");
		sql.append(" AND '");sql.append(endDate);sql.append("' ");
		sql.append(" GROUP BY ch.date");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = (ArrayList<CouponHistoryBean>)jdbcTemplate.query(sql.toString(), new ResultSetExtractor<ArrayList<CouponHistoryBean>>(){

			@Override
			public ArrayList<CouponHistoryBean> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<CouponHistoryBean> datas = new ArrayList<CouponHistoryBean>();
				while(rs.next()){
					CouponHistoryBean bean = new CouponHistoryBean();
					bean.setDate(rs.getString("date"));
					bean.setCount(rs.getInt("count"));
				
					datas.add(bean);
				}
				return datas;
			}
		});
		return list;
	}

	private ArrayList<CouponHistoryBean> getDayCouponReport(int storeCode,String date , int period) {
		String startDate = CalendarFactory.getInstance().setCalcDateTime(date, period);
		String endDate = date;

		ArrayList<CouponHistoryBean> list = new ArrayList<CouponHistoryBean>();

		StringBuffer sql = new StringBuffer();

//		sql.append("select cp.genIndex , cp.name , ch.date , ch.count ");
		sql.append("select ch.date ,SUM(ch.count) as count ");
		sql.append("from couponInfos cp , useCouponHistory ch ");
		sql.append("where (cp.genIndex=ch.genIndex) ");
		sql.append(" AND cp.storeCode=");sql.append(storeCode);
		sql.append(" AND ch.date between '");sql.append(startDate);sql.append("' ");
		sql.append(" AND '");sql.append(endDate);sql.append("' ");
		sql.append(" GROUP BY ch.date");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = (ArrayList<CouponHistoryBean>)jdbcTemplate.query(sql.toString(), new ResultSetExtractor<ArrayList<CouponHistoryBean>>(){

			@Override
			public ArrayList<CouponHistoryBean> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<CouponHistoryBean> datas = new ArrayList<CouponHistoryBean>();
				while(rs.next()){
					CouponHistoryBean bean = new CouponHistoryBean();
					bean.setDate(rs.getString("date"));
					bean.setCount(rs.getInt("count"));
				
					datas.add(bean);
				}
				return datas;
			}
		});
		return list;
	}
	
	private ArrayList<CouponHistoryBean> getDayDashBoardCoupon(int storeCode,String date , int period) {
		String startDate = CalendarFactory.getInstance().setCalcDateTime(date, period);
		String endDate = date;

		ArrayList<CouponHistoryBean> list = new ArrayList<CouponHistoryBean>();

		StringBuffer sql = new StringBuffer();
		
		sql.append("select ch.date ,SUM(ch.count) as count ");
		sql.append("from couponInfos cp , useCouponHistory ch ");
		sql.append("where (cp.genIndex=ch.genIndex) ");
		sql.append(" AND cp.storeCode=");sql.append(storeCode);
		sql.append(" AND ch.date between '");sql.append(startDate);sql.append("' ");
		sql.append(" AND '");sql.append(endDate);sql.append("' ");
		sql.append(" GROUP BY ch.date");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = (ArrayList<CouponHistoryBean>)jdbcTemplate.query(sql.toString(), new ResultSetExtractor<ArrayList<CouponHistoryBean>>(){

			@Override
			public ArrayList<CouponHistoryBean> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<CouponHistoryBean> datas = new ArrayList<CouponHistoryBean>();
				while(rs.next()){
					CouponHistoryBean bean = new CouponHistoryBean();
					bean.setDate(rs.getString("date"));
					bean.setCount(rs.getInt("count"));
				
					datas.add(bean);
				}
				return datas;
			}
		});
		return list;
	}

	// 쿠폰 차트 뿌리려면 쿠폰 정보도 알아야 해서 추가함
	private ArrayList<CouponBean> getCouponGenReport(int storeCode) {
		ArrayList<CouponBean> list = new ArrayList<CouponBean>();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ck.*, cp.name");
		sql.append(" from couponKinds ck, couponInfos cp");
		sql.append(" where cp.storeCode=");sql.append(storeCode);
		sql.append(" and ck.genIndex=cp.genIndex");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		list = (ArrayList<CouponBean>)jdbcTemplate.query(sql.toString(), new ResultSetExtractor<ArrayList<CouponBean>>() {
			@Override
			public ArrayList<CouponBean> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<CouponBean> datas = new ArrayList<CouponBean>();
				while(rs.next()){
					CouponBean bean = new CouponBean();
					bean.setGenIndex(rs.getInt(1));
					bean.setType(rs.getInt(2));
					bean.setImage(rs.getString(3));
					bean.setCodePath(rs.getString(4));
					bean.setCount(rs.getInt(5));
					bean.setName(rs.getString(6));
					
					datas.add(bean);
				}
				return datas;
			}
		});
		return list;
	}
	
	/**
	 * Image Coupon의 정보를 담아온다 
	 * @return
	 */
	public ArrayList<CouponBean> getImageCouponList() {
		ArrayList<CouponBean> list = new ArrayList<CouponBean>();

		StringBuffer sql = new StringBuffer();

		sql.append("Select uc.genIndex , ci.name ");
		sql.append("from useCoupons uc , couponInfos ci ");
		sql.append("where (uc.genIndex=ci.genIndex) ");
		sql.append("AND uc.number is null");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<ArrayList<CouponBean>>(){

			@Override
			public ArrayList<CouponBean> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<CouponBean> list = new ArrayList<CouponBean>();
				while(rs.next()){
					CouponBean bean = new CouponBean();
					bean.setGenIndex(rs.getInt("genIndex"));
					bean.setName(rs.getString("name"));

					list.add(bean);
				}
				return list;
			}
		});

		return list;
	}

	public ArrayList<ReportBean> getAppList() {
		ArrayList<ReportBean> list = new ArrayList<ReportBean>();

		StringBuffer sql = new StringBuffer();

		sql.append("Select appIndex , name , storeCode ");
		sql.append("from appInfos ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<ArrayList<ReportBean>>(){

			@Override
			public ArrayList<ReportBean> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<ReportBean> list = new ArrayList<ReportBean>();
				while(rs.next()){
					ReportBean bean = new ReportBean();
					bean.setnIndex(rs.getInt("appIndex"));
					bean.setAppName(rs.getString("name"));
					bean.setStoreCode(rs.getInt("storeCode"));
					bean.setDownCount(0);
					bean.setVisitCount(0);
					bean.setVisitorCount(0);

					list.add(bean);
				}
				return list;
			}
		});

		return list;
	}

	public ArrayList<PageInfoBean> getFrameList() {
		ArrayList<PageInfoBean> list = new ArrayList<PageInfoBean>();

		StringBuffer sql = new StringBuffer();

		sql.append("Select fr.nIndex, app.storeCode , fr.title , fr.path ");
		sql.append("from frameInfos fr , appInfos app , frameKinds kind ");
		sql.append("where (fr.appIndex=app.appIndex) ");
		sql.append(" AND (fr.nIndex=kind.nIndex) ");
		sql.append(" AND fr.nIndex in(select nIndex from frameKinds where type=0 or type=2) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<ArrayList<PageInfoBean>>(){

			@Override
			public ArrayList<PageInfoBean> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<PageInfoBean> list = new ArrayList<PageInfoBean>();
				while(rs.next()){
					PageInfoBean bean = new PageInfoBean();

					bean.setPageIndex(rs.getInt("nIndex"));
					bean.setStoreCode(rs.getInt("storeCode"));
					bean.setPageName(rs.getString("title"));
					bean.setPath(rs.getString("path"));
					bean.setVisitCount(0);

					list.add(bean);
				}
				return list;
			}
		});
		return list;
	}

	/**
	 * 당일의 CouponHistory 정보를 DB에 Insert
	 */
	public void insertDailyUseCouponHistory(CouponBean data){
		StringBuffer sql=new StringBuffer();

		sql.append("Insert into UseCouponHistory (nIndex, genIndex, date, count) ");
		sql.append(" values (?,?,?,?) ");
		
		int index=getCouponMaxIndex();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String date = CalendarFactory.getInstance().setCurrentDateTime(3);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { index, data.getGenIndex(), date, data.getCount()
				});
	}

	/**
	 * 당일의 DailyHisotry DB Insert
	 */
	public void insertDailyReportHistory(ReportBean bean){
		StringBuffer sql=new StringBuffer();

		sql.append("Insert into DayReports ");
		sql.append(" (nIndex, storeCode, date, downCount, visitCount, visitorCount) ");
		sql.append(" values (?,?,?,?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String date = CalendarFactory.getInstance().setCurrentDateTime(3);

		int nIndex=getReportMaxIndex();

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { nIndex, bean.getStoreCode(), 
					date , bean.getDownCount() , bean.getVisitCount(), bean.getVisitorCount()
				});
	}

	/**
	 * DayPageReport Insert Data
	 * @param bean
	 */
	public void insertPageReport(PageInfoBean bean){
		StringBuffer sql=new StringBuffer();

		sql.append("Insert into dayPageReports ");
		sql.append(" (nIndex, pageIndex , count, Date, storeCode) ");
		sql.append(" values (?,?,?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String date = CalendarFactory.getInstance().setCurrentDateTime(3);

		int nIndex=getPageMaxIndex();

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { nIndex, bean.getPageIndex(), 
					bean.getVisitCount() , date, bean.getStoreCode()
				});
	}

	// 일주일로 한정하자
	public ReportBean getSumSummrayInfo(){

		int storeCode= SessionManager.getInstance().getSessionCode();

		ReportBean bean = new ReportBean();

		int[] array=getReportSumData(storeCode);
		
		ReportBean info=TaskManager.getInstance().getAppInfoMap().get(storeCode);
		
		bean.setVisitCount(array[0]+info.getVisitCount()); //앱만들기 안하면 null 뱉어서 에러 ㅇㅇㅇㅇ
		bean.setVisitorCount(array[1]+info.getVisitorCount());
		bean.setDownCount(array[2]+info.getDownCount());
		bean.setCouponCount(getCouponCount(storeCode));
		bean.setEventCount(getEventCount(storeCode));
		bean.setUseCouponCount(getUseCouponCount(storeCode));

		// 평균 별점, 댓글 수를 가져오는 코드 getReportSumData에 넣어도 될듯..
		bean.setReplyCount(getReplyCount(storeCode));
		bean.setStarService(getStarServiceAvg(storeCode));
		bean.setStarMood(getStarMoodAvg(storeCode));
		bean.setStarGoods(getStarGoodsAvg(storeCode));
		bean.setTotalSales(getTotalSales(storeCode));
		
		return bean;
	}
	
	private int getTotalSales(int storeCode) {
		int total;

		StringBuffer sql = new StringBuffer();

		sql.append("select SUM(pi.pdPrice) as totalSales from productInfos pi, reply rp ");
		sql.append(" where storeCode=");sql.append(storeCode);
		sql.append(" and rp.pdIndex=pi.pdIndex;");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		total = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int total=0;
				if(rs.next()){
					total=rs.getInt(1);
				}
				return total;
			}
		});
		return total;
	}

	private int getCouponCount(int storeCode){
		int count;

		StringBuffer sql = new StringBuffer();

		sql.append("select COUNT(*) as couponNum from couponInfos ");
		sql.append(" where storeCode=");sql.append(storeCode);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		count = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int count=0;
				if(rs.next()){
					count=rs.getInt("couponNum");
				}
				return count;
			}
		});
		return count;
	}

	private int getEventCount(int storeCode){
		int count;

		StringBuffer sql = new StringBuffer();

		sql.append("select COUNT(*) as eventNum ");
		sql.append(" from calendarInfos cal , calendarKinds ck ");
		sql.append(" where (cal.nIndex=ck.nIndex) ");
		sql.append(" AND ck.type=1 ");
		sql.append(" AND cal.nIndex in (select nIndex from ");
		sql.append(" calendar_store_maps where code=");sql.append(storeCode);sql.append(")");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		count = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int count=0;
				if(rs.next()){
					count=rs.getInt("eventNum");
				}
				return count;
			}
		});
		return count;
	}
	
	private int getReplyCount(int storeCode) {
		int count;

		StringBuffer sql = new StringBuffer();

		sql.append("select COUNT(nIndex) as replyCount ");
		sql.append(" from reply ");
		sql.append(" where storeCode=");sql.append(storeCode);
		sql.append(";");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		count = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int count=0;
				if(rs.next()){
					count=rs.getInt(1);
				}
				return count;
			}
		});
		return count;
	}
	
	private float getStarServiceAvg(int storeCode) {
		float avg;

		StringBuffer sql = new StringBuffer();

		sql.append("select AVG(starService) as serviceAvg ");
		sql.append(" from reply ");
		sql.append(" where storeCode=");sql.append(storeCode);sql.append(";");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		avg = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Float>(){

			@Override
			public Float extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				float avg=0;
				if(rs.next()){
					avg=rs.getFloat(1);
				}
				return avg;
			}
		});
		return avg;
	}
	
	private float getStarMoodAvg(int storeCode) {
		float avg;

		StringBuffer sql = new StringBuffer();

		sql.append("select AVG(starMood) as moodAvg ");
		sql.append(" from reply ");
		sql.append(" where storeCode=");sql.append(storeCode);sql.append(";");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		avg = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Float>(){

			@Override
			public Float extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				float avg=0;
				if(rs.next()){
					avg=rs.getFloat(1);
				}
				return avg;
			}
		});
		return avg;
	} 
	
	private float getStarGoodsAvg(int storeCode) {
		float avg;

		StringBuffer sql = new StringBuffer();

		sql.append("select AVG(starGoods) as goodsAvg ");
		sql.append(" from reply ");
		sql.append(" where storeCode=");sql.append(storeCode);sql.append(";");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		avg = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Float>(){

			@Override
			public Float extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				float avg=0;
				if(rs.next()){
					avg=rs.getFloat(1);
				}
				return avg;
			}
		});
		return avg;
	}
	
	private int[] getReportSumData(int storeCode){
		// 평균 별점, 댓글 수 추가. 실시간 정보 스케줄링 안하므로 나중에 수정이 필요할 수 잇음
		int[] array=new int[3];

		StringBuffer sql = new StringBuffer();

		sql.append("select SUM(visitCount) as visit , SUM(visitorCount) as visitor , SUM(downCount) as down");
		sql.append(" from dayReports ");
		sql.append(" where storeCode=");sql.append(storeCode);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		array = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<int[]>(){

			@Override
			public int[] extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int[] count=new int[3];
				if(rs.next()){
					count[0]=rs.getInt("visit");
					count[1]=rs.getInt("visitor");
					count[2]=rs.getInt("down");
				}
				return count;
			}
		});
		return array;
	}

	public ReportListInfo getDayReportSumRealTime(int storeCode,String date , int period) {
		ReportListInfo totalBean = new ReportListInfo();


		//전날까지의 Data를 DB에서 긁어온다.
		ArrayList<ReportBean> dayReportList = getDayReport(storeCode,date, period);
		ArrayList<PageInfoBean> pageReportList = getDayPageReport(storeCode,date, period);
		ArrayList<CouponHistoryBean> couponReportList = getDayCouponReport(storeCode, date, period);

		//현재의 Data를 합친다.
		HashMap<Integer, ReportBean> reportMap=TaskManager.getInstance().getAppInfoMap();


		totalBean.setStoreCode(storeCode);
		totalBean.setDayReport(dayReportList);
		totalBean.setPageReport(pageReportList);
		totalBean.setCouponReport(couponReportList);

		return totalBean;
	}

	public int getReportMaxIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(nIndex) as maxIndex ");
		sql.append("from dayReports");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int index = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int maxIndex=0;

				if(rs.next()){
					maxIndex = rs.getInt("maxIndex");

					maxIndex= maxIndex+1;

					return maxIndex;
				}
				return null;
			}
		});
		return index;
	}

	public int getPageMaxIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(nIndex) as maxIndex ");
		sql.append("from dayPageReports");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int index = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int maxIndex=0;
				if(rs.next()){
					maxIndex = rs.getInt("maxIndex");

					maxIndex= maxIndex+1;

					return maxIndex;
				}
				return null;
			}
		});
		return index;
	}
	
	private int getUseCouponCount(int storeCode){
		
		int imgCount=getImageCouponSumCount(storeCode);
		
		int numCount=getNumberCouponSumCount(storeCode);
		
		int sumValue=imgCount+numCount;
		
		return sumValue;
	}
	
	private int getImageCouponSumCount(int storeCode){
		int count;

		StringBuffer sql = new StringBuffer();

		sql.append("select SUM(usecp.count) as sumCount ");
		sql.append("from couponInfos info , couponKinds kind, useCoupons usecp ");
		sql.append("where (info.genIndex=kind.genIndex) ");
		sql.append("AND (info.genIndex=usecp.genIndex) ");
		sql.append("AND kind.type=0 ");
		sql.append("AND info.storeCode=");sql.append(storeCode);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		count = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int count=0;
				if(rs.next()){
					count=rs.getInt("sumCount");
				}
				return count;
			}
		});
		return count;
	}
	
	private int getNumberCouponSumCount(int storeCode){
		int count;

		StringBuffer sql = new StringBuffer();

		sql.append("select SUM(usecp.nEnable) as sumCount ");
		sql.append("from couponInfos info , couponKinds kind, useCoupons usecp ");
		sql.append("where (info.genIndex=kind.genIndex) ");
		sql.append("AND (info.genIndex=usecp.genIndex) ");
		sql.append("AND kind.type=1 ");
		sql.append("AND info.storeCode=");sql.append(storeCode);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		count = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int count=0;
				if(rs.next()){
					count=rs.getInt("sumCount");
				}
				return count;
			}
		});
		return count;
	}
	
	public int getCouponMaxIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(nIndex) as maxIndex ");
		sql.append("from useCouponHistory");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int index = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int maxIndex=0;
				if(rs.next()){
					maxIndex = rs.getInt("maxIndex");

					maxIndex= maxIndex+1;

					return maxIndex;
				}
				return null;
			}
		});
		return index;
	}
}
