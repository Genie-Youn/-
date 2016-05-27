package com.tcp.owlets.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcp.owlets.bean.MemberBean;
import com.tcp.owlets.bean.MemberStoreMapBean;
import com.tcp.owlets.bean.StoreInfo;
import com.tcp.owlets.bean.StoreKindsBean;
import com.tcp.owlets.dao.MemberSqlMapper;


/**
 * 회원가입등 기본 기능에 대한 Service 작업이다.
 * BasicService 는 Interface 로 만들어서 method를 관리하자
 * @author blessldk
 *
 */
@Service
public class BasicHandler{

	@Autowired
	MemberSqlMapper memberDao;
	
	public MemberBean getMember(int index) {
		return memberDao.getMember(index);
	}

	public void updateMember(MemberBean member) {
		memberDao.updateMember(member);
	}

	public void insertMember(MemberBean member) {
		memberDao.insertMember(member);
	}

	public void deleteMember(int index) {
		memberDao.deleteMember(index);
	}

	public int checkLogin(int index) {
		// TODO Auto-generated method stub
		return memberDao.checkLogin(index);
	}

	public MemberStoreMapBean getCheckLogin(String mail, String passwd) {
		// TODO Auto-generated method stub
		return memberDao.getnIndex(mail, passwd);
	}

	public void insertStoreInfo(StoreInfo storeInfo) {
		memberDao.insertStoreInfo(storeInfo);
	}
	public void insertStoreKinds(StoreKindsBean storeKindsBean){
		memberDao.insertStoreKinds(storeKindsBean);
	}

	public void insertMemStoreMap() {
		memberDao.insertMemStoreMap();
	}
	
	/**
	 * 소상공인 기업 정보 
	 * @param storeCode
	 * @return
	 */
	public StoreInfo getStoreInfo(int storeCode){
		return memberDao.getStoreInfo(storeCode);
	}
	
	public void updateStoreInfo(StoreInfo info , int code , MultipartFile file) {
		memberDao.updateStore(info , code ,file);
	}
}