package com.example.mag0422.dao.brain;

import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.brain.Brain;
import com.example.mag0422.entity.parts.*;
import com.example.mag0422.entity.parts.servisePartEntity.PartCode;
import com.example.mag0422.entity.parts.servisePartEntity.ServicePartCodeImpl;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class UpdatePartsFromBrain {
    private List<Brain> brainList;

    private final PartsService<SSD_DB> ssdService;
    private final PartsService<ProcessorDB> processorService;
    private final PartsService<PowerDB> powerService;
    private final PartsService<MemoryDB> memoryService;
    private final PartsService<MotherboardDB> mbService;
    private final PartsService<HDD_DB> hddService;
    private final PartsService<GpuDB> gpuService;
    private final PartsService<CaseDB> caseDBPartsService;
    private final PartsService<FunDB> funDBPartsService;

    @Autowired
    public UpdatePartsFromBrain(PartsService<SSD_DB> ssdService, PartsService<ProcessorDB> processorService, PartsService<PowerDB> powerService, PartsService<MemoryDB> memoryService, PartsService<MotherboardDB> mbService, PartsService<HDD_DB> hddService, PartsService<GpuDB> gpuService, PartsService<CaseDB> caseDBPartsService, PartsService<FunDB> funDBPartsService, ServicePartCodeImpl servicePartCode) {
        this.ssdService = ssdService;
        this.processorService = processorService;
        this.powerService = powerService;
        this.memoryService = memoryService;
        this.mbService = mbService;
        this.hddService = hddService;
        this.gpuService = gpuService;
        this.caseDBPartsService = caseDBPartsService;
        this.funDBPartsService = funDBPartsService;
        this.servicePartCode = servicePartCode;
    }

    private final ServicePartCodeImpl servicePartCode;


    public void case_() {
        this.brainList = readXlsBrain();
        List<CaseDB> DbList = caseDBPartsService.getAll();
        for (int i = 0; i < DbList.size(); i++)
            for (int j = 0; j < brainList.size(); j++)
                if (DbList.get(i).getPartCode().getBrinePartNumber().equals(brainList.get(j).getCode())) {
                    CaseDB db = DbList.get(i);
                    db.setName(brainList.get(j).getName());
                    db.setSpecification(brainList.get(j).getDescription());
                    db.setWarranty(brainList.get(j).getWarranty());
                    db.setPrice(brainList.get(j).getPriceUsd());
                    //save part code with original code
                    if (
                            db.getPartCode().getOriginalPartNumber() == null |
                                    db.getPartCode().getOriginalPartNumber() == ""
                    ) {
                        PartCode partCode = db.getPartCode();
                        partCode.setOriginalPartNumber(brainList.get(j).getArticle());
                        servicePartCode.save(partCode);
                        System.out.println("+");
                    }
                    System.out.println(db);
                    caseDBPartsService.save(db);
                    break;
                } else {
                    if (j == brainList.size() - 1)
                        System.out.println("code " + DbList.get(i).getPartCode().getBrinePartNumber() + "  is not find");
                }
    }

    public void fun() {
        this.brainList = readXlsBrain();
int count = 0;
        List<FunDB> DbList = funDBPartsService.getAll();
        System.out.println("2");
        for (int i = 0; i < DbList.size(); i++)
            for (int j = 0; j < brainList.size(); j++)
                if (DbList.get(i).getPartCode().getBrinePartNumber().equals(brainList.get(j).getCode())) {
                    FunDB db = DbList.get(i);
                    db.setName(brainList.get(j).getName());
                    db.setSpecification(brainList.get(j).getDescription());
                    db.setWarranty(brainList.get(j).getWarranty());
                    db.setPrice(brainList.get(j).getPriceUsd());
                    System.out.println(count++);
                    //save part code with original code
                    if (
                            db.getPartCode().getOriginalPartNumber() == null |
                                    db.getPartCode().getOriginalPartNumber() == ""
                    ) {
                        System.out.println("3");
                        PartCode partCode = db.getPartCode();
                        partCode.setOriginalPartNumber(brainList.get(j).getArticle());
                        servicePartCode.save(partCode);
                        System.out.println("+");
                    }
                    System.out.println(db);
                    funDBPartsService.save(db);
                    break;
                } else {
                    if (j == brainList.size() - 1)
                        System.out.println("code " + DbList.get(i).getPartCode().getBrinePartNumber() + "  is not find");
                }
    }

    public void gpu() {
        this.brainList = readXlsBrain();
        List<GpuDB> DbList = gpuService.getAll();
        for (int i = 0; i < DbList.size(); i++)
            for (int j = 0; j < brainList.size(); j++)
                if (DbList.get(i).getPartCode().getBrinePartNumber().equals(brainList.get(j).getCode())) {
                    GpuDB db = DbList.get(i);
                    db.setName(brainList.get(j).getName());
                    db.setSpecification(brainList.get(j).getDescription());
                    db.setWarranty(brainList.get(j).getWarranty());
                    db.setPrice(brainList.get(j).getPriceUsd());
                    //save part code with original code
                    if (
                            db.getPartCode().getOriginalPartNumber() == null |
                                    db.getPartCode().getOriginalPartNumber() == ""
                    ) {
                        PartCode partCode = db.getPartCode();
                        partCode.setOriginalPartNumber(brainList.get(j).getArticle());
                        servicePartCode.save(partCode);
                        System.out.println("+");
                    }
                    System.out.println(db);
                    gpuService.save(db);
                    break;
                } else {
                    if (j == brainList.size() - 1)
                        System.out.println("code " + DbList.get(i).getPartCode().getBrinePartNumber() + "  is not find");
                }
    }

    public void hdd() {
        this.brainList = readXlsBrain();
        List<HDD_DB> DbList = hddService.getAll();
        for (int i = 0; i < DbList.size(); i++)
            for (int j = 0; j < brainList.size(); j++)
                if (DbList.get(i).getPartCode().getBrinePartNumber().equals(brainList.get(j).getCode())) {
                    HDD_DB db = DbList.get(i);
                    db.setName(brainList.get(j).getName());
                    db.setSpecification(brainList.get(j).getDescription());
                    db.setWarranty(brainList.get(j).getWarranty());
                    db.setPrice(brainList.get(j).getPriceUsd());
                    //save part code with original code
                    if (
                            db.getPartCode().getOriginalPartNumber() == null |
                                    db.getPartCode().getOriginalPartNumber() == ""
                    ) {
                        PartCode partCode = db.getPartCode();
                        partCode.setOriginalPartNumber(brainList.get(j).getArticle());
                        servicePartCode.save(partCode);
                        System.out.println("+");
                    }
                    System.out.println(db);
                    hddService.save(db);
                    break;
                } else {
                    if (j == brainList.size() - 1)
                        System.out.println("code " + DbList.get(i).getPartCode().getBrinePartNumber() + "  is not find");
                }
    }

    public void memorry() {
        this.brainList = readXlsBrain();
        List<MemoryDB> DbList = memoryService.getAll();
        for (int i = 0; i < DbList.size(); i++)
            for (int j = 0; j < brainList.size(); j++)
                if (DbList.get(i).getPartCode().getBrinePartNumber().equals(brainList.get(j).getCode())) {
                    MemoryDB db = DbList.get(i);
                    db.setName(brainList.get(j).getName());
                    db.setSpecification(brainList.get(j).getDescription());
                    db.setWarranty(brainList.get(j).getWarranty());
                    db.setPrice(brainList.get(j).getPriceUsd());
                    //save part code with original code
                    if (
                            db.getPartCode().getOriginalPartNumber() == null |
                                    db.getPartCode().getOriginalPartNumber() == ""
                    ) {
                        PartCode partCode = db.getPartCode();
                        partCode.setOriginalPartNumber(brainList.get(j).getArticle());
                        servicePartCode.save(partCode);
                        System.out.println("+");
                    }
                    System.out.println(db);
                    memoryService.save(db);
                    break;
                } else {
                    if (j == brainList.size() - 1)
                        System.out.println("code " + DbList.get(i).getPartCode().getBrinePartNumber() + "  is not find");
                }
    }

    public void motherboard() {
        this.brainList = readXlsBrain();
        List<MotherboardDB> DbList = mbService.getAll();
        for (int i = 0; i < DbList.size(); i++)
            for (int j = 0; j < brainList.size(); j++)
                if (DbList.get(i).getPartCode().getBrinePartNumber().equals(brainList.get(j).getCode())) {
                    MotherboardDB db = DbList.get(i);
                    db.setName(brainList.get(j).getName());
                    db.setSpecification(brainList.get(j).getDescription());
                    db.setWarranty(brainList.get(j).getWarranty());
                    db.setPrice(brainList.get(j).getPriceUsd());
                    //save part code with original code
                    if (
                            db.getPartCode().getOriginalPartNumber() == null |
                                    db.getPartCode().getOriginalPartNumber() == ""
                    ) {
                        PartCode partCode = db.getPartCode();
                        partCode.setOriginalPartNumber(brainList.get(j).getArticle());
                        servicePartCode.save(partCode);
                        System.out.println("+");
                    }
                    System.out.println(db);
                    mbService.save(db);
                    break;
                } else {
                    if (j == brainList.size() - 1)
                        System.out.println("code " + DbList.get(i).getPartCode().getBrinePartNumber() + "  is not find");
                }
    }

    public void power() {
        this.brainList = readXlsBrain();
        List<PowerDB> DbList = powerService.getAll();
        for (int i = 0; i < DbList.size(); i++)
            for (int j = 0; j < brainList.size(); j++)
                if (DbList.get(i).getPartCode().getBrinePartNumber().equals(brainList.get(j).getCode())) {
                    PowerDB db = DbList.get(i);
                    db.setName(brainList.get(j).getName());
                    db.setSpecification(brainList.get(j).getDescription());
                    db.setWarranty(brainList.get(j).getWarranty());
                    db.setPrice(brainList.get(j).getPriceUsd());
                    //save part code with original code
                    if (
                            db.getPartCode().getOriginalPartNumber() == null |
                                    db.getPartCode().getOriginalPartNumber() == ""
                    ) {
                        PartCode partCode = db.getPartCode();
                        partCode.setOriginalPartNumber(brainList.get(j).getArticle());
                        servicePartCode.save(partCode);
                        System.out.println("+");
                    }
                    System.out.println(db);
                    powerService.save(db);
                    break;
                } else {
                    if (j == brainList.size() - 1)
                        System.out.println("code " + DbList.get(i).getPartCode().getBrinePartNumber() + "  is not find");
                }
    }

    public void processor() {
        this.brainList = readXlsBrain();
        List<ProcessorDB> DbList = processorService.getAll();
        for (int i = 0; i < DbList.size(); i++)
            for (int j = 0; j < brainList.size(); j++)
                if (DbList.get(i).getPartCode().getBrinePartNumber().equals(brainList.get(j).getCode())) {
                    ProcessorDB db = DbList.get(i);
                    db.setName(brainList.get(j).getName());
                    db.setSpecification(brainList.get(j).getDescription());
                    db.setWarranty(brainList.get(j).getWarranty());
                    db.setPrice(brainList.get(j).getPriceUsd());
                    //save part code with original code
                    if (
                            db.getPartCode().getOriginalPartNumber() == null |
                                    db.getPartCode().getOriginalPartNumber() == ""
                    ) {
                        PartCode partCode = db.getPartCode();
                        partCode.setOriginalPartNumber(brainList.get(j).getArticle());
                        servicePartCode.save(partCode);
                        System.out.println("+");
                    }
                    System.out.println(db);
                    processorService.save(db);
                    break;
                } else {
                    if (j == brainList.size() - 1)
                        System.out.println("code " + DbList.get(i).getPartCode().getBrinePartNumber() + "  is not find");
                }
    }

    public void ssd() {
        this.brainList = readXlsBrain();
        List<SSD_DB> DbList = ssdService.getAll();
        for (int i = 0; i < DbList.size(); i++)
            for (int j = 0; j < brainList.size(); j++)
                if (DbList.get(i).getPartCode().getBrinePartNumber().equals(brainList.get(j).getCode())) {
                    SSD_DB db = DbList.get(i);
                    db.setName(brainList.get(j).getName());
                    db.setSpecification(brainList.get(j).getDescription());
                    db.setWarranty(brainList.get(j).getWarranty());
                    db.setPrice(brainList.get(j).getPriceUsd());
                    //save part code with original code
                    if (
                            db.getPartCode().getOriginalPartNumber() == null |
                                    db.getPartCode().getOriginalPartNumber() == ""
                    ) {
                        PartCode partCode = db.getPartCode();
                        partCode.setOriginalPartNumber(brainList.get(j).getArticle());
                        servicePartCode.save(partCode);
                        System.out.println("+");
                    }
                    System.out.println(db);
                    ssdService.save(db);
                    break;
                } else {
                    if (j == brainList.size() - 1)
                        System.out.println("code " + DbList.get(i).getPartCode().getBrinePartNumber() + "  is not find");
                }
    }

    public List<Brain> readXlsBrain() {
        System.out.println("readXlsBrain()");
        List<Brain> brainList = new ArrayList<>();
        Brain brainXLS = new Brain();

        int fieldNumbers = 30;
        // Read XSL file
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("C:/demo/brainxls.xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            brainXLS = new Brain();
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) continue;

            brainXLS.setCategoryId((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(0).getNumericCellValue());
            brainXLS.setCode(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(1).getStringCellValue());
            brainXLS.setGroup(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(2).getStringCellValue());
            brainXLS.setArticle(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(3).getStringCellValue());
            brainXLS.setVendor(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(4).getStringCellValue());
            brainXLS.setModel(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(5).getStringCellValue());
            brainXLS.setName(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(6).getStringCellValue());
            brainXLS.setDescription(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(7).getStringCellValue());
            brainXLS.setPriceUsd(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(8).getNumericCellValue());
            brainXLS.setPrice_ind(workbook.getSheetAt(0).getRow(1).getCell(9).getBooleanCellValue());
            brainXLS.setCategoryName(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(10).getStringCellValue());
            brainXLS.setBonus((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(11).getNumericCellValue() == 1 ? true : false);
            brainXLS.setRecommendedPrice(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(12).getNumericCellValue());
//            brainXLS.setDdp(workbook.getSheetAt(0).getRow(1).getCell(13).getNumericCellValue());
            brainXLS.setWarranty((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(14).getNumericCellValue());
            brainXLS.setStock(Integer.parseInt((String) workbook.getSheetAt(0).getRow(1).getCell(15).getStringCellValue()) == 1 ? true : false);
            brainXLS.setNote(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(16).getStringCellValue());
            brainXLS.setDayDelivery(Integer.parseInt(workbook.getSheetAt(0).getRow(1).getCell(17).getStringCellValue()));
            brainXLS.setProductId((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(18).getNumericCellValue());
            brainXLS.setUrl(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(19).getStringCellValue());
//            brainXLS.setUktved(Integer.parseInt(workbook.getSheetAt(0).getRow(1).getCell(20).getStringCellValue()));
//            brainXLS.setGroupId((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(21).getNumericCellValue());
            brainXLS.setClassId((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(22).getNumericCellValue());
            brainXLS.setClassName(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(23).getStringCellValue());
            brainXLS.setAvailable(Integer.parseInt(workbook.getSheetAt(0).getRow(1).getCell(24).getStringCellValue()));
            brainXLS.setCountry(workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(25).getStringCellValue());
            brainXLS.setRetailPrice((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(26).getNumericCellValue());
            brainXLS.setCostDelivery((int) workbook.getSheetAt(0).getRow(row.getRowNum()).getCell(27).getNumericCellValue());
//            brainXLS.setExclusive((boolean) workbook.getSheetAt(0).getRow(1).getCell(28).getNumericCellValue()););
            brainXLS.setFop(Integer.parseInt(workbook.getSheetAt(0).getRow(1).getCell(29).getStringCellValue()) == 1 ? true : false);

            brainList.add(brainXLS);
//            System.out.println(brainXLS);
        }
        return brainList;
    }

}
