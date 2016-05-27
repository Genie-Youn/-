package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.MemberStoreMapBean;

public class MemberStoreMapRowMapper implements RowMapper<MemberStoreMapBean> {

	@Override
	public MemberStoreMapBean mapRow(ResultSet rs, int index) throws SQLException {
		MemberStoreMapExtractor extractor = new MemberStoreMapExtractor();
		return extractor.extractData(rs);
	}
}
