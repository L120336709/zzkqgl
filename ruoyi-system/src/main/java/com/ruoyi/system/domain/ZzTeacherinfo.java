package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师信息表对象 zz_teacherinfo
 * 
 * @author ljg
 * @date 2023-08-04
 */
public class ZzTeacherinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 用户账号 */
    @Excel(name = "用户账号")
    private String userNo;

    /** 用户登录名 */
    @Excel(name = "用户登录名")
    private String userName;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String mobile;

    /** 手机是否绑定 */
    @Excel(name = "手机是否绑定")
    private String mobileIsbind;

    /** 邮箱地址 */
    @Excel(name = "邮箱地址")
    private String email;

    /** 邮箱是否绑定 */
    @Excel(name = "邮箱是否绑定")
    private String emailIsbind;

    /** 用户头像地址 */
    @Excel(name = "用户头像地址")
    private String userHead;

    /** 账号状态 1表示正常 0表示已禁用无法登录 */
    @Excel(name = "账号状态 1表示正常 0表示已禁用无法登录")
    private String state;

    /** 姓名 */
    @Excel(name = "姓名")
    private String personName;

    /** 姓名拼音 */
    @Excel(name = "姓名拼音")
    private String pinyName;

    /** 性别1男2女 */
    @Excel(name = "性别1男2女")
    private String gender;

    /** 民族 */
    @Excel(name = "民族")
    private String national;

    /** 出生日期 */
    @Excel(name = "出生日期")
    private String birthday;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idcard;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String employId;

    /** 所属学校Id */
    @Excel(name = "所属学校Id")
    private String orgId;

    /** 所属学校名称 */
    @Excel(name = "所属学校名称")
    private String orgName;

    /** 用户类型 */
    @Excel(name = "用户类型")
    private String orgIdentityid;

    /** 所在学校状态 
1表示在校
0表示不在校 */
    @Excel(name = "所在学校状态 1表示在校 0表示不在校")
    private String orgState;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setUserNo(String userNo) 
    {
        this.userNo = userNo;
    }

    public String getUserNo() 
    {
        return userNo;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getMobile() 
    {
        return mobile;
    }
    public void setMobileIsbind(String mobileIsbind) 
    {
        this.mobileIsbind = mobileIsbind;
    }

    public String getMobileIsbind() 
    {
        return mobileIsbind;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setEmailIsbind(String emailIsbind) 
    {
        this.emailIsbind = emailIsbind;
    }

    public String getEmailIsbind() 
    {
        return emailIsbind;
    }
    public void setUserHead(String userHead) 
    {
        this.userHead = userHead;
    }

    public String getUserHead() 
    {
        return userHead;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setPersonName(String personName) 
    {
        this.personName = personName;
    }

    public String getPersonName() 
    {
        return personName;
    }
    public void setPinyName(String pinyName) 
    {
        this.pinyName = pinyName;
    }

    public String getPinyName() 
    {
        return pinyName;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setNational(String national) 
    {
        this.national = national;
    }

    public String getNational() 
    {
        return national;
    }
    public void setBirthday(String birthday) 
    {
        this.birthday = birthday;
    }

    public String getBirthday() 
    {
        return birthday;
    }
    public void setIdcard(String idcard) 
    {
        this.idcard = idcard;
    }

    public String getIdcard() 
    {
        return idcard;
    }
    public void setEmployId(String employId) 
    {
        this.employId = employId;
    }

    public String getEmployId() 
    {
        return employId;
    }
    public void setOrgId(String orgId) 
    {
        this.orgId = orgId;
    }

    public String getOrgId() 
    {
        return orgId;
    }
    public void setOrgName(String orgName) 
    {
        this.orgName = orgName;
    }

    public String getOrgName() 
    {
        return orgName;
    }
    public void setOrgIdentityid(String orgIdentityid) 
    {
        this.orgIdentityid = orgIdentityid;
    }

    public String getOrgIdentityid() 
    {
        return orgIdentityid;
    }
    public void setOrgState(String orgState) 
    {
        this.orgState = orgState;
    }

    public String getOrgState() 
    {
        return orgState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userNo", getUserNo())
            .append("userName", getUserName())
            .append("mobile", getMobile())
            .append("mobileIsbind", getMobileIsbind())
            .append("email", getEmail())
            .append("emailIsbind", getEmailIsbind())
            .append("userHead", getUserHead())
            .append("state", getState())
            .append("personName", getPersonName())
            .append("pinyName", getPinyName())
            .append("gender", getGender())
            .append("national", getNational())
            .append("birthday", getBirthday())
            .append("idcard", getIdcard())
            .append("employId", getEmployId())
            .append("orgId", getOrgId())
            .append("orgName", getOrgName())
            .append("orgIdentityid", getOrgIdentityid())
            .append("orgState", getOrgState())
            .toString();
    }
}
