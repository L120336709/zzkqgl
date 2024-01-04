package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ZzTeachClass;

/**
 * 教师绑定班级Service接口
 * 
 * @author ljg
 * @date 2023-08-03
 */
public interface IZzTeachClassService 
{
    /**
     * 查询教师绑定班级
     * 
     * @param id 教师绑定班级主键
     * @return 教师绑定班级
     */
    public ZzTeachClass selectZzTeachClassById(Long id);

    /**
     * 查询教师绑定班级列表
     * 
     * @param zzTeachClass 教师绑定班级
     * @return 教师绑定班级集合
     */
    public List<ZzTeachClass> selectZzTeachClassList(ZzTeachClass zzTeachClass);

    /**
     * 新增教师绑定班级
     * 
     * @param zzTeachClass 教师绑定班级
     * @return 结果
     */
    public int insertZzTeachClass(ZzTeachClass zzTeachClass);

    /**
     * 修改教师绑定班级
     * 
     * @param zzTeachClass 教师绑定班级
     * @return 结果
     */
    public int updateZzTeachClass(ZzTeachClass zzTeachClass);

    /**
     * 批量删除教师绑定班级
     * 
     * @param ids 需要删除的教师绑定班级主键集合
     * @return 结果
     */
    public int deleteZzTeachClassByIds(String ids);

    /**
     * 删除教师绑定班级信息
     * 
     * @param id 教师绑定班级主键
     * @return 结果
     */
    public int deleteZzTeachClassById(Long id);
}
