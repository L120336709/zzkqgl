package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ZzClassinfo;
import org.apache.ibatis.annotations.Param;

/**
 * 班级信息表Mapper接口
 * 
 * @author ljg
 * @date 2023-08-03
 */
public interface ZzClassinfoMapper 
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

    List<ZzClassinfo> findByIdnumberList(@Param("list") List<String> classNickname);
    int insertZzClassinfoList( List<ZzClassinfo>  zzClassinfo);
    int updateZzClassinfoList( List<ZzClassinfo> zzClassinfo);
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
     * 删除班级信息表
     * 
     * @param id 班级信息表主键
     * @return 结果
     */
    public int deleteZzClassinfoById(Long id);

    /**
     * 批量删除班级信息表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZzClassinfoByIds(String[] ids);

    List<ZzClassinfo > selectListByClassIds( List<String> gradeId);


    int insertClassinfoList( List<ZzClassinfo> zzClassinfo);
    int updateClassinfoList( List<ZzClassinfo> zzClassinfo);
}
