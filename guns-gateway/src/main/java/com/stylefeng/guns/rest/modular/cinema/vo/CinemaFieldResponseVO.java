package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.api.cinema.vo.CinemaInfoVO;
import com.stylefeng.api.cinema.vo.FilmInfoVO;
import com.stylefeng.api.cinema.vo.HallInfoVO;
import lombok.Data;

@Data
public class CinemaFieldResponseVO {

    private CinemaInfoVO cinemaInfo;
    private FilmInfoVO filmInfo;
    private HallInfoVO hallInfo;

}