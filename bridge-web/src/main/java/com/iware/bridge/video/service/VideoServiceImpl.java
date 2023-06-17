package com.iware.bridge.video.service;

import com.alibaba.fastjson.JSONObject;
import com.iware.bridge.model.dao.global.VideoDao;
import com.iware.bridge.model.entity.global.Video;
import com.iware.bridge.video.dao.VideoExpDao;
import com.iware.bridge.video.vo.StructureVideoVO;
import com.iware.bridge.video.vo.YingshiYunToken;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: HX
 * @date: 2021-6-7
 */

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;
    @Autowired
    private VideoExpDao videoExpDao;

    @Override
    public String getAccessToken() {
        if (YingshiYunToken.YingYunAcessToken != null && YingshiYunToken.YingYunAcessToken.size() > 0) {

            long expireTime = Long.parseLong(YingshiYunToken.get(YingshiYunToken.EXPIRE_TIME));
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if(currentTime < expireTime - 15000){
                String accessToken = YingshiYunToken.get(YingshiYunToken.ACCESS_TOKEN);
                return accessToken;
            }

        }
        Map<String, String> requestData = new HashMap<>();
        requestData.put("appKey","1a12f657fba94ac992d51ccb57a28ed7");
        requestData.put("appSecret","7537a134e834850a1f2e5a1c381b2e4f");
        HttpClientUtil client = new HttpClientUtil("https://open.ys7.com/api/lapp/token/get","post",requestData);
        client.request();
        try {
            JSONObject response = client.getResponse();
            int statusCode = client.getStatus();
            if(statusCode == 200 && response.get("code").equals("200")){
                String dataString = response.getString("data");
                JSONObject dataObj = JSONObject.parseObject(dataString);
                String yingAccessToken = dataObj.getString(YingshiYunToken.ACCESS_TOKEN);
                String expireTime = dataObj.getString(YingshiYunToken.EXPIRE_TIME);
                YingshiYunToken.YingYunAcessToken = new HashMap<>();
                YingshiYunToken.put(YingshiYunToken.ACCESS_TOKEN, yingAccessToken);
                YingshiYunToken.put(YingshiYunToken.EXPIRE_TIME, expireTime);
                return yingAccessToken;
            }
        } catch (IOException e) {
            throw new BusinessException("萤石云身份认证失败！");
        }
        return null;
    }

    @Override
    public List<StructureVideoVO> getStructureVideoList(Integer projectId) {
        return videoExpDao.getStructureVideoList(projectId);
    }

    @Override
    public void addVideo(Video video) {
        videoDao.save(video);
    }

    @Override
    public void updVideo(Video video) {
        videoDao.update(video);
    }

    @Override
    public void deleteById(Integer id) {
        videoDao.deleteById(id);
    }
}
