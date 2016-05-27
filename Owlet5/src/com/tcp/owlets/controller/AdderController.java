package com.tcp.owlets.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.AppInfo;
import com.tcp.owlets.bean.AppSettingInfo;
import com.tcp.owlets.handler.AdderHandler;
import com.tcp.util.AppManager;
import com.tcp.util.DataParser;
import com.tcp.util.SessionManager;

/**
 * Adder에서 필요한 cmd들에 대한 처리를 이쪽에 넣어 주면 된다. BasicController랑 다 똑같으니 참고해서 넣으면 된다.
 *
 * @author blessldk
 *
 */
@Controller
@RequestMapping("/adder")
public class AdderController {
	@Autowired
	AdderHandler adderService;
	
	@RequestMapping("/editor")
	public String getViewEditor() {
		return "Adder";
	}

	/**
	 * Adder에서 작성한 Frame을 추가
	 * 
	 * @param adderInfo
	 * @param moveIndex
	 *            (0:adder로 Retun , 1:Preveiw 로 Return)
	 * @return
	 */
	@RequestMapping("/insertFrame")
	public String getFrame(
			@ModelAttribute(value = "frameInfo") AdderInfo adderInfo,
			@RequestParam(value = "continue") int moveIndex) {

		synchronized (adderInfo) {
			adderService.insertFrame(adderInfo , AppManager.FRAME_TYPE_DEFAULT);
		}

		String result;

		// 저장 후 이동 장소 선택 
		switch (moveIndex) {
		default:
			result = "redirect:/adder/editor.do";
			break;
		case 0:
			result = "redirect:/adder/editor.do";
			break;
		case 1:
			result = "redirect:/preview/frames.do";
			break;
		}
		return result;
	}

	/**
	 * Adder에서 수정한 Frame을 DB에 Update
	 * 
	 * @param adderInfo
	 * @return
	 */
	@RequestMapping("/updateFrame")
	public String setUpdateFrame(@ModelAttribute AdderInfo adderInfo,
			@RequestParam(value = "continue") int moveIndex , @RequestParam(value="index") int frameIndex) {

		adderInfo.setnIndex(frameIndex);

		adderService.updateFrame(adderInfo);

		String result;

		switch (moveIndex) {
		default:
			result = "redirect:/adder/editor.do";
			break;
		case 0:
			result = "redirect:/adder/editor.do";
			break;
		case 1:
			result = "redirect:/preview/frames.do";
			break;
		}
		return result;
	}

	/**
	 * 사진 업로더 View 띄우기
	 * 
	 * @return
	 */
	@RequestMapping("/photoUploader")
	public String viewPhohoUploader() {
		return "pImageUploader";
	}

	@RequestMapping(value="/enrollPage",method=RequestMethod.GET)
	public String getEnrollPage(@RequestParam(value="check") int check) {
		return "noticeEnroll";
	}

	@RequestMapping(value="/insertAppInfo" , method=RequestMethod.POST)
	public String insertAppInfo(@ModelAttribute(value="appInfo") AppInfo info){
		int code = SessionManager.getInstance().getSessionCode();

		adderService.insertAppInfo(info, code);
		
		AppSettingInfo bean=new AppSettingInfo();
		
		adderService.insertAppSettingInfo(bean, code);

		//		return "redirect:/preview/frames.do";

		return "redirect:/adder/editor.do";
	}

	/**
	 * List 에서 선택한 Frame으로 이동 
	 * @param frameIndex
	 * @return
	 */
	@RequestMapping(value="/getSelectFrame" , method=RequestMethod.POST)
	public @ResponseBody AdderInfo getSelectFrame(@RequestParam(value="index") int frameIndex){
		AdderInfo frame = adderService.getFrame(frameIndex);

		return frame;
	}

	@RequestMapping(value="/editFrame" , method=RequestMethod.GET)
	public String editFrame(){
		return "Adder";
	}

	@RequestMapping(value="/deleteFrame" , method=RequestMethod.GET)
	public String deleteFrame(@RequestParam(value="frameIndex") int frameIndex){

		adderService.deleteFrame(frameIndex);
		return "redirect:/preview/frames.do";
	}

	/**
	 * App생성 페이지 호출
	 * @return
	 */
	@RequestMapping("/appMaker.do")
	public String getAppMaker(){

		boolean isAppExist=adderService.isAppBasicExist();

		String returnUrl;

		if(isAppExist)
			returnUrl="redirect:/preview/frames.do";
		else
			returnUrl="maker";

		return returnUrl;
	}

	/**
	 *  App목록 전달 (Json)
	 * @return
	 */
	@RequestMapping(value="/getAppListToJson" , produces="application/json")
	public @ResponseBody ArrayList<AppInfo> getAppListToJson(){
		ArrayList<AppInfo> list = adderService.getAppList();

		return list;
	}

