package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.MemberBean;

public class MemberExtractor implements ResultSetExtractor<MemberBean>{

	@Override
	public MemberBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		MemberBean bean = new MemberBean();
		bean.setnIndex(rs.getInt(1));
		bean.setName(rs.getString(2));
		bean.setMail(rs.getString(3));
		bean.setPasswd(rs.getString(4));
		bean.setNickName(rs.getString(5));
		
		return bean;
	}

}
