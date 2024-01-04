package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ZzTrains;
import org.apache.ibatis.annotations.Param;

/**
 * 培训记录表Mapper接口
 * 
 * @author ljg
 * @date 2023-12-05
 */
public interface ZzTrainsMapper 
{
    /**
     * 查询培训记录表
     * 
     * @param id 培训记录表主键
     * @return 培训记录表
     */
    public ZzTrains selectZzTrainsById(Long id);

    /**
     * 查询培训记录表列表
     * 
     * @param zzTrains 培训记录表
     * @return 培训记录表集合
     */
    public List<ZzTrains> selectZzTrainsList(ZzTrains zzTrains);

    List<ZzTrains> findZzTrainsByIdnumberList(@Param("list") List<String> jobid);
    int insertZzTrainsList( List<ZzTrains>  zzTrains);
    int updateZzTrainsList( List<ZzTrains> zzTrains);
    /**
     * 新增培训记录表
     * 
     * @param zzTrains 培训记录表
     * @return 结果
     */
    public int insertZzTrains(ZzTrains zzTrains);

    /**
     * 修改培训记录表
     * 
     * @param zzTrains 培训记录表
     * @return 结果
     */
    public int updateZzTrains(ZzTrains zzTrains);

    /**
     * 删除培训记录表
     * 
     * @param id 培训记录表主键
     * @return 结果
     */
    public int deleteZzTrainsById(Long id);

    /**
     * 批量删除培训记录表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZzTrainsByIds(String[] ids);
}
