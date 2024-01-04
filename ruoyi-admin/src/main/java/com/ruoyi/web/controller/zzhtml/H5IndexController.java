package com.ruoyi.web.controller.zzhtml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import com.ruoyi.web.controller.system.SysLoginController;
import com.ruoyi.web.controller.tool.PcUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: App入口
 * @Author: jiang chao
 * @Create: 2021-11-12 13:04
 **/
@Controller
public class H5IndexController extends BaseController {

    @Autowired
    private IZzTokenService zzTokenService;
    @Autowired
    private SysPasswordService passwordService;
    @Autowired
    private ISysUserService userService;

    @Autowired
    private IZzStudentinfoService zzStudentinfoService;

    @Autowired
    private IZzTeacherinfoService zzTeacherinfoService;
    @Autowired
    private IZzTeachClassService zzTeachClassService;
    @Autowired
    private IZzVacationService zzVacationService;
    @Autowired
    private ISysMenuService sysMenuService;
    @Autowired
    private IZzClassinfoService zzClassinfoService;

    @Autowired
    private HttpServletRequest request;
    private String prefix = "kqgl/h5";

    public ModelAndView toLogin(JSONObject jsonArray, ModelMap map, String accessToken, String index) {
        String userHead = jsonArray.getString("userHead");
        String userName = jsonArray.getString("personName");//personName?userName
        String userNo = jsonArray.getString("userNo");
        map.addAttribute("userNo", userNo);
        map.addAttribute("accessToken", accessToken);
        JSONArray userOrg = (JSONArray) JSONArray.parse(jsonArray.getString("userOrg"));
        if (userOrg.size() > 0) {

            for(int o=0;o<userOrg.size();o++){
                JSONObject jsonObjectzz = (JSONObject) userOrg.get(o);
                String state = jsonObjectzz.getString("state");
                if(state.equals("1")){
                    String orgName = jsonObjectzz.getString("orgName");
                    map.addAttribute("orgName", orgName);

                    String identityId = jsonObjectzz.getString("identityId");
                    if (identityId.equals("101")) {//教师
                        ZzTeacherinfo zzTeacherinfo = new ZzTeacherinfo();
                        zzTeacherinfo.setUserNo(userNo);
                        List<ZzTeacherinfo> zzTeacherinfoList = zzTeacherinfoService.selectZzTeacherinfoList(zzTeacherinfo);
                        if (zzTeacherinfoList.size() > 0) {
                            Long TeacherId = zzTeacherinfoList.get(0).getId();
                            ZzTeachClass zzTeachClass = new ZzTeachClass();
                            zzTeachClass.setTeacherId(TeacherId + "");
                            List<ZzTeachClass> zzTeachClassList = zzTeachClassService.selectZzTeachClassList(zzTeachClass);
                            map.addAttribute("TeacherId", TeacherId);
                            if (zzTeachClassList.size() > 0) {
                                String classId = zzTeachClassList.get(0).getClassId();
                                String className = zzTeachClassList.get(0).getClassName();
                                map.addAttribute("classId", classId);
                                map.addAttribute("className", className);
                            }
                        }
                    }

                    if (identityId.equals("102")) {
                        //学生
                        // 查询学生表，获取学生信息，再根据班级id查询班级信息，最后查询请假信息
                        String className = jsonArray.getString("className");
                        map.addAttribute("className", className);

                        ZzStudentinfo zzStudentinfo = new ZzStudentinfo();
                        zzStudentinfo.setUserNo(userNo);
                        List<ZzStudentinfo> zzStudentinfoList = zzStudentinfoService.selectZzStudentinfoList(zzStudentinfo);
                        if (zzStudentinfoList.size() > 0) {
                            String stuId = zzStudentinfoList.get(0).getId() + "";
                            map = getqjljTime(map, stuId);
                        }
                        index = "/kqgl/h5/qingjia";
                    }
                }
            }
        }
        map.addAttribute("token", accessToken);
        map.addAttribute("userHead", userHead);
        map.addAttribute("userName", userName);

        SysLoginController sysLoginController = new SysLoginController();
//        SysUser sysUser = new SysUser();
//        sysUser.setLoginName(userNo);
//        sysUser.setStatus("0");
        SysUser userList = userService.selectUserByLoginName(userNo);
        boolean boo = true;
        if (userList != null && userList.getStatus().equals("0")) {
            boo = false;
        }

        if (boo == true) {
            SysUser sysUsernew = new SysUser();
            sysUsernew.setLoginName(userNo);
            sysUsernew.setUserName(userName);
            sysUsernew.setSalt(ShiroUtils.randomSalt());
            sysUsernew.setPassword(userNo + "@Ptuser");
            sysUsernew.setCreateBy("admin");
            sysUsernew.setPassword(passwordService.encryptPassword(
                    sysUsernew.getLoginName(), sysUsernew.getPassword(), sysUsernew.getSalt()));
            int xx = userService.insertUser(sysUsernew);

            if (userOrg.size() > 0) {
                JSONObject jsonObjectzz = (JSONObject) userOrg.get(0);
                String identityId = jsonObjectzz.getString("identityId");
                if (identityId.equals("101")) {//教师
                    Long[] roleIds = {2L};
                    userService.insertUserAuth(sysUsernew.getUserId(), roleIds);
                } else if (identityId.equals("102")) {//教师
                    Long[] roleIds = {4L};
                    userService.insertUserAuth(sysUsernew.getUserId(), roleIds);
                }
            }
            System.err.println("新增用户");
            sysLoginController.ajaxLogin(userNo, userNo, false);

        } else {
            AjaxResult s = sysLoginController.ajaxLogin(userNo, userNo + "@Ptuser", false);
        }

        //查询用户的权限
        List<SysMenu> sysMenu=sysMenuService.selectMenuList(new SysMenu(),userList.getUserId());
        int[] boo2={0,0};
        for(SysMenu sysMenu1:sysMenu){
            if(sysMenu1.getUrl().equals("/h5/qxkqtj")){
                boo2[0]=1;
            }
            if(sysMenu1.getUrl().equals("/h5/Kqtb")){
                boo2[1]=1;
            }
        }
        map.addAttribute("boo2", boo2[0]+","+boo2[1]);

        ZzToken zzToken = new ZzToken();
        zzToken.setUserId(Long.parseLong(userNo));
        List<ZzToken> zzTokenList = zzTokenService.selectZzTokenList(zzToken);

        if (zzTokenList.size() > 0) {
            zzToken.setId(zzTokenList.get(0).getId());
            zzToken.setAccessToken(accessToken);
            zzTokenService.updateZzToken(zzToken);
        } else {
            zzToken.setAccessToken(accessToken);
            zzTokenService.insertZzToken(zzToken);
        }

        ModelAndView view = new ModelAndView(index, map);
        return view;
    }


