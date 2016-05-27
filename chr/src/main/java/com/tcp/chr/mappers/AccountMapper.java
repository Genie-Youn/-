package com.tcp.chr.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tcp.chr.entities.Account;


/**
 *  AccountMapper
 * 
 *  계정 DAO
 *  @date 2016.05.25
 *  @author genie
 *  @sicne 1.0
 */
@Repository("accountMapper")
public class AccountMapper extends AbstractMapper {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccountMapper.class);
	
	public Account getAccountByEmail(String email){
		return (Account)selectOne("Account.selectAccountByEmail");
	}

}
