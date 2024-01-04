package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ZzVacation;

/**
 * 学生请假表Service接口
 * 
 * @author ljg
 * @date 2023-08-03
 */
public interface IZzVacationService 
{
    /**
     * 查询学生请假表
     * 
     * @param id 学生请假表主键
     * @return 学生请假表
     */
    public ZzVacation selectZzVacationById(Long id);

    /**
     * 查询学生请假表列表
     * 
     * @param zzVacation 学生请假表
     * @return 学生请假表集合
     */
    public List<ZzVacation> selectZzVacationList(ZzVacation zzVacation);

    public List<ZzVacation> selectZzVacationListByTime(ZzVacation zzVacation);

    public List<ZzVacation> selectZzVacationList31(ZzVacation zzVacation);

    public List<ZzVacation  > selectListByIds( List<String> studentId,String sqTime,String sqStatus, String qjStatus1 ,String qjStatus2,String qjStatus3);

    public List<ZzVacation  > selectListByIdsAndSqTime( List<String> studentId,String startTime,String endTime,String sqStatus);



    /**
     * 新增学生请假表
     * 
     * @param zzVacation 学生请假表
     * @return 结果
     */
    public int insertZzVacation(ZzVacation zzVacation);

    /**
     * 修改学生请假表
     * 
     * @param zzVacation 学生请假表
     * @return 结果
     */
    public int updateZzVacation(ZzVacation zzVacation);

    /**
     * 批量删除学生请假表
     * 
     * @param ids 需要删除的学生请假表主键集合
     * @return 结果
     */
    public int deleteZzVacationByIds(String ids);

    /**
     * 删除学生请假表信息
     * 
     * @param id 学生请假表主键
     * @return 结果
     */
    public int deleteZzVacationById(Long id);
}
