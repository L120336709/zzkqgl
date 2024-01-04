package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班级信息表对象 zz_classinfo
 * 
 * @author ljg
 * @date 2023-08-03
 */
public class ZzClassinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 班级id */
//    @Excel(name = "班级id")
    private String classId;

    /** 班级代码 ，如1班 对应01 */
//    @Excel(name = "班级代码 ，如1班 对应01")
    private String classCode;

    /** 班级名称，内置班级名称 */
//    @Excel(name = "班级名称，内置班级名称")
    private String className;

    /** 班级别名，如火箭班 */
    @Excel(name = "班级名称")
    private String classNickname;

    /** 年级（比如2018学年） */
//    @Excel(name = "年级", readConverterExp = "比=如2018学年")
    private String classLevel;

    /** 所属年级Id */
//    @Excel(name = "所属年级Id")
    private String gradeId;

    /** 所属年级代码 */
//    @Excel(name = "所属年级代码")
    private String gradeCode;

    /** 所属年级名称 */
    @Excel(name = "年级")
    private String gradeName;

    /** 学校ID */
//    @Excel(name = "学校ID")
    private String schoolId;

    /** 状态 1=可用 0不可用 */
//    @Excel(name = "状态 1=可用 0不可用")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClassId(String classId) 
    {
        this.classId = classId;
    }

    public String getClassId() 
    {
        return classId;
    }
    public void setClassCode(String classCode) 
    {
        this.classCode = classCode;
    }

    public String getClassCode() 
    {
        return classCode;
    }
    public void setClassName(String className) 
    {
        this.className = className;
    }

    public String getClassName() 
    {
        return className;
    }
    public void setClassNickname(String classNickname) 
    {
        this.classNickname = classNickname;
    }

    public String getClassNickname() 
    {
        return classNickname;
    }
    public void setClassLevel(String classLevel) 
    {
        this.classLevel = classLevel;
    }

    public String getClassLevel() 
    {
        return classLevel;
    }
    public void setGradeId(String gradeId) 
    {
        this.gradeId = gradeId;
    }

    public String getGradeId() 
    {
        return gradeId;
    }
    public void setGradeCode(String gradeCode) 
    {
        this.gradeCode = gradeCode;
    }

    public String getGradeCode() 
    {
        return gradeCode;
    }
    public void setGradeName(String gradeName) 
    {
        this.gradeName = gradeName;
    }

    public String getGradeName() 
    {
        return gradeName;
    }
    public void setSchoolId(String schoolId) 
    {
        this.schoolId = schoolId;
    }

    public String getSchoolId() 
    {
        return schoolId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("classId", getClassId())
            .append("classCode", getClassCode())
            .append("className", getClassName())
            .append("classNickname", getClassNickname())
            .append("classLevel", getClassLevel())
            .append("gradeId", getGradeId())
            .append("gradeCode", getGradeCode())
            .append("gradeName", getGradeName())
            .append("schoolId", getSchoolId())
            .append("status", getStatus())
            .toString();
    }
}
