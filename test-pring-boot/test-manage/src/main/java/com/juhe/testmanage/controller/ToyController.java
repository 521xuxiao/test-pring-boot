package com.juhe.testmanage.controller;

import com.juhe.testcommon.pojo.ReturnData;
import com.juhe.testmanage.service.ToyService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@ResponseBody
@Controller
@RequestMapping("/juhe/toy")
public class ToyController {
    @Value("${image.localPath}")
    private String imgPath;
    @Autowired
    private ToyService toyService;

    /**
     * 新增toy数据
     *
     * @param {boyId: '',  areaId:'', time:'非必传参', imgUrl:'非必传参'}
     * @return
     */
    @PostMapping("/addToy")
    public ReturnData addToy(@RequestBody Map<String, Object> params) {
        toyService.addToy(params);
        return new ReturnData("新增成功");
    }

    /**
     * 上传图片
		一共修改三处   1, 2, 3
     */
    @RequestMapping(value="/uploadPicture")
    public ReturnData uploadPicture(MultipartFile file, HttpServletRequest request) {
        ReturnData returndata = new ReturnData();
        if(file.isEmpty()) {
            returndata.setSuccess(false);
            returndata.setResultMessage("文件为空");
            return returndata;
        }
        String originalname = file.getOriginalFilename();
        String suffixname = originalname.substring(originalname.lastIndexOf("."));
        String path = imagePath;          //1.文件上传到服务器上面的路径             
        UUID id = UUID.randomUUID();
        String filename = id+suffixname;
        String fullpath = path+filename;
        String requestpath = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + requestpath;
        File dest = new File(fullpath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        String imgName = request.getParameter("imgName"); //  全段传过来的只是图片的名字
        if(!(imgName==null || "".equals(imgName))) {     // 2, 删除已经存在的图片
            String fullpathDelete = imagePath + imgName;   // 拼成完整的图片路径(本服务器本地的存放文件的路径)
            File files = new File(fullpathDelete);        //  
            files.delete();                               //  删除文件必须是服务器本地的存储路径
        }
        try {
            file.transferTo(dest);
            String filepath = basePath+"/ERP/uploadPicture/"+filename;   //   3, 修改回显的图片路径,和接口保持一致,映射图片的myWebConfig配置类也要改
            Map<String,Object> map = new HashMap<>();
            map.put("filename", filename);
            map.put("filepath", filepath);
            map.put("oldname", originalname);
            returndata.setResult(map);
            returndata.setSuccess(true);
            returndata.setResultMessage("success");
        } catch (IOException e) {
            e.printStackTrace();
            returndata.setSuccess(false);
            returndata.setResultMessage("系统异常");
        }
        return returndata;
    }

    /**
     * 删除图片
     */
    @GetMapping("/deleteImg")
    public ReturnData deleteImg(String imgName) {
        ReturnData returndata = new ReturnData();
        try {
            String fullpath = imgPath + "/" + imgName;
            File file = new File(fullpath);
            if (file.delete()) {
                returndata.setSuccess(true);
                returndata.setResultMessage("删除成功");
            } else {
                returndata.setSuccess(false);
                returndata.setResultMessage("要删除的图片不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returndata.setSuccess(false);
            returndata.setResultMessage("系统异常");
        }
        return returndata;
    }

    /**
     * 新增toy表格里面的数据
     *
     * @params {boyId: '', areaId: '', time:'' , imgUrl: ''}
     */
    @PostMapping("/addToys")
    public ReturnData addToys(@RequestBody Map<String, Object> params) {
        toyService.addToys(params);
        return new ReturnData("新增成功");
    }

    /**
     * 查询toy表里面的数据
     *
     * @params 无
     */

    @GetMapping("/queryToy")
    public ReturnData queryToy(int currentPage, int pageSize) {
        Map<String, Object> map = toyService.queryToy(currentPage, pageSize);
        return new ReturnData(map);
    }

    /**
     * 修改toy表格里面的数据
     *
     * @params {id:'', boyId:'', areaId:'', time:'', imgUrl:''}
     */
    @PostMapping("/updateToy")
    public ReturnData updateToy(@RequestBody Map<String, Object> params) {
        toyService.updateToy(params);
        return new ReturnData("修改成功");
    }

    /**
     * 删除toy表里面的数据
     *
     * @params {"id": ""}
     */
    @GetMapping("deleteToy")
    public ReturnData deleteToy(String id) {
        toyService.deleteToy(id);
        return new ReturnData("删除成功");
    }

    /**
     * 测试上传图片和修改删除图片于一体的接口
     */
    @PostMapping("/allUploadImg")
    public ReturnData allUploadImg(MultipartFile file, HttpServletRequest request) {
        ReturnData returnData = new ReturnData();
        if (file.isEmpty()) {
            returnData.setSuccess(false);
            returnData.setResultMessage("文件为空");
            return returnData;
        }
        String originalname = file.getOriginalFilename();
        String suffixname = originalname.substring(originalname.lastIndexOf("."));  // 文件的后缀名
        String path = imgPath + "/";
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String filename = id + suffixname;
        String fullpath = path + filename;
        String requestpath = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + requestpath;
        File dest = new File(fullpath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        String imgName = request.getParameter("imgName").toString();
        String fullpathDelete = imgPath + "/" + imgName;
        File files = new File(fullpathDelete);
        files.delete();
        try {
            file.transferTo(dest);
            String filepath = basePath + "/juhe/toy/allUploadImg/" + filename;
            Map<String, Object> map = new HashMap<>();
            map.put("filename", filename);
            map.put("filepath", filepath);
            map.put("oldname", originalname);
            returnData.setResult(map);
            returnData.setSuccess(true);
            returnData.setResultMessage("success");
        } catch (IOException e) {
            e.printStackTrace();
            returnData.setSuccess(false);
            returnData.setResultMessage("系统异常");
        }
        return returnData;
    }


    /**
     * 测试下载excele表格
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/downloadWasherChargrBusiness")
    public ReturnData downloadWasherChargrBusiness(HttpServletRequest request, HttpServletResponse response) {
        ReturnData returnData = new ReturnData();
        try {
            List<Map<String, Object>> list = toyService.downloadWasherChargrBusiness();
            String[] itemlist = {"id", "地区名称", "任务名称", "时间", "图片地址"};
            if (!list.isEmpty()) {
                boolean flag = downloadWasherChargrBusiness(itemlist, list, response);
                if (flag) {
                    return null;
                }
            } else {
                returnData.setSuccess(false);
                returnData.setResultMessage("暂无数据");
                return returnData;
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnData.setSuccess(false);
            returnData.setResultMessage("系统异常");
            return returnData;
        }
        return returnData;
    }

    public boolean downloadWasherChargrBusiness(String[] items, List<Map<String, Object>> list, HttpServletResponse response) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("toy玩具列表");
        sheet.setDefaultColumnWidth(15);
        XSSFRow row = sheet.createRow(0);
        //XSSFCell cell = row.createCell(0);
        for (int i = 0; i < items.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(items[i]);
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> datamap = list.get(i);
            sheet.setColumnWidth(1, 15 * 256);
            XSSFRow row1 = sheet.createRow(i + 1);
            XSSFCell cell2 = row1.createCell(0);
            cell2.setCellValue(datamap.get("id").toString());
            XSSFCell cell3 = row1.createCell(1);
            cell3.setCellValue(datamap.get("areaName").toString());
            XSSFCell cell4 = row1.createCell(2);
            cell4.setCellValue(datamap.get("time").toString());
            XSSFCell cell5 = row1.createCell(3);
            cell5.setCellValue(datamap.get("boyName").toString());
            XSSFCell cell6 = row1.createCell(4);
            cell6.setCellValue(datamap.get("imgUrl").toString());
        }
        response.setContentType("application/x-download");
        response.setCharacterEncoding("UTF-8");
        String filename = "";
        filename = URLEncoder.encode("toy玩具Exec.xls", "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.close();
        return true;
    }

    /**
     * 测试上传文件excel,读取里面的数据插入到数据库里面
     */
    @PostMapping("/uploadExcel")
    public ReturnData readExcel(MultipartFile file) throws IOException {
        if (file == null) {
            throw new RuntimeException("文件不存在！");
        }
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new RuntimeException(fileName + "不是excel文件");
        }
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            InputStream is = file.getInputStream();
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO流异常");
        }


        List<Map<String, Object>> list = new ArrayList<>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum() + 1;
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环所有行
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    String id = getCellValue(row.getCell(0));
                    String phone = getCellValue(row.getCell(1));
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("phone", phone);
                    list.add(map);
                }
            }
            workbook.close();
        }
        toyService.uploadExcel(list);
        return new ReturnData("上传成功并成功的插入到数据库里面...");
    }

    public String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //如果当前单元格内容为日期类型，需要特殊处理
        String dataFormatString = cell.getCellStyle().getDataFormatString();
        if (dataFormatString.equals("m/d/yy")) {
            cellValue = new SimpleDateFormat("DATE_FORMAT").format(cell.getDateCellValue());
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 测试线程睡眠问题
     */
    @PostMapping("/threadSleep")
    public ReturnData threadSleep() {
        try {
            Thread.sleep(3000);
            System.err.println("来了");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("线程异常问题");
        }
        return new ReturnData("测试成功");
    }


}












