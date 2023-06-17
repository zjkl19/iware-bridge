package com.iware.common.utils;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;

public class UnZipAnRar {

    private static Logger logger = LoggerFactory.getLogger(UnZipAnRar.class);

    public void unZip(File zipFile, String outDir) throws IOException {

        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                logger.debug("创建压缩目录成功");
            }
        }

        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration<ZipEntry> enumeration = zip.getEntries(); enumeration.hasMoreElements(); ) {
            ZipEntry entry = enumeration.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);

            if (entry.isDirectory()) {      //处理压缩文件包含文件夹的情况
                File fileDir = new File(outDir + zipEntryName);
                fileDir.mkdir();
                continue;
            }

            File file = new File(outDir, zipEntryName);
            if(!file.exists()) {
            	if(!file.getParentFile().exists()) {
            		file.getParentFile().mkdirs();
            	}

            	if (!file.createNewFile()) {
            	    logger.error("创建文件失败");
                }
            }

            try (OutputStream out = new FileOutputStream(file)) {
                byte[] buff = new byte[1024];
                int len;
                while ((len = in.read(buff)) > 0) {
                    out.write(buff, 0, len);
                }
                in.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public  void unRar(File rarFile, String outDir) throws IOException, RarException {
        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                logger.debug("创建压缩目录成功");
            }
        }
        FileInputStream fileInputStream = new FileInputStream(rarFile);
        try (Archive archive = new Archive(fileInputStream)) {
            FileHeader fileHeader = archive.nextFileHeader();
            while (fileHeader != null) {
                if (fileHeader.isDirectory()) {
                    fileHeader = archive.nextFileHeader();
                    continue;
                }
                File out = new File(outDir + fileHeader.getFileNameString());
                if (!out.exists()) {
                    if (!out.getParentFile().exists()) {
                        out.getParentFile().mkdirs();
                    }
                    if (!out.createNewFile()) {
                        logger.error("创建文件失败");
                    }
                }
                FileOutputStream os = new FileOutputStream(out);
                archive.extractFile(fileHeader, os);

                os.close();

                fileHeader = archive.nextFileHeader();
            }
        } catch (IOException | RarException e) {
            throw e;
        }
    }

    public void deleteFile(File file){
        //判断文件不为null或文件目录存在
        if (file != null && file.exists()){
        	//取得这个目录下的所有子文件对象
        	File[] files = file.listFiles();
        	//遍历该目录下的文件对象
        	for (File f: files){
        		//打印文件名
        		String name = file.getName();
        		logger.info(name);
        		//判断子目录是否存在子目录,如果是文件则删除
        		if (f.isDirectory()){
        			deleteFile(f);
        		}else {
        			f.delete();
        		}
        	}
        	//删除空文件夹  for循环已经把上一层节点的目录清空。
        	file.delete();
        }
    }

}
