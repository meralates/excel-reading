package com.example.excelreading;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="region_duration_conf")
public class RegionDurationConf { //spring bu sınıfı tablo olarak tanımlar.
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromRegion;
    private String toRegion;

    @Column(columnDefinition = "json") //json formatında saklama yapılır.
    private String equipmentConf;

}