    public ModelMap getqjljTime(ModelMap map, String stuId) {
        ZzVacation zzVacation = new ZzVacation();
        zzVacation.setStudentId(stuId + "");
        zzVacation.setStatus("1");
        zzVacation.setSqStatus("1");//批准的才算
        List<ZzVacation> zzVacationList = zzVacationService.selectZzVacationList31(zzVacation);
        //查询近31天内的请假记录，审批通过的

        Calendar calendar = Calendar.getInstance();
        int weeknow = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int monthnow = calendar.get(Calendar.DAY_OF_MONTH);

        Long months = 0L;
        Long benweek = 0L;
        Long benmonth = 0L;

        int zz = 0;
        for (ZzVacation zzVacation1 : zzVacationList) {
            if (zz == 0) {

            }
            zz++;
            Date sqTime = zzVacation1.getSqTime();
            Date now = new Date();
            Long starTime = sqTime.getTime();
            Long endTime = now.getTime();
            Long num = endTime - starTime;//时间戳相差的毫秒数

            Long mm = 30 * 24 * 60 * 60 * 1000L;
            if (num <= mm) {
                months = months + (zzVacation1.getEndTime().getTime() - zzVacation1.getStartTime().getTime()) / 1000;
            }
            if (num <= weeknow * 24 * 60 * 60 * 1000) {
                benweek = benweek + (zzVacation1.getEndTime().getTime() - zzVacation1.getStartTime().getTime()) / 1000;
            }
            if (num <= monthnow * 24 * 60 * 60 * 1000) {
                benmonth = benmonth + (zzVacation1.getEndTime().getTime() - zzVacation1.getStartTime().getTime()) / 1000;
            }


            Long time = (zzVacation1.getEndTime().getTime() - zzVacation1.getStartTime().getTime()) / 1000;
            Long td = time / 86400;  //天  24*60*60*1000
            Long th = time / 3600 - 24 * td;  //天  24*60*60*1000
            if (time % 60 > 0) {
                th = th + 1;
            }
            if (zzVacation1.getQjStatus()!=null&&!zzVacation1.getQjStatus().equals("2")) {
                map.addAttribute("qj_id", zzVacation1.getId());
                map.addAttribute("qj_day", td);
                map.addAttribute("qj_hour", th);
                map.addAttribute("oldStartTime", zzVacation1.getStartTime());
                map.addAttribute("oldEndTime", zzVacation1.getEndTime());
                map.addAttribute("qjStatus", zzVacation1.getQjStatus());
                map.addAttribute("sqStatus", zzVacation1.getSqStatus());
                map.addAttribute("qjType", zzVacation1.getQjType());
                map.addAttribute("qjReason", zzVacation1.getQjReason());
            }
        }

        Long d1 = months / 86400;  //天  24*60*60*1000
        Long h1 = months / 3600 - 24 * d1;  //天  24*60*60*1000
        if (months % 60 > 0) {
            h1 = h1 + 1;
        }
        if (h1 < 0) {
            h1 = 0L;
        }

        Long d2 = benweek / 86400;  //天  24*60*60*1000
        Long h2 = benweek / 3600 - 24 * d1;  //天  24*60*60*1000
        if (benweek % 60 > 0) {
            h2 = h2 + 1;
        }
        if (h2 < 0) {
            h2 = 0L;
        }
        Long d3 = benmonth / 86400;  //天  24*60*60*1000
        Long h3 = benmonth / 3600 - 24 * d1;  //天  24*60*60*1000
        if (benmonth % 60 > 0) {
            h3 = h3 + 1;
        }
        if (h3 < 0) {
            h3 = 0L;
        }
        map.addAttribute("months", d1 + "天" + h1 + "小时");
        map.addAttribute("benweek", d2 + "天" + h2 + "小时");
        map.addAttribute("benmonth", d3 + "天" + h3 + "小时");
        map.addAttribute("stuId", stuId);

        map.addAttribute("ZzVacations", zzVacationList.size());

        //单独查询当前是否有已提交未审核的请假数据
        zzVacation.setSqStatus("0");//批准的才算
        List<ZzVacation> zzVacationOne = zzVacationService.selectZzVacationList(zzVacation);
        if (zzVacationOne.size() > 0) {
            Long time = (zzVacationOne.get(0).getEndTime().getTime() - zzVacationOne.get(0).getStartTime().getTime()) / 1000;
            Long td = time / 86400;  //天  24*60*60*1000
            Long th = time / 3600 - 24 * td;  //天  24*60*60*1000
            if (time % 60 > 0) {
                th = th + 1;
            }
            map.addAttribute("qj_id", zzVacationOne.get(0).getId());
            map.addAttribute("qj_day", td);
            map.addAttribute("qj_hour", th);
            map.addAttribute("oldStartTime", zzVacationOne.get(0).getStartTime());
            map.addAttribute("oldEndTime", zzVacationOne.get(0).getEndTime());
            map.addAttribute("qjStatus", zzVacationOne.get(0).getQjStatus());
            map.addAttribute("sqStatus", zzVacationOne.get(0).getSqStatus());
            map.addAttribute("qjType", zzVacationOne.get(0).getQjType());
            map.addAttribute("qjReason", zzVacationOne.get(0).getQjReason());
        }
        return map;
    }

