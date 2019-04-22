package com.baizhi.cmfz_lh;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baizhi.eitity.Album;
import com.baizhi.eitity.Banner;
import com.baizhi.eitity.Chapter;
import com.baizhi.mapper.UserCountMapper;
import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
import com.baizhi.service.ChapterService;
import io.goeasy.GoEasy;
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
		int i = userCountMapper.selectCount(1);
		System.out.println(i);
	}

    @Test
    public void test3() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-c07f58e282ac45f493d42641b6a5259e");
        goEasy.publish("lh", "Hello world!");
    }

    @Test
    public void testAliDayu() throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
//替换成你的AK
        final String accessKeyId = "LTAIHl9FrdjSqR20";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "ulMF8JOMmY5CbCKQkALb0uwafzJXRU";//你的accessKeySecret，参考本文档步骤2
//初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers("18838188682");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("云通信");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_1000000");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
        }
    }

}
