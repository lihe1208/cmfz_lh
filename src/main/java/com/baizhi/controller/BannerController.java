package com.baizhi.controller;

import com.baizhi.eitity.Banner;
import com.baizhi.eitity.Menu;
import com.baizhi.eitity.PageHelper;
import com.baizhi.service.BannerService;
import com.baizhi.service.MenuService;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;
    @RequestMapping("undo")
    @ResponseBody
    public Map undo(HttpServletResponse response){
        Map map=new HashMap();
        List<Banner> banners = bannerService.undo();
        try {
            System.out.println("导出");

            //创建工作簿
            Workbook workbook = new HSSFWorkbook();
            //创建字体
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontName("楷体");
            font.setItalic(true);
            font.setColor(Font.COLOR_RED);
            //创建日期格式
            DataFormat dataFormat = workbook.createDataFormat();
            short format = dataFormat.getFormat("yyyy年MM月dd日");
            //创建样式
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(font);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            //创建日期格式的样式
            CellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle1.setDataFormat(format);
            //创建工作表
            Sheet sheet = workbook.createSheet("banner");
            //第一个参数给那个列设置宽度  下标   第二个参数  宽度设置为多少  需要乘以256
            sheet.setColumnWidth(3,40*256);
            sheet.setColumnWidth(4,20*256);
            //编号  名字   生日
            String[] strings = {"编号","标题","状态","图片路径","上传日期"};
            //创建行  参数第几行  下标
            Row row = sheet.createRow(0);
            for (int i = 0; i < strings.length; i++) {
                //创建单元格
                Cell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                //单元格赋值
                cell.setCellValue(strings[i]);
            }
            for (int i = 0; i < banners.size(); i++) {
                Row row1 = sheet.createRow(i + 1);
                row1.createCell(0).setCellValue(banners.get(i).getId());
                row1.createCell(1).setCellValue(banners.get(i).getTitle());
                row1.createCell(2).setCellValue(banners.get(i).getStatus());
                row1.createCell(3).setCellValue(banners.get(i).getImgPath());
                Cell cell = row1.createCell(4);
                cell.setCellStyle(cellStyle1);
                cell.setCellValue(banners.get(i).getCreateDate());
            }
            try {

                String path="D:/服务器/";

                /*这一块不对*/
                workbook.write(new FileOutputStream(new File(path+"test333.xls")));
                /*response.setHeader().set*/
               /* 将文件发送至客户端。*/
                /*注意：在ie浏览器下，如果采用location.href='/ServletDownload.do';方式下载，不能在servlet中使用response.getWriter();输出，而应该用response.getOutputStream();
                否则存在下载后的excel直接在浏览器上打开，而不是指定文件路径后下载。其他浏览器未测试过。
                服务端代码
                1)、设置响应的头文件，会自动识别文件内容*/
                response.setContentType("multipart/form-data");
                /*2)、设置Content-Disposition*/
                response.setHeader("Content-Disposition", "attachment;filename=test333.xls");
               /* 3)、输出流*/
                OutputStream out = response.getOutputStream();
               /* 4)、获取服务端生成的excel文件，这里的path等于4.8中的path*/
                InputStream in = new FileInputStream(new File(path+"test333.xls"));
               /* 5)、输出文件*/
                int b;
                while((b=in.read())!=-1){
                    out.write(b);
                }


                } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("list",banners);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("list",banners);
        }

        return map;
    }

    @RequestMapping("redo")
    @ResponseBody
    public Map redo() throws IOException {
        System.out.println("导入");
        Workbook workbook =  new HSSFWorkbook(new FileInputStream(new File("D:/服务器/test.xls")));
        Sheet sheet = workbook.getSheet("banner");
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i < lastRowNum; i++) {
            Row row = sheet.getRow(i + 1);
            double id = row.getCell(0).getNumericCellValue();
            String title = row.getCell(1).getStringCellValue();
            double status = row.getCell(2).getNumericCellValue();
            String imgPath = row.getCell(3).getStringCellValue();
            Date bir = row.getCell(4).getDateCellValue();
            Banner banner = new Banner((int) id, title, imgPath, bir, (int) status);
            System.out.println(banner);
        }

        Map map=new HashMap();
        return map;
    }


    @RequestMapping("queryAll")
    @ResponseBody
    public Map<List,Integer> queryAll(Integer page,Integer rows){
        Map map=new HashMap();
        PageHelper pageHelper = bannerService.queryAll(page,rows);
        map.put("rows",pageHelper.getBanners());
        map.put("total",pageHelper.getTotal());

        System.out.println(map);
        return  map;

    }



    @RequestMapping("updateBanner")
    @ResponseBody
    public Map update(Banner banner,MultipartFile file) throws IOException {



        System.out.println(banner);

        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+oldName.substring(oldName.lastIndexOf("."));
        String filePath="D://lun//";
        file.transferTo(new File(filePath+newName));
        banner.setImgPath(newName);
        Map map=new HashMap();
        Date date=new Date();
        banner.setCreateDate(date);
        System.out.println(banner);
        try {
            bannerService.update(banner);
            map.put("isUpdate",true);
        }catch(Exception e){
            e.printStackTrace();
            map.put("isUpdate",false);
        }

        return  map;

    }
    @RequestMapping("insertBanner")
    @ResponseBody
    public Map insert(Banner banner,MultipartFile file) throws IOException {

        System.out.println(banner);

        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+oldName.substring(oldName.lastIndexOf("."));
        String filePath="D://lun//";
        file.transferTo(new File(filePath+newName));
        System.out.println(newName);
        banner.setImgPath(newName);
        Map map=new HashMap();
        banner.setId(0);
        Date date=new Date();
        banner.setCreateDate(date);
        System.out.println(banner);
        try {
            bannerService.insert(banner);
            map.put("isInsert",true);
        }catch(Exception e){
            e.printStackTrace();
            map.put("isInsert",false);
        }

        return  map;

    }

    @RequestMapping("deleteBanner")
    @ResponseBody
    public Map delete(Integer id){

        Map map=new HashMap();
        try{
            bannerService.delete(id);
            map.put("isDelete",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("isDelete",false);
        }
        return  map;
    }

}
