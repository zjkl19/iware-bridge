package com.iware.bridge.app.assess.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository

public interface AppMonitorFileService {
	/**
	 * 上传分片文件
	 * @param file
	 * @param monitorId
	 */
	public void uploadPartFile(MultipartFile file,Integer monitorId);
	/**
	 * 合并分片文件
	 * @param monitorId
	 * @param fileName
	 * @return
	 */
	public String mergePartFile(Integer monitorId,String fileName);
}
