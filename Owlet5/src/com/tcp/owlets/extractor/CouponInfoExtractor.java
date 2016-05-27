package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.CouponBean;

public class CouponInfoExtractor implements ResultSetExtractor<CouponBean> {
	@Override
	public CouponBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		CouponBean bean = new CouponBean();
		bean.setGenIndex(rs.getInt(1));
		bean.setStoreCode(rs.getInt(2));
		bean.setName(rs.getString(3));
		bean.setType(rs.getInt(4));
		bean.setOriginType(rs.getInt(4));
		bean.setImage(rs.getString(5));
		bean.setCodePath(rs.getString(6));
		bean.setStartDate(rs.getString(7));
		bean.setEndDate(rs.getString(8));
		bean.setCount(rs.getInt(9));
		
		return bean;
	}

}
