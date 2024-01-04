package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZzTokenMapper;
import com.ruoyi.system.domain.ZzToken;
import com.ruoyi.system.service.IZzTokenService;
import com.ruoyi.common.core.text.Convert;

/**
 * token存储Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-19
 */
@Service
public class ZzTokenServiceImpl implements IZzTokenService 
{
    @Autowired
    private ZzTokenMapper zzTokenMapper;

    /**
     * 查询token存储
     * 
     * @param id token存储主键
     * @return token存储
     */
    @Override
    public ZzToken selectZzTokenById(Long id)
    {
        return zzTokenMapper.selectZzTokenById(id);
    }

    /**
     * 查询token存储列表
     * 
     * @param zzToken token存储
     * @return token存储
     */
    @Override
    public List<ZzToken> selectZzTokenList(ZzToken zzToken)
    {
        return zzTokenMapper.selectZzTokenList(zzToken);
    }

    /**
     * 新增token存储
     * 
     * @param zzToken token存储
     * @return 结果
     */
    @Override
    public int insertZzToken(ZzToken zzToken)
    {
        return zzTokenMapper.insertZzToken(zzToken);
    }

    /**
     * 修改token存储
     * 
     * @param zzToken token存储
     * @return 结果
     */
    @Override
    public int updateZzToken(ZzToken zzToken)
    {
        return zzTokenMapper.updateZzToken(zzToken);
    }

    /**
     * 批量删除token存储
     * 
     * @param ids 需要删除的token存储主键
     * @return 结果
     */
    @Override
    public int deleteZzTokenByIds(String ids)
    {
        return zzTokenMapper.deleteZzTokenByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除token存储信息
     * 
     * @param id token存储主键
     * @return 结果
     */
    @Override
    public int deleteZzTokenById(Long id)
    {
        return zzTokenMapper.deleteZzTokenById(id);
    }
}
