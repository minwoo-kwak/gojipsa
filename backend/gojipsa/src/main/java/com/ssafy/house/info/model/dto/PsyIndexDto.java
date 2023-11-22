package com.ssafy.house.info.model.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PsyIndexDto {
	private Date date;
	private double whole;
    private double capital;
    private double seoul;
    private double incheon;
    private double gyeonggi;
    private double non_capital;
    private double busan;
    private double daegu;
    private double gwangju;
    private double daejeon;
    private double ulsan;
    private double sejong;
    private double gangwon;
    private double chungbuk;
    private double chungnam;
    private double jeonbuk;
    private double jeonnam;
    private double gyeongbuk;
    private double gyeongnam;
    private double jeju;
}
