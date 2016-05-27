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
import org.springframework.web.multipart.MultipartFile;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.AppSettingInfo;
import com.tcp.owlets.extractor.AppOptionExtractor;
import com.tcp.owlets.extractor.AppOptionMapper;
import com.tcp.owlets.extractor.NoticeRowMapper;
import com.tcp.util.FileManager;
import com.tcp.util.SessionManager;

@Repository
public class OptionSqlMapper {
	@Autowired
	DataSource dataSource;
 
	public void insertMenu(int num , int index){
		StringBuffer sql=new StringBuffer();

		sql.append("Update FrameKinds set nEnable=? , menuEnable=? , menuNum=? ");
		sql.append(" where nIndex=?");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {1 , 1 , num , index});
	}

	public AppSettingInfo selectSettingInfo(int code){
		StringBuffer sql=new StringBuffer();
		sql.append("Select appIndex, layout, color, image, bgColor, pathfile ");
		sql.append(" from AppSettingInfos ");
		sql.append(" where appIndex=");
		sql.append("(Select appIndex from AppInfos where storeCode=");
		sql.append(code);sql.append(")");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		List<AppSettingInfo> list= new ArrayList<AppSettingInfo>();

		list=jdbcTemplate.query(sql.toString(), new AppOptionMapper());

		return (AppSettingInfo)list.get(0);
	}

	public ArrayList<AdderInfo> selectMenuData(int code){
		StringBuffer sql=new StringBuffer();

		sql.append("Select info.nIndex, info.title , kind.type ");
		sql.append(" from frameInfos info , frameKinds kind ");
		sql.append(" where (info.nIndex=kind.nIndex) ");
		sql.append(" AND info.appIndex = (select appIndex from appInfos ");
		sql.append(" where storeCode=");sql.append(code);sql.append(")");
		sql.append(" AND kind.menuEnable=1");
		sql.append(" ORDER BY kind.menuNum ASC ");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		List<AdderInfo> menuList=new ArrayList<AdderInfo>();

		menuList = jdbcTemplate.query(sql.toString() , new ResultSetExtractor<ArrayList<AdderInfo>>() {
			@Override
			public ArrayList<AdderInfo> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				ArrayList<AdderInfo> list=new ArrayList<AdderInfo>();
				while(rs.next()){
					AdderInfo info =new AdderInfo();

					info.setnIndex(rs.getInt("nIndex"));
					info.setTitle(rs.getString("title"));
					info.setPath(rs.getString("type")+"\\s"+info.getnIndex());

					list.add(info);
				}
				return list;
			}
		});
		return (ArrayList<AdderInfo>)menuList;
	}

	public void initMenuConfig(int storeCode){
		StringBuffer sql=new StringBuffer();

		sql.append("Update frameKinds set menuNum=0 , menuEnable=0, nEnable=0 ");
		sql.append(" where nIndex in (");
		sql.append(" select nIndex from frameInfos where appIndex=(");
		sql.append(" select appIndex from appInfos where storeCode=? ))");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql.toString(),
				new Object[] {storeCode});
	}

	public void updateSettingInfo(String bColor, String fColor , MultipartFile file , int change){
		int code=SessionManager.getInstance().getSessionCode();
		
		if(file!=null){
			FileManager.getInstance().saveServerFile(file, 2);
			
			String image = FileManager.getInstance().getFileName() 
					+ FileManager.getInstance().getFileType();
			
			StringBuffer sql=new StringBuffer();
			
			sql.append("Update AppSettingInfos set color=? , bgColor=? , image=? ");
			sql.append("where appIndex=");
			sql.append("(Select appIndex from appInfos where storeCode=");
			sql.append(code);sql.append(")");
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(
					sql.toString(),
					new Object[] { fColor, bColor, image});
			
		}else{
			StringBuffer sql=new StringBuffer();

			sql.append("Update AppSettingInfos set color=? , bgColor=? ");
			sql.append("where appIndex=");
			sql.append("(Select appIndex from appInfos where storeCode=");
			sql.append(code);sql.append(")");
			
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(
					sql.toString(),
					new Object[] { fColor, bColor});
			
		}
	}

}
