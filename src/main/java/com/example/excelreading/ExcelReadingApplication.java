package com.example.excelreading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.io.IOException;

@SpringBootApplication
public class ExcelReadingApplication {

	private final RegionDurationConfService regionDurationConfService;

	@Autowired
	public ExcelReadingApplication(RegionDurationConfService regionDurationConfService) {
		this.regionDurationConfService = regionDurationConfService;
	}

	//baslangıc sınıfı
	public static void main(String[] args) {
		SpringApplication.run(ExcelReadingApplication.class, args);
	}



	// Uygulama başlatıldığında `processExcelFile` metodunu çalıştırır
	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		try {
			regionDurationConfService.processExcelFile();
			System.out.println("Excel file processed and records attempted to save.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
