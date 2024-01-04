package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 培训记录表对象 zz_trains
 * 
 * @author ljg
 * @date 2023-12-05
 */
public class ZzTrains extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 工号 */
    @Excel(name = "工号")
    private String jobid;

    /** 姓名 */
    @Excel(name = "姓名")
    private String tname;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 出生年月 */
    @Excel(name = "出生年月")
    private String birth;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 专业部 */
    @Excel(name = "专业部")
    private String majorbu;

    /** 培训标题 */
    @Excel(name = "培训标题")
    private String trainTitle;

    /** 培训内容 */
    @Excel(name = "培训内容")
    private String trainContent;

    /** 培训时间 */
    @Excel(name = "培训时间")
    private String trainTime;

    /** 培训地点 */
    @Excel(name = "培训地点")
    private String trainLocation;

    /** 培训级别 */
    @Excel(name = "培训级别")
    private String trainLevel;

    /** 完成状态 */
    @Excel(name = "完成状态")
    private String completion;

    /** 导入时间 */
//    @Excel(name = "导入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date drtime;

    /** 更新时间 */
//    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gxtime;

    /** 导入人 */
//    @Excel(name = "导入人")
    private String peoples;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setJobid(String jobid) 
    {
        this.jobid = jobid;
    }

    public String getJobid() 
    {
        return jobid;
    }
    public void setTname(String tname) 
    {
        this.tname = tname;
    }

    public String getTname() 
    {
        return tname;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }
    public void setBirth(String birth) 
    {
        this.birth = birth;
    }

    public String getBirth() 
    {
        return birth;
    }
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }
    public void setMajorbu(String majorbu) 
    {
        this.majorbu = majorbu;
    }

    public String getMajorbu() 
    {
        return majorbu;
    }
    public void setTrainTitle(String trainTitle) 
    {
        this.trainTitle = trainTitle;
    }

    public String getTrainTitle() 
    {
        return trainTitle;
    }
    public void setTrainContent(String trainContent) 
    {
        this.trainContent = trainContent;
    }

    public String getTrainContent() 
    {
        return trainContent;
    }
    public void setTrainTime(String trainTime) 
    {
        this.trainTime = trainTime;
    }

    public String getTrainTime() 
    {
        return trainTime;
    }
    public void setTrainLocation(String trainLocation) 
    {
        this.trainLocation = trainLocation;
    }

    public String getTrainLocation() 
    {
        return trainLocation;
    }
    public void setTrainLevel(String trainLevel) 
    {
        this.trainLevel = trainLevel;
    }

    public String getTrainLevel() 
    {
        return trainLevel;
    }
    public void setCompletion(String completion) 
    {
        this.completion = completion;
    }

    public String getCompletion() 
    {
        return completion;
    }
    public void setDrtime(Date drtime) 
    {
        this.drtime = drtime;
    }

    public Date getDrtime() 
    {
        return drtime;
    }
    public void setGxtime(Date gxtime) 
    {
        this.gxtime = gxtime;
    }

    public Date getGxtime() 
    {
        return gxtime;
    }
    public void setPeoples(String peoples) 
    {
        this.peoples = peoples;
    }

    public String getPeoples() 
    {
        return peoples;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jobid", getJobid())
            .append("tname", getTname())
            .append("gender", getGender())
            .append("age", getAge())
            .append("birth", getBirth())
            .append("major", getMajor())
            .append("majorbu", getMajorbu())
            .append("trainTitle", getTrainTitle())
            .append("trainContent", getTrainContent())
            .append("trainTime", getTrainTime())
            .append("trainLocation", getTrainLocation())
            .append("trainLevel", getTrainLevel())
            .append("completion", getCompletion())
            .append("drtime", getDrtime())
            .append("gxtime", getGxtime())
            .append("peoples", getPeoples())
            .toString();
    }
}
