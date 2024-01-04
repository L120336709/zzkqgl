package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZzTrainsMapper;
import com.ruoyi.system.domain.ZzTrains;
import com.ruoyi.system.service.IZzTrainsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 培训记录表Service业务层处理
 * 
 * @author ljg
 * @date 2023-12-05
 */
@Service
public class ZzTrainsServiceImpl implements IZzTrainsService 
{
    @Autowired
    private ZzTrainsMapper zzTrainsMapper;

    /**
     * 查询培训记录表
     * 
     * @param id 培训记录表主键
     * @return 培训记录表
     */
    @Override
    public ZzTrains selectZzTrainsById(Long id)
    {
        return zzTrainsMapper.selectZzTrainsById(id);
    }

    /**
     * 查询培训记录表列表
     * 
     * @param zzTrains 培训记录表
     * @return 培训记录表
     */
    @Override
    public List<ZzTrains> selectZzTrainsList(ZzTrains zzTrains)
    {
        return zzTrainsMapper.selectZzTrainsList(zzTrains);
    }
    @Override
    public String ExamZzTrainsListImport(List<ZzTrains> zzTrainsList, Integer updateSupport ){
        if (StringUtils.isNull(zzTrainsList) || zzTrainsList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }

        StringBuilder nullfenshuMsg = new StringBuilder();
        int nullfen = 0;
        //记录本次导入数据的所有数据，筛选是否有重复的
        StringBuilder examsidMsg = new StringBuilder();
        List<ZzTrains> ZzTrainsCFuList = new ArrayList<>();

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<ZzTrains> ZzTrainsinsert = new ArrayList<>();
        List<ZzTrains> ZzTrainsupdate = new ArrayList<>();

        List<String> Idnumber = new ArrayList<>();
        for (ZzTrains zzStudentinfo : zzTrainsList) {
            Idnumber.add(zzStudentinfo.getJobid());
        }

        //先查当前考试的所有缺考学生数据
        List<ZzTrains> ZzTrainssOld = new ArrayList<>();

        //删除旧数据，包括临时数据,删除了旧数据就不用查已存的成绩了
        if (updateSupport == 1) {
            ZzTrainssOld = zzTrainsMapper.findZzTrainsByIdnumberList(Idnumber);
        }


        //把存在的和不存在的数据分别保存，进行插入和更新
        for (ZzTrains zzTrainsNew : zzTrainsList) {
            //判断是否有空值的必填数据
            int nulls = 0;
//            if (zzTrainsNew.getPersonName() == null || zzTrainsNew.getPersonName().equals("")) {
//                nullfen++;
//                nulls = 1;
//            }


//            if (nulls == 1) {
//                nullfenshuMsg.append("<br/>"
//                        + zzTrainsNew.getPersonName() + " " + zzTrainsNew.getIdcard()
//                        + "存在必填字段没有数据");
//            }


            //判断导入的数据中是否有重复的
            boolean boos = true;
            for (ZzTrains zzStudentinfo : ZzTrainsCFuList) {
                if (zzStudentinfo.getJobid().equals(zzTrainsNew.getJobid()) == true) {
                    examsidMsg.append("<br/>重复数据："
                            + zzTrainsNew.getTname() + " " + zzTrainsNew.getJobid() + "工号重复");
                    boos = false;
                }
            }
            if (boos == true) {
                ZzTrainsCFuList.add(zzTrainsNew);
            }


            boolean i = false;//用于判断是否已经放入存在的集合
            for (ZzTrains ZzTrainsold : ZzTrainssOld) {
                //判断是否已经有存在的数据
                if (zzTrainsNew.getJobid().equals(ZzTrainsold.getJobid())) {
                    zzTrainsNew.setId(ZzTrainsold.getId());
                    zzTrainsNew.setGxtime(new Date());
                    ZzTrainsupdate.add(zzTrainsNew);
                    i = true;
                }
            }
            if (i == false) {
                zzTrainsNew.setDrtime(new Date());
                ZzTrainsinsert.add(zzTrainsNew);
            }
        }


        if (nullfen > 0) {
            nullfenshuMsg.insert(0, "共有 " + nullfen + " 条数据存在空缺，对应信息如下");
            throw new ServiceException(nullfenshuMsg.toString());
        }

        if (ZzTrainsCFuList.size() != zzTrainsList.size()) {
            examsidMsg.insert(0, "很抱歉，导入失败！共 " + (zzTrainsList.size() - ZzTrainsCFuList.size()) +
                    " 条数据存在必填字段重复问题，对应信息如下：\n");
            throw new ServiceException(examsidMsg.toString());
        }


        if (updateSupport == 0) {
            List<ZzTrains> ZzTrainss = zzTrainsMapper.selectZzTrainsList(new ZzTrains());
            String idstr = "";
            for (ZzTrains zzStudentinfo : ZzTrainss) {
                if (idstr == "") {
                    idstr = zzStudentinfo.getId() + "";
                } else {
                    idstr = idstr + "," + zzStudentinfo.getId();
                }
            }
            String[] ids = idstr.split(",");
            zzTrainsMapper.deleteZzTrainsByIds(ids);
        }

        //分别对新增数据和更新数据进行操作
        if (ZzTrainsinsert.size() > 0) {

            zzTrainsMapper.insertZzTrainsList(ZzTrainsinsert);
            successNum = successNum + ZzTrainsinsert.size();
            for (ZzTrains mes : ZzTrainsinsert) {
                successMsg.append("<br/>" + mes.getJobid() + " " + mes.getTname() + " 导入成功");
            }
        }

        if (ZzTrainsupdate.size() > 0) {
            zzTrainsMapper.updateZzTrainsList(ZzTrainsupdate);
            successNum = successNum + ZzTrainsupdate.size();
            for (ZzTrains mes : ZzTrainsupdate) {
                successMsg.append("<br/>" + mes.getJobid() + " " + mes.getTname() + " 更新成功");
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "数据已全部导入成功！共 " + successNum + " 条");
        }
        return successMsg.toString();
    }


    /**
     * 新增培训记录表
     * 
     * @param zzTrains 培训记录表
     * @return 结果
     */
    @Override
    public int insertZzTrains(ZzTrains zzTrains)
    {
        return zzTrainsMapper.insertZzTrains(zzTrains);
    }

    /**
     * 修改培训记录表
     * 
     * @param zzTrains 培训记录表
     * @return 结果
     */
    @Override
    public int updateZzTrains(ZzTrains zzTrains)
    {
        return zzTrainsMapper.updateZzTrains(zzTrains);
    }

    /**
     * 批量删除培训记录表
     * 
     * @param ids 需要删除的培训记录表主键
     * @return 结果
     */
    @Override
    public int deleteZzTrainsByIds(String ids)
    {
        return zzTrainsMapper.deleteZzTrainsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除培训记录表信息
     * 
     * @param id 培训记录表主键
     * @return 结果
     */
    @Override
    public int deleteZzTrainsById(Long id)
    {
        return zzTrainsMapper.deleteZzTrainsById(id);
    }
}
