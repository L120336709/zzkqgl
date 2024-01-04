package com.ruoyi.web.controller.kqgl;

import java.util.*;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.ZzStudentinfo;
import com.ruoyi.system.domain.ZzTeachClass;
import com.ruoyi.system.domain.ZzTeacherinfo;
import com.ruoyi.system.service.IZzClassinfoService;
import com.ruoyi.system.service.IZzStudentinfoService;
import com.ruoyi.system.service.IZzTeachClassService;
import org.apache.poi.ss.usermodel.DateUtil;
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
import com.ruoyi.system.domain.ZzVacation;
import com.ruoyi.system.service.IZzVacationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.crypto.Data;

/**
 * 学生请假表Controller
 *
 * @author ljg
 * @date 2023-08-03
 */
@Controller
@RequestMapping("/kqgl/vacation")
public class ZzVacationController extends BaseController {
    private String prefix = "kqgl/vacation";

    @Autowired
    private IZzVacationService zzVacationService;
    @Autowired
    private IZzStudentinfoService zzStudentinfoService;
    @Autowired
    private IZzTeachClassService zzTeachClassService;
    @RequiresPermissions("kqgl:vacation:view")
    @GetMapping()
    public String vacation() {
        return prefix + "/vacation";
    }

    /**
     * 查询学生请假表列表
     */
    @RequiresPermissions("kqgl:vacation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ZzVacation zzVacation) {
        startPage();
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser.getLoginName().equals("admin")||sysUser.getLoginName().equals("56999")){

        }else {
            zzVacation.setPzTeacherId(sysUser.getUserId()+"");
        }
        zzVacation.setStatus("1");
        List<ZzVacation> list = zzVacationService.selectZzVacationListByTime(zzVacation);

