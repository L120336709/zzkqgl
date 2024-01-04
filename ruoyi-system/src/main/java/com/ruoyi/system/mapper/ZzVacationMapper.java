package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.ZzVacation;
import org.apache.ibatis.annotations.Param;

/**
 * 学生请假表Mapper接口
 *
 * @author ljg
 * @date 2023-08-03
 */
public interface ZzVacationMapper {
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

    List<ZzVacation> selectListByIds(Map<String, Object> map);

    List<ZzVacation> selectListByIdsAndSqTime(Map<String, Object> map);



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
     * 删除学生请假表
     *
     * @param id 学生请假表主键
     * @return 结果
     */
    public int deleteZzVacationById(Long id);

    /**
     * 批量删除学生请假表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZzVacationByIds(String[] ids);
}
