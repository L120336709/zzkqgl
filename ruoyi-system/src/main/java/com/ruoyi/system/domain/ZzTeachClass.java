package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师绑定班级对象 zz_teach_class
 * 
 * @author ljg
 * @date 2023-08-03
 */
public class ZzTeachClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 教师id */
    @Excel(name = "教师id")
    private String teacherId;

    /** 教师id */
    @Excel(name = "教师姓名")
    private String teacherName;

    /** 班级id */
    @Excel(name = "班级id")
    private String classId;

    /** 班级id */
    @Excel(name = "班级name")
    private String className;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createPeople;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTeacherId(String teacherId) 
    {
        this.teacherId = teacherId;
    }

    public String getTeacherId() 
    {
        return teacherId;
    }
    public void setClassId(String classId) 
    {
        this.classId = classId;
    }

    public String getClassId() 
    {
        return classId;
    }
    public void setCreatePeople(String createPeople) 
    {
        this.createPeople = createPeople;
    }

    public String getCreatePeople() 
    {
        return createPeople;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "ZzTeachClass{" +
                "id=" + id +
                ", teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", createPeople='" + createPeople + '\'' +
                '}';
    }
}
