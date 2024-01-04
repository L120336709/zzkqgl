package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZzTeacherinfoMapper;
import com.ruoyi.system.domain.ZzTeacherinfo;
import com.ruoyi.system.service.IZzTeacherinfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 教师信息表Service业务层处理
 * 
 * @author ljg
 * @date 2023-08-03
 */
@Service
public class ZzTeacherinfoServiceImpl implements IZzTeacherinfoService 
{
    @Autowired
    private ZzTeacherinfoMapper zzTeacherinfoMapper;

    /**
     * 查询教师信息表
     * 
     * @param id 教师信息表主键
     * @return 教师信息表
     */
    @Override
    public ZzTeacherinfo selectZzTeacherinfoById(Long id)
    {
        return zzTeacherinfoMapper.selectZzTeacherinfoById(id);
    }

    /**
     * 查询教师信息表列表
     * 
     * @param zzTeacherinfo 教师信息表
     * @return 教师信息表
     */
    @Override
    public List<ZzTeacherinfo> selectZzTeacherinfoList(ZzTeacherinfo zzTeacherinfo)
    {
        return zzTeacherinfoMapper.selectZzTeacherinfoList(zzTeacherinfo);
    }

    /**
     * 新增教师信息表
     * 
     * @param zzTeacherinfo 教师信息表
     * @return 结果
     */
    @Override
    public int insertZzTeacherinfo(ZzTeacherinfo zzTeacherinfo)
    {
        return zzTeacherinfoMapper.insertZzTeacherinfo(zzTeacherinfo);
    }

    /**
     * 修改教师信息表
     * 
     * @param zzTeacherinfo 教师信息表
     * @return 结果
     */
    @Override
    public int updateZzTeacherinfo(ZzTeacherinfo zzTeacherinfo)
    {
        return zzTeacherinfoMapper.updateZzTeacherinfo(zzTeacherinfo);
    }

    /**
     * 批量删除教师信息表
     * 
     * @param ids 需要删除的教师信息表主键
     * @return 结果
     */
    @Override
    public int deleteZzTeacherinfoByIds(String ids)
    {
        return zzTeacherinfoMapper.deleteZzTeacherinfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除教师信息表信息
     * 
     * @param id 教师信息表主键
     * @return 结果
     */
    @Override
    public int deleteZzTeacherinfoById(Long id)
    {
        return zzTeacherinfoMapper.deleteZzTeacherinfoById(id);
    }


    @Override
    public  List<ZzTeacherinfo> selectListByTeacherIds(List<String> userId)
    {
        return zzTeacherinfoMapper.selectListByTeacherIds(userId);
    }



    @Override
    public int insertTeacherinfoList(List<ZzTeacherinfo>  zzTeacherinfo)
    {
        return zzTeacherinfoMapper.insertTeacherinfoList(zzTeacherinfo);
    }

    @Override
    public int updateTeacherinfoList(List<ZzTeacherinfo> zzTeacherinfo)
    {
        return zzTeacherinfoMapper.updateTeacherinfoList(zzTeacherinfo);
    }

}
