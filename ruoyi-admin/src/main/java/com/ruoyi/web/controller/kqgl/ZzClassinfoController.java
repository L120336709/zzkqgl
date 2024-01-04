package com.ruoyi.web.controller.kqgl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.ZzGradeinfo;
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
import com.ruoyi.system.domain.ZzClassinfo;
import com.ruoyi.system.service.IZzClassinfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 班级信息表Controller
 * 
 * @author ljg
 * @date 2023-08-03
 */
@Controller
@RequestMapping("/kqgl/classinfo")
public class ZzClassinfoController extends BaseController
{
    private String prefix = "kqgl/classinfo";

    @Autowired
    private IZzClassinfoService zzClassinfoService;

    //@RequiresPermissions("kqgl:classinfo:view")
    @GetMapping()
    public String classinfo()
    {
        return prefix + "/classinfo";
    }

    /**
     * 查询班级信息表列表
     */
    // @RequiresPermissions("kqgl:classinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ZzClassinfo zzClassinfo)
    {
        startPage();
        zzClassinfo.setStatus("1");
        List<ZzClassinfo> list = zzClassinfoService.selectZzClassinfoList(zzClassinfo);
        return getDataTable(list);
    }

    @PostMapping("/ZzClassinfolist")
    @ResponseBody
    public List<ZzClassinfo>  ZzClassinfolist(ZzClassinfo zzClassinfo) {
        zzClassinfo.setStatus("1");
        List<ZzClassinfo> list = zzClassinfoService.selectZzClassinfoList(zzClassinfo);
        return  list;
    }

    @PostMapping("/getclassById")
    @ResponseBody
    public ZzClassinfo getclassById(Long classId)
    {
        ZzClassinfo zzClassinfo = zzClassinfoService.selectZzClassinfoById(classId);
        return zzClassinfo;
    }

    /**
     * 接口获取年级数据并填充更新到年级表
     */
    @PostMapping("/getClassList")
    @ResponseBody
    public AjaxResult getClassList() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        JSONObject jsonObject = ZzApiController.getClass("10010308");
        JSONObject jsonObject2 = JSONObject.parseObject(jsonObject.getString("data"));
        List<JSONObject> timeStrings = (List<JSONObject>) JSON.parse(jsonObject2.getString("records"));

        List<ZzClassinfo> zzClassinfoList = new ArrayList<>();
        List<String> classids = new ArrayList<>();
        for (JSONObject jsonObject1 : timeStrings) {
            ZzClassinfo zzClassinfo = new ZzClassinfo();
            zzClassinfo.setClassId(jsonObject1.getString("classId"));
            zzClassinfo.setClassCode(jsonObject1.getString("classCode"));
            zzClassinfo.setClassName(jsonObject1.getString("className"));
            zzClassinfo.setClassNickname(jsonObject1.getString("classNickName"));
            zzClassinfo.setClassLevel(jsonObject1.getString("classLevel"));
            zzClassinfo.setGradeId(jsonObject1.getString("gradeId"));
            zzClassinfo.setGradeCode(jsonObject1.getString("gradeCode"));
            zzClassinfo.setGradeName(jsonObject1.getString("gradeName"));
            zzClassinfo.setStatus(jsonObject1.getString("state"));
            zzClassinfo.setSchoolId("10010308");
            classids.add(jsonObject1.getString("classId"));
            zzClassinfoList.add(zzClassinfo);
        }
        //批量查询当前年级id对应的数据库数据
        List<ZzClassinfo> zzClassinfoListOld = new ArrayList<>();

        if (classids.size() > 0) {
            zzClassinfoListOld=zzClassinfoService.selectListByClassIds(classids);
        }

        //查询所有班级数据，状态为1的
        ZzClassinfo zzClassinfo0=new ZzClassinfo();
        zzClassinfo0.setStatus("1");
        List<ZzClassinfo> zzClassinfoListOldAll=zzClassinfoService.selectZzClassinfoList(zzClassinfo0);
        List<ZzClassinfo> zzClassinfoListUpdateTo0 = new ArrayList<>();
        for(ZzClassinfo zzClassinfo:zzClassinfoListOldAll ){
            boolean boo=true;
            for(String classid:classids){
                if(zzClassinfo.getClassId().equals(classid)){
                    boo=false;
                }
            }
            //当前班级不存在于最新班级，把状态置为0
            if(boo==true){
                zzClassinfo.setStatus("0");
                zzClassinfoListUpdateTo0.add(zzClassinfo);
            }
        }


