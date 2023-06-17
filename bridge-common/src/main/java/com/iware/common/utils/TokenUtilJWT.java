package com.iware.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtilJWT {

    private static Logger logger = LoggerFactory.getLogger(TokenUtilJWT.class);

    //公共密钥客户端不会知道
    public static String SECRET = "iware123";

    public static String createToken(String userID, String username) {
        //签名发布时间
        Date iatDate=new Date();

        //设置签名过期时间  1分钟
        Calendar nowTime=Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,1);
        Date expiresDate=nowTime.getTime();

        Map<String,Object> map=new HashMap<String, Object>();
        map.put("alg","HS256");//设置算法 为HS256
        map.put("typ","JWT");//设置类型为JWT
        String token = "";
		try {
			token = JWT.create().withHeader(map)
			        .withClaim("userID",userID)
			        .withClaim("username",username)
			        .withIssuedAt(iatDate)  //设置签发时间
			        .withExpiresAt(expiresDate)  //设置过期时间 过期时间大于签发时间
			        .sign(Algorithm.HMAC256(SECRET));//用公共密钥加密
            logger.info("----------- 生成Token --------> {} ", token);
		} catch (IllegalArgumentException | JWTCreationException e) {
            logger.error(ExceptionUtils.getErrorStack(e));
		}
       return token;
    }

    public static String createToken(Map<String,String> mapParam,Integer expireTime) {
        //签名发布时间
        Date iatDate=new Date();

        //设置签名过期时间  1分钟
        Calendar nowTime=Calendar.getInstance();
        nowTime.add(Calendar.SECOND,expireTime);
        Date expiresDate=nowTime.getTime();

        Map<String,Object> map=new HashMap<String, Object>();
        map.put("alg","HS256");//设置算法 为HS256
        map.put("typ","JWT");//设置类型为JWT
        String token = "";
        try {
            JWTCreator.Builder tokenObject = JWT.create().withHeader(map);

            if(!mapParam.isEmpty()) {
                for (Map.Entry<String, String> entry : mapParam.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    tokenObject=tokenObject.withClaim(key,value);
                }
            }
            token=tokenObject.withIssuedAt(iatDate)  //设置签发时间
                    .withExpiresAt(expiresDate)  //设置过期时间 过期时间大于签发时间
                    .sign(Algorithm.HMAC256(SECRET));//用公共密钥加密
            logger.info("----------- 生成Token --------> {} ", token);
        } catch (IllegalArgumentException|JWTCreationException e) {
            logger.error(ExceptionUtils.getErrorStack(e));
        }
        return token;
    }

    public static Map<String,Claim> verifyToken(String token) {
        logger.info("----------- 验证Token --------> {} ", token);
        JWTVerifier verifier = null;
        DecodedJWT jwt=null;
		try {
			//用公共密钥解密验证
			verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        }catch(Exception e) {
            throw new BusinessException(GlobalResultEnum.CACHE_EXPIRE);
        }
        return jwt.getClaims();
    }
}
