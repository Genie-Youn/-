package com.tcp.owlets.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tcp.logger.LoggerConfig;
import com.tcp.util.FileManager;

@Controller
@RequestMapping("/file")
public class FileUploaderController {

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam(value="name" , required=false) String name,
			@RequestParam(value="file" , required=false) MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				LoggerConfig.getWebLogger().debug("File OriginName :" + file.getOriginalFilename());
				
				String[] fileName = file.getOriginalFilename().split("\\.");
				
				LoggerConfig.getWebLogger().debug(fileName[0]);

				int fileType = fileName.length-1;

				byte[] bytes = file.getBytes();

				// Creating the directory to store file
//				String rootPath = System.getProperty("catalina.home");
				String rootPath = "http://localhost:8080/";
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				LoggerConfig.getEngineLogger().debug("파일경로 : " + dir.toString() 
						+ file.getOriginalFilename() + file.getContentType());

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name + "."+fileName[fileType]);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}

	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				String[] fileName = file.getOriginalFilename().split("\\.");
				
				int fileType = fileName.length-1;
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name +"." + fileName[fileType]);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				LoggerConfig.getEngineLogger().debug("파일경로 : " + dir.toString());

				message = message + "You successfully uploaded file=" + name
						+ "<br />";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
	
	@RequestMapping(value = "/uploadAdderFile", method = RequestMethod.POST)
	public @ResponseBody HashMap<Integer , String> uploadAdderFileHandler(@RequestParam("file") MultipartFile[] files) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		
		map.clear();
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			
			boolean isSave = FileManager.getInstance().saveServerFile(file, 0);
			
			if(isSave){
				String fileName = FileManager.getInstance().getFileName() + FileManager.getInstance().getFileType();
				
				String returnData = FileManager.getInstance().getUrlPath() + fileName;

				map.put(i, returnData);
			}
		}
		
		return map;
	}
}
