package com.example.mag0422.excel.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JavaExcelApp {
    public void createSheet()  {
        String link = "src\\main\\resources\\static\\";
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("MySheet");

        try {
            FileOutputStream fos = new FileOutputStream(link + "my1.xls");
            wb.write(fos);
            fos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
