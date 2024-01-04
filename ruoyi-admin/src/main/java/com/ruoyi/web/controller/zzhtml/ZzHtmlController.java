//package com.ruoyi.web.controller.zzhtml;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ruoyi.common.core.domain.AjaxResult;
//import com.ruoyi.common.core.domain.entity.SysUser;
//import com.ruoyi.common.utils.ShiroUtils;
//import com.ruoyi.framework.shiro.service.SysPasswordService;
//import com.ruoyi.system.domain.ZzToken;
//import com.ruoyi.system.service.ISysUserService;
//import com.ruoyi.system.service.IZzTokenService;
//import com.ruoyi.web.controller.system.SysLoginController;
//import com.ruoyi.web.controller.tool.PcUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//@RequestMapping("/zzhtml")
//public class ZzHtmlController {
//    @Autowired
//    private SysPasswordService passwordService;
//    @Autowired
//    private ISysUserService userService;
//    @Autowired
//    private IZzTokenService zzTokenService;
//
//
//    public ModelAndView toLogin(JSONObject jsonArray,ModelMap map,String accessToken,String index){
//        String userHead = jsonArray.getString("userHead");
//        String userName = jsonArray.getString("personName");//personName?userName
//
//        JSONArray userOrg = (JSONArray) JSONArray.parse(jsonArray.getString("userOrg"));
//        if (userOrg.size() > 0) {
//            JSONObject jsonObjectzz = (JSONObject) userOrg.get(0);
//            String orgName = jsonObjectzz.getString("orgName");
//            map.addAttribute("orgName", orgName);
//        }
//        map.addAttribute("token", accessToken);
//        map.addAttribute("userHead", userHead);
//        map.addAttribute("userName", userName);
//        String userNo = jsonArray.getString("userNo");
//
//
//        String password = jsonArray.getString("password");
//        SysLoginController sysLoginController = new SysLoginController();
////        SysUser sysUser=new SysUser();
////        sysUser.setUserName(userNo);
////        List<SysUser> userList=userService.selectUserList(sysUser);
////        if(userList.size()==1){
////        }
//
//        AjaxResult s = sysLoginController.ajaxLogin(userNo, userNo+"@Ptuser", false);
//        //该用户没有信息，新增用户
//        if (s.get("code").toString().equals("500")) {
//            SysUser sysUsernew = new SysUser();
//            sysUsernew.setLoginName(userNo);
//            sysUsernew.setUserName(userName);
//            sysUsernew.setSalt(ShiroUtils.randomSalt());
//            sysUsernew.setPassword(userNo+"@Ptuser");
//            sysUsernew.setCreateBy("admin");
//            sysUsernew.setPassword(passwordService.encryptPassword(
//                    sysUsernew.getLoginName(), sysUsernew.getPassword(), sysUsernew.getSalt()));
//            int xx = userService.insertUser(sysUsernew);
//            if (xx == 1) {
//                System.err.println("新增用户");
//                s = sysLoginController.ajaxLogin(userNo, userNo, false);
//            } else {
//            }
//        }
//        ZzToken zzToken=new ZzToken();
//        zzToken.setUserId(Long.parseLong(userNo));
//        List<ZzToken> zzTokenList=zzTokenService.selectZzTokenList(zzToken);
//
//        if(zzTokenList.size()>0){
//            zzToken.setId(zzTokenList.get(0).getId());
//            zzToken.setAccessToken(accessToken);
//            zzTokenService.updateZzToken(zzToken);
//        }else {
//            zzToken.setAccessToken(accessToken);
//            zzTokenService.insertZzToken(zzToken);
//        }
//
//        ModelAndView view = new ModelAndView(index , map);
//        return view;
//    }
//
//    @GetMapping("/login")
//    public ModelAndView zzhtmlLogin(HttpServletRequest request, String accessToken, ModelMap map) {
//        boolean isMoblie = PcUtils.JudgeIsMoblie(request);
//        if (isMoblie) {
//            JSONObject jsonObject = ZPTinterface.getUserinfo(accessToken);
//            String resultCode = jsonObject.getString("code");//0000
//            if (resultCode.equals("2000")) {
//                JSONObject jsonArray = JSONObject.parseObject(jsonObject.getString("data"));
//                ModelAndView view =toLogin(jsonArray,map,accessToken, "/xydn/h5/index");
//                return view;
//            } else {
//                ModelAndView view = new ModelAndView("/error/unauth", map);
//                return view;
//            }
//        } else {
//            ModelAndView view = new ModelAndView("/zzhtml/login", map);
//            return view;
//        }
//    }
//
//    @GetMapping("/login2")
//    public ModelAndView zzhtmlLogin2(HttpServletRequest request, String accessToken, ModelMap map) {
//        ModelAndView view = new ModelAndView("/xydn/h5/index", map);
//        return view;
//    }
//
//
//    @GetMapping("/yygl")
//    public ModelAndView yygl(String accessToken, ModelMap map)
//    {
//        JSONObject jsonObject = ZPTinterface.getUserinfo(accessToken);
//        String resultCode = jsonObject.getString("code");//0000
//        if (resultCode.equals("2000")) {
//            JSONObject jsonArray = JSONObject.parseObject(jsonObject.getString("data"));
//            ModelAndView view =toLogin(jsonArray,map,accessToken, "/zzhtml/h5/yygl");
//            return view;
//        } else {
//            ModelAndView view = new ModelAndView("/error/unauth", map);
//            return view;
//        }
//    }
//
//    /**
//     * 获取最新通知
//     *
//     * @param u
//     * @return
//     */
//    @PostMapping("/getMessage")
//    @ResponseBody
//    public ArrayList<String> getMessage(String u) {
//        ArrayList<String> strings = new ArrayList<>();
//
//        String orgId="10010308";
//        String nodeID = "";
//        String url = "newsList";
//        if (u.equals("1")) {
//            nodeID = "4960";
//        } else if (u.equals("2")) {
//            nodeID = "86";
//        } else if (u.equals("3")) {
//            nodeID = "5261";
//        } else if (u.equals("4")) {
//            url = "articleList";
//            nodeID = "82";
//        }
//        JSONObject jsonObject = ApiController.getMessage(url, orgId, nodeID, 1, 5);
//        String resultCode = jsonObject.getString("code");//0000
//
//        if (resultCode.equals("200")) {
//            JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject.getString("data"));
//            if(u.equals("4")){
//                for (int i = 0; i < jsonArray.size(); i++) {
//                    JSONObject jsonObjects = (JSONObject) jsonArray.get(i);
//                    String title = jsonObjects.getString("articleName");
//                    String created = jsonObjects.getString("createTime");
//                    String articleContent = jsonObjects.getString("articleContent");
//                    strings.add(title + "|||" + created.substring(0, 10)+"|||"+articleContent);
//                }
//                if(jsonArray.size()==0){
//                    for (int i = 0; i < 5; i++) {
//                        String title = "暂无数据，呵护眼睛 预防近视 主题系列教育活动";
//                        String created =  "2023-06-06";
//                        String articleContent = "<p>暂无数据,呵护眼睛 预防近视 主题系列教育活动</p>";
//                        strings.add(title + "|||" + created.substring(0, 10)+"|||"+articleContent);
//                    }
//                }
//            }else {
//                for (int i = 0; i < jsonArray.size(); i++) {
//                    JSONObject jsonObjects = (JSONObject) jsonArray.get(i);
//                    String title = jsonObjects.getString("title");
//                    String created = jsonObjects.getString("created");
//                    String content = jsonObjects.getString("content");
//                    strings.add(title + "|||" + created.substring(0, 10)+"|||"+content);
//                }
//                if(jsonArray.size()==0){
//                    if(u.equals("1")){
//                        for (int i = 0; i < 5; i++) {
//                            String title = "暂无数据，恩施市中等职业技术学校百日誓师大会";
//                            String created =  "2023-06-06";
//                            String content = "<p>暂无数据,小手牵大手、共建美丽校园</p>";
//                            strings.add(title + "|||" + created.substring(0, 10)+"|||"+content);
//                        }
//                    }else  if(u.equals("2")){
//                        for (int i = 0; i < 5; i++) {
//                            String title = "暂无数据，小手牵大手、共建美丽校园";
//                            String created =  "2023-06-06";
//                            String content = "<p>暂无数据,小手牵大手、共建美丽校园</p>";
//                            strings.add(title + "|||" + created.substring(0, 10)+"|||"+content);
//                        }
//                    }else  if(u.equals("3")){
//                        for (int i = 0; i < 5; i++) {
//                            String title = "暂无数据，为学生打开思绪飞翔的窗口";
//                            String created =  "2023-06-06";
//                            String content = "<p>暂无数据,为学生打开思绪飞翔的窗口</p>";
//                            strings.add(title + "|||" + created.substring(0, 10)+"|||"+content);
//                        }
//                    }
//
//                }
//            }
//        }
//
//
//        return strings;
//    }
//
//
//    /**
//     * 获取最新3张图片
//     *
//     * @return
//     */
//    @PostMapping("/getPicList")
//    @ResponseBody
//    public ArrayList<String> getPicList() {
//        ArrayList<String> strings = new ArrayList<>();
//        JSONObject jsonObject = ApiController.getMessage("picList", "10010308", "84", 1, 3);
//        String resultCode = jsonObject.getString("code");//0000
//        if (resultCode.equals("200")) {
//            JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject.getString("data"));
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject jsonObjects = (JSONObject) jsonArray.get(i);
//                String title = jsonObjects.getString("title");
//                String url = jsonObjects.getString("url");
//                strings.add(title + "__" + url);
//            }
//            if(jsonArray.size()==0){
//                for (int i = 0; i < 3; i++) {
//                    String title = "暂无数据";
//                    String url = "/img/background.png";
//                    strings.add(title + "__" + url);
//                }
//            }
//        }
//        return strings;
//    }
//
//
//}
