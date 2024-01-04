package com.ruoyi.web.controller.kqgl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.ZzClassinfo;
import com.ruoyi.system.domain.ZzTeachClass;
import com.ruoyi.system.domain.ZzTeacherinfo;
import com.ruoyi.system.service.IZzClassinfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ZzStudentinfo;
import com.ruoyi.system.service.IZzStudentinfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 学生信息表Controller
 *
 * @author ljg
 * @date 2023-08-03
 */
@Controller
@Component
@EnableScheduling
@RequestMapping("/kqgl/studentinfo")
public class ZzStudentinfoController extends BaseController {
    private String prefix = "kqgl/studentinfo";

    @Autowired
    private IZzStudentinfoService zzStudentinfoService;

    @RequiresPermissions("kqgl:studentinfo:view")
    @GetMapping()
    public String studentinfo() {
        return prefix + "/studentinfo";
    }

    /**
     * 查询学生信息表列表
     */
    @RequiresPermissions("kqgl:studentinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ZzStudentinfo zzStudentinfo) {
        startPage();
        List<ZzStudentinfo> list = zzStudentinfoService.selectZzStudentinfoList(zzStudentinfo);
        return getDataTable(list);
    }

    @Autowired
    private IZzClassinfoService zzClassinfoService;

    @PostMapping("/getStudent")
    @ResponseBody
    public AjaxResult list(Long classId) {
       // ZzClassinfo zzClassinfo = zzClassinfoService.selectZzClassinfoById(classId);
        ZzStudentinfo zzStudentinfo = new ZzStudentinfo();
        zzStudentinfo.setClassId(classId+"");
        //zzStudentinfo.setClassId(zzClassinfo.getClassId());
        zzStudentinfo.setState("1");
        List<ZzStudentinfo> zzStudentinfoList=zzStudentinfoService.selectZzStudentinfoList(zzStudentinfo);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("data", zzStudentinfoList);
        return ajaxResult;
    }

    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<ZzStudentinfo> util = new ExcelUtil<ZzStudentinfo>(ZzStudentinfo.class);
        return util.importTemplateExcel("用户数据");
    }


    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, Integer updateSupport) throws Exception
    {
        ExcelUtil<ZzStudentinfo> util = new ExcelUtil<ZzStudentinfo>(ZzStudentinfo.class);
        List<ZzStudentinfo> zzStudentinfoList = util.importExcel(file.getInputStream());
        String message = zzStudentinfoService.ExamZzStudentinfoListImport(zzStudentinfoList, updateSupport);
        return AjaxResult.success(message);
    }

//    @Scheduled(cron = "1 0 0 * * ? ")
//    public void startTask() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        System.out.println("每隔10秒执行一次：" + new Date());
//        getStudentList();
//        ZzGradeinfoController zzGradeinfoController=new ZzGradeinfoController();
//        zzGradeinfoController.getGradeList();
//        ZzClassinfoController zzClassinfoController=new ZzClassinfoController();
//        zzClassinfoController.getClassList();
//        ZzTeacherinfoController zzTeacherinfoController=new ZzTeacherinfoController();
//        zzTeacherinfoController.getTeacherList();
//    }


    /**
     * 接口获取教职工数据并填充更新到教职工表
     */
    @PostMapping("/getStudentList")
    @ResponseBody
    public AjaxResult getStudentList() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int pageNo = 0;
        int pageSize = 100;
        Integer total = 1;

        List<ZzStudentinfo> zzStudentinfoList = new ArrayList<>();
        List<String> userIds = new ArrayList<>();

        while (pageNo * pageSize < total) {
            pageNo = pageNo + 1;
            JSONObject jsonObject = ZzApiController.getStudent("10010308", pageNo, pageSize);
            JSONObject jsonObject2 = JSONObject.parseObject(jsonObject.getString("data"));
            List<JSONObject> timeStrings = (List<JSONObject>) JSON.parse(jsonObject2.getString("records"));
            total = jsonObject2.getInteger("total");
            for (JSONObject jsonObject1 : timeStrings) {
                ZzStudentinfo zzClassinfo = new ZzStudentinfo();
                zzClassinfo.setUserId(jsonObject1.getString("userId"));
                zzClassinfo.setUserNo(jsonObject1.getString("userNo"));
                zzClassinfo.setUserName(jsonObject1.getString("userName"));
                zzClassinfo.setClassId(jsonObject1.getString("classId"));
                zzClassinfo.setEduNo(jsonObject1.getString("eduNo"));
                zzClassinfo.setUserHead(jsonObject1.getString("userHead"));
                zzClassinfo.setState(jsonObject1.getString("state"));
                zzClassinfo.setPersonName(jsonObject1.getString("personName"));
                zzClassinfo.setPinyName(jsonObject1.getString("pinyName"));
                zzClassinfo.setGender(jsonObject1.getString("gender"));
                zzClassinfo.setIdcard(jsonObject1.getString("idcard"));
                List<JSONObject> userOrg = (List<JSONObject>) JSON.parse(jsonObject1.getString("userOrg"));
                if (userOrg.size() > 0) {
                    zzClassinfo.setOrgId(userOrg.get(0).getString("orgId"));
                    zzClassinfo.setIdentityId(userOrg.get(0).getString("identityId"));
                    zzClassinfo.setOrgName(userOrg.get(0).getString("orgName"));
                    zzClassinfo.setOrgState(userOrg.get(0).getString("state"));
                }
                userIds.add(jsonObject1.getString("userId"));
                zzStudentinfoList.add(zzClassinfo);
            }
        }

        //批量查询当前年级id对应的数据库数据
        //获取所有学生数据，对比接口获取的学生数据，有相同的数据就更新，接口有数据库没有就新增，接口没有数据库有就删除（设置状态）
        List<ZzStudentinfo> zzClassinfoListOld = zzStudentinfoService.selectZzStudentinfoList(new ZzStudentinfo());

