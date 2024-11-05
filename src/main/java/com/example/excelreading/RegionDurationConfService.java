package com.example.excelreading;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegionDurationConfService {

    private final RegionDurationConfRepository repository;

    public RegionDurationConfService(RegionDurationConfRepository repository) {
        this.repository = repository;
    }

    @Transactional //veritabanı için
    public void processExcelFile() throws IOException {
        // Dosyayı filePath kullanarak aç
        // Dosya yolu
        String filePath = "C:\\Users\\MONSTER\\Desktop\\excel-reading\\25.10.2024 mesafe.xlsm";
        try (InputStream inputStream = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            processSheet(sheet);
            workbook.close();
        }
    }
   /* private void processSheet(Sheet sheet) {
        List<RegionDurationConf> records = new ArrayList<>();
        Row headerRow = sheet.getRow(0);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;



            String fromRegion = row.getCell(0).getStringCellValue().trim(); // Trim boşlukları temizle
            for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                String toRegion = headerRow.getCell(j).getStringCellValue().trim();
                double distance = row.getCell(j).getNumericCellValue();
                String equipmentConfJson = calculateDurationJson(distance);

                records.add(createRegionDurationConf(fromRegion, toRegion, equipmentConfJson));
            }
        }

        repository.saveAll(records); // Tüm kayıtları veritabanına kaydet
    }

    */


    private void processSheet(Sheet sheet) {
        List<RegionDurationConf> records = new ArrayList<>();
        Row headerRow = sheet.getRow(0);

        // İlk satırı başlık olarak kullanıyoruz, veriler 1. satırdan itibaren işleniyor.
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(0) == null) continue;

            // fromRegion verisini al ve boşsa kaydı atla.
            String fromRegion = row.getCell(0).getStringCellValue().trim();
            if (fromRegion.isEmpty()) continue;

            // Diğer sütunları dolaşarak toRegion ve distance verilerini al.
            for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                if (headerRow.getCell(j) == null || row.getCell(j) == null) continue;

                String toRegion = headerRow.getCell(j).getStringCellValue().trim();
                if (toRegion.isEmpty()) continue;

                // Hücre sayısal değilse atla
                if (row.getCell(j).getCellType() != CellType.NUMERIC) continue;

                double distance = row.getCell(j).getNumericCellValue();
                String equipmentConfJson = calculateDurationJson(distance);
                System.out.println("From: " + fromRegion + ", To: " + toRegion + ", EquipmentConf: " + equipmentConfJson);

                records.add(createRegionDurationConf(fromRegion, toRegion, equipmentConfJson));
            }
        }

        // Eğer listede kayıt varsa veritabanına kaydet
        if (!records.isEmpty()) {
            repository.saveAll(records);
        }
    }




    private RegionDurationConf createRegionDurationConf(String from, String to, String equipmentConf) {
        RegionDurationConf conf = new RegionDurationConf();
        conf.setFromRegion(from);
        conf.setToRegion(to);
        conf.setEquipmentConf(equipmentConf);
        return conf;
    }
    private String calculateDurationJson(double distance) {
        Map<String,EquipmentDuration>
                equipmentDurations=new HashMap<>();
        for (Equipment equipment: Equipment.values()){
            int duration=(int)Math.ceil(distance/equipment.getSpeed()/60);
            equipmentDurations.put(equipment.getName(),new EquipmentDuration(equipment.getName(),duration));
        }
        try {
            return new
                    ObjectMapper().writeValueAsString(equipmentDurations);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("Error creating json",e);
        }
    }
}
