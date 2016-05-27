package com.tcp.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tcp.logger.LoggerConfig;

public class PathFactory {

	private static PathFactory instance=null;
	
	private static String mobileDefaultPath=ResManager.getString("Mobile.default.path");
	
	private static String mobileIndexpath=ResManager.getString("Mobile.index.path");
	
	private String defaultPath;
	
	private String indexPath;
	
	private String urlPath;
	
	public static PathFactory getInstance(){
		if(instance==null){
			instance=new PathFactory();
		}
		return instance;
	}

	public void setDefaultPath(){
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.getRequestAttributes();

		StringBuffer path = new StringBuffer();
		
		path.append("http://");
		path.append(attr.getRequest().getServerName());
		path.append(":");
		path.append(attr.getRequest().getServerPort());
		path.append(mobileDefaultPath);
		
		this.defaultPath=path.toString();
	}
	
	public void setIndexPath(){
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.getRequestAttributes();

		StringBuffer path = new StringBuffer();
		
		path.append("http://");
		path.append(attr.getRequest().getServerName());
		path.append(":");
		path.append(attr.getRequest().getServerPort());
		path.append(mobileIndexpath);
		
		this.indexPath=path.toString();
	}
	/**
	 * Frame 생성시 해당 Frame의 종류를 설정하여 Frame Path를 만든다.
	 * @param frameIndex
	 * @param frameType
	 */
	public void createFramePath(int frameIndex , int frameType){
		int storeCode=SessionManager.getInstance().getSessionCode();
		
		
		StringBuffer url = new StringBuffer();
		if(frameType< 6)
			url.append(getDefaultPath());
		else
			url.append(getIndexPath());

		switch(frameType){
		case 0:
			url.append(ResManager.getString("Mobile.Default.Frame.Url"));
			break;
		case 1:
			url.append(ResManager.getString("Mobile.list.Url"));
			break;
		case 2:
			url.append(ResManager.getString("Mobile.Store.Url"));
			break;
		case 3:
			url.append(ResManager.getString("Mobile.Coupon.Image.Url"));
			break;
		case 4:
			url.append(ResManager.getString("Mobile.Coupon.List.Url"));
			break;
		case 5:
			url.append(ResManager.getString("Mobile.Group.Url"));
			break;
		case 6:
			url.append(ResManager.getString("Mobile.index.Url"));
			break;
		}
		url.append("?num=");url.append(frameIndex);
		url.append("&code=");url.append(storeCode);
		if(frameType==5){
			url.append("&group=");url.append(frameIndex);
		}
		setUrlPath(url.toString());
	}

	public String getDefaultPath() {
		return defaultPath;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}
	
}
