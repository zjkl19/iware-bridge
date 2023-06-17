package com.iware.common.data;

import com.iware.common.constant.GlobalConstant;
import com.iware.common.pojo.UserToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * The class Thread local map.
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadLocalMap {

	/**
	 * The constant threadContext.
	 */
	private static final ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new MapThreadLocal();

	/**
	 * Put.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	public static void put(String key, Object value) {
		getContextMap().put(key, value);
	}

	/**
	 * Remove object.
	 *
	 * @param key the key
	 *
	 * @return the object
	 */
	public static Object remove(String key) {
		return getContextMap().remove(key);
	}

	/**
	 * Get object.
	 *
	 * @param key the key
	 *
	 * @return the object
	 */
	public static Object get(String key) {
		return getContextMap().get(key);
	}

	public static Integer getUserId() {
		Object obj = getContextMap().get(GlobalConstant.SESSION_USER_KEY);
		if(obj != null){
			UserToken uToken = (UserToken)obj;
			return uToken.getUserInfo().getId();
		}
		return null;
	}

	public static Integer getRoleId() {
		Object obj = getContextMap().get(GlobalConstant.SESSION_USER_KEY);
		if(obj != null){
			UserToken uToken = (UserToken)obj;
			return uToken.getRoleInfo().getId();
		}
		return null;
	}

	public static Integer getUnitId() {
		Object obj = getContextMap().get(GlobalConstant.SESSION_USER_KEY);
		if(obj != null){
			UserToken uToken = (UserToken)obj;
			return uToken.getUserInfo().getUnitId();
		}
		return null;
	}

	public static String getRouterUrl() {
		Object obj = getContextMap().get(GlobalConstant.ROUTER_URL);
		if(obj != null){
			return (String)obj;
		}
		return null;
	}


	private static class MapThreadLocal extends ThreadLocal<Map<String, Object>> {
		/**
		 * Initial value map.
		 *
		 * @return the map
		 */
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>(GlobalConstant.Number.NUM_3) {

				private static final long serialVersionUID = 3637958959138295593L;

				@Override
				public Object put(String key, Object value) {
					return super.put(key, value);
				}
			};
		}
	}

	/**
	 * 取得thread context Map的实例。
	 *
	 * @return thread context Map的实例
	 */
	private static Map<String, Object> getContextMap() {
		return THREAD_CONTEXT.get();
	}

	/**
	 * 清理线程所有被hold住的对象。以便重用！
	 */
	public static void remove() {
		getContextMap().clear();
	}
}
