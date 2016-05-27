package com.tcp.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import com.tcp.logger.LoggerConfig;

/**
 * Client 에서 요청한 파일에 대해
 * ServerFile 로 변환 , 경로설정, 파일 확장자 설정 등
 * File 다루는 모든 기능을 함.
 * 
 * 최대한 File 처리는 이쪽에서 처리하는 것으로 한다.
 * 
 * @author blessldk
 *
 */
public class FileManager {

	private static FileManager instance=null;

//	private static int imageCount=0;
	
	private String filePath="";
	private String fileType="";
	private String fileName="";
	private String urlPath;

	public static FileManager getInstance(){
		if(instance==null){
			instance = new FileManager();
		}
		return instance;
	}

	/**
	 * 파일을 서버에 저장하는 메소드
	 * @param file
	 * @param pathIndex (0:adder 1:쿠폰 2:로고)
	 * @return
	 */
	public boolean saveServerFile(MultipartFile file , int pathIndex){
		
		LoggerConfig.getWebLogger().debug("In? :" + file.getOriginalFilename());
		
		if(!file.isEmpty()){
			try{
				byte[] bytes = file.getBytes();

				File dir = new File(setFilePath(pathIndex));
				if(!dir.exists())
					dir.mkdir();

				LoggerConfig.getFileLogger().debug("FilePath :" + dir);

				setFileName();
				setFileType(file);
				setUrlPath(pathIndex);
				
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + getFileName() + getFileType());

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				LoggerConfig.getFileLogger().debug("Success : " + getFileName() + getFileType());

				return true;
			}
			catch(Exception e){
				LoggerConfig.getFileLogger().error("You failed to upLoad " + e.getMessage());

				return false;
			}
		}else{
			LoggerConfig.getFileLogger().error("You failed to upLoad Not File Exsit");

			return false;
		}
	}

	/**
	 * Server 저장용 파일 명
	 * 
	 * @return
	 */
	public String getFileName(){
		return this.fileName;
	}

	/**
	 * File 의 이름을 생성한다.
	 * 이름 생성은  code_yymmdd_imageCount 로 명명한다.
	 * imageCount 는 계속 1씩 증가한다.
	 * @return fileName+ fileType
	 */
	private void setFileName(){
		int code = SessionManager.getInstance().getSessionCode();
		
		String nowDate = CalendarFactory.getInstance().setCurrentDateTime(2);
		
		UUID uid = UUID.randomUUID();
		
		String uidStr = uid.toString().replaceAll("-", "").substring(0, 4);
		
		StringBuffer codeName= new StringBuffer();
		codeName.append(code);
		codeName.append("_");
		codeName.append(nowDate);
		codeName.append("_");
		codeName.append(uidStr);

//		imageCount++;

		this.fileName = codeName.toString();
	}


	/**
	 * file 확장자 추출 메소드
	 * @param file
	 * @return
	 */
	private void setFileType(MultipartFile file){

		String[] fileName = file.getOriginalFilename().split("\\."); //.로 구분하고자 하는 경우 반드시 정규식;

		String type = fileName[fileName.length-1];
		
		type = "."+type;
		
		this.fileType = type;
	}
	
	public String getFileType(){
		return this.fileType;
	}

	/**
	 * 파일 경로 설정 메소드
	 * @param pathIndex (0: adder 1: Coupons 2: Logo)
	 * @return
	 */
	private String setFilePath(int pathIndex){
		int code = SessionManager.getInstance().getSessionCode();

		setFilePath(getRootPath());
		
		File dir = new File(getFilePath());
		if(!dir.exists())
			dir.mkdir();
		
		File codeDir = new File(getPrivatePath());
		if(!codeDir.exists())
			codeDir.mkdir();
		
		StringBuffer path = new StringBuffer();
		path.append(getPrivatePath());
		switch(pathIndex){
		case 0:
			path.append(File.separator);
			path.append("adder");
			break;
		case 1:
			path.append(File.separator);
			path.append("coupons");
			break;
		case 2:
			break;
		default :
			break;
		}
		return path.toString();
	}
	
	private String getRootPath(){
		String rootPath = System.getProperty("catalina.home");
		
		String customPath=ResManager.getString("config.app.path");
		
		StringBuffer path = new StringBuffer();
		
		path.append(rootPath);
		path.append(File.separator);
		path.append("webapps");
		path.append(File.separator);
		path.append(customPath);
		path.append(File.separator);
		path.append("tcpFile");
		
		return path.toString();
	}
	
	private String getPrivatePath(){
		int code = SessionManager.getInstance().getSessionCode();
		
		StringBuffer path = new StringBuffer();
		path.append(getFilePath());
		path.append(File.separator);
		path.append("res_" +code);
		
		return path.toString();
	}
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getUrlPath(){
		return urlPath;
	}
	
	public void setUrlPath(int pathIndex){
		int code = SessionManager.getInstance().getSessionCode();
		
		StringBuffer path = new StringBuffer();
		
		path.append(File.separator);
		path.append("tcpFile");
		path.append(File.separator);
		path.append("res_" +code);
		
		switch(pathIndex){
		case 0:
			path.append(File.separator);
			path.append("adder");
			break;
		case 1:
			path.append(File.separator);
			path.append("coupons");
			break;
		case 2:
			break;
		default :
			break;
		}
		path.append(File.separator);

		this.urlPath=path.toString();
	}
	
	public void getRealPath(){
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();
		
		
		HttpSession session=attr.getRequest().getSession();
		
//		System.out.println(session.getServletContext().getContextPath());
		
//		System.out.println("contextpath :" + attr.getRequest().getContextPath());
	}

	//	public static void main(String[] args) {
	//		FileManager file = new FileManager();
	//	}
}
