package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.AppSettingInfo;

public class AppOptionMapper implements RowMapper<AppSettingInfo>{

	@Override
	public AppSettingInfo mapRow(ResultSet rs, int line) throws SQLException {
		AppOptionExtractor extractor = new AppOptionExtractor();
		return extractor.extractData(rs);
	}
}

