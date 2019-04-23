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
import java.net.URLEncoder;
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
    public Album selectOne(String id) {

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
    public void undo(HttpServletResponse response, String fileName) {
        fileName = "专辑详情表";

            System.out.println("导出");


        List<Album> albums = albumService.select1();
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


        //设置响应的编码
        try {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应类型
        response.setContentType("application/ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
        try {
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
