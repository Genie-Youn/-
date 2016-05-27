package com.tcp.chr.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tcp.chr.annotations.API;
import com.tcp.chr.annotations.AccessRole;
import com.tcp.chr.annotations.API.Useage;
import com.tcp.chr.annotations.AccessRole.Role;
import com.tcp.chr.entities.Store;
import com.tcp.chr.entities.StoreStatus;

@Controller
public class TestController {
	
	@Autowired
	RedisTemplate<Long, List> redisTemplate;
	
	@Resource(name="redisTemplate")
	private ValueOperations<Long, List> valueOps;
	
	@API(Useage.Experimental)
	@AccessRole(role = Role.selector)
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public void redis(){
		System.out.println("시작");
		Store test1 = new Store();
		Store test2 = new Store();
		Store test3 = new Store();
		List list = new ArrayList<Store>();
		List tag = new ArrayList<String>();
		tag.add("한식");
		tag.add("점심");
		test1.setSeqStore((long)1);
		test1.setStar("4.0");
		test1.setStatus(StoreStatus.normal);
		test1.setStoreName("봉구스 밥버거");
		test1.setTag(tag);
		
		test2.setSeqStore((long)2);
		test2.setStar("1.0");
		test2.setStatus(StoreStatus.busy);
		test2.setStoreName("미미네");
		test2.setTag(tag);
		
		test3.setSeqStore((long)3);
		test3.setStar("2.0");
		test3.setStatus(StoreStatus.normal);
		test3.setStoreName("유진이네");
		test3.setTag(tag);
		
		list.add(test1);
		list.add(test2);
		list.add(test3);
		
		valueOps.set((long)0, list);
		System.out.println(valueOps.get((long)0));
	}
}