    @GetMapping(value = "/h5/toqjgl")
    public ModelAndView toqjgl(String stuId, ModelMap map, String className, String accessToken) {
        map.addAttribute("stuId", stuId);
        map = getqjljTime(map, stuId);
        ZzStudentinfo zzStudentinfo = zzStudentinfoService.selectZzStudentinfoById(Long.parseLong(stuId));
        map.addAttribute("userName", zzStudentinfo.getPersonName());
        map.addAttribute("className", className);
        map.addAttribute("accessToken", accessToken);
        ModelAndView view = new ModelAndView(prefix + "/qingjia");
        return view;
    }

    @GetMapping(value = "/h5/qxkqtj")
    public ModelAndView qxkqtj(ModelMap map) {
        ModelAndView view = new ModelAndView(prefix + "/qxkqtj");
        return view;
    }

    @GetMapping(value = "/h5/index")
    public ModelAndView index(HttpServletRequest request, String accessToken, ModelMap map) {
        //JSONObject jsonObjects = ZPTinterface.getStudent(accessToken,"10039578");
        //JSONObject jsonObject2 = ZPTinterface.getStudent2(accessToken,"10010308");
        //JSONObject jsonObject2 = ZPTinterface.getTeacher(accessToken,"10010308");
        //JSONObject jsonObject2 = ZPTinterface.getGrade(accessToken,"10010308");
        //JSONObject jsonObject3 = ZPTinterface.getClass(accessToken,"10010308");
        //JSONObject TBstu = ZPTinterface.TBstu(accessToken,"10010308");
        JSONObject jsonObject = ZPTinterface.getUserinfo(accessToken);
        String resultCode = jsonObject.getString("code");//0000
        System.err.println(jsonObject.toString());
        if (resultCode.equals("2000")) {
            JSONObject jsonArray = JSONObject.parseObject(jsonObject.getString("data"));
            ModelAndView view = toLogin(jsonArray, map, accessToken, prefix + "/student");
            return view;
        } else {
            return new ModelAndView(prefix + "/error");
        }
    }


