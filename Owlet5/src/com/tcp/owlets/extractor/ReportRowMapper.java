package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.ReportBean;

public class ReportRowMapper implements RowMapper<ReportBean> {

	@Override
	public ReportBean mapRow(ResultSet rs, int line) throws SQLException {
		ReportExtractor extractor = new ReportExtractor();
		return extractor.extractData(rs);
	}

}
