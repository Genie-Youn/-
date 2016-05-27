package com.tcp.owlets.handler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.dao.NoticeSqlMapper;

@Service
public class NoticeHandler{

	@Autowired
	NoticeSqlMapper noticeDao;
	
	public ArrayList<EventNoticeBean> getList(int index) {
		return noticeDao.getNoticeList(index);
	}
}
