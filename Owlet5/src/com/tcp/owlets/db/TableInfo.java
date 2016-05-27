package com.tcp.owlets.db;

import java.util.ArrayList;

/**
 * Table 정보 저장용 Bean
 * 
 * 테이블 네임과 컬럼들에 대한 정보를 ArrayList로 저장하도록 했다.
 * 
 * 음..그렇게 좋은 구조는 아닌듯 하지만.. 그냥 배열보다야 빠르다.
 * 
 * @author blessldk
 *
 */
public class TableInfo {

	private String tableName;
	
	private ArrayList<ColumnInfo> columnList;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<ColumnInfo> getColumnList() {
		return columnList;
	}

	public void setColumnList(ArrayList<ColumnInfo> columnList) {
		this.columnList = columnList;
	}
}