        return getDataTable(list);
    }

    @PostMapping("/listByClass")
    @ResponseBody
    public TableDataInfo listByClass(ZzVacation zzVacation) {
        startPage();
        List<ZzVacation> list = zzVacationService.selectZzVacationList(zzVacation);
        return getDataTable(list);
    }

    //获取当前用户绑定的班级，然后班级查询班级学生，再根据学生id查询请假信息
    @PostMapping("/listByclass")
    @ResponseBody
    public TableDataInfo listByclass(ZzVacation zzVacation) {

        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();

        ZzTeachClass zzTeachClass=new ZzTeachClass();
        zzTeachClass.setTeacherId(sysUser.getUserId()+"");
        List<ZzTeachClass> zzTeachClass1=zzTeachClassService.selectZzTeachClassList(zzTeachClass);

        List<ZzVacation> list = new ArrayList<>();
        List<String> studentId=new ArrayList<>();
        if(zzTeachClass1.size()>0){
            for(ZzTeachClass zzTeachClass2:zzTeachClass1){

                zzTeachClass2.getClassId();

            }

        }else {
            return getDataTable(list);
        }

        startPage();
        //list = zzVacationService.selectListByIds(studentId);
        return getDataTable(list);
    }


    //获取全校学生数据，按照年级、班级分别统计人数
    @PostMapping("/getStuByGradeAndClass")
    @ResponseBody
    public AjaxResult getStuByGradeAndClass(String type, String classId, ZzVacation zzVacation) {


        List<ZzStudentinfo> zzStudentinfoList = zzStudentinfoService.selectZzStuListByclassId("");

        List<ZzStudentinfo> zzStudentinfoLists = new ArrayList<>();


        for (ZzStudentinfo zzStudentinfo0 : zzStudentinfoList) {
            boolean boo = true;
            String[] names = zzStudentinfo0.getClassId().split(",");
            for (ZzStudentinfo zzStudentinfo1 : zzStudentinfoLists) {
                //按照年级统计
                String[] names2 = zzStudentinfo1.getClassId().split(",");
                if (type.equals("1")) {
                    if (names[0].equals(names2[0])) {
                        boo = false;
                        zzStudentinfo1.setGender(Integer.parseInt(zzStudentinfo1.getGender()) + 1 + "");
                    }
                } else if (type.equals("2")) {
                    if (names[1].equals(names2[1]) && names[0].equals(classId)) {
                        boo = false;
                        zzStudentinfo1.setGender(Integer.parseInt(zzStudentinfo1.getGender()) + 1 + "");
                    }
                }
            }
            if (boo == true) {
                if (type.equals("1")) {
                    zzStudentinfo0.setGender("1");
                    zzStudentinfo0.setState("0");
                    zzStudentinfoLists.add(zzStudentinfo0);
                } else if (type.equals("2") && names[0].equals(classId)) {
                    zzStudentinfo0.setGender("1");
                    zzStudentinfo0.setState("0");
                    zzStudentinfoLists.add(zzStudentinfo0);
                }
            }
        }

        Calendar calendarstart = Calendar.getInstance();
        Calendar calendarend = Calendar.getInstance();

        if (zzVacation.getStartTime() != null) {
            calendarstart.setTime(zzVacation.getStartTime());
            calendarend.setTime(zzVacation.getEndTime());
        }

        // 将当前日期时间的时、分、秒、毫秒部分设置为0
        calendarstart.set(Calendar.HOUR_OF_DAY, 0);
        calendarstart.set(Calendar.MINUTE, 0);
        calendarstart.set(Calendar.SECOND, 0);
        calendarstart.set(Calendar.MILLISECOND, 0);

        // 获取修改后的日期时间
        Date zeroTime = calendarstart.getTime();
        zzVacation.setStartTime(zeroTime);
        calendarend.set(Calendar.HOUR_OF_DAY, 23);
        calendarend.set(Calendar.MINUTE, 59);
        calendarend.set(Calendar.SECOND, 59);
        calendarend.set(Calendar.MILLISECOND, 0);
        Date zeroTime2 = calendarend.getTime();
        zzVacation.setEndTime(zeroTime2);

        zzVacation.setSqStatus("1");
        List<ZzVacation> zzVacationList = new ArrayList<>();
        if(type.equals("1")){
            zzVacationList=kqlist(zzVacation, "1", "");
        }else {
            zzVacationList= kqlist(zzVacation, "2", classId);
        }

        for (ZzStudentinfo zzStudentinfo1 : zzStudentinfoLists) {
            //按照年级统计
            String[] names2 = zzStudentinfo1.getClassId().split(",");
            for (ZzVacation zzVacation1 : zzVacationList) {
                String[] vvnames2 = zzVacation1.getStudentName().split(",");
                if (type.equals("1")) {
                    if (vvnames2[0].equals(names2[0])) {
                        zzStudentinfo1.setOrgState(zzVacation1.getQjType());
                        zzStudentinfo1.setState(zzVacation1.getStatus());
                    }
                } else if (type.equals("2")) {
                    if (vvnames2[1].equals(names2[1]) && vvnames2[0].equals(classId)) {
                        zzStudentinfo1.setOrgState(zzVacation1.getQjType());
                        zzStudentinfo1.setState(zzVacation1.getStatus());
                    }
                }
            }
        }


        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("data", zzStudentinfoLists);
        return ajaxResult;
    }


    /**
     * @param zzVacation
     * @param type       1年级 2班级
     * @return
     */
    @PostMapping("/kqlist")
    @ResponseBody
    public List<ZzVacation> kqlist(ZzVacation zzVacation, String type, String pap) {

        List<ZzVacation> list = zzVacationService.selectZzVacationListByTime(zzVacation);

        List<ZzVacation> lists = new ArrayList<>();



        for (ZzVacation zzVacationlist : list) {
            boolean boo = true;
            String[] names = zzVacationlist.getStudentName().split(",");
            for (ZzVacation zzVacationlists : lists) {
                //按照年级统计
                String[] names2 = zzVacationlists.getStudentName().split(",");

                String[] qjname={"公假","事假","病假","休学","其他","退学"};
                String[] qjString=zzVacationlists.getQjType().split(";");

                Integer[] qjsum=new Integer[6];
                for(int i=0;i<qjString.length;i++){
                    qjsum[i]=Integer.parseInt(qjString[i]);

                }


                if(zzVacationlist.getQjType()==null){
                    qjsum[4]=qjsum[4]+1;
                }else {
                    for(int i=0;i<qjname.length;i++){
                        if(qjname[i].equals(zzVacationlist.getQjType())){
                            qjsum[i]=qjsum[i]+1;
                        }
                    }
                }
                String QjType=qjsum[0]+"";
                for(int i=1;i<qjname.length;i++){
                    QjType=QjType+";"+qjsum[i];
                }


                if (type.equals("1")) {
                    if (names[0].equals(names2[0])) {
                        boo = false;
                        zzVacationlists.setQjType(QjType);
                        zzVacationlists.setStatus(Integer.parseInt(zzVacationlists.getStatus()) + 1 + "");
                    }
                } else if (type.equals("2")) {
                    if (names[1].equals(names2[1]) && names[0].equals(pap)) {
                        boo = false;
                        zzVacationlists.setQjType(QjType);
                        zzVacationlists.setStatus(Integer.parseInt(zzVacationlists.getStatus()) + 1 + "");
                    }
                }
            }
            if (boo == true) {
                String[] qjname={"公假","事假","病假","休学","其他","退学"};
                Integer[] qjsum={0,0,0,0,0,0};
                if(zzVacationlist.getQjType()==null){
                    qjsum[4]=1;
                }else {
                    for(int i=0;i<qjname.length;i++){
                        if(qjname[i].equals(zzVacationlist.getQjType())){
                            qjsum[i]=1;
                        }
                    }
                }
                String QjType=qjsum[0]+"";
                for(int i=1;i<qjname.length;i++){
                    QjType=QjType+";"+qjsum[i];
                }
                zzVacationlist.setQjType(QjType);

                if (type.equals("1")) {
                    zzVacationlist.setStatus("1");
                    lists.add(zzVacationlist);
                } else if (type.equals("2") && names[0].equals(pap)) {
                    zzVacationlist.setStatus("1");
                    lists.add(zzVacationlist);
                }

            }
        }
        return lists;

    }

    public String SumTime(ZzVacation zzVacation) {
        Long time = (zzVacation.getEndTime().getTime() - zzVacation.getStartTime().getTime()) / 1000;
        Long td = time / 86400;  //天  24*60*60*1000
        Long th = time / 3600 - 24 * td;  //天  24*60*60*1000
        if (time % 60 > 0) {
            th = th + 1;
        }
        if (th < 0) {
            th = 0L;
        }
        return td + "天" + th + "小时" + ";" + time;
    }

    /**
     * 查询班级所有学生的请假信息
     *
     * @param classId
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("/classStuList")
    @ResponseBody
    public List<ZzVacation> classStuList(String classId, String startTime, String endTime, String stuName, String paixu, String sc) {
        //根据班级id，查询对应学生id，考勤表中查询对应id的请假信息
        ZzStudentinfo zzStudentinfo = new ZzStudentinfo();
        zzStudentinfo.setPersonName(stuName);
        zzStudentinfo.setClassId(classId);
        List<ZzStudentinfo> zzStudentinfoList = zzStudentinfoService.selectZzStudentinfoList(zzStudentinfo);

        List<String> studentId = new ArrayList<>();
        for (ZzStudentinfo zzStudentinfo1 : zzStudentinfoList) {
            studentId.add(zzStudentinfo1.getId() + "");
        }

        if (studentId.size() == 0) {
            return new ArrayList<>();
        }

        List<ZzVacation> list = zzVacationService.selectListByIdsAndSqTime(studentId, startTime, endTime, "1");
        List<ZzVacation> lists = new ArrayList<>();
        for (ZzVacation zzVacation : list) {
            boolean boo = true;
            for (ZzVacation zzVacation0 : lists) {
                if (zzVacation0.getStudentId().equals(zzVacation.getStudentId())) {
                    boo = false;
                    zzVacation0.setStartTimeData(zzVacation0.getStartTimeData() + ";" +
                            DateUtils.parseDateToStr("YYYY-MM-dd hh:mm:ss", zzVacation.getStartTime()));
                    zzVacation0.setEndTimeData(zzVacation0.getEndTimeData() + ";" +
                            DateUtils.parseDateToStr("YYYY-MM-dd hh:mm:ss", zzVacation.getEndTime()));
                    zzVacation0.setStatus(Integer.parseInt(zzVacation0.getStatus()) + 1 + "");
                    zzVacation0.setSqTimeData(zzVacation0.getSqTimeData() + ";" +
                            DateUtils.parseDateToStr("YYYY-MM-dd hh:mm:ss", zzVacation.getSqTime()));

                    zzVacation0.setPzTimeData(zzVacation0.getPzTimeData() + ";" +
                            DateUtils.parseDateToStr("YYYY-MM-dd hh:mm:ss", zzVacation.getPzTime()));
                    if (zzVacation.getXjTime() != null) {
                        zzVacation0.setXjTimeData(zzVacation0.getXjTimeData() + ";" +
                                DateUtils.parseDateToStr("YYYY-MM-dd hh:mm:ss", zzVacation.getXjTime()));
                    }
                    zzVacation0.setPzTeacherName(zzVacation0.getPzTeacherName() + ";" + zzVacation.getPzTeacherName());

                    String[] ljtimes = SumTime(zzVacation).split(";");
                    zzVacation0.setLjTimeInt(zzVacation0.getLjTimeInt() + Long.parseLong(ljtimes[1]));
                    zzVacation0.setLjTime(zzVacation0.getLjTime() + ";" + ljtimes[0]);
                }
            }
            if (boo == true) {
                zzVacation.setStatus("1");

                zzVacation.setStartTimeData(DateUtils.parseDateToStr("YYYY年MM月dd日 hh:mm:ss", zzVacation.getStartTime()));
                zzVacation.setEndTimeData(DateUtils.parseDateToStr("YYYY年MM月dd日 hh:mm:ss", zzVacation.getEndTime()));
                zzVacation.setSqTimeData(DateUtils.parseDateToStr("YYYY年MM月dd日 hh:mm:ss", zzVacation.getSqTime()));
                zzVacation.setPzTimeData(DateUtils.parseDateToStr("YYYY年MM月dd日 hh:mm:ss", zzVacation.getPzTime()));
                if (zzVacation.getXjTime() != null) {
                    zzVacation.setXjTimeData(DateUtils.parseDateToStr("YYYY年MM月dd日 hh:mm:ss", zzVacation.getXjTime()));
                }
                String[] ljtimes = SumTime(zzVacation).split(";");
                zzVacation.setLjTime(ljtimes[0]);
                zzVacation.setLjTimeInt(Long.parseLong(ljtimes[1]));
                lists.add(zzVacation);
            }
        }
        //Collections.sort(lists);

        Collections.sort(lists, new Comparator<ZzVacation>() {
            @Override
            //按照code的大小升序排列
            public int compare(ZzVacation o1, ZzVacation o2) {
                if (paixu != null && paixu != "") {
                    if (paixu.equals("1")) {
                        if (Integer.parseInt(o1.getStatus()) > Integer.parseInt(o2.getStatus())) {
                            return 1;
                        }
                    } else {
                        if (Integer.parseInt(o1.getStatus()) < Integer.parseInt(o2.getStatus())) {
                            return 1;
                        }
                    }
                } else if (sc != null && sc != "") {
                    if (sc.equals("1")) {
                        if (o1.getLjTimeInt() > o2.getLjTimeInt()) {
                            return 1;
                        }
                    } else {
                        if (o1.getLjTimeInt() < o2.getLjTimeInt()) {
                            return 1;
                        }
                    }

                }

                if (paixu != null && paixu != "") {
                    if (o1.getStatus() == o2.getStatus()) {
                        return 0;
                    }
                } else if (sc != null && sc != "") {
                    if (o1.getLjTimeInt() == o2.getLjTimeInt()) {
                        return 0;
                    }
                }

                return -1;
            }
        });

        return lists;
    }


    @PostMapping("/studentlist")
    @ResponseBody
    public List<ZzVacation> studentlist(String studentIds, String sqTime, String qjStatus, String sqStatus, String fw) {
        String[] ss = studentIds.split(",");
        List<String> studentIdlist = new ArrayList<>();
        for (String s : ss) {
            studentIdlist.add(s);
        }
        if (fw.equals("1")) {
            if (sqTime == null || sqTime == "") {
                sqTime = DateUtils.getDate();
            }
        }
        if (qjStatus != null) {
            String[] qjStatuslist = qjStatus.split(",");
            List<ZzVacation> list = zzVacationService.selectListByIds(studentIdlist, sqTime, sqStatus, qjStatuslist[0], qjStatuslist[1], qjStatuslist[2]);
            return list;
        } else {
            List<ZzVacation> list = zzVacationService.selectListByIds(studentIdlist, sqTime, sqStatus, null, null, null);
            return list;
        }
    }

    /**
     * 导出学生请假表列表
     */
    @RequiresPermissions("kqgl:vacation:export")
    @Log(title = "学生请假表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZzVacation zzVacation) {
        List<ZzVacation> list = zzVacationService.selectZzVacationList(zzVacation);
        ExcelUtil<ZzVacation> util = new ExcelUtil<ZzVacation>(ZzVacation.class);
        return util.exportExcel(list, "学生请假表数据");
    }

    /**
     * 新增学生请假表
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存学生请假表
     */

    @Log(title = "学生请假表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZzVacation zzVacation) {

        Date datanow=new Date();

        zzVacation.setSqTime(datanow);
        zzVacation.setSqStatus("1");    //直接设置审批完成
        zzVacation.setStatus("1");      //设置数据正常

        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();

        //判断当前时间和请假时间的关系
        String qjStatus;
        if(datanow.getTime()<zzVacation.getStartTime().getTime()){
            qjStatus="0";
        }else  if(datanow.getTime()<zzVacation.getEndTime().getTime()){
            qjStatus="1";
        }else {
            qjStatus="-1";
        }
        zzVacation.setQjStatus(qjStatus);

        //申请就自动审批
        zzVacation.setPzTime(new Date());
        zzVacation.setPzTeacherId(sysUser.getUserId() + "");
        zzVacation.setPzTeacherName(sysUser.getUserName());


        zzVacation.setSqPeople(sysUser.getUserName());
        return toAjax(zzVacationService.insertZzVacation(zzVacation));
    }

    /**
     * 修改学生请假表
     */
    @RequiresPermissions("kqgl:vacation:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ZzVacation zzVacation = zzVacationService.selectZzVacationById(id);
        mmap.put("zzVacation", zzVacation);
        return prefix + "/edit";
    }

    /**
     * 修改保存学生请假表
     */
    //@RequiresPermissions("kqgl:vacation:edit")
    @Log(title = "学生请假表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZzVacation zzVacation) {

        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        zzVacation.setPzTime(new Date());
        zzVacation.setPzTeacherId(sysUser.getUserId() + "");
        zzVacation.setPzTeacherName(sysUser.getUserName());
        return toAjax(zzVacationService.updateZzVacation(zzVacation));
    }

    /**
     * 删除学生请假表
     */
    @RequiresPermissions("kqgl:vacation:remove")
    @Log(title = "学生请假表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(zzVacationService.deleteZzVacationByIds(ids));
    }
}
