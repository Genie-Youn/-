package com.tcp.owlets.dao;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.MemberBean;
import com.tcp.owlets.bean.MemberStoreMapBean;
import com.tcp.owlets.bean.StoreInfo;
import com.tcp.owlets.bean.StoreKindsBean;
import com.tcp.owlets.extractor.MemberRowMapper;
import com.tcp.owlets.extractor.StoreRowMapper;
import com.tcp.util.FileManager;
import com.tcp.util.SessionManager;

/**
 * MemberTable 관련된 Sql을 작성한다.
 * 원래는 Mybatis 할까 했는데.. 귀찮기도 하고 너무 xml 많이 만드는것도 귀찮다.
 * 
 * 그냥 JDBCTemplate 만 활용해도 충분한 듯 ㅋㅋㅋ
 * @author blessldk
 *
 */
@Repository
public class MemberSqlMapper {
	@Autowired
	DataSource dataSource;
	
	private int nIndex=0;
	private int sCode=0;

	public MemberBean getMember(int index) {
		List<MemberBean> memberList = new ArrayList<MemberBean>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		StringBuffer sql = new StringBuffer();
		sql.append("select nIndex, name , mail , password , nickName ");
		sql.append("from members where nIndex=");
		sql.append(index);

		LoggerConfig.getDBLogger().debug(sql.toString());
		
		memberList = jdbcTemplate.query(sql.toString(), new MemberRowMapper());

		return memberList.get(0);
	}

	public void updateMember(MemberBean member) {
		StringBuffer sql = new StringBuffer();
		sql.append("Update members set password=?, nickName=? ");
		sql.append(" where nIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		MemberStoreMapBean bean = SessionManager.getInstance().getSession();

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { member.getPasswd() ,
					member.getNickName() , bean.getnIndex()});
	}

