package com.ruoyi.web.controller.kqgl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.web.controller.zzhtml.ZPTinterface;
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
import com.ruoyi.system.domain.ZzGradeinfo;
import com.ruoyi.system.service.IZzGradeinfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 年级表Controller
 *
 * @author ljg
 * @date 2023-08-03
 */
@Controller
@RequestMapping("/kqgl/gradeinfo")
public class ZzGradeinfoController extends BaseController {
    private String prefix = "kqgl/gradeinfo";

    @Autowired
    private IZzGradeinfoService zzGradeinfoService;

    @RequiresPermissions("kqgl:gradeinfo:view")
    @GetMapping()
    public String gradeinfo() {
        return prefix + "/gradeinfo";
    }

    /**
     * 查询年级表列表
     */
    @RequiresPermissions("kqgl:gradeinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ZzGradeinfo zzGradeinfo) {
        startPage();
        List<ZzGradeinfo> list = zzGradeinfoService.selectZzGradeinfoList(zzGradeinfo);
        return getDataTable(list);
    }

    @PostMapping("/ZzGradeinfolist")
    @ResponseBody
    public List<ZzGradeinfo>  ZzGradeinfolist(ZzGradeinfo zzGradeinfo) {
        List<ZzGradeinfo> list = zzGradeinfoService.selectZzGradeinfoList(zzGradeinfo);
        return  list;
    }

    /**
     * 接口获取年级数据并填充更新到年级表
     */
    @PostMapping("/getGradeList")
    @ResponseBody
    public AjaxResult getGradeList() {
        JSONObject jsonObject = ZzApiController.getGrade("10010308");

        JSONObject jsonObject2 = JSONObject.parseObject(jsonObject.getString("data"));

        List<JSONObject> timeStrings = (List<JSONObject>) JSON.parse(jsonObject2.getString("records"));

        List<ZzGradeinfo> zzGradeinfoList = new ArrayList<>();
        List<String> gradeids = new ArrayList<>();
        for (JSONObject jsonObject1 : timeStrings) {
            ZzGradeinfo zzGradeinfo = new ZzGradeinfo();
            zzGradeinfo.setGradeId(jsonObject1.getString("gradeId"));
            zzGradeinfo.setGradeCode(jsonObject1.getString("gradeCode"));
            zzGradeinfo.setGradeName(jsonObject1.getString("gradeName"));
            zzGradeinfo.setSchoolId("10010308");
            gradeids.add(jsonObject1.getString("gradeId"));
            zzGradeinfoList.add(zzGradeinfo);
        }
        //批量查询当前年级id对应的数据库数据
        List<ZzGradeinfo> zzGradeinfoListOld = new ArrayList<>();
        if (gradeids.size() > 0) {
            zzGradeinfoListOld=zzGradeinfoService.selectListByGradeIds(gradeids);
        }

        List<ZzGradeinfo> zzGradeinfoListInsert = new ArrayList<>();
        List<ZzGradeinfo> zzGradeinfoListUpdate = new ArrayList<>();
        for (ZzGradeinfo zzGradeinfo : zzGradeinfoList) {
            boolean boo = true;
            // 遍历数据判断是否有旧数据，有则更新，没有则插入
            for (ZzGradeinfo zzGradeinfoOld : zzGradeinfoListOld) {
                if (zzGradeinfo.getGradeId().equals(zzGradeinfoOld.getGradeId())) {
                    boo = false;
                    zzGradeinfo.setId(zzGradeinfoOld.getId());
                }
            }
            if (boo == true) {
                zzGradeinfoListInsert.add(zzGradeinfo);
            } else {
                zzGradeinfoListUpdate.add(zzGradeinfo);
            }
        }

        // 批量插入和批量更新
        if(zzGradeinfoListInsert.size()>0){
            zzGradeinfoService.insertGradeinfoList(zzGradeinfoListInsert);
        }
        if(zzGradeinfoListUpdate.size()>0){
            zzGradeinfoService.updateGradeinfoList(zzGradeinfoListUpdate);
        }

        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("msg", "同步年级数据成功！");
        return ajaxResult;
    }


    /**
     * 导出年级表列表
     */
    @RequiresPermissions("kqgl:gradeinfo:export")
    @Log(title = "年级表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZzGradeinfo zzGradeinfo) {
        List<ZzGradeinfo> list = zzGradeinfoService.selectZzGradeinfoList(zzGradeinfo);
        ExcelUtil<ZzGradeinfo> util = new ExcelUtil<ZzGradeinfo>(ZzGradeinfo.class);
        return util.exportExcel(list, "年级表数据");
    }

    /**
     * 新增年级表
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存年级表
     */
    @RequiresPermissions("kqgl:gradeinfo:add")
    @Log(title = "年级表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZzGradeinfo zzGradeinfo) {

       List<ZzGradeinfo> zzGradeinfoList= zzGradeinfoService.selectZzGradeinfoList(zzGradeinfo);
        if(zzGradeinfoList.size()>0){
            return AjaxResult.error("当前年级已经存在");
        }
        return toAjax(zzGradeinfoService.insertZzGradeinfo(zzGradeinfo));
    }

    /**
     * 修改年级表
     */
    @RequiresPermissions("kqgl:gradeinfo:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ZzGradeinfo zzGradeinfo = zzGradeinfoService.selectZzGradeinfoById(id);
        mmap.put("zzGradeinfo", zzGradeinfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存年级表
     */
    @RequiresPermissions("kqgl:gradeinfo:edit")
    @Log(title = "年级表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZzGradeinfo zzGradeinfo) {
        return toAjax(zzGradeinfoService.updateZzGradeinfo(zzGradeinfo));
    }

    /**
     * 删除年级表
     */
    @RequiresPermissions("kqgl:gradeinfo:remove")
    @Log(title = "年级表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(zzGradeinfoService.deleteZzGradeinfoByIds(ids));
    }
}
