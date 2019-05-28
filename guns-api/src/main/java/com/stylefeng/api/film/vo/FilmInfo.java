package com.stylefeng.api.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmInfo implements Serializable{

    private String filmId;
    private int filmType;
    private String imgAddress;
    private String fileName;
    private String filmScore;
    private int expectNum;
    private String showTime;
    private int boxNum;
    private String scope;


}