	public void insertMember(MemberBean member) {
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into members ");
		sql.append("(nIndex, name, mail, password, nickName) ");
		sql.append("VALUES (? , ? , ? ,? ,?)");

//		System.out.println(sql.toString());

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		//AUTO_INCREMNET를 이용하여 자동계산 할 수 있지 않을까?... 응 있어
		int index = getMaxIndex();
		
		setIndex(index);

//		System.out.println("maxIndex : " + index);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { index,member.getName() , member.getMail() ,
					member.getPasswd() , member.getNickName() });
	}

	public void deleteMember(int index) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from members ");
		sql.append("where nIndex=");
		sql.append(index);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql.toString());
	}

	public int getMaxIndex() {
		StringBuffer sql = new StringBuffer();
		sql.append("Select MAX(nIndex) as maxIndex ");
		sql.append("from members");

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

	public int checkLogin(int index) {
		StringBuffer sql = new StringBuffer();
		sql.append("Select mail, password from members ");
		sql.append(" where nIndex=");sql.append(index);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int nIndex = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if(rs.next()){
					int index=1;

					return index;
				}
				return 0;
			}
		});
		return nIndex;
	}

	public MemberStoreMapBean getnIndex(String mail, String password) {
		MemberStoreMapBean bean = new MemberStoreMapBean();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT mem.nIndex , mem.name, ms.code ");
		sql.append("from members mem , member_store_maps ms ");
		sql.append("where mem.mail='");
		sql.append(mail);sql.append("' ");
		sql.append("AND mem.password='");
		sql.append(password);sql.append("' ");
		sql.append("AND (mem.nIndex=ms.nIndex) ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
		
//		LoggerConfig.getDBLogger().debug(sql.toString());

		bean = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<MemberStoreMapBean>() {
			@Override
			public MemberStoreMapBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if(rs.next()){
					MemberStoreMapBean bean = new MemberStoreMapBean();
					bean.setnIndex(rs.getInt("nIndex"));
					bean.setStoreCode(rs.getInt("code"));
					bean.setName(rs.getString("name"));
					
//					LoggerConfig.getDBLogger().debug("bean Set : " + bean.getnIndex());

					return bean;
				}
				return null;
			}
		});
		return bean;
	}
	
	public void insertStoreInfo(StoreInfo storeInfo) {
		StringBuffer sql = new StringBuffer();
		
		LoggerConfig.getWebLogger().debug(storeInfo.getStoreName());

		sql.append("Insert into stores ");
		sql.append("(code, name, image, phone, address, url) ");
		sql.append("VALUES (? , ? , ? ,? ,? ,?)");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int code = setStoreCode(getIndex(), storeInfo.getAddress());
		
		jdbcTemplate.update(
				sql.toString(),
				new Object[] { code,storeInfo.getStoreName() , storeInfo.getImage() ,
					storeInfo.getPhone() , storeInfo.getAddress(), storeInfo.getUrl()});
	}
	/*
	 * storeKindsInfo 윤지수
	 * 2015-11-10
	 */
	public void insertStoreKinds(StoreKindsBean storeKindsBean) {
		
		StringBuffer sql = new StringBuffer();
		storeKindsBean.setStartTime(storeKindsBean.getStartTimeHour() + ":" + storeKindsBean.getStartTimeMin() + ":00");
		storeKindsBean.setEndTime(storeKindsBean.getEndTimeHour() + ":" + storeKindsBean.getEndTimeMin() + ":00");
		
		sql.append("Insert into storeKinds ");
		sql.append("(storeCode, address, groupType, startTime, endTime, timeType) ");
		sql.append("VALUES (? , ? , ? ,? ,? ,?)");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int code = setStoreCode(getIndex(), storeKindsBean.getAddress());
		
		jdbcTemplate.update(
				sql.toString(),
				new Object[] { code,storeKindsBean.getAddress() , storeKindsBean.getGroupType() ,
					storeKindsBean.getStartTime() , storeKindsBean.getEndTime(), 1});
	}
	public void insertMemStoreMap() {
		StringBuffer sql = new StringBuffer();

		sql.append("Insert into member_store_maps ");
		sql.append("(nIndex , code) ");
		sql.append("VALUES (? , ?)");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] { getIndex() , getCode()});
	}
	
	public StoreInfo getStoreInfo(int storeCode){
		List<StoreInfo> memberList = new ArrayList<StoreInfo>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		StringBuffer sql = new StringBuffer();
		sql.append("select code, name , image , phone , address , url ");
		sql.append("from stores where code=");
		sql.append(storeCode);

		memberList = jdbcTemplate.query(sql.toString(),  new StoreRowMapper());

		return memberList.get(0);
	}
	
	
	private int setStoreCode(int memIndex , int addCode){
		int code = (memIndex*100)+addCode;
		
		setCode(code);
		
		return (memIndex*100)+addCode;
	}

	private int getIndex(){
		return nIndex;
	}
	private void setIndex(int nIndex){
		this.nIndex = nIndex;
	}
	
	private int getCode(){
		return sCode;
	}
	private void setCode(int code){
		this.sCode = code;
	}
	
	public void updateStore(StoreInfo info , int code , MultipartFile file) {
		
		if(!file.getOriginalFilename().equals("")){
			FileManager.getInstance().saveServerFile(file, 2);

			String image = FileManager.getInstance().getFileName() + FileManager.getInstance().getFileType();
			
			StringBuffer sql = new StringBuffer();
			sql.append("Update stores set name=?, image=?, phone=?, url=? ");
			sql.append(" where code=?");
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			jdbcTemplate.update(
					sql.toString(),
					new Object[] { info.getStoreName() , image ,
						info.getPhone() , info.getUrl() , code});
		}else{
			StringBuffer sql = new StringBuffer();
			sql.append("Update stores set name=?,  phone=?, url=? ");
			sql.append(" where code=?");
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			jdbcTemplate.update(
					sql.toString(),
					new Object[] { info.getStoreName() , 
						info.getPhone() , info.getUrl() , code});
		}
	}
	
}
