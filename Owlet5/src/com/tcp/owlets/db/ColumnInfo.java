package com.tcp.owlets.db;

/**
 * 컬럼 정보 저장 
 * 컬럼 이름 하고 컬럼 타입 저장함
 * @author blessldk
 *
 */
public class ColumnInfo {

	private String name;
	private String type;
	
	public ColumnInfo(String name , String type){
		this.setName(name);
		this.setType(type);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
