package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.CouponHistoryBean;


public class ReportHistoryRowMapper implements RowMapper<CouponHistoryBean>{
	@Override
	public CouponHistoryBean mapRow(ResultSet rs, int line)
			throws SQLException {
		ReportHistoryExtractor extractor = new ReportHistoryExtractor();
		return extractor.extractData(rs);
	}
}
