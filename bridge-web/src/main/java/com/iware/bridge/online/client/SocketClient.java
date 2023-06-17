package com.iware.bridge.online.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
* @ClassName: SocketClient
* @Description: socket客户端（继承本类即可接收消息）
* @author mzh
* @since jdk1.7
* @date 2019-7-10 上午10:51:12
*
 */
public class SocketClient  extends WebSocketClient  {

    private IMessageProcess messageProcess;

	public SocketClient(IMessageProcess process) throws URISyntaxException  {
		super(new URI("ws://59.110.175.205:9002"));
		this.messageProcess = process;
	}

    @Override
    public void onOpen(ServerHandshake shake) {
	    messageProcess.onOpen();
    }

    @Override
    public void onMessage(String paramString) {
        messageProcess.onMessage(paramString);
    }

    @Override
    public void onClose(int paramInt, String paramString, boolean paramBoolean) {
	    messageProcess.onClose();
    }

    @Override
    public void onError(Exception e) {
        messageProcess.onError(e.getMessage());

    }

}
