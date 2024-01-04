package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ZzClassinfo;
import com.ruoyi.system.mapper.ZzClassinfoMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZzStudentinfoMapper;
import com.ruoyi.system.domain.ZzStudentinfo;
import com.ruoyi.system.service.IZzStudentinfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 学生信息表Service业务层处理
 *
 * @author ljg
 * @date 2023-08-03
 */
@Service
public class ZzStudentinfoServiceImpl implements IZzStudentinfoService {
    @Autowired
    private ZzStudentinfoMapper zzStudentinfoMapper;

    @Autowired
    private ZzClassinfoMapper zzClassinfoMapper;

    /**
     * 查询学生信息表
     *
     * @param id 学生信息表主键
     * @return 学生信息表
     */
    @Override
    public ZzStudentinfo selectZzStudentinfoById(Long id) {
        return zzStudentinfoMapper.selectZzStudentinfoById(id);
    }

    /**
     * 查询学生信息表列表
     *
     * @param zzStudentinfo 学生信息表
     * @return 学生信息表
     */
    @Override
    public List<ZzStudentinfo> selectZzStudentinfoList(ZzStudentinfo zzStudentinfo) {
        return zzStudentinfoMapper.selectZzStudentinfoList(zzStudentinfo);
    }


    @Override
    public String ExamZzStudentinfoListImport(List<ZzStudentinfo> zzStudentinfoList, Integer updateSupport) {
        if (StringUtils.isNull(zzStudentinfoList) || zzStudentinfoList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }

        StringBuilder nullfenshuMsg = new StringBuilder();
        int nullfen = 0;
        //记录本次导入数据的所有数据，筛选是否有重复的
        StringBuilder examsidMsg = new StringBuilder();
        List<ZzStudentinfo> ZzStudentinfoCFuList = new ArrayList<>();

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<ZzStudentinfo> ZzStudentinfoinsert = new ArrayList<>();
        List<ZzStudentinfo> ZzStudentinfoupdate = new ArrayList<>();

        List<String> Idnumber = new ArrayList<>();
        for (ZzStudentinfo zzStudentinfo : zzStudentinfoList) {
            Idnumber.add(zzStudentinfo.getIdcard());
        }

        //先查当前考试的所有缺考学生数据
        List<ZzStudentinfo> ZzStudentinfosOld = new ArrayList<>();

        //删除旧数据，包括临时数据,删除了旧数据就不用查已存的成绩了
        if (updateSupport == 1) {
            ZzStudentinfosOld = zzStudentinfoMapper.findZzStudentinfoByIdnumberList(Idnumber);
        }


        //获取班级，判断名称是否对应
        List<ZzClassinfo> zzClassinfoList = zzClassinfoMapper.selectZzClassinfoList(new ZzClassinfo());

        int classnames = 0;
        StringBuilder ClassNameMsg = new StringBuilder();


        //把存在的和不存在的数据分别保存，进行插入和更新
        for (ZzStudentinfo zzStudentinfoNew : zzStudentinfoList) {

            //判断是否有空值的必填数据
            int nulls = 0;
            if (zzStudentinfoNew.getPersonName() == null || zzStudentinfoNew.getPersonName().equals("")) {
                nullfen++;
                nulls = 1;
            }
            if (zzStudentinfoNew.getIdcard() == null || zzStudentinfoNew.getIdcard().equals("")) {
                nullfen++;
                nulls = 1;
            }
            if (zzStudentinfoNew.getClassName() == null || zzStudentinfoNew.getClassName().equals("")) {
                nullfen++;
                nulls = 1;
            }
            if (zzStudentinfoNew.getGradeName() == null || zzStudentinfoNew.getGradeName().equals("")) {
                nullfen++;
                nulls = 1;
            }

            if (nulls == 1) {
                nullfenshuMsg.append("<br/>"
                        + zzStudentinfoNew.getPersonName() + " " + zzStudentinfoNew.getIdcard()
                        + "存在必填字段没有数据");
            }
            //判断年级、班级是否存在
            boolean boo = false;
            for (ZzClassinfo zzClassinfo : zzClassinfoList) {
                if (zzClassinfo.getClassNickname().equals(zzStudentinfoNew.getClassName())) {
                    if (zzClassinfo.getGradeName().equals(zzStudentinfoNew.getGradeName())) {
                        zzStudentinfoNew.setClassId(zzClassinfo.getClassId());
                        boo = true;
                    }
                }
            }

            if (boo == false) {
                classnames++;
                ClassNameMsg.append("<br/>"
                        + zzStudentinfoNew.getPersonName() + " " + zzStudentinfoNew.getIdcard()
                        + " " + zzStudentinfoNew.getGradeName()
                        + " " + zzStudentinfoNew.getClassName()
                        + "对应的年级或者班级不存在");
            }

            //判断导入的数据中是否有重复的
            boolean boos = true;
            for (ZzStudentinfo zzStudentinfo : ZzStudentinfoCFuList) {
                if (zzStudentinfo.getIdcard().equals(zzStudentinfoNew.getIdcard()) == true) {
                    examsidMsg.append("<br/>重复数据："
                            + zzStudentinfoNew.getPersonName() + " " + zzStudentinfoNew.getIdcard() + "身份证号重复");
                    boos = false;
                }
            }
            if (boos == true) {
                ZzStudentinfoCFuList.add(zzStudentinfoNew);
            }


            boolean i = false;//用于判断是否已经放入存在的集合
            for (ZzStudentinfo ZzStudentinfoold : ZzStudentinfosOld) {
                //判断是否已经有存在的数据
                if (zzStudentinfoNew.getIdcard().equals(ZzStudentinfoold.getIdcard())) {
                    ZzStudentinfoold.setPersonName(zzStudentinfoNew.getPersonName());
                    if (zzStudentinfoNew.getGender() != null && zzStudentinfoNew.getGender() != "") {
                        if (zzStudentinfoNew.getGender().equals("男")) {
                            ZzStudentinfoold.setGender("1");
                        } else if (zzStudentinfoNew.getGender().equals("女")) {
                            ZzStudentinfoold.setGender("2");
                        }
                    }
                    ZzStudentinfoupdate.add(ZzStudentinfoold);
                    i = true;
                }
            }
            if (i == false) {
                ZzStudentinfoinsert.add(zzStudentinfoNew);
            }
        }

        if (classnames > 0) {
            ClassNameMsg.insert(0, "共有 " + classnames + " 条数据对应的年级或者班级不存在，对应信息如下");
            throw new ServiceException(ClassNameMsg.toString());
        }
        if (nullfen > 0) {
            nullfenshuMsg.insert(0, "共有 " + nullfen + " 条数据存在空缺，对应信息如下");
            throw new ServiceException(nullfenshuMsg.toString());
        }

        if (ZzStudentinfoCFuList.size() != zzStudentinfoList.size()) {
            examsidMsg.insert(0, "很抱歉，导入失败！共 " + (zzStudentinfoList.size() - ZzStudentinfoCFuList.size()) +
                    " 条数据存在必填字段重复问题，对应信息如下：\n");
            throw new ServiceException(examsidMsg.toString());
        }


        if (updateSupport == 0) {
            List<ZzStudentinfo> ZzStudentinfos = zzStudentinfoMapper.selectZzStudentinfoList(new ZzStudentinfo());
            String idstr = "";
            for (ZzStudentinfo zzStudentinfo : ZzStudentinfos) {
                if (idstr == "") {
                    idstr = zzStudentinfo.getId() + "";
                } else {
                    idstr = idstr + "," + zzStudentinfo.getId();
                }
            }
            String[] ids = idstr.split(",");
            zzStudentinfoMapper.deleteZzStudentinfoByIds(ids);
        }

        //分别对新增数据和更新数据进行操作
        if (ZzStudentinfoinsert.size() > 0) {

            List<ZzStudentinfo> zzStudentinfoListOlds = zzStudentinfoMapper.selectZzStudentinfoList(new ZzStudentinfo());
            Integer userno = 900001;
            for (ZzStudentinfo zzStudentinfoOld : zzStudentinfoListOlds) {
                if (userno < Integer.parseInt(zzStudentinfoOld.getUserNo())) {
                    userno = Integer.parseInt(zzStudentinfoOld.getUserNo());
                }
            }
            for (ZzStudentinfo zzStudentinfo : ZzStudentinfoinsert) {
                userno = userno + 1;
                zzStudentinfo.setUserNo(userno + "");
                zzStudentinfo.setState("1");
                zzStudentinfo.setOrgId("10010308");
                zzStudentinfo.setOrgName("恩施市中等职业技术学校");
                zzStudentinfo.setIdentityId("102");
                zzStudentinfo.setOrgState("1");
                if (zzStudentinfo.getGender() != null && zzStudentinfo.getGender() != "") {
                    if (zzStudentinfo.getGender().equals("男")) {
                        zzStudentinfo.setGender("1");
                    } else if (zzStudentinfo.getGender().equals("女")) {
                        zzStudentinfo.setGender("2");
                    }
                }
            }


            zzStudentinfoMapper.insertStudentinfoList(ZzStudentinfoinsert);
            successNum = successNum + ZzStudentinfoinsert.size();
            for (ZzStudentinfo mes : ZzStudentinfoinsert) {
                successMsg.append("<br/>" + mes.getPersonName() + " " + mes.getIdcard() + " 导入成功");
            }
        }

        if (ZzStudentinfoupdate.size() > 0) {
            zzStudentinfoMapper.updateStudentinfoList(ZzStudentinfoupdate);
            successNum = successNum + ZzStudentinfoupdate.size();
            for (ZzStudentinfo mes : ZzStudentinfoupdate) {
                successMsg.append("<br/>" + mes.getPersonName() + " " + mes.getIdcard() + " 更新成功");
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


    @Override
    public List<ZzStudentinfo> selectZzStuListByclassId(String classId) {
        ZzStudentinfo zzStudentinfo = new ZzStudentinfo();
        zzStudentinfo.setClassId(classId);
        zzStudentinfo.setOrgState("1");//查询在校
        return zzStudentinfoMapper.selectZzStuListByclassId(zzStudentinfo);
    }

    /**
     * 新增学生信息表
     *
     * @param zzStudentinfo 学生信息表
     * @return 结果
     */
    @Override
    public int insertZzStudentinfo(ZzStudentinfo zzStudentinfo) {
        return zzStudentinfoMapper.insertZzStudentinfo(zzStudentinfo);
    }

    /**
     * 修改学生信息表
     *
     * @param zzStudentinfo 学生信息表
     * @return 结果
     */
    @Override
    public int updateZzStudentinfo(ZzStudentinfo zzStudentinfo) {
        return zzStudentinfoMapper.updateZzStudentinfo(zzStudentinfo);
    }

    /**
     * 批量删除学生信息表
     *
     * @param ids 需要删除的学生信息表主键
     * @return 结果
     */
    @Override
    public int deleteZzStudentinfoByIds(String ids) {
        return zzStudentinfoMapper.deleteZzStudentinfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除学生信息表信息
     *
     * @param id 学生信息表主键
     * @return 结果
     */
    @Override
    public int deleteZzStudentinfoById(Long id) {
        return zzStudentinfoMapper.deleteZzStudentinfoById(id);
    }

    @Override
    public List<ZzStudentinfo> selectListByStudentinfoIds(List<String> userId) {
        return zzStudentinfoMapper.selectListByStudentinfoIds(userId);
    }


    @Override
    public int insertStudentinfoList(List<ZzStudentinfo> zzStudentinfo) {
        return zzStudentinfoMapper.insertStudentinfoList(zzStudentinfo);
    }

    @Override
    public int updateStudentinfoList(List<ZzStudentinfo> zzStudentinfo) {
        return zzStudentinfoMapper.updateStudentinfoList(zzStudentinfo);
    }


}
