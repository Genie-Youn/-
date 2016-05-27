package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.CouponHistoryBean;

public class ReportHistoryExtractor implements ResultSetExtractor<CouponHistoryBean>{
	@Override
	public CouponHistoryBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		CouponHistoryBean bean = new CouponHistoryBean();
		bean.setGenIndex(rs.getInt(1));
		bean.setName(rs.getString(2));
		bean.setDate(rs.getString(3));
		bean.setCount(rs.getInt(4));
		return bean;
	}

}
