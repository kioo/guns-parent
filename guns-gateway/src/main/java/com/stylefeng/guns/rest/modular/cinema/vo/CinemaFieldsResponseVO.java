package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.api.cinema.vo.CinemaInfoVO;
import com.stylefeng.api.cinema.vo.FilmInfoVO;
import lombok.Data;

import java.util.List;

@Data
public class CinemaFieldsResponseVO {

    private CinemaInfoVO cinemaInfo;
    private List<FilmInfoVO> filmList;

}
