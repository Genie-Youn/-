package com.tcp.owlets;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.CouponBean;
import com.tcp.owlets.bean.PageInfoBean;
import com.tcp.owlets.bean.ReportBean;
import com.tcp.owlets.db.TableInfo;
import com.tcp.owlets.db.TableStructure;
import com.tcp.owlets.handler.AdderHandler;
import com.tcp.owlets.handler.ReportHandler;
import com.tcp.util.AppManager;
import com.tcp.util.PathFactory;
import com.tcp.util.TaskManager;


/**
 * Service Initialize Handler
 * @author blessldk
 *
 */
@Service
public class AdminHandler {

	@Autowired
	ReportHandler reportService;
	
	private Connection connPool =null;
	
	@Autowired
	DataSource dataSource;
	
	
	@PostConstruct
	public void init(){
		try{
			startServer();
//			setMonitoringAppInfo();
//			setMakedAppList();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void startServer() throws ClassNotFoundException, SQLException{
		checkDB();
		
		setMonitoringAppInfo();
//		
		setMakedAppList();
	}

	private void checkDB() throws SQLException, ClassNotFoundException{
		
		connPool = dataSource.getConnection();

		LoggerConfig.getDBLogger().debug("Check DB...");
		
		TableStructure structure = new TableStructure();
		ArrayList<TableInfo> tableList = structure.standardTable();

		Connection conn = connPool;

		Statement stmt = null;
		ResultSet rs = null;

		stmt = conn.createStatement();

		StringBuffer sql = new StringBuffer();
		sql.append("SHOW TABLES");

		rs = stmt.executeQuery(sql.toString());

		ArrayList<String> list = new ArrayList<String>();
		while(rs.next()){
			String str = rs.getString(1);
			if(str == null) str ="";
			list.add(str.toUpperCase());
		}

		StringBuffer createSql = new StringBuffer();

		for(TableInfo table : tableList){
			if(!list.contains(table.getTableName().toUpperCase())){
				createSql.setLength(0);
				createSql.append("CREATE TABLE ");
				createSql.append(table.getTableName());
				createSql.append(" ("); 
				for(int i=0; i < table.getColumnList().size() ; i++){
					createSql.append(table.getColumnList().get(i).getName());
					createSql.append(table.getColumnList().get(i).getType());
					if(i < table.getColumnList().size() -1)
						createSql.append(", \r\n");
				}

				createSql.append(") ");
//				System.out.println(createSql.toString());
				stmt.executeUpdate(createSql.toString());
			}
		}
		
		//DB Migration 을 한번 진행하면 됨. 위에 for문 처럼 돌리고 내부에 alter 문을 실행하면 됨.
		// 근데 alter 문은  모듈을 작성해서 모듈을 작성해요 급한거 아니니 나중에 하시고
	}
	
	/**
	 * 모니터 요소 Check
	 */
	private void setMonitoringAppInfo(){
		ArrayList<ReportBean> appList = reportService.getAppList();
		ArrayList<PageInfoBean> frameList = reportService.getFrameList();
		ArrayList<CouponBean> imageCouponList = reportService.getImageCouponList();
		
		TaskManager.getInstance().setAppInfoMap(appList);
		TaskManager.getInstance().setCouponsMap(imageCouponList);
		TaskManager.getInstance().setPageInfoMap(frameList);
		
		LoggerConfig.getEngineLogger().debug("AppInfo Size  : "
				+TaskManager.getInstance().getAppInfoMap().size());
		
		LoggerConfig.getEngineLogger().debug("CouponInfo Size  : "
				+TaskManager.getInstance().getCouponMap().size());
		
		LoggerConfig.getEngineLogger().debug("PageInfo Size  : "
				+TaskManager.getInstance().getPageInfoMap().size());
	}
	
	/**
	 * Check App List
	 */
	private void setMakedAppList(){
		ArrayList<ReportBean> appList = reportService.getAppList();
		
		for(ReportBean bean : appList){
			AppManager.getInstance().addApp(bean.getStoreCode(), bean.getnIndex());
		}
	}
}
