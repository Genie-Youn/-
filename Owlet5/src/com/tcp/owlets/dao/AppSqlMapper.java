package com.tcp.owlets.dao;

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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.AppInfo;
import com.tcp.owlets.bean.AppSettingInfo;
import com.tcp.owlets.bean.PageInfoBean;
import com.tcp.owlets.bean.ReportBean;
import com.tcp.owlets.extractor.AppRowMapper;
import com.tcp.util.AppManager;
import com.tcp.util.CalendarFactory;
import com.tcp.util.PathFactory;
import com.tcp.util.SessionManager;
import com.tcp.util.TaskManager;

@Repository
public class AppSqlMapper {
	@Autowired
	DataSource dataSource;

	private int adderIndex=0;

	private int groupIdx=0;

	public void insertFrame(AdderInfo adderInfo , int type) {
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into frameInfos ");
		sql.append("(nIndex, title , content, date, path, nlevel, appIndex) ");
		sql.append("VALUES (?,?,?,?,?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int nIndex= getMaxIndex();

		setAdderIndex(nIndex);

		int storeCode=SessionManager.getInstance().getSessionCode();

		//Frame path create......
		PathFactory.getInstance().createFramePath(nIndex, type);

		String path=PathFactory.getInstance().getUrlPath();

		/*
		 * appIndex 를 검색해서 가져온다. 모든 appIndex는 Server가 도는 동안 HashMap에 들어있다.
		 * 해당 HashMap은 Server 시작시 갱신한다.
		 */
		int appIndex=AppManager.getInstance().findAppIndex(storeCode);


		String date = CalendarFactory.getInstance().setCurrentDateTime(1);

		PageInfoBean page=new PageInfoBean();
		page.setPageIndex(nIndex);
		page.setPageName(adderInfo.getTitle());
		page.setVisitCount(0);
		page.setStoreCode(storeCode);

		if(type==0 || type==2){
			TaskManager.getInstance().addPageInfo(page);
		}
		
		if(adderInfo.getTitle().equals("")){
			adderInfo.setTitle("제목없음");
		}

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { nIndex , adderInfo.getTitle(), adderInfo.getContent(),
					date , path , adderInfo.getLevel() , appIndex
				});
	}

