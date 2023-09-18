package com.example.weatherforecast.entity;

import lombok.Getter;
import javax.persistence.*;

@Entity
@Table(name = "vilage_fcst")
@Getter
public class VilageFcst {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String baseDate;
    private String baseTime;
    private String fcstDate;
    private String fcstTime;
    private String category;
    private String fcstValue;
    private String nx;
    private String ny;

    public VilageFcst() {

    }
    public VilageFcst(String baseDate, String baseTime, String fcstDate, String fcstTime, String category, String fcstValue, String nx, String ny) {
        this.baseDate = baseDate;
        this.baseTime = baseTime;
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.category = category;
        this.fcstValue = fcstValue;
        this.nx = nx;
        this.ny = ny;
    }

}
