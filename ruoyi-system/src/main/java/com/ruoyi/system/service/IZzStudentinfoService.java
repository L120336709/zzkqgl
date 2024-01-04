package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.ZzStudentinfo;

/**
 * 学生信息表Service接口
 *
 * @author ljg
 * @date 2023-08-03
 */
public interface IZzStudentinfoService {
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

    String ExamZzStudentinfoListImport(List<ZzStudentinfo> zzStudentinfoList, Integer updateSupport );

    public List<ZzStudentinfo> selectZzStuListByclassId(String classId);
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
     * 批量删除学生信息表
     *
     * @param ids 需要删除的学生信息表主键集合
     * @return 结果
     */
    public int deleteZzStudentinfoByIds(String ids);

    /**
     * 删除学生信息表信息
     *
     * @param id 学生信息表主键
     * @return 结果
     */
    public int deleteZzStudentinfoById(Long id);

    public List<ZzStudentinfo> selectListByStudentinfoIds(List<String> userId);

    public int insertStudentinfoList(List<ZzStudentinfo> zzStudentinfo);

    public int updateStudentinfoList(List<ZzStudentinfo> zzStudentinfo);
}
