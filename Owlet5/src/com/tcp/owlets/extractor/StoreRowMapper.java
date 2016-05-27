package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.StoreInfo;


public class StoreRowMapper implements RowMapper<StoreInfo> {

	@Override
	public StoreInfo mapRow(ResultSet rs, int arg1) throws SQLException {
		StoreExtractor extractor = new StoreExtractor();
		return extractor.extractData(rs);
	}

}
