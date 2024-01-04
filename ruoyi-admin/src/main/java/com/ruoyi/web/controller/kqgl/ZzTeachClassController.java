package com.ruoyi.web.controller.kqgl;

import java.util.List;

import com.ruoyi.system.domain.ZzStudentinfo;
import com.ruoyi.system.domain.ZzTeacherinfo;
import com.ruoyi.system.service.IZzStudentinfoService;
import com.ruoyi.system.service.IZzTeacherinfoService;
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
import com.ruoyi.system.domain.ZzTeachClass;
import com.ruoyi.system.service.IZzTeachClassService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.servlet.ModelAndView;

/**
 * 教师绑定班级Controller
 * 
 * @author ljg
 * @date 2023-08-03
 */
@Controller
@RequestMapping("/kqgl/class")
public class ZzTeachClassController extends BaseController
{
    private String prefix = "kqgl/class";

    @Autowired
    private IZzTeachClassService zzTeachClassService;

//    @RequiresPermissions("kqgl:class:view")
    @GetMapping()
    public String gclass()
    {
        return prefix + "/class";
    }

    @GetMapping("/classlist")
    public String classlist()
    {
        return prefix + "/classlist";
    }
    @GetMapping("/classlistphone")
    public String classlistphone()
    {
        return prefix + "/classlistphone";
    }

    /**
     * 查询教师绑定班级列表
     */
    //@RequiresPermissions("kqgl:class:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ZzTeachClass zzTeachClass)
    {
        startPage();
        List<ZzTeachClass> list = zzTeachClassService.selectZzTeachClassList(zzTeachClass);
        return getDataTable(list);
    }


    /**
     * 导出教师绑定班级列表
     */
    // @RequiresPermissions("kqgl:class:export")
    @Log(title = "教师绑定班级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZzTeachClass zzTeachClass)
    {
        List<ZzTeachClass> list = zzTeachClassService.selectZzTeachClassList(zzTeachClass);
        ExcelUtil<ZzTeachClass> util = new ExcelUtil<ZzTeachClass>(ZzTeachClass.class);
        return util.exportExcel(list, "教师绑定班级数据");
    }

    /**
     * 新增教师绑定班级
     */
    @GetMapping("/add")
    public String add()
    {

        return prefix + "/add";
    }

    @Autowired
    private IZzTeacherinfoService zzTeacherinfoService;

    @GetMapping("/add/{id}")
    public ModelAndView addTeacher(@PathVariable("id") Long id, ModelMap mmap)
    {
        ZzTeacherinfo zzTeacherinfo= zzTeacherinfoService.selectZzTeacherinfoById(id);
        mmap.put("zzTeacherinfo", zzTeacherinfo);
        ModelAndView view = new ModelAndView(prefix + "/add", mmap);
        return view;
    }

    /**
     * 新增保存教师绑定班级
     */
    // @RequiresPermissions("kqgl:class:add")
    @Log(title = "教师绑定班级", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZzTeachClass zzTeachClass)
    {
        return toAjax(zzTeachClassService.insertZzTeachClass(zzTeachClass));
    }

    /**
     * 修改教师绑定班级
     */
    // @RequiresPermissions("kqgl:class:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ZzTeachClass zzTeachClass = zzTeachClassService.selectZzTeachClassById(id);
        mmap.put("zzTeachClass", zzTeachClass);
        return prefix + "/edit";
    }

    /**
     * 修改保存教师绑定班级
     */
    //@RequiresPermissions("kqgl:class:edit")
    @Log(title = "教师绑定班级", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZzTeachClass zzTeachClass)
    {
        return toAjax(zzTeachClassService.updateZzTeachClass(zzTeachClass));
    }

    /**
     * 删除教师绑定班级
     */
    //@RequiresPermissions("kqgl:class:remove")
    @Log(title = "教师绑定班级", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zzTeachClassService.deleteZzTeachClassByIds(ids));
    }
}
