package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.AppSettingInfo;

public class AppOptionExtractor implements ResultSetExtractor<AppSettingInfo> {

	@Override
	public AppSettingInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		AppSettingInfo bean = new AppSettingInfo();
		bean.setAppIndex(rs.getInt(1));
		bean.setLayout(rs.getInt(2));
		bean.setColor(rs.getString(3));
		bean.setImage(rs.getString(4));
		bean.setBgColor(rs.getString(5));
		bean.setPathFile(rs.getString(6));
		
		return bean;
	}

}
