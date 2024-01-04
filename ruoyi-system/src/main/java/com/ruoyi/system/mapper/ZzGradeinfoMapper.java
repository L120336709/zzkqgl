package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ZzGradeinfo;
import org.apache.ibatis.annotations.Param;

/**
 * 年级表Mapper接口
 * 
 * @author ljg
 * @date 2023-08-03
 */
public interface ZzGradeinfoMapper 
{
    /**
     * 查询年级表
     * 
     * @param id 年级表主键
     * @return 年级表
     */
    public ZzGradeinfo selectZzGradeinfoById(Long id);

    /**
     * 查询年级表列表
     * 
     * @param zzGradeinfo 年级表
     * @return 年级表集合
     */
    public List<ZzGradeinfo> selectZzGradeinfoList(ZzGradeinfo zzGradeinfo);

    /**
     * 新增年级表
     * 
     * @param zzGradeinfo 年级表
     * @return 结果
     */
    public int insertZzGradeinfo(ZzGradeinfo zzGradeinfo);

    /**
     * 修改年级表
     * 
     * @param zzGradeinfo 年级表
     * @return 结果
     */
    public int updateZzGradeinfo(ZzGradeinfo zzGradeinfo);


    List<ZzGradeinfo> selectListByGradeIds(@Param("list") List<String> gradeId);


    int insertGradeinfoList(@Param("list") List<ZzGradeinfo> zzGradeinfo);
    int updateGradeinfoList(@Param("list") List<ZzGradeinfo> zzGradeinfo);

    /**
     * 删除年级表
     * 
     * @param id 年级表主键
     * @return 结果
     */
    public int deleteZzGradeinfoById(Long id);

    /**
     * 批量删除年级表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZzGradeinfoByIds(String[] ids);
}
