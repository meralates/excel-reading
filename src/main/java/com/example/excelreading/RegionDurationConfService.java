package com.example.excelreading;

<<<<<<< HEAD
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    // Dosya yolu
    private final String filePath = "C:\\Users\\MONSTER\\Desktop\\excel-reading\\25.10.2024 mesafe.xlsm";

    public RegionDurationConfService(RegionDurationConfRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void processExcelFile() throws IOException {
        // Dosyayı filePath kullanarak aç
        try (InputStream inputStream = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            processSheet(sheet);
            workbook.close();
        }
    }
    private void processSheet(Sheet sheet) {
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
=======
public class RegionDurationConfService {
>>>>>>> 46f56253e455f639767b4fddcee6bacec0d9e9e9
}
