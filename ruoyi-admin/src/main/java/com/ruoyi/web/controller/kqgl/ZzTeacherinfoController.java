package com.ruoyi.web.controller.kqgl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ZzTeacherinfo;
import com.ruoyi.system.service.IZzTeacherinfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 教师信息表Controller
 * 
 * @author ljg
 * @date 2023-08-03
 */
@Controller
@RequestMapping("/kqgl/teacherinfo")
public class ZzTeacherinfoController extends BaseController
{
    private String prefix = "kqgl/teacherinfo";

    @Autowired
    private IZzTeacherinfoService zzTeacherinfoService;

    //@RequiresPermissions("kqgl:teacherinfo:view")
    @GetMapping()
    public String teacherinfo()
    {
        return prefix + "/teacherinfo";
    }

    @GetMapping("teacherlist")
    public String teacherlist()
    {
        return prefix + "/teacherlist";
    }

    /**
     * 查询教师信息表列表
     */
    //@RequiresPermissions("kqgl:teacherinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ZzTeacherinfo zzTeacherinfo)
    {
        startPage();
        List<ZzTeacherinfo> list = zzTeacherinfoService.selectZzTeacherinfoList(zzTeacherinfo);
        return getDataTable(list);
    }
    @PostMapping("/getTeacherById")
    @ResponseBody
    public ZzTeacherinfo getTeacherById(Long teacherId)
    {
        ZzTeacherinfo zzTeacherinfo = zzTeacherinfoService.selectZzTeacherinfoById(teacherId);
        return zzTeacherinfo;
    }




    /**
     * 接口获取教职工数据并填充更新到教职工表
     */
    @PostMapping("/getTeacherList")
    @ResponseBody
    public AjaxResult getTeacherList() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int pageNo=0;
        int pageSize=100;
        Integer total = 1;


        List<ZzTeacherinfo> zzTeacherinfoList = new ArrayList<>();
        List<String> userIds = new ArrayList<>();


        while (pageNo*pageSize<total){
            pageNo=pageNo+1;
            JSONObject jsonObject = ZzApiController.getTeacher("10010308",pageNo,pageSize);
            JSONObject jsonObject2 = JSONObject.parseObject(jsonObject.getString("data"));
            List<JSONObject>  timeStrings = (List<JSONObject>) JSON.parse(jsonObject2.getString("records"));
            total = jsonObject2.getInteger("total");
            for (JSONObject jsonObject1 : timeStrings) {
                ZzTeacherinfo zzClassinfo = new ZzTeacherinfo();
                zzClassinfo.setUserId(jsonObject1.getString("userId"));
                zzClassinfo.setUserNo(jsonObject1.getString("userNo"));
                zzClassinfo.setUserName(jsonObject1.getString("userName"));
                zzClassinfo.setMobile(jsonObject1.getString("mobile"));
                zzClassinfo.setMobileIsbind(jsonObject1.getString("mobileIsbind"));
                zzClassinfo.setEmail(jsonObject1.getString("email"));
                zzClassinfo.setEmailIsbind(jsonObject1.getString("emailIsbind"));
                zzClassinfo.setUserHead(jsonObject1.getString("userHead"));
                zzClassinfo.setState(jsonObject1.getString("state"));
                zzClassinfo.setPersonName(jsonObject1.getString("personName"));
                zzClassinfo.setPinyName(jsonObject1.getString("pinyName"));
                zzClassinfo.setGender(jsonObject1.getString("gender"));
                zzClassinfo.setNational(jsonObject1.getString("national"));
                zzClassinfo.setBirthday(jsonObject1.getString("birthday"));
                zzClassinfo.setIdcard(jsonObject1.getString("idcard"));

                List<JSONObject> userOrg = (List<JSONObject>) JSON.parse(jsonObject1.getString("userOrg"));

                if(userOrg.size()>0){
                    zzClassinfo.setOrgId(userOrg.get(0).getString("orgId"));
                    zzClassinfo.setOrgIdentityid(userOrg.get(0).getString("identityId"));
                    zzClassinfo.setOrgName(userOrg.get(0).getString("orgName"));
                    zzClassinfo.setOrgState(userOrg.get(0).getString("state"));
                }

                userIds.add(jsonObject1.getString("userId"));
                zzTeacherinfoList.add(zzClassinfo);
            }
        }