    @GetMapping(value = "/loginbyno")
    public ModelAndView loginbyno(String userNo, ModelMap map) {
        SysUser sysUser = userService.selectUserByLoginName(userNo);
        if (sysUser != null) {
            SysLoginController sysLoginController = new SysLoginController();
            AjaxResult s = sysLoginController.ajaxLogin(userNo, userNo + "@Ptuser", false);
            return new ModelAndView("index");
        } else {
            return new ModelAndView("/error/unauth");
        }
    }

    @GetMapping("/logins")
    public String login(String userNo, String accessToken, ModelMap mmap) {
        mmap.put("userNo", userNo);
        mmap.put("accessToken", accessToken);
        return "logins";
    }


    @GetMapping(value = "/h5/kqtb")
    public ModelAndView kqtj(String accessToken, ModelMap map) {
        JSONObject jsonObject = ZPTinterface.getUserinfo(accessToken);
        String resultCode = jsonObject.getString("code");//0000
        System.err.println(jsonObject.toString());
        if (resultCode.equals("2000")) {
            JSONObject jsonArray = JSONObject.parseObject(jsonObject.getString("data"));
            ModelAndView view = toLogin(jsonArray, map, accessToken, prefix + "/kqtb");
            return view;
        } else {
            return new ModelAndView(prefix + "/error");
        }
    }

    @GetMapping(value = "/h5/Kqtb")
    public ModelAndView Kqtb() {
        ModelAndView view = new ModelAndView(prefix + "/kqtb");
        return view;
    }

    @GetMapping(value = "/h5/Kqhzb")
    public ModelAndView Kqhzb(String classId, ModelMap map) {
        ZzClassinfo zzClassinfo = new ZzClassinfo();
        zzClassinfo.setClassId(classId);
        List<ZzClassinfo> zzClassinfoList = zzClassinfoService.selectZzClassinfoList(zzClassinfo);

        if (zzClassinfoList.size() > 0) {
            map.addAttribute("className", zzClassinfoList.get(0).getClassNickname());
            //            map.addAttribute("className", zzClassinfoList.get(0).getClassNickname() +
            //                    zzClassinfoList.get(0).getGradeName() +
            //                    zzClassinfoList.get(0).getClassName());
            //        }
        }
        ModelAndView view = new ModelAndView(prefix + "/kqhzb", map);
        return view;
    }

