package com.iware.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.iware.common.constant.GlobalConstant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalUtil {

	private static final Logger log = LoggerFactory.getLogger(LocalUtil.class);

	public static final String MESSAGE_PREFIX = "NetworkInterface";

	/**
	 * 获取本地IP getLocalIp:(这里用一句话描述这个方法的作用). <br/>
	 * @author
	 * @return
	 * @throws UnknownHostException
	 * @since JDK 1.7
	 */
	public static String getLocalIp() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	/**
	 * 获取本地IPV4 IP， 在linux和windows下适用
	 * @return
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public static String getLocalRealIp() {
		try {
			InetAddress ip = null;
			String networkCardName = System.getProperty("networkCardName");
			if(StringUtils.hasText(networkCardName)) {
				NetworkInterface netInterface = NetworkInterface.getByName(networkCardName);
				if(netInterface == null) {
					log.warn("no find networkcard of name {}, use the default one", networkCardName);
				} else {
					Enumeration<InetAddress> addresses = netInterface
							.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = addresses.nextElement();
						if (ip instanceof Inet4Address
								&& (!GlobalConstant.LOCALHOST_IP.equals(ip.getHostAddress()))) {
							return ip.getHostAddress();
						}
					}
					log.warn("no find right ip address of the networkcard {}, use the default one", networkCardName);
				}
			} else {
				log.warn("no find networkcard name in the system variable, use the default one");
			}

			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				log.debug("{} ==>{}", MESSAGE_PREFIX, netInterface.getName());
				log.debug("{} ==>{}", MESSAGE_PREFIX, netInterface.getDisplayName());
				log.debug("{} ==>{}", MESSAGE_PREFIX, netInterface.getMTU());
				log.debug("{} ==>{}", MESSAGE_PREFIX, netInterface.isLoopback());
				log.debug("{} ==>{}", MESSAGE_PREFIX, netInterface.isUp());
				log.debug("{} ==>{}", MESSAGE_PREFIX, netInterface.isVirtual());

				// 去除回环接口，子接口，未运行和接口
				if (netInterface.isLoopback()
						|| netInterface.isVirtual()
						|| !netInterface.isUp()) {
					continue;
				}

				//判断网卡名称不为docker和KVM的虚机网卡
				if(!"docker0".equalsIgnoreCase(netInterface.getName())
						&& !"virbr0".equalsIgnoreCase(netInterface.getName())){
					Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = addresses.nextElement();
						log.debug(ip.getCanonicalHostName());
						if (ip instanceof Inet4Address && ip.isSiteLocalAddress()) {
							return ip.getHostAddress();
						}
					}
				}
			}
			return getLocalIp();
		} catch (SocketException | UnknownHostException e) {
			log.warn(ExceptionUtils.getErrorStack(e));
			return GlobalConstant.LOCALHOST_IP;
		}
	}

	public static String getSystemVariable(String key) {
		try {
			String value = System.getProperty(key);
			if (value == null) {
				value = System.getenv(key);
			}
			return value;
		}
		catch (Exception ex) {
			if (log.isDebugEnabled()) {
				log.debug("Could not access system property '{}': {}", key, ex);
			}
			return null;
		}
	}
}