        List<ZzClassinfo> zzClassinfoListInsert = new ArrayList<>();
        List<ZzClassinfo> zzClassinfoListUpdate = new ArrayList<>();
        for (ZzClassinfo zzClassinfo : zzClassinfoList) {
            boolean boo = true;
            boolean boo2=true;
            // 遍历数据判断是否有旧数据，有则更新，没有则插入
            for (ZzClassinfo zzClassinfoOld : zzClassinfoListOld) {
                if (zzClassinfo.getClassId().equals(zzClassinfoOld.getClassId())) {
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
            zzClassinfoService.insertClassinfoList(zzClassinfoListInsert);
        }
        if(zzClassinfoListUpdate.size()>0){
            zzClassinfoService.updateClassinfoList(zzClassinfoListUpdate);
        }
        if(zzClassinfoListUpdateTo0.size()>0){
            zzClassinfoService.updateClassinfoList(zzClassinfoListUpdateTo0);
        }

        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("msg", "同步班级数据成功！");
        return ajaxResult;
    }

    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<ZzClassinfo> util = new ExcelUtil<ZzClassinfo>(ZzClassinfo.class);
        return util.importTemplateExcel("用户数据");
    }


    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, Integer updateSupport) throws Exception
    {
        ExcelUtil<ZzClassinfo> util = new ExcelUtil<ZzClassinfo>(ZzClassinfo.class);
        List<ZzClassinfo> zzClassinfoList = util.importExcel(file.getInputStream());
        String message = zzClassinfoService.ExamZzClassinfoListImport(zzClassinfoList, updateSupport);
        return AjaxResult.success(message);
    }
    /**
     * 导出班级信息表列表
     */
    @RequiresPermissions("kqgl:classinfo:export")
    @Log(title = "班级信息表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZzClassinfo zzClassinfo)
    {
        List<ZzClassinfo> list = zzClassinfoService.selectZzClassinfoList(zzClassinfo);
        ExcelUtil<ZzClassinfo> util = new ExcelUtil<ZzClassinfo>(ZzClassinfo.class);
        return util.exportExcel(list, "班级信息表数据");
    }

    /**
     * 新增班级信息表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存班级信息表
     */
    @RequiresPermissions("kqgl:classinfo:add")
    @Log(title = "班级信息表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZzClassinfo zzClassinfo)
    {
        List<ZzClassinfo> zzClassinfoList=zzClassinfoService.selectZzClassinfoList(new ZzClassinfo());
        Integer classid=90000001;
        for(ZzClassinfo zzClassinfoOld:zzClassinfoList){
            if(classid<Integer.parseInt(zzClassinfoOld.getClassId())){
                classid=Integer.parseInt(zzClassinfoOld.getClassId());
            }
            if(zzClassinfo.getClassNickname().equals(zzClassinfoOld.getClassNickname())){
                return AjaxResult.error("当前班级已存在");
            }
        }
        zzClassinfo.setClassName(zzClassinfo.getClassNickname());
        zzClassinfo.setClassId((classid+1)+"");
        zzClassinfo.setStatus("1");
        return toAjax(zzClassinfoService.insertZzClassinfo(zzClassinfo));
    }

    /**
     * 修改班级信息表
     */
    @RequiresPermissions("kqgl:classinfo:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ZzClassinfo zzClassinfo = zzClassinfoService.selectZzClassinfoById(id);
        mmap.put("zzClassinfo", zzClassinfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存班级信息表
     */
    @RequiresPermissions("kqgl:classinfo:edit")
    @Log(title = "班级信息表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZzClassinfo zzClassinfo)
    {
        return toAjax(zzClassinfoService.updateZzClassinfo(zzClassinfo));
    }

    /**
     * 删除班级信息表
     */
    @RequiresPermissions("kqgl:classinfo:remove")
    @Log(title = "班级信息表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zzClassinfoService.deleteZzClassinfoByIds(ids));
    }
}
