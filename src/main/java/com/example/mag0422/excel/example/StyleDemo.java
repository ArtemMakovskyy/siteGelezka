package com.example.mag0422.excel.example;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StyleDemo {

    private static HSSFCellStyle getSampleStyle(HSSFWorkbook workbook) {
        // Font
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setItalic(true);

        // Font Height
        font.setFontHeightInPoints((short) 18);

        // Font Color
        font.setColor(IndexedColors.RED.index);

        // Style
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        return style;
    }

    public static void main(String[] args) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Style Demo");

        HSSFRow row = sheet.createRow(0);

        //
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("String with Style");

        HSSFCellStyle style = getSampleStyle(workbook);
        cell.setCellStyle(style);

        File file = new File("C:/demo/style.xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());

    }

}