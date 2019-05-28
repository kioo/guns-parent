package com.stylefeng.api.film;

import com.stylefeng.api.film.vo.*;

import java.util.List;

public interface FilmServiceApi {

    // 获取banners
    List<BannerVO> getBanners();

    // 获取热映影片
    FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    //获取即将上映硬盘（受欢迎做排序）
    FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    // 获取票房排行
    List<FilmInfo> getBoxRanking();

    // 获取人气排行榜
    List<FilmInfo> getExpectRanking();

    // 获取top 100
    List<FilmInfo> getTop();

    // ==== 获取影片条件接口
    // 分类条件
    List<CatVO> getCats();

    // 片源添加
    List<SourceVO> getSources();

    // 获取年代条件
    List<YearVO> getYears();

    // 根据影片ID 或者名称获取影片信息
    FilmDetailVO getFilmDetail(int searchType, String searchParam);

    // 获取影片描述信息
    FilmDescVO getFilmDesc(String uuid);
    // 获取图片信息
    ImgVO getImgs(String uuid);
    // 获取导演信息
    ActorVO getDectInfo(String uuid);
    // 获取演员信息
    List<ActorVO> getActors(String filmId);
}
