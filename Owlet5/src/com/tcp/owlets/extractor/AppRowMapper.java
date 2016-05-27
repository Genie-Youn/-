package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.AdderInfo;


public class AppRowMapper implements RowMapper<AdderInfo>{

	@Override
	public AdderInfo mapRow(ResultSet rs, int line) throws SQLException {
		AppExtractor extractor = new AppExtractor();
		return extractor.extractData(rs);
	}
}
