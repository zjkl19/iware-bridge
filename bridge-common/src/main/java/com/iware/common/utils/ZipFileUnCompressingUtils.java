package com.iware.common.utils;

import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 解压zip文件
 * @author CHEN
 *
 */
public class ZipFileUnCompressingUtils {

	private static Logger logger = LoggerFactory.getLogger(ZipFileUnCompressingUtils.class);

	public static void main(String[] args) {
		unzip("D:\\file\\p.zip", "D:\\file\\");
	}
	/**
	 * 解压缩到指定文件夹
	 * @param source 源文件（zip）
	 * @param destDir 目标位置
	 * @throws IOException
	 */
	public static void unzip(String source,String destDir){
		logger.debug("解压文件：{}", source);
		logger.debug("解压目标路径：{}", destDir);
		if(StringUtils.isEmpty(source)){
			return;
		}
		File file = new File(source);
		File parent = new File(destDir);
		try (ZipInputStream is = new ZipInputStream(new FileInputStream(file), Charset.forName("gbk"))) {

			ZipEntry entry = null;
			//循环压缩文件Entry
			while((entry = is.getNextEntry()) != null){
				//当entry的path为"/"时不考虑
				if(entry.isDirectory()){
					continue;
				}
				file = new File(parent, entry.getName());
				if(!file.exists()){
						file.getParentFile().mkdirs();
				}

				try (FileOutputStream fout = new FileOutputStream(file)) {
					byte buffer[] = new byte[1024];
					int len = -1;
					while((len = is.read(buffer)) != -1){
						fout.write(buffer, 0, len);
					}
					fout.flush();
				} catch (IOException e) {
					throw e;
				}
			}

		} catch (IOException e) {
			logger.error(ExceptionUtils.getErrorStack(e));
			throw new BusinessException(GlobalResultEnum.FILE_UN_COMPRESS_ERROR);
		}

	}


	/**
	 * 返回已ext结尾的文件列表
	 * @param path 要遍历的目录
	 * @param ext 扩展名
	 * @return
	 */
	public static List<File> findFilesEndWith(String path,final String ext){
		if(path == null || path.trim().equals("") || ext == null || ext.trim().equals("")){
			return Collections.emptyList();
		}
		File file = new File(path);
		List<File> list = new ArrayList<>();
		if(file.isDirectory()){
			File [] files = file.listFiles();
			while(files.length > 0){
				File f = files[0];
				File[] temp = null;
				if(f.isDirectory()){
				    File[] listFiles = f.listFiles();
					temp = new File[files.length + listFiles.length];
					System.arraycopy(files, 0, temp, 0, files.length);
					System.arraycopy(listFiles,0, temp, files.length, listFiles.length);
					files = temp;
				}else{
					if(f.getName().endsWith(ext)){
						list.add(f);
					}
				}
				temp = new File[files.length-1];
				System.arraycopy(files, 1, temp, 0, files.length - 1);
				files = temp;
			}
		}
		return list;
	}

}
