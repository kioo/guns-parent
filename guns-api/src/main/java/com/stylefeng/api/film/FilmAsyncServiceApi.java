package com.stylefeng.api.film;

import com.stylefeng.api.film.vo.ActorVO;
import com.stylefeng.api.film.vo.FilmDescVO;
import com.stylefeng.api.film.vo.ImgVO;

import java.util.List;

public interface FilmAsyncServiceApi {
    // 获取影片描述信息
    FilmDescVO getFilmDesc(String uuid);
    // 获取图片信息
    ImgVO getImgs(String uuid);
    // 获取导演信息
    ActorVO getDectInfo(String uuid);
    // 获取演员信息
    List<ActorVO> getActors(String filmId);
}
