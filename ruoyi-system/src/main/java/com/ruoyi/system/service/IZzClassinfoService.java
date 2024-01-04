package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ZzClassinfo;

/**
 * 班级信息表Service接口
 * 
 * @author ljg
 * @date 2023-08-03
 */
public interface IZzClassinfoService 
{
    /**
     * 查询班级信息表
     * 
     * @param id 班级信息表主键
     * @return 班级信息表
     */
    public ZzClassinfo selectZzClassinfoById(Long id);

    /**
     * 查询班级信息表列表
     * 
     * @param zzClassinfo 班级信息表
     * @return 班级信息表集合
     */
    public List<ZzClassinfo> selectZzClassinfoList(ZzClassinfo zzClassinfo);

    String ExamZzClassinfoListImport  (List<ZzClassinfo> zzClassinfoList, Integer updateSupport );
    /**
     * 新增班级信息表
     * 
     * @param zzClassinfo 班级信息表
     * @return 结果
     */
    public int insertZzClassinfo(ZzClassinfo zzClassinfo);

    /**
     * 修改班级信息表
     * 
     * @param zzClassinfo 班级信息表
     * @return 结果
     */
    public int updateZzClassinfo(ZzClassinfo zzClassinfo);

    /**
     * 批量删除班级信息表
     * 
     * @param ids 需要删除的班级信息表主键集合
     * @return 结果
     */
    public int deleteZzClassinfoByIds(String ids);

    /**
     * 删除班级信息表信息
     * 
     * @param id 班级信息表主键
     * @return 结果
     */
    public int deleteZzClassinfoById(Long id);




    public  List<ZzClassinfo> selectListByClassIds(List<String> gradeId);


    public int insertClassinfoList(List<ZzClassinfo>  zzClassinfo);

    public int updateClassinfoList(List<ZzClassinfo> zzClassinfo);
}
