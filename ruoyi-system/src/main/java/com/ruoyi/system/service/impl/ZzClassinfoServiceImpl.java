package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ZzGradeinfo;
import com.ruoyi.system.mapper.ZzGradeinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZzClassinfoMapper;
import com.ruoyi.system.domain.ZzClassinfo;
import com.ruoyi.system.service.IZzClassinfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 班级信息表Service业务层处理
 *
 * @author ljg
 * @date 2023-08-03
 */
@Service
public class ZzClassinfoServiceImpl implements IZzClassinfoService {
    @Autowired
    private ZzClassinfoMapper zzClassinfoMapper;
    @Autowired
    private ZzGradeinfoMapper zzGradeinfoMapper;

    /**
     * 查询班级信息表
     *
     * @param id 班级信息表主键
     * @return 班级信息表
     */
    @Override
    public ZzClassinfo selectZzClassinfoById(Long id) {
        return zzClassinfoMapper.selectZzClassinfoById(id);
    }

    /**
     * 查询班级信息表列表
     *
     * @param zzClassinfo 班级信息表
     * @return 班级信息表
     */
    @Override
    public List<ZzClassinfo> selectZzClassinfoList(ZzClassinfo zzClassinfo) {
        return zzClassinfoMapper.selectZzClassinfoList(zzClassinfo);
    }

