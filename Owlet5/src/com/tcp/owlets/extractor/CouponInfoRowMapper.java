package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.CouponBean;

public class CouponInfoRowMapper implements RowMapper<CouponBean>{

	@Override
	public CouponBean mapRow(ResultSet rs, int line) throws SQLException {
		CouponInfoExtractor extractor = new CouponInfoExtractor();
		return extractor.extractData(rs);
	}

}
