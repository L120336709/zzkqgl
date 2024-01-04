package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZzTeachClassMapper;
import com.ruoyi.system.domain.ZzTeachClass;
import com.ruoyi.system.service.IZzTeachClassService;
import com.ruoyi.common.core.text.Convert;

/**
 * 教师绑定班级Service业务层处理
 * 
 * @author ljg
 * @date 2023-08-03
 */
@Service
public class ZzTeachClassServiceImpl implements IZzTeachClassService 
{
    @Autowired
    private ZzTeachClassMapper zzTeachClassMapper;

    /**
     * 查询教师绑定班级
     * 
     * @param id 教师绑定班级主键
     * @return 教师绑定班级
     */
    @Override
    public ZzTeachClass selectZzTeachClassById(Long id)
    {
        return zzTeachClassMapper.selectZzTeachClassById(id);
    }

    /**
     * 查询教师绑定班级列表
     * 
     * @param zzTeachClass 教师绑定班级
     * @return 教师绑定班级
     */
    @Override
    public List<ZzTeachClass> selectZzTeachClassList(ZzTeachClass zzTeachClass)
    {
        return zzTeachClassMapper.selectZzTeachClassList(zzTeachClass);
    }

    /**
     * 新增教师绑定班级
     * 
     * @param zzTeachClass 教师绑定班级
     * @return 结果
     */
    @Override
    public int insertZzTeachClass(ZzTeachClass zzTeachClass)
    {
        zzTeachClass.setCreateTime(DateUtils.getNowDate());
        SysUser sysUser=ShiroUtils.getSysUser();
        zzTeachClass.setCreatePeople(sysUser.getUserName());
        return zzTeachClassMapper.insertZzTeachClass(zzTeachClass);
    }

    /**
     * 修改教师绑定班级
     * 
     * @param zzTeachClass 教师绑定班级
     * @return 结果
     */
    @Override
    public int updateZzTeachClass(ZzTeachClass zzTeachClass)
    {
        return zzTeachClassMapper.updateZzTeachClass(zzTeachClass);
    }

    /**
     * 批量删除教师绑定班级
     * 
     * @param ids 需要删除的教师绑定班级主键
     * @return 结果
     */
    @Override
    public int deleteZzTeachClassByIds(String ids)
    {
        return zzTeachClassMapper.deleteZzTeachClassByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除教师绑定班级信息
     * 
     * @param id 教师绑定班级主键
     * @return 结果
     */
    @Override
    public int deleteZzTeachClassById(Long id)
    {
        return zzTeachClassMapper.deleteZzTeachClassById(id);
    }
}
