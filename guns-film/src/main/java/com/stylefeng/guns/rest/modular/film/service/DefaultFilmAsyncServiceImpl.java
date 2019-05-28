package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.api.film.FilmAsyncServiceApi;
import com.stylefeng.api.film.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.MoocActorTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocActorT;
import com.stylefeng.guns.rest.common.persistence.model.MoocFilmInfoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = FilmAsyncServiceApi.class)
public class DefaultFilmAsyncServiceImpl implements FilmAsyncServiceApi {

    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;
    @Autowired
    private MoocActorTMapper moocActorTMapper;

    private MoocFilmInfoT getFilmInfo(String filmId) {

        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();
        moocFilmInfoT.setFilmId(filmId);

        moocFilmInfoT = moocFilmInfoTMapper.selectOne(moocFilmInfoT);
        return moocFilmInfoT;
    }

    @Override
    public FilmDescVO getFilmDesc(String uuid) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(uuid);

        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(moocFilmInfoT.getBiography());
        filmDescVO.setFilmId(moocFilmInfoT.getBiography());

        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String uuid) {

        MoocFilmInfoT moocFilmInfoT = getFilmInfo(uuid);
        // 图片地址是5个以逗号分割的链接
        String filmImgStr = moocFilmInfoT.getFilmImgs();
        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMianImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);

        return imgVO;
    }

    @Override
    public ActorVO getDectInfo(String uuid) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(uuid);
        //获取导演编号
        Integer directId = moocFilmInfoT.getDirectorId();
        MoocActorT moocActorT = moocActorTMapper.selectById(directId);

        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(moocActorT.getActorImg());
        actorVO.setDirectorName(moocActorT.getActorName());
        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        List<ActorVO> actors = moocActorTMapper.getActors(filmId);
        return actors;
    }
}
