package com.example.weatherforecast.entity;

import lombok.Getter;
import javax.persistence.*;

@Entity
@Table(name = "ultra_srt_fcst")
@Getter
public class UltraSrtFcst {
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

    public UltraSrtFcst() {

    }
    public UltraSrtFcst(String baseDate, String baseTime, String fcstDate, String fcstTime, String category, String fcstValue, String nx, String ny) {
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