//        if (userIds.size() > 0) {
//            zzClassinfoListOld = zzStudentinfoService.selectListByStudentinfoIds(userIds);
//        }

        List<ZzStudentinfo> zzClassinfoListInsert = new ArrayList<>();
        List<ZzStudentinfo> zzClassinfoListUpdate = new ArrayList<>();
        for (ZzStudentinfo zzClassinfo : zzStudentinfoList) {
            boolean boo = true;
            boolean boo2=true;
            // 遍历数据判断是否有旧数据，有则更新，没有则插入
            for (ZzStudentinfo zzClassinfoOld : zzClassinfoListOld) {
                if (zzClassinfo.getUserId().equals(zzClassinfoOld.getUserId())) {
                    boo = false;
                    zzClassinfo.setId(zzClassinfoOld.getId());
                    if(zzClassinfo.getPersonName().equals("测试异常学生")){
                        System.err.println(zzClassinfo.toString());
                    }
                    if(BeanUtils.describe(zzClassinfo).equals(BeanUtils.describe(zzClassinfoOld))){
                        boo2=false;
                    }
                }
            }
            if (boo == true) {
                zzClassinfoListInsert.add(zzClassinfo);
            } else {
                //数据存在变化才更新
                if(boo2==true){
                    zzClassinfoListUpdate.add(zzClassinfo);
                }

            }
        }


        //判断存在旧数据中，不存在接口数据中，那么设置为删除状态
        for (ZzStudentinfo zzStudentinfoOld : zzClassinfoListOld) {
            boolean boo=false;
            for (ZzStudentinfo zzStudentinfo : zzStudentinfoList) {
                if(zzStudentinfo.getUserId().equals(zzStudentinfoOld.getUserId())){
                    boo=true;
                }
            }
            if(boo==false){
                ZzStudentinfo zzStudentinfo=new ZzStudentinfo();
                zzStudentinfo.setId(zzStudentinfoOld.getId());
                zzStudentinfo.setState("0");
                zzStudentinfo.setOrgState("0");
                zzClassinfoListUpdate.add(zzStudentinfo);
            }
        }

        // 批量插入和批量更新
        if (zzClassinfoListInsert.size() > 0) {
            zzStudentinfoService.insertStudentinfoList(zzClassinfoListInsert);
        }
        if (zzClassinfoListUpdate.size() > 0) {
            zzStudentinfoService.updateStudentinfoList(zzClassinfoListUpdate);
        }

        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("msg", "同步学生数据成功！");
        return ajaxResult;
    }

    /**
     * 导出学生信息表列表
     */
    @RequiresPermissions("kqgl:studentinfo:export")
    @Log(title = "学生信息表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZzStudentinfo zzStudentinfo) {
        List<ZzStudentinfo> list = zzStudentinfoService.selectZzStudentinfoList(zzStudentinfo);
        ExcelUtil<ZzStudentinfo> util = new ExcelUtil<ZzStudentinfo>(ZzStudentinfo.class);
        return util.exportExcel(list, "学生信息表数据");
    }

    /**
     * 新增学生信息表
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存学生信息表
     */
    @RequiresPermissions("kqgl:studentinfo:add")
    @Log(title = "学生信息表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZzStudentinfo zzStudentinfo) {


        List<ZzStudentinfo> zzStudentinfoList=zzStudentinfoService.selectZzStudentinfoList(new ZzStudentinfo());
        Integer userno=900001;
        for(ZzStudentinfo zzStudentinfoOld:zzStudentinfoList){
            if(userno<Integer.parseInt(zzStudentinfoOld.getUserNo())){
                userno=Integer.parseInt(zzStudentinfoOld.getUserNo());
            }
            if(zzStudentinfo.getIdcard().equals(zzStudentinfoOld.getIdcard())){
                return AjaxResult.error("当前身份证对应用户已存在");
            }
        }
        zzStudentinfo.setUserNo((userno+1)+"");

        return toAjax(zzStudentinfoService.insertZzStudentinfo(zzStudentinfo));
    }

    /**
     * 修改学生信息表
     */
    @RequiresPermissions("kqgl:studentinfo:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ZzStudentinfo zzStudentinfo = zzStudentinfoService.selectZzStudentinfoById(id);


        ZzClassinfo zzClassinfo=new ZzClassinfo();
        zzClassinfo.setClassId(zzStudentinfo.getClassId());
            List<ZzClassinfo> zzClassinfos=      zzClassinfoService.selectZzClassinfoList(zzClassinfo);

        zzStudentinfo.setUserHead(zzClassinfos.get(0).getClassNickname());
        mmap.put("zzStudentinfo", zzStudentinfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存学生信息表
     */
    @RequiresPermissions("kqgl:studentinfo:edit")
    @Log(title = "学生信息表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZzStudentinfo zzStudentinfo) {
        return toAjax(zzStudentinfoService.updateZzStudentinfo(zzStudentinfo));
    }

    /**
     * 删除学生信息表
     */
    @RequiresPermissions("kqgl:studentinfo:remove")
    @Log(title = "学生信息表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(zzStudentinfoService.deleteZzStudentinfoByIds(ids));
    }
}
