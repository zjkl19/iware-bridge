package com.iware.bridge.online.listener;

import com.iware.bridge.app.inspection.service.AppNoticeService;
import com.iware.bridge.info.dao.UserExpDao;
import com.iware.bridge.model.dao.global.StructureDao;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.model.entity.user.User;
import com.iware.bridge.online.service.MailService;
import com.iware.bridge.online.service.SensorService;
import com.iware.bridge.online.utils.OnlineUtils;
import com.iware.bridge.online.vo.ProjectUserVO;
import com.iware.cache.redis.RedisUtil;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.enums.SensorStatusEnum;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 监听redis key失效
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListener.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PowerProperties power;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private UserExpDao userExpDao;
    @Autowired
    private StructureDao structureDao;
    @Autowired
    private AppNoticeService appNoticeService;
    @Autowired
    private MailService mailService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        logger.warn("redis失效的key{}" + message.toString());
        if (key.contains(GlobalConstant.SENSOR_EXPIRE)) { //判断为设备的缓存失效时
            sensorExpireHandler(key);
        }
    }

    /**
     * 设备的缓存失效处理
     */
    @Transactional
    public void sensorExpireHandler(String key) {

        String time = DateUtils.formatDate(new Date(), GlobalConstant.FULL_DATE_FORMAT);
        //判断当前传感器状态，如果原本是在线，则更新状态通知
        String[] args = key.split(GlobalConstant.Symbol.MH);
        Integer meansPointStatus = (Integer) redisUtil.hget(GlobalConstant.SENSOR_STATUS, args[1]);
        if (SensorStatusEnum.ONLINE.getCode().equals(meansPointStatus)) {
            //更新传感器状态
            redisUtil.hset(GlobalConstant.SENSOR_STATUS, args[1], SensorStatusEnum.OFFLINE.getCode());
            sensorService.updateSensorStatus(Integer.parseInt(args[1]), SensorStatusEnum.OFFLINE.getCode());
            //发送短信

            MeansPoint meansPoint = (MeansPoint) redisUtil.get(OnlineUtils.getMeansPointKey(args[1]));
            Structure structure = structureDao.findById(meansPoint.getStructureId());
            //获取此结构物的承接单位列表
            ProjectUserVO projectUserVO = userExpDao.getUserListByStructure(meansPoint.getStructureId(), power.getOnlinePower());

            if (projectUserVO != null && !CollectionUtils.isEmpty(projectUserVO.getUserList())) {
                List<User> userList = projectUserVO.getUserList();

                StringBuilder content = new StringBuilder();

                content.append("【离线通知】");
                content.append(time).append("，\"").append(projectUserVO.getName())
                        .append("项目\" 中的 \"").append(structure.getName())
                        .append("结构物\" ，测点编号为 \"").append(meansPoint.getName())
                        .append("\" 的传感器近3次无数据，疑似离线，请及时检查和处理。");

                appNoticeService.saveNoticeByUserList(userList, content.toString());

                //邮件通知
                content.append("——桥隧管养一体化平台");
                List<String> emails = userList.stream().filter(item -> !StringUtils.isEmpty(item.getEmail()))
                        .map(User::getEmail).distinct().collect(Collectors.toList());

                mailService.batchSendMessage(emails, "离线通知", content.toString());
            }
        }
    }
}
