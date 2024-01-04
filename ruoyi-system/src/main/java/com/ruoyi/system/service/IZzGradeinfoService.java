package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ZzGradeinfo;

/**
 * 年级表Service接口
 * 
 * @author ljg
 * @date 2023-08-03
 */
public interface IZzGradeinfoService 
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
    public  List<ZzGradeinfo> selectListByGradeIds(List<String> gradeId);

    public int insertGradeinfoList(List<ZzGradeinfo>  zzGradeinfo);
    public int updateGradeinfoList(List<ZzGradeinfo>  zzGradeinfo);
    /**
     * 批量删除年级表
     * 
     * @param ids 需要删除的年级表主键集合
     * @return 结果
     */
    public int deleteZzGradeinfoByIds(String ids);

    /**
     * 删除年级表信息
     * 
     * @param id 年级表主键
     * @return 结果
     */
    public int deleteZzGradeinfoById(Long id);
}
