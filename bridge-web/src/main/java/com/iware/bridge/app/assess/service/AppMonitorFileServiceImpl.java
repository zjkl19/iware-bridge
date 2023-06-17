package com.iware.bridge.app.assess.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Service

public class AppMonitorFileServiceImpl implements AppMonitorFileService{
	@Value("${file.upload-path}")
	private String path;
	private static final int BUFFER_SIZE = 2 * 1024;

	public static Logger logger=LoggerFactory.getLogger(AppMonitorFileServiceImpl.class);

	/**
	 * 上传分片文件
	 * @param file
	 * @param monitorId
	 */
	public void uploadPartFile(MultipartFile file,Integer monitorId) {
		String filename = file.getName();
		String destPath=path+"/tmp/monitor/file/"+monitorId+"/part/";
		new File(destPath).mkdirs();
		String newFilePath = destPath+filename;
		Integer partNo=0;
		while(true) {
			File isFile = new File(newFilePath+"_"+partNo);
			if(!isFile.exists()) {
				break;
			}
			partNo++;
		}
		File newFile = new File(newFilePath+"_"+partNo);
		if(!newFile.exists()) {
			try {
				file.transferTo(newFile);
			} catch (Exception e) {
				StringBuilder builder=new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				Arrays.stream(e.getStackTrace()).forEach(s->builder.append(s).append("\n"));
				logger.error(builder.toString());
			}
		}

	}
	/**
	 * 合并分片文件
	 */
	@SuppressWarnings("resource")
	@Override
	public String mergePartFile(Integer monitorId,String fileName) {
		byte[] buf = new byte[BUFFER_SIZE];
		String destPath=path+"/tmp/monitor/file/"+monitorId+"/part/";
		String wholeFilePath=path+"/tmp/monitor/file/"+monitorId+"/";
		File dirFile = new File(destPath);
		File wholeFile = new File(wholeFilePath+fileName);
		FileOutputStream wholeFileOutput=null;
		try {
			wholeFileOutput = new FileOutputStream(wholeFile);
			if(dirFile.isDirectory()) {
				File[] files = dirFile.listFiles();
				Integer partNo=0;
				for(int i=0;i<files.length;i++) {
					for(File file:files) {
						String childFileName=file.getName();
						if(childFileName.endsWith("_"+partNo)) {
							int len;
							FileInputStream childFileInput=null;
							try {
								childFileInput = new FileInputStream(file);
								while ((len = childFileInput.read(buf)) != -1) {
									wholeFileOutput.write(buf, 0, len);
								}
							}catch (Exception e){
								StringBuilder builder=new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
								Arrays.stream(e.getStackTrace()).forEach(s->builder.append(s).append("\n"));
								logger.error(builder.toString());
							}finally {
								if(childFileInput!=null) {
									childFileInput.close();
								}
							}

							break;
						}
					}
					partNo++;
				}
			}
			wholeFileOutput.close();
		} catch (IOException e1) {
			StringBuilder builder=new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Arrays.stream(e1.getStackTrace()).forEach(s->builder.append(s).append("\n"));
			logger.error(builder.toString());
		} finally {
			if(wholeFileOutput!=null){
				try {
					wholeFileOutput.close();
				}catch (IOException e){
					StringBuilder builder=new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					Arrays.stream(e.getStackTrace()).forEach(s->builder.append(s).append("\n"));
					logger.error(builder.toString());
				}
			}
		}



		return wholeFile.getPath();
	}


}
