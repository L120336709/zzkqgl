package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生请假表对象 zz_vacation
 * 
 * @author ljg
 * @date 2023-08-03
 */
public class ZzVacation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 学生id */
    @Excel(name = "学生id")
    private String studentId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    private String className;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    private String startTimeData;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String endTimeData;

    private String ljTime;
    private Long ljTimeInt;

    /** 出校时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @Excel(name = "出校时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date chuTime;

    /** 返校时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "返校时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date huiTime;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date sqTime;

    private String sqTimeData;

    /** 批准时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "批准时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date pzTime;

    private String pzTimeData;

    /** 销假时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "销假时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date xjTime;

    private String xjTimeData;

    /** 批准老师id */
    @Excel(name = "批准老师id")
    private String pzTeacherId;

    /** 批准老师姓名 */
    @Excel(name = "批准老师姓名")
    private String pzTeacherName;

    /** 申请状态（审批中0、已批准1、已驳回-1） */
    @Excel(name = "申请状态", readConverterExp = "审=批中0、已批准1、已驳回-1")
    private String sqStatus;

    /** 请假状态（0未到时间、1请假时间内、-1已超时、2已销假） */
    @Excel(name = "请假状态", readConverterExp = "0=未到时间、1请假时间内、-1已超时、2已销假")
    private String qjStatus;

    /** 数据状态（0已删除、1正常） */
    @Excel(name = "数据状态", readConverterExp = "0=已删除、1正常")
    private String status;

    private String qjReason;
    private String qjType;
    private String sqPeople;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStudentId(String studentId) 
    {
        this.studentId = studentId;
    }

    public String getStudentId() 
    {
        return studentId;
    }
    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setChuTime(Date chuTime) 
    {
        this.chuTime = chuTime;
    }

    public Date getChuTime() 
    {
        return chuTime;
    }
    public void setHuiTime(Date huiTime) 
    {
        this.huiTime = huiTime;
    }

    public Date getHuiTime() 
    {
        return huiTime;
    }
    public void setSqTime(Date sqTime) 
    {
        this.sqTime = sqTime;
    }

    public Date getSqTime() 
    {
        return sqTime;
    }
    public void setPzTime(Date pzTime) 
    {
        this.pzTime = pzTime;
    }

    public Date getPzTime() 
    {
        return pzTime;
    }
    public void setXjTime(Date xjTime) 
    {
        this.xjTime = xjTime;
    }

    public Date getXjTime() 
    {
        return xjTime;
    }
    public void setPzTeacherId(String pzTeacherId) 
    {
        this.pzTeacherId = pzTeacherId;
    }

    public String getPzTeacherId() 
    {
        return pzTeacherId;
    }
    public void setPzTeacherName(String pzTeacherName) 
    {
        this.pzTeacherName = pzTeacherName;
    }

    public String getPzTeacherName() 
    {
        return pzTeacherName;
    }
    public void setSqStatus(String sqStatus) 
    {
        this.sqStatus = sqStatus;
    }

    public String getSqStatus() 
    {
        return sqStatus;
    }
    public void setQjStatus(String qjStatus) 
    {
        this.qjStatus = qjStatus;
    }

    public String getQjStatus() 
    {
        return qjStatus;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public String getStartTimeData() {
        return startTimeData;
    }

    public void setStartTimeData(String startTimeData) {
        this.startTimeData = startTimeData;
    }

    public String getEndTimeData() {
        return endTimeData;
    }

    public void setEndTimeData(String endTimeData) {
        this.endTimeData = endTimeData;
    }

    public String getSqTimeData() {
        return sqTimeData;
    }

    public void setSqTimeData(String sqTimeData) {
        this.sqTimeData = sqTimeData;
    }

    public String getPzTimeData() {
        return pzTimeData;
    }

    public void setPzTimeData(String pzTimeData) {
        this.pzTimeData = pzTimeData;
    }

    public String getXjTimeData() {
        return xjTimeData;
    }

    public void setXjTimeData(String xjTimeData) {
        this.xjTimeData = xjTimeData;
    }

    public String getLjTime() {
        return ljTime;
    }

    public void setLjTime(String ljTime) {
        this.ljTime = ljTime;
    }

    public Long getLjTimeInt() {
        return ljTimeInt;
    }

    public void setLjTimeInt(Long ljTimeInt) {
        this.ljTimeInt = ljTimeInt;
    }

    public String getQjReason() {
        return qjReason;
    }

    public void setQjReason(String qjReason) {
        this.qjReason = qjReason;
    }

    public String getQjType() {
        return qjType;
    }

    public void setQjType(String qjType) {
        this.qjType = qjType;
    }

    public String getSqPeople() {
        return sqPeople;
    }

    public void setSqPeople(String sqPeople) {
        this.sqPeople = sqPeople;
    }

    @Override
    public String toString() {
        return "ZzVacation{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", startTime=" + startTime +
                ", startTimeData='" + startTimeData + '\'' +
                ", endTime=" + endTime +
                ", endTimeData='" + endTimeData + '\'' +
                ", ljTime='" + ljTime + '\'' +
                ", ljTimeInt=" + ljTimeInt +
                ", chuTime=" + chuTime +
                ", huiTime=" + huiTime +
                ", sqTime=" + sqTime +
                ", sqTimeData='" + sqTimeData + '\'' +
                ", pzTime=" + pzTime +
                ", pzTimeData='" + pzTimeData + '\'' +
                ", xjTime=" + xjTime +
                ", xjTimeData='" + xjTimeData + '\'' +
                ", pzTeacherId='" + pzTeacherId + '\'' +
                ", pzTeacherName='" + pzTeacherName + '\'' +
                ", sqStatus='" + sqStatus + '\'' +
                ", qjStatus='" + qjStatus + '\'' +
                ", status='" + status + '\'' +
                ", qjReason='" + qjReason + '\'' +
                ", qjType='" + qjType + '\'' +
                ", sqPeople='" + sqPeople + '\'' +
                '}';
    }


}
