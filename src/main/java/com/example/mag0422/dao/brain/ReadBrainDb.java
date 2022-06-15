package com.example.mag0422.dao.brain;

import com.example.mag0422.entity.brain.Brain;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//@Component
public class ReadBrainDb {
 public    List<Brain> readXlsBrain(){
        List<Brain> brainList =new ArrayList<>();
//        Brain brainXLS = new Brain();
//
//        int fieldNumbers = 30;
//        // Read XSL file
//        FileInputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(new File("C:/demo/brainxls.xls"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        // Get the workbook instance for XLS file
//        HSSFWorkbook workbook = null;
//        try {
//            workbook = new HSSFWorkbook(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Get first sheet from the workbook
//        HSSFSheet sheet = workbook.getSheetAt(0);
//
//        // Get iterator to all the rows in current sheet
//        Iterator<Row> rowIterator = sheet.iterator();
//        while (rowIterator.hasNext()) {
//            brainXLS = new Brain();
//            Row row = rowIterator.next();
//            if (row.getRowNum() == 0) continue;
//
//            brainXLS.setCategoryId((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(0).getNumericCellValue());
//            brainXLS.setCode(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(1).getStringCellValue());
//            brainXLS.setGroup(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(2).getStringCellValue());
//            brainXLS.setArticle(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(3).getStringCellValue());
//            brainXLS.setVendor(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(4).getStringCellValue());
//            brainXLS.setModel(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(5).getStringCellValue());
//            brainXLS.setName(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(6).getStringCellValue());
//            brainXLS.setDescription(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(7).getStringCellValue());
//            brainXLS.setPriceUsd(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(8).getNumericCellValue());
//            brainXLS.setPrice_ind(workbook.getSheetAt(0).getRow(1).getCell(9).getBooleanCellValue());
//            brainXLS.setCategoryName(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(10).getStringCellValue());
//            brainXLS.setBonus((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(11).getNumericCellValue() == 1 ? true : false);
//            brainXLS.setRecommendedPrice(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(12).getNumericCellValue());
////            brainXLS.setDdp(workbook.getSheetAt(0).getRow(1).getCell(13).getNumericCellValue());
//            brainXLS.setWarranty((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(14).getNumericCellValue());
//            brainXLS.setStock(Integer.parseInt((String) workbook.getSheetAt(0).getRow(1).getCell(15).getStringCellValue()) == 1 ? true : false);
//            brainXLS.setNote(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(16).getStringCellValue());
//            brainXLS.setDayDelivery(Integer.parseInt(workbook.getSheetAt(0).getRow(1).getCell(17).getStringCellValue()));
//            brainXLS.setProductId((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(18).getNumericCellValue());
//            brainXLS.setUrl(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(19).getStringCellValue());
////            brainXLS.setUktved(Integer.parseInt(workbook.getSheetAt(0).getRow(1).getCell(20).getStringCellValue()));
////            brainXLS.setGroupId((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(21).getNumericCellValue());
//            brainXLS.setClassId((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(22).getNumericCellValue());
//            brainXLS.setClassName(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(23).getStringCellValue());
//            brainXLS.setAvailable(Integer.parseInt(workbook.getSheetAt(0).getRow(1).getCell(24).getStringCellValue()));
//            brainXLS.setCountry(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(25).getStringCellValue());
//            brainXLS.setRetailPrice((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(26).getNumericCellValue());
//            brainXLS.setCostDelivery((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(27).getNumericCellValue());
////            brainXLS.setExclusive((boolean) workbook.getSheetAt(0).getRow(1).getCell(28).getNumericCellValue()););
//            brainXLS.setFop(Integer.parseInt(workbook.getSheetAt(0).getRow(1).getCell(29).getStringCellValue()) == 1 ? true : false);
//
//            brainList.add(brainXLS);
////            System.out.println(brainXLS);
//        }
        return brainList;
    }
}
