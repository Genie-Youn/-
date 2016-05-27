package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.MemberStoreMapBean;

public class MemberStoreMapExtractor implements ResultSetExtractor<MemberStoreMapBean> {

	@Override
	public MemberStoreMapBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		MemberStoreMapBean bean = new MemberStoreMapBean();
		bean.setnIndex(rs.getInt("nIndex"));
		bean.setStoreCode(rs.getInt("storeCode"));
		return bean;
	}

}
