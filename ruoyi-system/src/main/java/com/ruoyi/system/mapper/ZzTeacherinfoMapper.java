package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ZzTeacherinfo;

/**
 * 教师信息表Mapper接口
 * 
 * @author ljg
 * @date 2023-08-03
 */
public interface ZzTeacherinfoMapper 
{
    /**
     * 查询教师信息表
     * 
     * @param id 教师信息表主键
     * @return 教师信息表
     */
    public ZzTeacherinfo selectZzTeacherinfoById(Long id);

    /**
     * 查询教师信息表列表
     * 
     * @param zzTeacherinfo 教师信息表
     * @return 教师信息表集合
     */
    public List<ZzTeacherinfo> selectZzTeacherinfoList(ZzTeacherinfo zzTeacherinfo);

    /**
     * 新增教师信息表
     * 
     * @param zzTeacherinfo 教师信息表
     * @return 结果
     */
    public int insertZzTeacherinfo(ZzTeacherinfo zzTeacherinfo);

    /**
     * 修改教师信息表
     * 
     * @param zzTeacherinfo 教师信息表
     * @return 结果
     */
    public int updateZzTeacherinfo(ZzTeacherinfo zzTeacherinfo);

    /**
     * 删除教师信息表
     * 
     * @param id 教师信息表主键
     * @return 结果
     */
    public int deleteZzTeacherinfoById(Long id);

    /**
     * 批量删除教师信息表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZzTeacherinfoByIds(String[] ids);

    List<ZzTeacherinfo  > selectListByTeacherIds( List<String> userId);


    int insertTeacherinfoList( List<ZzTeacherinfo> zzTeacherinfo);
    int updateTeacherinfoList( List<ZzTeacherinfo> zzTeacherinfo);
}
