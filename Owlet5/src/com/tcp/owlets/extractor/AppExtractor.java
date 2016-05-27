package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.AdderInfo;

public class AppExtractor implements ResultSetExtractor<AdderInfo> {

	@Override
	public AdderInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		AdderInfo bean = new AdderInfo();
		bean.setnIndex(rs.getInt(1));
		bean.setTitle(rs.getString(2));
		bean.setContent(rs.getString(3));
		bean.setPath(rs.getString(4));
		
		return bean;
	}


}
