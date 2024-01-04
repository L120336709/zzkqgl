package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ZzStudentinfo;
import org.apache.ibatis.annotations.Param;

/**
 * 学生信息表Mapper接口
 * 
 * @author ljg
 * @date 2023-08-03
 */
public interface ZzStudentinfoMapper 
{
    /**
     * 查询学生信息表
     * 
     * @param id 学生信息表主键
     * @return 学生信息表
     */
    public ZzStudentinfo selectZzStudentinfoById(Long id);

    /**
     * 查询学生信息表列表
     * 
     * @param zzStudentinfo 学生信息表
     * @return 学生信息表集合
     */
    public List<ZzStudentinfo> selectZzStudentinfoList(ZzStudentinfo zzStudentinfo);

    List<ZzStudentinfo> selectZzStuListByclassId(ZzStudentinfo zzStudentinfo);

    //根据学生考号
    List<ZzStudentinfo> findZzStudentinfoByIdnumberList(@Param("list") List<String> Idcard);
    /**
     * 新增学生信息表
     * 
     * @param zzStudentinfo 学生信息表
     * @return 结果
     */
    public int insertZzStudentinfo(ZzStudentinfo zzStudentinfo);

    /**
     * 修改学生信息表
     * 
     * @param zzStudentinfo 学生信息表
     * @return 结果
     */
    public int updateZzStudentinfo(ZzStudentinfo zzStudentinfo);

    /**
     * 删除学生信息表
     * 
     * @param id 学生信息表主键
     * @return 结果
     */
    public int deleteZzStudentinfoById(Long id);

    /**
     * 批量删除学生信息表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZzStudentinfoByIds(String[] ids);


    List<ZzStudentinfo  > selectListByStudentinfoIds( List<String> userId);

    int insertStudentinfoList( List<ZzStudentinfo>  zzStudentinfo);
    int updateStudentinfoList( List<ZzStudentinfo> zzStudentinfo);
}
