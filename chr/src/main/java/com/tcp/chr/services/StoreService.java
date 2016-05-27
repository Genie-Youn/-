package com.tcp.chr.services;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.tcp.chr.entities.Account;

@Service("storeService")
public class StoreService {

	private static final Logger LOG = LoggerFactory.getLogger(StoreService.class);

	@Autowired
	RedisTemplate<Long, List> redisTemplate;
	
	@Resource(name="redisTemplate")
	private ValueOperations<Long, List> valueOps;
	
	/**
	 * redis에서 사용자의 seqAccount값으로 추천목록을 가져온다.
	 * 
	 * @param req
	 * @return List<Store>
	 */
	public List getCurationList(HttpServletRequest req){
		Account ac = (Account)req.getSession().getAttribute("account");
		List curationList = valueOps.get(ac.getSeqAccount());
		return curationList;
	}
}
