package com.example.weatherforecast.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "mid_fcst")
@Getter
public class MidFcst {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String wfSv;

    public MidFcst() {

    }
    public MidFcst(String wfSv) {
        this.wfSv = wfSv;
    }

}