        //批量查询当前年级id对应的数据库数据
        List<ZzTeacherinfo> zzClassinfoListOld = new ArrayList<>();
        if (userIds.size() > 0) {
            zzClassinfoListOld=zzTeacherinfoService.selectListByTeacherIds(userIds);
        }

        List<ZzTeacherinfo> zzClassinfoListInsert = new ArrayList<>();
        List<ZzTeacherinfo> zzClassinfoListUpdate = new ArrayList<>();
        for (ZzTeacherinfo zzClassinfo : zzTeacherinfoList) {
            boolean boo = true;
            boolean boo2=true;
            // 遍历数据判断是否有旧数据，有则更新，没有则插入
            for (ZzTeacherinfo zzClassinfoOld : zzClassinfoListOld) {
                if (zzClassinfo.getUserId().equals(zzClassinfoOld.getUserId())) {
                    boo = false;
                    zzClassinfo.setId(zzClassinfoOld.getId());
                    if(BeanUtils.describe(zzClassinfo).equals(BeanUtils.describe(zzClassinfoOld))){
                        boo2=false;
                    }
                }
            }
            if (boo == true) {
                zzClassinfoListInsert.add(zzClassinfo);
            } else {
                if(boo2==true) {
                    zzClassinfoListUpdate.add(zzClassinfo);
                }
            }
        }

        // 批量插入和批量更新
        if(zzClassinfoListInsert.size()>0){
            zzTeacherinfoService.insertTeacherinfoList(zzClassinfoListInsert);
        }
        if(zzClassinfoListUpdate.size()>0){
            zzTeacherinfoService.updateTeacherinfoList(zzClassinfoListUpdate);
        }

        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("msg", "同步教职工数据成功！");
        return ajaxResult;
    }

    /**
     * 导出教师信息表列表
     */
    //@RequiresPermissions("kqgl:teacherinfo:export")
    @Log(title = "教师信息表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZzTeacherinfo zzTeacherinfo)
    {
        List<ZzTeacherinfo> list = zzTeacherinfoService.selectZzTeacherinfoList(zzTeacherinfo);
        ExcelUtil<ZzTeacherinfo> util = new ExcelUtil<ZzTeacherinfo>(ZzTeacherinfo.class);
        return util.exportExcel(list, "教师信息表数据");
    }

    /**
     * 新增教师信息表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存教师信息表
     */
    // @RequiresPermissions("kqgl:teacherinfo:add")
    @Log(title = "教师信息表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZzTeacherinfo zzTeacherinfo)
    {
        return toAjax(zzTeacherinfoService.insertZzTeacherinfo(zzTeacherinfo));
    }

    /**
     * 修改教师信息表
     */
    //@RequiresPermissions("kqgl:teacherinfo:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ZzTeacherinfo zzTeacherinfo = zzTeacherinfoService.selectZzTeacherinfoById(id);
        mmap.put("zzTeacherinfo", zzTeacherinfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存教师信息表
     */
    //@RequiresPermissions("kqgl:teacherinfo:edit")
    @Log(title = "教师信息表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZzTeacherinfo zzTeacherinfo)
    {
        return toAjax(zzTeacherinfoService.updateZzTeacherinfo(zzTeacherinfo));
    }

    /**
     * 删除教师信息表
     */
    // @RequiresPermissions("kqgl:teacherinfo:remove")
    @Log(title = "教师信息表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zzTeacherinfoService.deleteZzTeacherinfoByIds(ids));
    }
}
