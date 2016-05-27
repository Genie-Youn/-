package com.tcp.util;

import java.util.Comparator;

import com.tcp.owlets.bean.PageInfoBean;

public class CollectorSort implements Comparator<PageInfoBean>{
	
	private static CollectorSort instance=null;
	
	public static CollectorSort getInstance(){
		if(instance==null){
			instance=new CollectorSort();
		}
		return instance;
	}
	
	@Override
	public int compare(PageInfoBean p1, PageInfoBean p2) {
		return p1.getVisitCount() > p2.getVisitCount() ? -1 : p1.getVisitCount() < p2.getVisitCount() ? 1:0;
	}

}
