package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.MemberBean;


public class MemberRowMapper implements RowMapper<MemberBean> {

	@Override
	public MemberBean mapRow(ResultSet rs, int index) throws SQLException {
		MemberExtractor extractor = new MemberExtractor();
		return extractor.extractData(rs);
	}

}