    @Override
    public String ExamZzClassinfoListImport(List<ZzClassinfo> zzClassinfoList, Integer updateSupport) {
        if (StringUtils.isNull(zzClassinfoList) || zzClassinfoList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }

        StringBuilder nullfenshuMsg = new StringBuilder();
        int nullfen = 0;
        //记录本次导入数据的所有数据，筛选是否有重复的
        StringBuilder examsidMsg = new StringBuilder();
        List<ZzClassinfo> ZzClassinfoCFuList = new ArrayList<>();

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<ZzClassinfo> ZzClassinfoinsert = new ArrayList<>();
        List<ZzClassinfo> ZzClassinfoupdate = new ArrayList<>();

        List<String> classNickname = new ArrayList<>();
        for (ZzClassinfo zzClassinfo : zzClassinfoList) {
            classNickname.add(zzClassinfo.getClassNickname());
        }

        //先查当前考试的所有缺考学生数据
        List<ZzClassinfo> zzClassinfosOld = new ArrayList<>();

        //删除旧数据，包括临时数据,删除了旧数据就不用查已存的成绩了
        if (updateSupport == 1) {
            zzClassinfosOld = zzClassinfoMapper.findByIdnumberList(classNickname);
        }


        //获取班级，判断名称是否对应
        List<ZzGradeinfo> zzGradeinfoList = zzGradeinfoMapper.selectZzGradeinfoList(new ZzGradeinfo());

        int gades = 0;
        StringBuilder GradeNames = new StringBuilder();


        //把存在的和不存在的数据分别保存，进行插入和更新
        for (ZzClassinfo zzClassinfoNew : zzClassinfoList) {

            //判断是否有空值的必填数据
            int nulls = 0;
            if (zzClassinfoNew.getClassNickname() == null || zzClassinfoNew.getClassNickname().equals("")) {
                nullfen++;
                nulls = 1;
            }
            if (zzClassinfoNew.getGradeName() == null || zzClassinfoNew.getGradeName().equals("")) {
                nullfen++;
                nulls = 1;
            }

            if (nulls == 1) {
                nullfenshuMsg.append("<br/>"
                        + zzClassinfoNew.getClassNickname() + " " + zzClassinfoNew.getGradeName()
                        + "存在必填字段没有数据");
            }
            //判断年级、班级是否存在
            boolean boo = false;
            for (ZzGradeinfo zzGradeinfo : zzGradeinfoList) {
                if (zzGradeinfo.getGradeName().equals(zzClassinfoNew.getGradeName())) {
                    zzClassinfoNew.setGradeId(zzGradeinfo.getId() + "");
                    boo = true;
                }
            }

            if (boo == false) {
                gades++;
                GradeNames.append("<br/>"
                        + " " + zzClassinfoNew.getClassNickname()
                        + " " + zzClassinfoNew.getGradeName()
                        + "对应的年级不存在");
            }

            //判断导入的数据中是否有重复的
            boolean boos = true;
            for (ZzClassinfo zzClassinfo : ZzClassinfoCFuList) {
                if (zzClassinfo.getClassNickname().equals(zzClassinfoNew.getClassNickname()) == true) {
                    examsidMsg.append("<br/>重复数据："
                            + zzClassinfoNew.getGradeName() + " " + zzClassinfoNew.getClassNickname() + " 班级名称存在重复");
                    boos = false;
                }
            }
            if (boos == true) {
                ZzClassinfoCFuList.add(zzClassinfoNew);
            }


            boolean i = false;//用于判断是否已经放入存在的集合
            for (ZzClassinfo ZzStudentinfoold : zzClassinfosOld) {
                //判断是否已经有存在的数据
                if (zzClassinfoNew.getClassNickname().equals(ZzStudentinfoold.getClassNickname())) {
                    ZzStudentinfoold.setGradeName(zzClassinfoNew.getGradeName());

                    ZzClassinfoupdate.add(ZzStudentinfoold);
                    i = true;
                }
            }
            if (i == false) {
                ZzClassinfoinsert.add(zzClassinfoNew);
            }
        }

        if (gades > 0) {
            GradeNames.insert(0, "共有 " + gades + " 条数据对应的年级不存在，对应信息如下");
            throw new ServiceException(GradeNames.toString());
        }

        if (nullfen > 0) {
            nullfenshuMsg.insert(0, "共有 " + nullfen + " 条数据存在空缺，对应信息如下");
            throw new ServiceException(nullfenshuMsg.toString());
        }

        if (ZzClassinfoCFuList.size() != zzClassinfoList.size()) {
            examsidMsg.insert(0, "很抱歉，导入失败！共 " + (zzClassinfoList.size() - ZzClassinfoCFuList.size()) +
                    " 条数据存在必填字段重复问题，对应信息如下：\n");
            throw new ServiceException(examsidMsg.toString());
        }


        if (updateSupport == 0) {
            List<ZzClassinfo> zzClassinfos = zzClassinfoMapper.selectZzClassinfoList(new ZzClassinfo());
            String idstr = "";
            for (ZzClassinfo zzClassinfo : zzClassinfos) {
                if (idstr == "") {
                    idstr = zzClassinfo.getId() + "";
                } else {
                    idstr = idstr + "," + zzClassinfo.getId();
                }
            }
            String[] ids = idstr.split(",");
            zzClassinfoMapper.deleteZzClassinfoByIds(ids);
        }

        //分别对新增数据和更新数据进行操作
        if (ZzClassinfoinsert.size() > 0) {

            List<ZzClassinfo> zzStudentinfoListOlds = zzClassinfoMapper.selectZzClassinfoList(new ZzClassinfo());
            Integer classid = 90000001;
            for (ZzClassinfo zzStudentinfoOld : zzStudentinfoListOlds) {
                if (classid < Integer.parseInt(zzStudentinfoOld.getClassId())) {
                    classid = Integer.parseInt(zzStudentinfoOld.getClassId());
                }
            }
            for (ZzClassinfo zzClassinfo : ZzClassinfoinsert) {
                classid = classid + 1;
                zzClassinfo.setClassId(classid + "");

                zzClassinfo.setClassName(zzClassinfo.getClassNickname());
                zzClassinfo.setSchoolId("10010308");
                zzClassinfo.setStatus("1");

            }


            zzClassinfoMapper.insertZzClassinfoList(ZzClassinfoinsert);
            successNum = successNum + ZzClassinfoinsert.size();
            for (ZzClassinfo mes : ZzClassinfoinsert) {
                successMsg.append("<br/>" + mes.getClassNickname() + " " + mes.getGradeName() + " 导入成功");
            }
        }

        if (ZzClassinfoupdate.size() > 0) {
            zzClassinfoMapper.updateZzClassinfoList(ZzClassinfoupdate);
            successNum = successNum + ZzClassinfoupdate.size();
            for (ZzClassinfo mes : ZzClassinfoupdate) {
                successMsg.append("<br/>" + mes.getClassNickname() + " " + mes.getGradeName() + " 更新成功");
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
     * 新增班级信息表
     *
     * @param zzClassinfo 班级信息表
     * @return 结果
     */
    @Override
    public int insertZzClassinfo(ZzClassinfo zzClassinfo) {
        return zzClassinfoMapper.insertZzClassinfo(zzClassinfo);
    }

    /**
     * 修改班级信息表
     *
     * @param zzClassinfo 班级信息表
     * @return 结果
     */
    @Override
    public int updateZzClassinfo(ZzClassinfo zzClassinfo) {
        return zzClassinfoMapper.updateZzClassinfo(zzClassinfo);
    }

    /**
     * 批量删除班级信息表
     *
     * @param ids 需要删除的班级信息表主键
     * @return 结果
     */
    @Override
    public int deleteZzClassinfoByIds(String ids) {
        return zzClassinfoMapper.deleteZzClassinfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除班级信息表信息
     *
     * @param id 班级信息表主键
     * @return 结果
     */
    @Override
    public int deleteZzClassinfoById(Long id) {
        return zzClassinfoMapper.deleteZzClassinfoById(id);
    }


    @Override
    public List<ZzClassinfo> selectListByClassIds(List<String> gradeId) {
        return zzClassinfoMapper.selectListByClassIds(gradeId);
    }


    @Override
    public int insertClassinfoList(List<ZzClassinfo> zzClassinfo) {
        return zzClassinfoMapper.insertClassinfoList(zzClassinfo);
    }

    @Override
    public int updateClassinfoList(List<ZzClassinfo> zzClassinfo) {
        return zzClassinfoMapper.updateClassinfoList(zzClassinfo);
    }

}
