package com.iware.common.utils;

import com.iware.common.constant.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessUtils {

	private static Logger logger = LoggerFactory.getLogger(ProcessUtils.class);

	/**
	 * 执行cmd
	 * @param cmd
	 * @return
	 */
	public static Boolean processCmd(String cmd){
		if(StringUtils.isNotBlank(cmd)){
			Runtime runtime = Runtime.getRuntime();
			try {
				Process process = runtime.exec(cmd);
				if(process.waitFor() == 0){
					return Boolean.TRUE;
				}else{
					printError(cmd, process);
					return Boolean.FALSE;
				}
			} catch(Exception e){
				logger.error(ExceptionUtils.getErrorStack(e));
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * 有结果
	 * @param cmd
	 * @return
	 */
	public static String processCmdForResult(String cmd){
		String result = null;
		if(StringUtils.isNotBlank(cmd)){
			Runtime runtime = Runtime.getRuntime();
			try {
				Process process = runtime.exec(cmd);
				if(process.waitFor() == 0){
					result = getSheelCmdResult(process);
				}else{
					printError(cmd, process);
				}
			} catch(Exception e){
				logger.error(ExceptionUtils.getErrorStack(e));
			}
		}
		return result;
	}

	/**
	 * process执行错误时，打印错误信息
	 * @param process
	 * @throws IOException
	 */
	private static void printError(String cmd, Process process) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		StringBuilder errorMsg = new StringBuilder("exec cmd["+cmd+"] error,msg is :\n");
		String line = null;
		while ((line = br.readLine()) != null) {
			errorMsg.append(line).append("\n");
		}
		logger.error("{}", errorMsg);
	}

	public static Boolean snapshotRevert(String domainName, String snapshotName){
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(new String[]{"virsh","snapshot-revert", domainName, snapshotName, "--force"});

			if(process.waitFor() == 0){
				return Boolean.TRUE;
			}else{
				printError(null, process);
				return Boolean.FALSE;
			}

		} catch(Exception e){
			logger.error(ExceptionUtils.getErrorStack(e));
		}
		return null;
	}

	public static List<String> gpuInfo(){
		List<String> gpuInfoList = new ArrayList<>();
		try{
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(new String[]{"lspci","-nn"});
			process.waitFor();
			if(process.waitFor() == 0){
				String content = getSheelCmdResult(process);
				if(StringUtils.isNotBlank(content)){
					for(String line : content.split(GlobalConstant.Symbol.SPLIT_BR)){
						if(line.toUpperCase().contains("NVIDIA")
								&& line.toUpperCase().contains("3D controller")){
							gpuInfoList.add(line);
						}
					}
				}
			}else{
				printError(null, process);
			}
		}catch(Exception e){
			logger.error(ExceptionUtils.getErrorStack(e));
		}
		return gpuInfoList;
	}

	public static Integer vncPortNum(String domainName){
		Integer vncPort = null;
		String vncPortStr = null;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(new String[]{"virsh","vncdisplay",domainName});
			process.waitFor();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
			  sb.append(line).append(GlobalConstant.Symbol.SPLIT_BR);
			}

			String content = sb.toString().trim();

			logger.info("vnc port is :{}", content);

			if(content.contains(":")){
				vncPortStr = content.split(":")[1];
			}

			if(StringUtils.isNotBlank(vncPortStr)){
				vncPort = Integer.parseInt(vncPortStr);
			}

			if(vncPort - 5900 <= 0){
				vncPort += 5900;
			}
		} catch(Exception e){
			logger.error(ExceptionUtils.getErrorStack(e));
		}
		return vncPort;
	}

	public static String processIpByDhcp(String mac, String bridgeName){
		String result = null;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(new String[]{"virsh", "net-dhcp-leases", bridgeName});
			process.waitFor();

			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				if(line.toUpperCase().contains(mac.toUpperCase())){
					String[] infoArr = line.split("  ");
					if(infoArr != null && infoArr.length > 0){
						result = infoArr[5];
					}
				}
			}
			logger.info("arp result ..... {}",result);
			//根据正则表达式获取IP信息
			if(StringUtils.isNotBlank(result)){
				result = getIp("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}", result);
			}

			return result;
		}catch(Exception e){
			logger.error(ExceptionUtils.getErrorStack(e));
		}
		return result;
	}

	public static String getIp(String regular, String res) {
        if (StringUtils.isNotEmpty(res)) {
        	if(res.length() < 7 || res.length() > 15){
        		return null;
    		}

            Matcher m = Pattern.compile(regular).matcher(res);
            boolean result = m.find();
            while(result) {
            	return m.group();
            }
        }
        return null;
    }

	/**
	 * 获取结果
	 * @param process
	 * @return
	 */
	private static String getSheelCmdResult(Process process){
		StringBuilder sb = new StringBuilder();
		try (
			InputStream is = process.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(inputStreamReader);
			){
			String line = null;
			while ((line = br.readLine()) != null) {
			  sb.append(line).append(GlobalConstant.Symbol.SPLIT_BR);
			}
		} catch (IOException e) {
			logger.error(ExceptionUtils.getErrorStack(e));
		}
		return sb.toString().trim();
	}

	public static String processScript(String filePath,List<String> paramList) {
		String params = "";
		String content = null;
		if(!CollectionUtils.isEmpty(paramList)) {
			for (String param : paramList) {
				if(StringUtils.isNotEmpty(param)) {
					params += " " + param;
				}
			}
		}
		String cmd = "sh " + filePath + params;
		logger.info("执行脚本命令：{}", cmd);
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(cmd);
			if(process.waitFor() == 0){
				content = getSheelCmdResult(process);
				logger.info("执行shell脚本成功");
				logger.info("result is :{}", content);
			}else{
				logger.error("执行shell脚本失败");
				printError(null, process);
			}
		} catch (Exception e) {
			logger.error("执行shell脚本失败");
			logger.error(ExceptionUtils.getErrorStack(e));
		}

		return content;
	}

	public static void main(String[] args) {
		String content = //"[2020-08-26 10:52:36.622][del_network4Tencet.sh]Begin del ovs tencent network..."+
						 //"[2020-08-26 10:52:36.744][del_network4Tencet.sh]End delete ovs tencent network success!"+
                         "result:-1";

		String regex = "(?<=result:)(-)?\\d+";
		Matcher m = Pattern.compile(regex).matcher(content);
        boolean flag = m.find();
        //System.out.println("是否成功匹配："+ flag);
        if(flag) {
        	Integer result = Integer.parseInt(m.group());
        	//System.out.println("匹配结果："+ result);
        }
	}

}
