package com.baizhi.controller;

import com.baizhi.eitity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.AudioUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;
    @RequestMapping("insert")
    @ResponseBody
    public Map insert(Chapter chapter, MultipartFile file, HttpSession session){

        System.out.println(chapter);
        //获取大小
        long size = file.getSize();
        //格式
        chapter.setSize(size/1024/1024+"MB");

        //获取到文件上传的目录
        String realPath = session.getServletContext().getRealPath("/");
        String dir = realPath + "mp3";
        File file1 = new File(dir);
        if (!file1.exists()){
            file1.mkdir();
        }

        //源文件名字
        String originalFilename=file.getOriginalFilename();
        //mp3
        /*String extension = FilenameUtils.getExtension(originalFilename);*/
        //保存的名字  UUID
        String uuid = UUID.randomUUID().toString();
        //重命名mp3等同于String extension...
        String saveName = uuid + originalFilename.substring(originalFilename.lastIndexOf("."));

        //上传
        File file2 = null;
        try {
            file2=new File(dir,saveName);
            file.transferTo(file2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        chapter.setTitle(chapter.getTitle());
        chapter.setId(0);
        Date date=new Date();
        chapter.setPublishDate(date);

        //*时长/*/
        long duration = AudioUtil.getDuration(file2);
        //long转String
        String duration1=""+duration/3600+":"+duration/60+":"+duration%60;
        /*String duration1=Long.toString(duration);*/
        chapter.setDuration(duration1);
        chapter.setAlbumId(chapter.getAlbumId());
        Map map= new HashMap();

        try {
            chapterService.insert(chapter);
            map.put("isInsert",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isInsert",false);
        }
        return map;
    }
    @RequestMapping("download")
    @ResponseBody
    public Map download(String title, String downLoadPath,HttpSession session,HttpServletResponse resp) throws IOException{

        Map map=new HashMap();
        try {
            //获取文件的路径
            String realPath = session.getServletContext().getRealPath("/audio");
            String filePath = realPath + "/" + downLoadPath;
            File file = new File(filePath);
            //修改下载时的名字
            String extension = FilenameUtils.getExtension(downLoadPath);
            String oldName = title+"."+extension;
            //下载
            //设置响应的编码
            String encode =null;
            try {
                encode= URLEncoder.encode(oldName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //设置响应头
            resp.setHeader("Content-Disposition","attachment;fileName="+encode);
            //设置响应类型
            resp.setContentType("audio/mpeg");

            ServletOutputStream outputStream = null;
            InputStream is=null;
            try {

                outputStream = resp.getOutputStream();
                is=new FileInputStream(filePath);
                /*outputStream.write(FileUtils.readFileToByteArray(file));*/
                while(true){
                    int i=is.read();
                    if(i==-1)
                        break;
                    outputStream.write(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        map.put("download",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("download",true);
        }

return map;
    }
}