	@RequestMapping(value="/getAppList")
	public ModelAndView getAppList(){
		ArrayList<AppInfo> list = adderService.getAppList();

		return new ModelAndView("appList" , "appList" , list);
	}

	@RequestMapping(value="/menu")
	public String getMenuView(){
		return "menuConfiguration";
	}


	@RequestMapping(value="/insertSettingInfo")
	public String insertAppSettingInfo(@ModelAttribute(value="info") AppSettingInfo setInfo){

		return "redirect:/skin";
	}

	@RequestMapping(value="/insertGroup")
	public @ResponseBody int insertGroup(@RequestParam(value="name" , required=false) String name){
		try{
			int resultValue=0;
			
			synchronized (adderService) {
				int storeCode=SessionManager.getInstance().getSessionCode();
				
				AdderInfo bean=new AdderInfo();
				
				bean.setTitle(name);
				bean.setContent("");
				
				adderService.insertFrame(bean, 5);
				
				resultValue=adderService.insertGroupInfo(storeCode, name);
				
				return resultValue;
			}
		}catch(Exception e){
			LoggerConfig.getWebLogger().error(e.getMessage());
			
			return 0;
		}
	}

	@RequestMapping(value="/updateGroup")
	public @ResponseBody int updateGroup(@RequestParam(value="index" , required=false) int nIndex,
			@RequestParam(value="name" , required=false) String name){
		try{	
			adderService.updateGroupInfo(nIndex, name);
			
			AdderInfo bean=new AdderInfo();
			bean.setTitle(name);
			bean.setContent("");
			
			adderService.updateFrame(bean);
			
			return 1;
		}catch(Exception e){
			LoggerConfig.getWebLogger().error(e.getMessage());

			return 0;
		}

	}

	@RequestMapping(value="/deleteGroup")
	public @ResponseBody int deleteGroup(@RequestParam(value="index" , required=false) int nIndex){
		try{
			adderService.deleteGroupInfo(nIndex);
			adderService.deleteFrame(nIndex);

			return 1;
		}catch(Exception e){
			LoggerConfig.getWebLogger().error(e.getMessage());

			return 0;
		}
	}

	@RequestMapping(value="/selectGroup" , produces="application/json")
	public @ResponseBody HashMap<String, ArrayList<AdderInfo>> selectGroup(){

		HashMap<String, ArrayList<AdderInfo>> map=new HashMap<String, ArrayList<AdderInfo>>();

		ArrayList<AdderInfo> list=new ArrayList<AdderInfo>();

		ArrayList<AdderInfo> childs;
		
		int code=SessionManager.getInstance().getSessionCode();
		
		ArrayList<AdderInfo> info=adderService.getGroupInfo(code);

		list=adderService.getGroupFrame(code);
		
		for(AdderInfo group : info){
			childs = new ArrayList<AdderInfo>();
			for(AdderInfo data : list){
				if(group.getGroupIndex()==data.getGroupIndex()){
					childs.add(data);
				}
			}
			String key=group.getGroupIndex()+"_"+group.getGroupName();
			
			map.put(key, childs);
		}
		return map;
	}
	
	@RequestMapping(value="insertConfigGroup")
	public @ResponseBody int insertConfigGroup(@RequestParam(value="group") String[] data){
		try{
			int storeCode=SessionManager.getInstance().getSessionCode();
			
			adderService.initDataConfig(storeCode);
			
			for(int i=0; i< data.length ; i++){
				LoggerConfig.getWebLogger().debug("Data : " + data[i]);
				if(data[i].length() > 1){
					int[] array=DataParser.getInstance().configData(data[i]);

					int groupIndex=DataParser.getInstance().configGroupIndex(data[i]);

					adderService.insertDataConfig(array, groupIndex);
				}
			}
			return 1;
		}catch(Exception e){
			LoggerConfig.getWebLogger().debug(e.getMessage());

			return 0;
		}
	}
	
	@RequestMapping("/batchConfig")
	public String getViewInsert(){
		return "menuBatchConfiguration";
	}
	
	@RequestMapping(value="/eventEditPage",method=RequestMethod.GET)
	public String getEventEditPage(@RequestParam(value="check") int check) {
		return "eventEnroll";
	}
	
	/**
	 * Login 후 Page 이동 
	 * @return
	 */
	@RequestMapping("/loginMove.do")
	public String getLoginLink(){

		boolean isAppExist=adderService.isAppExist();

		String returnUrl;

		if(isAppExist)
			returnUrl="redirect:/analysis/main.do";
		else
			returnUrl="redirect:/adder/appMaker.do";

		return returnUrl;
	}
}