    @GetMapping(value = "/h5/Index")
    public ModelAndView Index() {
        ModelAndView view = new ModelAndView(prefix + "/index");
        return view;
    }

    @GetMapping(value = "/h5/kqgl")
    public ModelAndView kqgl(ModelMap map) {
        ZzToken zzToken = new ZzToken();
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        zzToken.setUserId(Long.parseLong(sysUser.getLoginName()));
        List<ZzToken> list = zzTokenService.selectZzTokenList(zzToken);

        if (list.size() > 0) {
            String accessToken = list.get(0).getAccessToken();
            JSONObject jsonObject = ZPTinterface.getUserinfo(accessToken);
            String resultCode = jsonObject.getString("code");//0000
            System.err.println(jsonObject.toString());
            if (resultCode.equals("2000")) {
                JSONObject jsonArray = JSONObject.parseObject(jsonObject.getString("data"));
                ModelAndView view = toLogin(jsonArray, map, accessToken, prefix + "/student");
                return view;
            } else {
                return new ModelAndView(prefix + "/error");
            }
        } else {
            return new ModelAndView(prefix + "/error");
        }


    }

    @GetMapping(value = "/h5/qjgl")
    public ModelAndView qjgl() {
        ModelAndView view = new ModelAndView(prefix + "/qingjia");
        return view;
    }

    @GetMapping(value = "/h5/qjlist")
    public ModelAndView qjlist(String studentIds, String className, ModelMap map, String accessToken) {
        map.addAttribute("studentIds", studentIds);
        map.addAttribute("className", className);
        map.addAttribute("accessToken", accessToken);
        ModelAndView view = new ModelAndView(prefix + "/qjlist");
        return view;
    }

    @GetMapping(value = "/zykscx/index")
    public String zykscx() {
        //System.err.println("====yk/kccx");
        boolean isMoblie = PcUtils.JudgeIsMoblie(request);
        if (isMoblie) {
            return "newzyksh5";//手机端
        } else {
            return "newzyks";
        }
    }


    @GetMapping(value = "/zykscx/2index")
    public String zykscx2() {
        //System.err.println("====yk/kccx");
        boolean isMoblie = PcUtils.JudgeIsMoblie(request);
        if (isMoblie) {
            return "newzyksh5";//手机端
        } else {
            return "newzyks";
        }
    }

    @GetMapping(value = "/zykscx/2zkds")
    public String zkds2() {
        //System.err.println("====yk/kccx");
        boolean isMoblie = PcUtils.JudgeIsMoblie(request);
        if (isMoblie) {
            return "newzkdsh5";//手机端
        } else {
            return "newzkds";
        }
    }

    @GetMapping(value = "/zykscx/zkds")
    public String zkds() {
        //System.err.println("====yk/kccx");
        boolean isMoblie = PcUtils.JudgeIsMoblie(request);
        if (isMoblie) {
            return "newzkdsh5";//手机端
        } else {
            return "newzkds";
        }
    }


    @GetMapping(value = "/zykscx/h5index")
    public String h5zykscx() {
        return "/newzyksh5";
    }

    @GetMapping(value = "/zykscx/zkdsh5")
    public String zkdsh5() {
        return "/newzkdsh5";
    }


    @GetMapping(value = "/h5/search")
    public String search() {
        return prefix + "/search";
    }

    @GetMapping(value = "/h5/detail")
    public ModelAndView detail(String peopleid, ModelMap map) {
        map.addAttribute("peopleid", peopleid);
        ModelAndView view = new ModelAndView(prefix + "/detail", map);
        return view;
    }

    //    @GetMapping(value = "/h5/gzcxindex")
    @GetMapping(value = "/h5/dzgzzhindex")
    public ModelAndView dzgzzhindex() {
        ModelAndView view = new ModelAndView(prefix + "/index");
        return view;
    }

    @GetMapping(value = "/h5/index2")
    public ModelAndView index2() {
        ModelAndView view = new ModelAndView(prefix + "/index");
        return view;
    }

    @GetMapping(value = "/h5/echarts")
    public ModelAndView echarts() {
        ModelAndView view = new ModelAndView(prefix + "/echarts");
        return view;
    }
}
