package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.eitity.Album;
import com.baizhi.eitity.Banner;
import com.baizhi.eitity.Chapter;
import com.baizhi.eitity.Menu;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @Autowired
    ChapterService chapterService;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<Album> queryAll(){
        List<Album> chapters = albumService.select1();
        System.err.println(chapters);
        return  chapters;

    }

    @RequestMapping("selectOne")
    @ResponseBody
    public Album selectOne(Integer id){

        Album album = albumService.selectOne(id);
        return album;
    }

    @RequestMapping("insertAlbum")
    @ResponseBody
    public Map insert(Album album, MultipartFile file) throws IOException {

        System.out.println(album);

        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+oldName.substring(oldName.lastIndexOf("."));
        String filePath="D://lun//";
        file.transferTo(new File(filePath+newName));
        System.out.println(newName);


        album.setImgPath(filePath);
        Map map=new HashMap();
        String uuid1 = UUID.randomUUID().toString();
        album.setId(uuid1);
        album.setScore(1);
        Date date=new Date();
        album.setPublishDate(date);
        System.out.println(album);
        try {
            albumService.insert(album);
            map.put("isInsert",true);
        }catch(Exception e){
            e.printStackTrace();
            map.put("isInsert",false);
        }

        return  map;

    }
    @RequestMapping("undo")
    @ResponseBody
    public Map undo(HttpServletResponse response){
        Map map=new HashMap();

        try {
            System.out.println("导出");

            //创建工作簿
            /*Workbook workbook = new HSSFWorkbook();*/

            List<Album> albums = albumService.undo();
            List<Chapter> chapters = chapterService.select();
            Album album = new Album();
            Workbook workbook = ExcelExportUtil.exportExcel
                    (new ExportParams("专辑表","专辑"),
                            Album.class,albums);
            try {
                workbook.write(new FileOutputStream(new File("D:服务器/poi.xls")));
            } catch (IOException e) {
                e.printStackTrace();
            }


                String path="D:/服务器/";

                /*这一块不对*/
                /*workbook.write(new FileOutputStream(new File(path+"test333.xls")));
                response.setHeader();*/
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
                /*OutputStream out = response.getOutputStream();
                *//* 4)、获取服务端生成的excel文件，这里的path等于4.8中的path*//*
                InputStream in = new FileInputStream(new File(path+"test333.xls"));*/
                /* 5)、输出文件*/
                /*int b;
                while((b=in.read())!=-1){
                    out.write(b);
                }*/

            map.put("list",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("list",false);
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



}
