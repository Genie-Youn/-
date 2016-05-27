package com.tcp.owlets.db;

/**
 * PrimaryKey 를 별도로 등록할 경우가 있을 수도 있어
 * 만들어 놓긴하였으나.. 각각의 테이블이 그렇게 크지 않고..
 * 막상 적다보니... 음 별로 필요없네..?
 * 
 * 그치만 혹시 모르니 일단 냅두는걸로..
 * @author blessldk
 *
 */
public class PrimaryKeyInfo {

	private String column;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}
}