	public void updateFrame(AdderInfo adderInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("Update frameInfos set title=? , content=? , date=? ");
		sql.append(" where nIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String date = CalendarFactory.getInstance().setCurrentDateTime(1);

		jdbcTemplate.update(
				sql.toString(),
				new Object[]{adderInfo.getTitle() , adderInfo.getContent() ,
					date , adderInfo.getnIndex()
				});
	}

	public void deleteFrame(int frameIndex) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from frameInfos ");
		sql.append("where nIndex=");sql.append(frameIndex);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());
	}

	public void deleteFrameKinds(int frameIndex) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from frameKinds ");
		sql.append("where nIndex=");sql.append(frameIndex);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());
	}
	
	public void deletePageReports(int frameIndex) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from PageReports ");
		sql.append("where nIndex=");sql.append(frameIndex);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());
	}

	public AdderInfo getFrame(int frameIndex) {
		AdderInfo adderInfo = new AdderInfo();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		StringBuffer sql = new StringBuffer();
		sql.append("select nIndex , title , content ");
		sql.append("from frameInfos where nIndex=");sql.append(frameIndex);

		adderInfo = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<AdderInfo>() {
			@Override
			public AdderInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				AdderInfo info =new AdderInfo();

				while(rs.next()){
					info.setnIndex(rs.getInt("nIndex"));
					info.setTitle(rs.getString("title"));
					info.setContent(rs.getString("content"));

					return info;
				}
				return null;
			}
		});

		return adderInfo;
	}

	public int getMaxIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(nIndex) as maxIndex ");
		sql.append("from frameInfos");

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

	public ArrayList<AdderInfo> getFrameList(int codeIndex) {
		List<AdderInfo> frameList = new ArrayList<AdderInfo>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		StringBuffer sql = new StringBuffer();
		sql.append("select info.nIndex , info.title , info.content , info.path ");
		sql.append("from frameInfos info , frameKinds kind ");
		sql.append(" where appIndex = ( select appIndex ");
		sql.append(" from appInfos where storeCode=");sql.append(codeIndex);
		sql.append(") ");
		sql.append(" AND kind.type=0");
		sql.append(" AND (info.nIndex=kind.nIndex) ");
		sql.append(" ORDER BY nIndex ASC ");

		frameList = jdbcTemplate.query(sql.toString(), new AppRowMapper());

		return (ArrayList<AdderInfo>) frameList;
	}

	public ArrayList<AdderInfo> getUrlList(int codeIndex) {
		List<AdderInfo> frameList = new ArrayList<AdderInfo>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		StringBuffer sql = new StringBuffer();
		sql.append("select nIndex , title ,content , path ");
		sql.append("from frameInfos ");
		sql.append(" where appIndex = ( select appIndex ");
		sql.append(" from appInfos where storeCode=");sql.append(codeIndex);
		sql.append(") ");
		sql.append(" ORDER BY nIndex ASC ");

		frameList = jdbcTemplate.query(sql.toString(), new AppRowMapper());

		return (ArrayList<AdderInfo>) frameList;
	}
	public void insertAppInfo(AppInfo info , int storeCode){
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into appInfos ");
		sql.append("(appIndex, name , storeCode, version , confirm ) ");
		sql.append("VALUES (?,?,?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int nIndex= getAppMaxIndex();

		AppManager.getInstance().addApp(storeCode, nIndex);

		ReportBean bean=new ReportBean();
		bean.setnIndex(nIndex);
		bean.setAppName(info.getName());
		bean.setStoreCode(storeCode);

		TaskManager.getInstance().addAppMapInfo(bean);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { nIndex , info.getName(), storeCode, info.getVersion(), 0});
	}
	
	public int getAppMaxIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(appIndex) as maxIndex ");
		sql.append("from appInfos");

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

	public ArrayList<AppInfo> getAppList(){
		int storeCode=SessionManager.getInstance().getSessionCode();

		List<AppInfo> appList = new ArrayList<AppInfo>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		StringBuffer sql = new StringBuffer();
		sql.append("select appIndex, name , storeCode, version ");
		sql.append("from appInfos ");
		sql.append(" where storeCode=");sql.append(storeCode);
		sql.append(" ORDER BY nIndex ASC ");

		appList = jdbcTemplate.query(sql.toString(), new ResultSetExtractor<List<AppInfo>>(){

			@Override
			public List<AppInfo> extractData(ResultSet rs) throws SQLException,
			DataAccessException {

				List<AppInfo> appList = new ArrayList<AppInfo>();

				while(rs.next()){
					AppInfo info = new AppInfo();
					info.setAppIndex(rs.getInt("appIndex"));
					info.setName(rs.getString("name"));
					info.setStoreCode(rs.getInt("storeCode"));
					info.setVersion(rs.getInt("version"));

					appList.add(info);
				}
				return appList;
			}
		});

		return (ArrayList<AppInfo>) appList;
	}

	public boolean isAppBasicExist(){
		int storeCode = SessionManager.getInstance().getSessionCode();

		StringBuffer sql = new StringBuffer();
		sql.append("Select Count(*) as exist from appInfos ");
		sql.append("where storeCode=");sql.append(storeCode);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int exist = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int exist=0;

				if(rs.next()){
					exist = rs.getInt("exist");

					return exist;
				}
				return exist;
			}
		});

		boolean isExist= exist==0?false:true;

		return isExist;

	}

	/**
	 * Frame 종류를 Insert 한다.
	 * @param adderInfo
	 * @param type (0: adderFrame , 1: FrameList , 2:Store , 3:Coupon , 4:CouponList)
	 */
	public void insertFrameKind(AdderInfo adderInfo , int type) {
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into frameKinds ");
		sql.append("(nIndex, type, nEnable , menuEnable  , menuNum) ");
		sql.append("VALUES (?,?,?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { getAdderIndex() , type, 0, 0 , 0});
	}

	public int getAdderIndex() {
		return adderIndex;
	}

	public void setAdderIndex(int adderIndex) {
		this.adderIndex = adderIndex;
	}

	/**
	 * Store Mobile Page가 있는지 검사
	 * @return
	 */
	public int isStoreFrameExist(){
		int storeCode = SessionManager.getInstance().getSessionCode();

		StringBuffer sql = new StringBuffer();
		sql.append("Select info.nIndex ");
		sql.append("from frameInfos info , frameKinds kind ");
		sql.append("where (info.nIndex=kind.nIndex) ");
		sql.append("AND kind.type=2 ");
		sql.append("AND info.appIndex=");
		sql.append("(select appIndex from appInfos where storeCode=");
		sql.append(storeCode);sql.append(")");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int exist = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int exist=0;

				if(rs.next()){
					exist = rs.getInt("nIndex");

					return exist;
				}else{
					return -1;
				}
			}
		});

		return exist;
	}

	public int isWebAppFrameExist(){
		int storeCode = SessionManager.getInstance().getSessionCode();

		StringBuffer sql = new StringBuffer();
		sql.append("Select info.nIndex ");
		sql.append("from frameInfos info , frameKinds kind ");
		sql.append("where (info.nIndex=kind.nIndex) ");
		sql.append("AND kind.type=6 ");
		sql.append("AND info.appIndex=");
		sql.append("(select appIndex from appInfos where storeCode=");
		sql.append(storeCode);sql.append(")");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int exist = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int exist=0;

				if(rs.next()){
					exist = rs.getInt("nIndex");

					return exist;
				}
				return 0;
			}
		});

		return exist;
	}
	
	/**
	 * NoticeList Page가 있는지 검사.
	 * @return
	 */
	public boolean isNoticeListFrameExist(){
		int storeCode = SessionManager.getInstance().getSessionCode();

		StringBuffer sql = new StringBuffer();
		sql.append("Select Count(*) as exist ");
		sql.append("from frameInfos info , frameKinds kind ");
		sql.append("where (info.nIndex=kind.nIndex) ");
		sql.append("AND kind.type=1 ");
		sql.append("AND info.appIndex=");
		sql.append("(select appIndex from appInfos where storeCode=");
		sql.append(storeCode);sql.append(")");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int exist = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int exist=0;

				if(rs.next()){
					exist = rs.getInt("exist");

					return exist;
				}
				return null;
			}
		});

		boolean isExist= exist==0?false:true;

		return isExist;

	}

	/**
	 * CouponList Page가 있는지 검사.
	 * @return
	 */
	public boolean isCouponListFrameExist(){
		int storeCode = SessionManager.getInstance().getSessionCode();

		StringBuffer sql = new StringBuffer();
		sql.append("Select Count(*) as exist ");
		sql.append("from frameInfos info , frameKinds kind ");
		sql.append("where (info.nIndex=kind.nIndex) ");
		sql.append("AND kind.type=4 ");
		sql.append("AND info.appIndex=");
		sql.append("(select appIndex from appInfos where storeCode=");
		sql.append(storeCode);sql.append(")");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int exist = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int exist=0;

				if(rs.next()){
					exist = rs.getInt("exist");

					return exist;
				}
				return null;
			}
		});

		boolean isExist= exist==0?false:true;

		return isExist;

	}

	/**
	 * Frame 추가시 PageReport에 함께 추가한다.
	 * @param frameIndex
	 */
	public void insertPageReport(){
		StringBuffer sql= new StringBuffer();

		sql.append("Insert into pageReports (nIndex, visitCount) ");
		sql.append(" values(?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { getAdderIndex(),0});
	}

	public void insertAppSettingInfo(AppSettingInfo settingInfo , int storeCode) {
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into AppSettingInfos ");
		sql.append("(appIndex , layout , color , image , bgColor , pathfile) ");
		sql.append("VALUES (?,?,?,?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		/*
		 * appIndex 를 검색해서 가져온다. 모든 appIndex는 Server가 도는 동안 HashMap에 들어있다.
		 * 해당 HashMap은 Server 시작시 갱신한다.
		 */
		int appIndex=AppManager.getInstance().findAppIndex(storeCode);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { appIndex, settingInfo.getLayout(),settingInfo.getColor(),
					settingInfo.getImage(),settingInfo.getBgColor() , settingInfo.getPathFile()
				});
	}

	public int insertGroupData(int storeCode,String groupName){
		StringBuffer sql=new StringBuffer();

		sql.append("Insert into GroupInfos (nIndex, storeCode, name) ");
		sql.append(" values (?,?,?) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int groupIndex=getAdderIndex();

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { groupIndex, storeCode ,  groupName});

		return groupIndex;
	}

	public void updateGroupData(int groupIndex, String groupName){
		StringBuffer sql=new StringBuffer();

		sql.append("Update GroupInfos set name=? ");
		sql.append(" where nIndex=? ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { groupName, groupIndex });
	}

	public void deleteGroupData(int groupIndex){
		StringBuffer sql=new StringBuffer();

		sql.append("Delete from GroupInfos where nIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { groupIndex});
	}

	public int getGroupIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(nIndex) as maxIndex ");
		sql.append("from GroupInfos");

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

	public ArrayList<AdderInfo> getGroupMenu(int storeCode){
		ArrayList<AdderInfo> list =new ArrayList<AdderInfo>();

		StringBuffer sql=new StringBuffer();

		sql.append("Select nIndex, name ");
		sql.append("from groupInfos ");
		sql.append("where storeCode=");sql.append(storeCode);
		sql.append(" ORDER BY nIndex ASC ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		list= jdbcTemplate.query(sql.toString() , new ResultSetExtractor<ArrayList<AdderInfo>>() {
			@Override
			public ArrayList<AdderInfo> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<AdderInfo> list =new ArrayList<AdderInfo>();

				while(rs.next()){
					AdderInfo bean =new AdderInfo();

					bean.setGroupIndex(rs.getInt("nIndex"));
					bean.setGroupName(rs.getString("name"));

					list.add(bean);
				}

				return list;
			}
		});
		return list;
	}

	public ArrayList<AdderInfo> getGroupFrame(int storeCode){

		ArrayList<AdderInfo> info=new ArrayList<AdderInfo>();

		StringBuffer sql=new StringBuffer();

		sql.append("Select nIndex, groupIndex, title ");
		sql.append(" from frameInfos ");
		sql.append(" where groupIndex in (select g.nIndex from groupInfos g where g.storeCode=");
		sql.append(storeCode);sql.append(")");

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
					bean.setTitle(rs.getString("title"));

					list.add(bean);
				}

				return list;
			}
		});
		return info;
	}

	public void insertGroupConfigData(int[] data , int groupIndex){
		StringBuffer sql=new StringBuffer();

		sql.append("Update FrameInfos set groupIndex=?");
		sql.append(" where nIndex in ");
		sql.append("(");sql.append(data[0]);
		for(int i=1; i < data.length ;i++){
			sql.append(",");
			sql.append(data[i]);
		}
		sql.append(")");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {groupIndex});
	}

	public void initGroupConfig(int storeCode){
		StringBuffer sql=new StringBuffer();

		sql.append("Update frameInfos set groupIndex=null ");
		sql.append(" where appIndex = (");
		sql.append(" select appIndex from appInfos where storeCode=? )");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {storeCode});
	}
	
	/**
	 * FrameInfo에 저장된 모든 Frame을 가져온다
	 * 
	 * 단 6번은 App Index Page로 불러올 경우 내부에 앱이 생기므로 
	 * 
	 * 제외하고 가져온다.
	 * @param codeIndex
	 * @return
	 */
	public ArrayList<AdderInfo> getTotalFrameList(int codeIndex) {
		List<AdderInfo> frameList = new ArrayList<AdderInfo>();

		StringBuffer sql = new StringBuffer();
		sql.append("select info.nIndex , info.title , kind.type ");
		sql.append("from frameInfos info, frameKinds kind ");
		sql.append(" where (info.nIndex=kind.nIndex) ");
		sql.append(" AND appIndex = ( select appIndex ");
		sql.append(" from appInfos where storeCode=");sql.append(codeIndex);
		sql.append(") ");
		sql.append(" AND kind.type < 6");
		sql.append(" ORDER BY nIndex ASC ");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		frameList =jdbcTemplate.query(sql.toString() , new ResultSetExtractor<ArrayList<AdderInfo>>() {
			@Override
			public ArrayList<AdderInfo> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<AdderInfo> list=new ArrayList<AdderInfo>();

				while(rs.next()){
					AdderInfo bean=new AdderInfo();

					bean.setnIndex(rs.getInt("nIndex"));
					bean.setTitle(rs.getString("title"));
					bean.setPath(rs.getString("type")+"\\s"+bean.getnIndex());

					list.add(bean);
				}

				return list;
			}
		});
		
		return (ArrayList<AdderInfo>) frameList;
	}
	
	public AppInfo selectAppInfo(int code){
		StringBuffer sql=new StringBuffer();
		
		sql.append("Select appIndex , name , storeCode, version ");
		sql.append(" from appInfos ");
		sql.append(" where storeCode=");sql.append(code);
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		AppInfo info=new AppInfo();
		
		info = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<AppInfo>() {
			@Override
			public AppInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				AppInfo bean=new AppInfo();
				
				while(rs.next()){
					bean.setAppIndex(rs.getInt("appIndex"));
					bean.setName(rs.getString("name"));
					bean.setStoreCode(rs.getInt("storeCode"));
					bean.setVersion(rs.getInt("version"));
				}
				return bean;
			}
		});
		return info;
	}
	
	public void updateAppInfo(AppInfo bean , int code){
		StringBuffer sql=new StringBuffer();
		
		sql.append("update appInfos set name=? ");
		sql.append(" where storeCode=? ");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(
				sql.toString(),
				new Object[] {bean.getName() , code});
	}
	

	public void setGroupIndex(int groupIndex) {
		this.groupIdx = groupIndex;
	}

	public int getGroupIdx(){
		return this.groupIdx;
	}
	
	public void updateAppInfo(AppInfo info){
		int storeCode= SessionManager.getInstance().getSessionCode();
		
		StringBuffer sql = new StringBuffer();

		sql.append("update appInfos set ");
		sql.append("confirm=? ");
		sql.append(" where storeCode=?");
		sql.append(" AND appIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { 1 , storeCode , info.getAppIndex()});
	}
	
	public boolean isAppExist(){
		int storeCode = SessionManager.getInstance().getSessionCode();

		StringBuffer sql = new StringBuffer();
		sql.append("Select Count(*) as exist from appInfos ");
		sql.append("where storeCode=");sql.append(storeCode);
		sql.append(" AND confirm=1");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int exist = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				int exist=0;

				if(rs.next()){
					exist = rs.getInt("exist");

					return exist;
				}
				return exist;
			}
		});

		boolean isExist= exist==0?false:true;

		return isExist;

	}
}
