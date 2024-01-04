package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZzGradeinfoMapper;
import com.ruoyi.system.domain.ZzGradeinfo;
import com.ruoyi.system.service.IZzGradeinfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 年级表Service业务层处理
 * 
 * @author ljg
 * @date 2023-08-03
 */
@Service
public class ZzGradeinfoServiceImpl implements IZzGradeinfoService 
{
    @Autowired
    private ZzGradeinfoMapper zzGradeinfoMapper;

    /**
     * 查询年级表
     * 
     * @param id 年级表主键
     * @return 年级表
     */
    @Override
    public ZzGradeinfo selectZzGradeinfoById(Long id)
    {
        return zzGradeinfoMapper.selectZzGradeinfoById(id);
    }

    /**
     * 查询年级表列表
     * 
     * @param zzGradeinfo 年级表
     * @return 年级表
     */
    @Override
    public List<ZzGradeinfo> selectZzGradeinfoList(ZzGradeinfo zzGradeinfo)
    {
        return zzGradeinfoMapper.selectZzGradeinfoList(zzGradeinfo);
    }

    /**
     * 新增年级表
     * 
     * @param zzGradeinfo 年级表
     * @return 结果
     */
    @Override
    public int insertZzGradeinfo(ZzGradeinfo zzGradeinfo)
    {
        return zzGradeinfoMapper.insertZzGradeinfo(zzGradeinfo);
    }

    /**
     * 修改年级表
     * 
     * @param zzGradeinfo 年级表
     * @return 结果
     */
    @Override
    public int updateZzGradeinfo(ZzGradeinfo zzGradeinfo)
    {
        return zzGradeinfoMapper.updateZzGradeinfo(zzGradeinfo);
    }


    @Override
    public  List<ZzGradeinfo> selectListByGradeIds(List<String> gradeId)
    {
        return zzGradeinfoMapper.selectListByGradeIds(gradeId);
    }



    @Override
    public int insertGradeinfoList(List<ZzGradeinfo>  zzGradeinfo)
    {
        return zzGradeinfoMapper.insertGradeinfoList(zzGradeinfo);
    }

    @Override
    public int updateGradeinfoList(List<ZzGradeinfo> zzGradeinfo)
    {
        return zzGradeinfoMapper.updateGradeinfoList(zzGradeinfo);
    }

    /**
     * 批量删除年级表
     * 
     * @param ids 需要删除的年级表主键
     * @return 结果
     */
    @Override
    public int deleteZzGradeinfoByIds(String ids)
    {
        return zzGradeinfoMapper.deleteZzGradeinfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除年级表信息
     * 
     * @param id 年级表主键
     * @return 结果
     */
    @Override
    public int deleteZzGradeinfoById(Long id)
    {
        return zzGradeinfoMapper.deleteZzGradeinfoById(id);
    }
}
