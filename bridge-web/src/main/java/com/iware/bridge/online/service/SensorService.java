package com.iware.bridge.online.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.online.vo.MeansPointVO;
import com.iware.bridge.online.vo.SelectVO;
import com.iware.bridge.online.vo.SensorFilter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-27
 */

public interface SensorService {

    /** 获取传感器列表 */
    PageInfo<MeansPointVO> listSensor(Integer pageNum, Integer pageSize, SensorFilter filter);

    /** 新增一条传感器 */
    public Integer addSensor(MeansPointVO meansPointVO, MultipartFile file);

    /** 修改一条传感器 */
    public void updSensor(MeansPointVO meansPointVO, MultipartFile file);

    /** 删除一条传感器 */
    public void deleteById(Integer id);

    /** 时程数据页面获取传感器信息 */
    public List<Object> listByType(Integer type, Integer structureId, Integer queryAll);

    /** 获取传感器类型/原理/产商/构件 */
    public SelectVO getSelectList();

    /** 加载传感器到缓存 */
    public void loadSensorList();

    /** 每小时更新高频和无频率值的传感器状态*/
    public void updateStatusByFrequency();

    /** 更新单个传感器状态 **/
    public void updateSensorStatus(Integer meansPointId, Integer status);
}
