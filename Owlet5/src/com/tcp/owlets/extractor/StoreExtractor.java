package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.StoreInfo;

public class StoreExtractor implements ResultSetExtractor<StoreInfo>{

	@Override
	public StoreInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		StoreInfo info = new StoreInfo();
		info.setCode(rs.getInt(1));
		info.setStoreName(rs.getString(2));
		info.setImage(rs.getString(3));
		info.setPhone(rs.getString(4));
		info.setAddress(rs.getInt(5));
		info.setUrl(rs.getString(6));
		return info;
	}

}
