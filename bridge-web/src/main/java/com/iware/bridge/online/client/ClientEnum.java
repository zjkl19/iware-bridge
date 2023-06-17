package com.iware.bridge.online.client;


public enum ClientEnum {

	CLIENT;

	private static SocketClient socketClient = null;
	private static  String userName = null;
	private static String password = null;


	public static void initClient(String user,String pwd,IMessageProcess process) throws Exception {
		socketClient = new SocketClient(process);
		if(socketClient != null) {
		    socketClient.setConnectionLostTimeout(0);
			socketClient.connectBlocking();
			userName = user;
			password = pwd;
			socketClient.send("{user:'"+user+"',password:'"+pwd+"'}");
		}
		while(true) {
			try {
				if(!socketClient.isOpen()) {
					socketClient = new SocketClient(process);
					if(socketClient != null) {
						socketClient.connectBlocking();
						socketClient.send("{user:'"+userName+"',password:'"+password+"'}");
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
