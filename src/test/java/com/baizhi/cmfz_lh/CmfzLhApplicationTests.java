package com.baizhi.cmfz_lh;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.eitity.Album;
import com.baizhi.eitity.Banner;
import com.baizhi.eitity.Chapter;
import com.baizhi.mapper.UserCountMapper;
import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
import com.baizhi.service.ChapterService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzLhApplicationTests {

	@Autowired
	BannerService bannerService;
	@Autowired
	AlbumService albumService;
	@Autowired
	ChapterService chapterService;
	@Test
	public void contextLoads() {
	}
	@Test
	public void testPOI() {
		List<Banner> banners = bannerService.selectAll();
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
			workbook.write(new FileOutputStream(new File("D:/服务器/test.xls")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void importExcel(){

		try {
			Workbook workbook =  new HSSFWorkbook(new FileInputStream(new File("D:/服务器/test.xls")));
			Sheet sheet = workbook.getSheet("banner");
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 0; i < lastRowNum; i++) {
				Row row = sheet.getRow(i + 1);
				double id = row.getCell(0).getNumericCellValue();
				String title = row.getCell(1).getStringCellValue();
				double status = row.getCell(2).getNumericCellValue();
				String imgPath =row.getCell(3).getStringCellValue();
				Date bir = row.getCell(4).getDateCellValue();
				Banner banner= new Banner((int) id, title, imgPath, bir,(int) status);
				System.out.println(banner);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@Test
	public void testEasyPoi(){

		List<Album> albums = albumService.select1();
		Album album = new Album();
		Workbook workbook = ExcelExportUtil.exportExcel
				(new ExportParams("专辑表","专辑"),
				Album.class,albums);
		try {
			workbook.write(new FileOutputStream(new File("D:服务器/poi.xls")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEasyPoi1(){

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
	}

	@Autowired
	UserCountMapper userCountMapper;

	@Test
	public void test1() {
		List list = userCountMapper.selectChinaMan();
		System.out.println(list);
	}
}
