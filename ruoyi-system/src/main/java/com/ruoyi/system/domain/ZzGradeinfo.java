package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 年级表对象 zz_gradeinfo
 * 
 * @author ljg
 * @date 2023-08-03
 */
public class ZzGradeinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 所属年级Id */
    @Excel(name = "所属年级Id")
    private String gradeId;

    /** 所属年级代码 */
    @Excel(name = "所属年级代码")
    private String gradeCode;

    /** 所属年级名称 */
    @Excel(name = "所属年级名称")
    private String gradeName;

    /** 学校ID */
    @Excel(name = "学校ID")
    private String schoolId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gradeId", getGradeId())
            .append("gradeCode", getGradeCode())
            .append("gradeName", getGradeName())
            .append("schoolId", getSchoolId())
            .toString();
    }
}
