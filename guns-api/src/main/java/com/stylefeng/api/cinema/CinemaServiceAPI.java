package com.stylefeng.api.cinema;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.api.cinema.vo.*;
import com.stylefeng.api.film.vo.FilmInfo;

import java.util.List;

public interface CinemaServiceAPI {

    // 1. 根据 CinemaQueryVO 查询影院列表
    Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);

    // 2。 更加条件获取品牌列表【除了99 以外,其他数字为 isActive】
    List<BrandVO> getBrands(int brandId);

    // 3. 获取行政区域列表
    List<AreaVO> getAreas(int areaId);

    // 4. 获取影厅列表
    List<HallTypeVO> getHallTypes(int hallType);

    // 5. 获取影院编号，获取影院信息
    CinemaInfoVO getCinemaInfoById(int cinemaId);

    // 6. 获取所有电影的信息和对应的放映场次信息，根据影院编号
    List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId);

    // 7. 根据放映场次ID 获取放映信息
    HallInfoVO getFilmFieldInfo(int fieldId);

    // 8. 根据放映场次查询播放的电影编号， 然后根据电影编号获取对应的电影信息
    FilmInfoVO getFilmInfoByFieldId(int fieldId);
    /**
     * 订单模块需要的
     */
    OrderQueryVO getOrderNeeds(int fieldId);
}
