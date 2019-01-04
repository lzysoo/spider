package qqnews;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class NewsToExcel {
    public static void writenListToExcel(String filePath, String sheetName, List<NewsModel> newsList) throws IOException {
        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        //建表
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        //设置表头
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("news_title");
        row.createCell(1).setCellValue("news_url");
        row.createCell(2).setCellValue("news_contents");
        //添加内容
        for(int i = 0; i < newsList.size(); i++){
            XSSFRow everyRow = sheet.createRow(i + 1);
            everyRow.createCell(0).setCellValue(newsList.get(i).getNewsTitle());
            everyRow.createCell(1).setCellValue(newsList.get(i).getNewsUrl());
            everyRow.createCell(1).setCellValue(newsList.get(i).getNewsContents());
        }
        //释放资源
        workbook.write(outputStream);
        outputStream.close();
    }
}
