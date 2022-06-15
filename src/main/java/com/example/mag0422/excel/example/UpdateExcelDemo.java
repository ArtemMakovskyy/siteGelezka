package com.example.mag0422.excel.example;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UpdateExcelDemo {

    public static void main(String[] args) throws IOException {

        File file = new File("C:/demo/employee.xls");
        // Read XSL file
        FileInputStream inputStream = new FileInputStream(file);

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFCell cell = sheet.getRow(1).getCell(2);
        cell.setCellValue(cell.getNumericCellValue() * 2);

        cell = sheet.getRow(2).getCell(2);
        cell.setCellValue(cell.getNumericCellValue() * 2);

        cell = sheet.getRow(3).getCell(2);
        cell.setCellValue(cell.getNumericCellValue() * 2);

        inputStream.close();

        // Write File
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();

    }

}