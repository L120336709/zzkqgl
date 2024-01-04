package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息表对象 zz_studentinfo
 * 
 * @author ljg
 * @date 2023-08-04
 */
public class ZzStudentinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**   */
    private Long id;

    /** 用户id */
//    @Excel(name = "用户id")
    private String userId;

    /** 用户账号 */
 //   @Excel(name = "用户账号")
    private String userNo;

    /** 用户登录名 */
  //  @Excel(name = "用户登录名")
    private String userName;

    /** 用户头像地址 */
  //  @Excel(name = "用户头像地址")
    private String userHead;

    /** 姓名 */
    @Excel(name = "姓名*")
    private String personName;

    /** 姓名拼音 */
   // @Excel(name = "姓名拼音")
    private String pinyName;

    /** 性别1男2女 */
    @Excel(name = "性别",readConverterExp="男=1,女=2")
    private String gender;

    /** 学籍号 */
    @Excel(name = "学籍号")
    private String eduNo;

    /** 身份证号 */
    @Excel(name = "身份证号*")
    private String idcard;

    /** 班级id */
    @Excel(name = "年级*")
    private String gradeName;

    /** 班级id */
    @Excel(name = "班级*")
    private String className;

    /** 班级id */
    //@Excel(name = "班级id")
    private String classId;

    /** 账号状态 1表示正常 0表示已禁用无法登录 */
  //  @Excel(name = "账号状态 1表示正常 0表示已禁用无法登录")
    private String state;


    /** 所属学校Id */
   // @Excel(name = "所属学校Id")
    private String orgId;

    /** 所属学校名称 */
  //  @Excel(name = "所属学校名称")
    private String orgName;

    /** 用户类型 */
 //   @Excel(name = "用户类型")
    private String identityId;

    /** 所在学校状态 
1表示在校
0表示不在校 */
 //   @Excel(name = "所在学校状态 1表示在校 0表示不在校")
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
    public void setUserHead(String userHead) 
    {
        this.userHead = userHead;
    }

    public String getUserHead() 
    {
        return userHead;
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
    public void setIdcard(String idcard) 
    {
        this.idcard = idcard;
    }

    public String getIdcard() 
    {
        return idcard;
    }
    public void setClassId(String classId) 
    {
        this.classId = classId;
    }

    public String getClassId() 
    {
        return classId;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setEduNo(String eduNo) 
    {
        this.eduNo = eduNo;
    }

    public String getEduNo() 
    {
        return eduNo;
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
    public void setIdentityId(String identityId) 
    {
        this.identityId = identityId;
    }

    public String getIdentityId() 
    {
        return identityId;
    }
    public void setOrgState(String orgState) 
    {
        this.orgState = orgState;
    }

    public String getOrgState() 
    {
        return orgState;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "ZzStudentinfo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userNo='" + userNo + '\'' +
                ", userName='" + userName + '\'' +
                ", userHead='" + userHead + '\'' +
                ", personName='" + personName + '\'' +
                ", pinyName='" + pinyName + '\'' +
                ", gender='" + gender + '\'' +
                ", eduNo='" + eduNo + '\'' +
                ", idcard='" + idcard + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", className='" + className + '\'' +
                ", classId='" + classId + '\'' +
                ", state='" + state + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", identityId='" + identityId + '\'' +
                ", orgState='" + orgState + '\'' +
                '}';
    }
}
