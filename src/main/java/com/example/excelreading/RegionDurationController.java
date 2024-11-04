package com.example.excelreading;

<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/region-duration")
public class RegionDurationController {
    private final RegionDurationConfService service;
    public RegionDurationController(RegionDurationConfService service) {
        this.service = service;
    }
    @PostMapping(value = "/upload")
    public ResponseEntity<String>uploadExcelFile(){
        try {
            service.processExcelFile();
            return ResponseEntity.ok("Data processed successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save data");
        }
    }
=======
public class RegionDurationController {
>>>>>>> 46f56253e455f639767b4fddcee6bacec0d9e9e9
}
