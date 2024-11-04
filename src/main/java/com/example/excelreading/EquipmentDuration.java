package com.example.excelreading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EquipmentDuration {

    private int duration;
    private String EquipmentDuration;

    public EquipmentDuration(String name, int duration) {
    }
}