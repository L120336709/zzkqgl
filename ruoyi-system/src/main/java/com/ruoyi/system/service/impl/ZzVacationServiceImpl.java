package com.ruoyi.system.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZzVacationMapper;
import com.ruoyi.system.domain.ZzVacation;
import com.ruoyi.system.service.IZzVacationService;
import com.ruoyi.common.core.text.Convert;

/**
 * 学生请假表Service业务层处理
 * 
 * @author ljg
 * @date 2023-08-03
 */
@Service
public class ZzVacationServiceImpl implements IZzVacationService 
{
    @Autowired
    private ZzVacationMapper zzVacationMapper;

    /**
     * 查询学生请假表
     * 
     * @param id 学生请假表主键
     * @return 学生请假表
     */
    @Override
    public ZzVacation selectZzVacationById(Long id)
    {
        return zzVacationMapper.selectZzVacationById(id);
    }

    /**
     * 查询学生请假表列表
     * 
     * @param zzVacation 学生请假表
     * @return 学生请假表
     */
    @Override
    public List<ZzVacation> selectZzVacationList(ZzVacation zzVacation)
    {
        return zzVacationMapper.selectZzVacationList(zzVacation);
    }

    @Override
    public List<ZzVacation> selectZzVacationListByTime(ZzVacation zzVacation)
    {
        return zzVacationMapper.selectZzVacationListByTime(zzVacation);
    }


    @Override
    public List<ZzVacation  > selectListByIds( List<String> studentId,String sqTime,String sqStatus,String qjStatus1 ,String qjStatus2,String qjStatus3)
    {

        HashMap<String,Object> map = new HashMap<>();
        map.put("list", studentId);
        map.put("sqTime",sqTime);
        map.put("qjStatus1",qjStatus1);
        map.put("qjStatus2",qjStatus2);
        map.put("qjStatus3",qjStatus3);

        map.put("sqStatus",sqStatus);

        return zzVacationMapper.selectListByIds(map);
    }


    @Override
    public List<ZzVacation  > selectListByIdsAndSqTime( List<String> studentId,String startTime,String endTime,String sqStatus)
    {

        HashMap<String,Object> map = new HashMap<>();
        map.put("list", studentId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("sqStatus",sqStatus);

        return zzVacationMapper.selectListByIdsAndSqTime(map);
    }

    @Override
    public List<ZzVacation> selectZzVacationList31(ZzVacation zzVacation)
    {
        return zzVacationMapper.selectZzVacationList31(zzVacation);
    }


    /**
     * 新增学生请假表
     * 
     * @param zzVacation 学生请假表
     * @return 结果
     */
    @Override
    public int insertZzVacation(ZzVacation zzVacation)
    {
        return zzVacationMapper.insertZzVacation(zzVacation);
    }

    /**
     * 修改学生请假表
     * 
     * @param zzVacation 学生请假表
     * @return 结果
     */
    @Override
    public int updateZzVacation(ZzVacation zzVacation)
    {
        return zzVacationMapper.updateZzVacation(zzVacation);
    }

    /**
     * 批量删除学生请假表
     * 
     * @param ids 需要删除的学生请假表主键
     * @return 结果
     */
    @Override
    public int deleteZzVacationByIds(String ids)
    {
        return zzVacationMapper.deleteZzVacationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除学生请假表信息
     * 
     * @param id 学生请假表主键
     * @return 结果
     */
    @Override
    public int deleteZzVacationById(Long id)
    {
        return zzVacationMapper.deleteZzVacationById(id);
    }
}
