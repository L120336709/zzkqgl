package com.ruoyi.web.controller.zzhtml;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.shiro.SecurityUtils;
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
import com.ruoyi.system.domain.ZzToken;
import com.ruoyi.system.service.IZzTokenService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * token存储Controller
 * 
 * @author ruoyi
 * @date 2023-06-19
 */
@Controller
@RequestMapping("/system/token")
public class ZzTokenController extends BaseController
{
    private String prefix = "system/token";

    @Autowired
    private IZzTokenService zzTokenService;

    @RequiresPermissions("system:token:view")
    @GetMapping()
    public String token()
    {
        return prefix + "/token";
    }

    /**
     * 查询token存储列表
     */
    @RequiresPermissions("system:token:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ZzToken zzToken)
    {
        startPage();
        List<ZzToken> list = zzTokenService.selectZzTokenList(zzToken);
        return getDataTable(list);
    }

    /**
     * 查询token存储列表
     */
    @PostMapping("/getTokenByNo")
    @ResponseBody
    public ZzToken  getTokenByNo()
    {
        ZzToken zzToken=new ZzToken();
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        zzToken.setUserId(Long.parseLong(sysUser.getLoginName()));
        List<ZzToken> list = zzTokenService.selectZzTokenList(zzToken);
        if(list.size()==1){
            return list.get(0);
        }else {
            return new ZzToken();
        }
    }

    /**
     * 导出token存储列表
     */
    @RequiresPermissions("system:token:export")
    @Log(title = "token存储", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZzToken zzToken)
    {
        List<ZzToken> list = zzTokenService.selectZzTokenList(zzToken);
        ExcelUtil<ZzToken> util = new ExcelUtil<ZzToken>(ZzToken.class);
        return util.exportExcel(list, "token存储数据");
    }

    /**
     * 新增token存储
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存token存储
     */
    @RequiresPermissions("system:token:add")
    @Log(title = "token存储", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZzToken zzToken)
    {
        return toAjax(zzTokenService.insertZzToken(zzToken));
    }

    /**
     * 修改token存储
     */
    @RequiresPermissions("system:token:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ZzToken zzToken = zzTokenService.selectZzTokenById(id);
        mmap.put("zzToken", zzToken);
        return prefix + "/edit";
    }

    /**
     * 修改保存token存储
     */
    @RequiresPermissions("system:token:edit")
    @Log(title = "token存储", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZzToken zzToken)
    {
        return toAjax(zzTokenService.updateZzToken(zzToken));
    }

    /**
     * 删除token存储
     */
    @RequiresPermissions("system:token:remove")
    @Log(title = "token存储", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zzTokenService.deleteZzTokenByIds(ids));
    }
}
