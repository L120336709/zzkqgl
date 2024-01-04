package com.ruoyi.web.controller.kqgl;

import java.util.List;
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
import com.ruoyi.system.domain.ZzTrains;
import com.ruoyi.system.service.IZzTrainsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 培训记录表Controller
 * 
 * @author ljg
 * @date 2023-12-05
 */
@Controller
@RequestMapping("/kqgl/trains")
public class ZzTrainsController extends BaseController
{
    private String prefix = "kqgl/trains";

    @Autowired
    private IZzTrainsService zzTrainsService;

    @RequiresPermissions("kqgl:trains:view")
    @GetMapping()
    public String trains()
    {
        return prefix + "/trains";
    }

    /**
     * 查询培训记录表列表
     */
    @RequiresPermissions("kqgl:trains:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ZzTrains zzTrains)
    {
        startPage();
        List<ZzTrains> list = zzTrainsService.selectZzTrainsList(zzTrains);
        return getDataTable(list);
    }

    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<ZzTrains> util = new ExcelUtil<ZzTrains>(ZzTrains.class);
        return util.importTemplateExcel("用户数据");
    }


    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, Integer updateSupport) throws Exception
    {
        ExcelUtil<ZzTrains> util = new ExcelUtil<ZzTrains>(ZzTrains.class);
        List<ZzTrains> zzTrainsList = util.importExcel(file.getInputStream());
        String message = zzTrainsService.ExamZzTrainsListImport(zzTrainsList, updateSupport);
        return AjaxResult.success(message);
    }
    /**
     * 导出培训记录表列表
     */
    @RequiresPermissions("kqgl:trains:export")
    @Log(title = "培训记录表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZzTrains zzTrains)
    {
        List<ZzTrains> list = zzTrainsService.selectZzTrainsList(zzTrains);
        ExcelUtil<ZzTrains> util = new ExcelUtil<ZzTrains>(ZzTrains.class);
        return util.exportExcel(list, "培训记录表数据");
    }

    /**
     * 新增培训记录表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存培训记录表
     */
    @RequiresPermissions("kqgl:trains:add")
    @Log(title = "培训记录表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZzTrains zzTrains)
    {
        return toAjax(zzTrainsService.insertZzTrains(zzTrains));
    }

    /**
     * 修改培训记录表
     */
    @RequiresPermissions("kqgl:trains:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ZzTrains zzTrains = zzTrainsService.selectZzTrainsById(id);
        mmap.put("zzTrains", zzTrains);
        return prefix + "/edit";
    }

    /**
     * 修改保存培训记录表
     */
    @RequiresPermissions("kqgl:trains:edit")
    @Log(title = "培训记录表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZzTrains zzTrains)
    {
        return toAjax(zzTrainsService.updateZzTrains(zzTrains));
    }

    /**
     * 删除培训记录表
     */
    @RequiresPermissions("kqgl:trains:remove")
    @Log(title = "培训记录表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zzTrainsService.deleteZzTrainsByIds(ids));
    }
}
