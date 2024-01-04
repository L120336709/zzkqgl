//package com.ruoyi.web.controller.tool;
//
//import com.ruoyi.system.domain.Subjecttable;
//import com.ruoyi.system.domain.XydnExamname;
//import com.ruoyi.system.domain.XydnSubject;
//import com.ruoyi.system.service.ISubjecttableService;
//import com.ruoyi.system.service.IXydnExamnameService;
//import com.ruoyi.system.service.IXydnSubjectService;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.Row;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.*;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/value")
//public class DownExcel {
//
//    @Autowired
//    private IXydnExamnameService xydnExamnameService;
//    @Autowired
//    private IXydnSubjectService xydnSubjectService;
//
//    @Autowired
//    private ISubjecttableService subjecttableService;
//
//    @GetMapping("/importTemplate")
//    @ResponseBody
//    public void downloadEQCSModel(HttpServletRequest request, HttpServletResponse response,
//                                  HttpSession session, String examid) throws IOException {
//
//        System.err.println("exami="+examid);
//
//        XydnExamname xydnExamname=xydnExamnameService.selectXydnExamnameById(examid);
//        System.err.println("xydnExamname="+xydnExamname);
//        String file_name =  xydnExamname.getExamName()+".xlsx"; //要下载的文件名
//        if (file_name == null) {
//            file_name = "";
//        }
//
//        response.setContentType("application/octet-stream");
//        if (request.getHeader("user-agent").toLowerCase().indexOf("firefox") > -1) {
//            //火狐浏览器自己会对URL进行一次URL转码所以区别处理
//            response.setHeader("Content-Disposition", "attachment;filename="
//                    + new String(file_name.getBytes("utf-8"), "ISO-8859-1"));
//        } else {
//            response.setHeader("Content-Disposition", "attachment;filename="
//                    + URLEncoder.encode(file_name, "utf-8"));
//        }
//        //新建文件输入输出流
//        OutputStream output = null;
//        FileInputStream fis = null;
//
//        XydnSubject xydnSubject=new XydnSubject();
//        xydnSubject.setExamId(examid);
//
//        List<XydnSubject> xydnSubjectList=xydnSubjectService.selectXydnSubjectList(xydnSubject);
//        try {
//            String[] sqlColumn = new String[xydnSubjectList.size()+6];
//            sqlColumn[0]="examname";
//            sqlColumn[1]="username";
//            sqlColumn[2]="idcard";
//            sqlColumn[3]="danwei";
//            sqlColumn[4]="rekeasetime";
//            sqlColumn[5]="pingjia";
//
//            String[] sqlColumnName = new String[xydnSubjectList.size()+6];
//            sqlColumnName[0]="考试名称";
//            sqlColumnName[1]="姓名";
//            sqlColumnName[2]="身份证";
//            sqlColumnName[3]="单位或学校";
//            sqlColumnName[4]="成绩发布日期(年/月/日 时:分:秒)";
//            sqlColumnName[5]="考试评价";
//
//
//            for(int i=0;i<xydnSubjectList.size();i++){
//                String subjectid=xydnSubjectList.get(i).getSubjectId();
//                sqlColumn[i+6]=subjectid;
//                //根据科目id，获取对应科目名称
//
//                Subjecttable subjecttable=subjecttableService.selectSubjecttableBySubjectid(Long.valueOf(subjectid));
//                if(subjecttable!=null){
//                    sqlColumnName[i+6]=subjecttable.getSubjectname();
//                }
//            }
//
//            //考试名称、发布日期、姓名、身份证、单位/学校 考试评价、每个科目
//           // String[] sqlColumn = {"id", "examId", "examName", "changeAchie", "age"};
//            //String[] sqlColumnName = {"人员编号", "人员姓名", "登录密码", "注册时间222", "测试222"};
//
//            // 将此方法提取出去，可以生成一个util工具类
//            @SuppressWarnings("resource")
//            HSSFWorkbook wb = new HSSFWorkbook();
//            // 创建工作表
//            HSSFSheet sheet = wb.createSheet();
//
//            // 创建行
//            Row row = sheet.createRow(0);
//
//            // 创建样式
//            HSSFCellStyle style = wb.createCellStyle();
//            // style.setDataFormat(format.getFormat("@"));
//            // 居中格式
//            style.setAlignment(HorizontalAlignment.CENTER);
//
//            // 创建单元格（生成动态的表头）,且让各表头居中显示
//            // Cell cell=row.createCell(0);
//            Cell cell = null;
//            for (int i = 0; i < sqlColumn.length; i++) {
//                // 创建传入进来的表头的个数
//                cell = row.createCell((short) i);
//                // 表头的值就是传入进来的值
//                cell.setCellValue(sqlColumnName[i]);
//                sheet.setColumnWidth(i, 20 * 200);// 设置列宽
//                cell.setCellStyle(style);
//            }
//            output = response.getOutputStream();//新建文件输入输出流对象
//            wb.write(output);
//            output.flush();
//            System.err.println("exami3333="+examid);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null) {
//                fis.close();
//                fis = null;
//            }
//            if (output != null) {
//                output.close();
//                output = null;
//            }
//        }
//    }
//
//
//    public static Map<Integer,List<String>> initType(InputStream inputStream,String examIdName) {
//        Map<Integer,List<String>> examdata=new HashMap<>();
//        try {
//            //1 获取excel文件流   excel  xls 文件   暂不支持xlsx
////            if (path.contains("xlsx") || path.contains("XLSX")) {
////                System.err.println("请使用xls格式文件");
////                return;
////            }
//            //ExcelUtil<XydnExamname> util = new ExcelUtil<XydnExamname>(XydnExamname.class);
//           // List<XydnExamname> xydnExamnameList = util.importExcel(inputStream);
//
//
//            POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
//            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
//            //2 获取sheet 列数
//            int sheets = workbook.getNumberOfSheets();
//            //3 遍历所有sheet列
//            for (int i = 0; i < sheets; i++) {
//                //获取sheet
//                HSSFSheet sheet = workbook.getSheetAt(i);
//                //读取第一行
//                HSSFRow headerRow = sheet.getRow(0);
//                //获取sheet    所有行数
//                int rows = sheet.getPhysicalNumberOfRows();
//                List<String> headerColumns = new ArrayList<>();
//                for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {
//                    HSSFCell cell = headerRow.getCell(j);
//                    cell.getStringCellValue();
//                    headerColumns.add(cell.getStringCellValue());
//                }
////                for(String ss:headerColumns){
////                    System.err.println(ss);
////                }
//                //获取类型
//                HSSFRow secondRow = sheet.getRow(0);
//
//                int cells = secondRow.getPhysicalNumberOfCells();
//
//                String nameid=null;
//                int m=0;
//                for (int j = 0; j < rows; j++) {
//                    HSSFRow row = sheet.getRow(j);
//                    List<String> cellsValue = new ArrayList<>();
//                    //获取字段属性
//                    boolean boo=false;
//                    for (int k = 0; k < cells; k++) {
//                        //获取单元格
//                        HSSFCell cell = row.getCell(k);
//
//                        if(cell==null){
//                            cell = row.createCell(k);
//                        }
//
//                        //设置单元格类型
//                        cell.setCellType(CellType.STRING);
//
//                        //获取单元格数据
//                        String cellValue = cell.getStringCellValue();
//
//                        cellsValue.add(cellValue);
//
//                        if(cellValue.equals(examIdName)){
//                            boo=true;
//                        }
//
//                    }
//                    if(j == 0){
//                        boo=true;
//                    }
//                    if(boo){
//                        examdata.put(m,cellsValue);
//                        m++;
//                    }
//
////                    for(String ss:cellsValue){
////                        System.err.println(ss);
////                    }
//                }
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return examdata;
//    }
//}
