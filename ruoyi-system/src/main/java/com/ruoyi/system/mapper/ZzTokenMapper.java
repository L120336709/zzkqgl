package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ZzToken;

/**
 * token存储Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-19
 */
public interface ZzTokenMapper 
{
    /**
     * 查询token存储
     * 
     * @param id token存储主键
     * @return token存储
     */
    public ZzToken selectZzTokenById(Long id);

    /**
     * 查询token存储列表
     * 
     * @param zzToken token存储
     * @return token存储集合
     */
    public List<ZzToken> selectZzTokenList(ZzToken zzToken);

    /**
     * 新增token存储
     * 
     * @param zzToken token存储
     * @return 结果
     */
    public int insertZzToken(ZzToken zzToken);

    /**
     * 修改token存储
     * 
     * @param zzToken token存储
     * @return 结果
     */
    public int updateZzToken(ZzToken zzToken);

    /**
     * 删除token存储
     * 
     * @param id token存储主键
     * @return 结果
     */
    public int deleteZzTokenById(Long id);

    /**
     * 批量删除token存储
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZzTokenByIds(String[] ids);
}
