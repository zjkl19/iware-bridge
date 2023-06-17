package com.iware.common.utils;
import com.iware.common.exception.BusinessException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * 压缩文件
 * @author admin
 *
 */
public class InZipUntil {

	private static Logger logger = LoggerFactory.getLogger(InZipUntil.class);

	private static final int  BUFFER_SIZE = 2 * 1024;

    public String inZip( String inDir,File zipFile) throws IOException {
    	ZipOutputStream zos = new ZipOutputStream(zipFile);
    	File sourceFile = new File(inDir);
    	compressZip(sourceFile,zos,sourceFile.getName());
    	zos.close();
        return zipFile.getPath();
    }

    /**
     * 递归压缩目录
     * @param sourceFile
     * @param zos
     * @param name
     * @throws IOException
     */
    private void compressZip(File sourceFile, ZipOutputStream zos, String name) throws IOException {
    	byte[] buf = new byte[BUFFER_SIZE];
    	if(sourceFile.isFile()) {
    		zos.putNextEntry(new ZipEntry(name));
    		int len;

    		try (FileInputStream ln = new FileInputStream(sourceFile)) {
				while((len=ln.read(buf))!=-1) {
					zos.write(buf, 0, len);
				}
			} catch (IOException e) {
    			throw e;
			}
    		zos.closeEntry();
    	}else {
    		 File[] listFiles = sourceFile.listFiles();
    		 if(listFiles == null || listFiles.length == 0) {
    			 zos.putNextEntry(new ZipEntry(name + "/"));
    			 zos.closeEntry();
    		 }else{
    			 for(File file:listFiles) {
    				 compressZip(file, zos, name + "/" + file.getName());
    			 }

    		 }


    	}

    }


    public String inZipManyFile(List<String> files,File zipFile) {

    	try (ZipOutputStream zos = new ZipOutputStream(zipFile)) {

			for(String file:files) {
				byte[] buf = new byte[BUFFER_SIZE];
	    		File sourceFile = new File(file);
	    		if(!sourceFile.isFile()) {
	    			throw new BusinessException("文件不存在");
	    		}
	    		zos.putNextEntry(new ZipEntry(sourceFile.getName()));
	    		int len;
	    		try (FileInputStream ln = new FileInputStream(sourceFile)) {
					while((len = ln.read(buf))!= -1) {
						zos.write(buf, 0, len);
					}
				} catch (FileNotFoundException e) {
					throw new BusinessException("文件不存在");
				}

	    		zos.closeEntry();
	    	}
	        return zipFile.getPath();
		} catch (IOException e) {
			logger.error(ExceptionUtils.getErrorStack(e));
		}
		return null;
    }
}
