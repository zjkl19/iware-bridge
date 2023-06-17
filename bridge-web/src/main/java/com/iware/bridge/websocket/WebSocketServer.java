package com.iware.bridge.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {

	static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static AtomicInteger onlineCount = new AtomicInteger(0);
	private Session session;
	private Integer userId;

	private static Map<String, List<Session>> codingMap = new HashMap<>();

	private static Map<Integer, Session> userMap = new HashMap<>();

	private static Map<Integer, List<String>> userCodingMap = new HashMap<>();

	//concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
	private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

	/** 连接建立成功调用的方法*/
	@OnOpen
	public void onOpen(Session session,@PathParam("userId") Integer userId) {
		this.session = session;
		this.userId = userId;

		if(userMap.containsKey(userId)) {//判断user是否存在之前的session,存在清理掉
			subOnline();
		}

		webSocketSet.add(this);     //加入set中
		addOnline();			//添加在线用户信息(用来添加coding的访问信息)
	}

	@OnMessage
	public void onMessage(String message) {

		JSONArray array = JSON.parseArray(message);
		List<String> codingList = array.toJavaList(String.class);

		for(String coding : codingList){
			if(codingMap.containsKey(coding)) {
				List<Session> arrayList = codingMap.get(coding);
				arrayList.add(session);
				codingMap.put(coding, arrayList);
			}else {
				List<Session> list = new ArrayList<>();
				list.add(session);
				codingMap.put(coding, list);
			}
		}
		userCodingMap.put(userId, codingList);
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this);
		subOnline();
	}

	/**
	 *
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		logger.error("发生错误");
		logger.error(error.getMessage());
	}

	/**
	 * 实现服务器主动推送
	 */
	public void sendMessage(String message, Session session) throws IOException {
		synchronized(session){
			session.getBasicRemote().sendText(message);
		}
	}


	public void addOnline() {
		userMap.put(userId, session);
		onlineCount.incrementAndGet();
		logger.info("有一用户上线，当前在线用户: {}人", onlineCount);
	}

	public void subOnline() {

		Session current = userMap.get(userId);
		List<String> codingList = userCodingMap.get(userId);

		if (!CollectionUtils.isEmpty(codingList)) {
			for(String coding : codingList){
				List<Session> sessionList = codingMap.get(coding);
				sessionList.remove(current);
				codingMap.put(coding, sessionList);
			}
		}

		userMap.remove(userId);
		onlineCount.decrementAndGet();
		logger.info("有一用户离线，当前在线用户: {}人", onlineCount);
	}


	public static Map<String, List<Session>> getCodingMap() {
		return codingMap;
	}

	public static void setCodingMap(Map<String, List<Session>> codingMap) {
		WebSocketServer.codingMap = codingMap;
	}
}
