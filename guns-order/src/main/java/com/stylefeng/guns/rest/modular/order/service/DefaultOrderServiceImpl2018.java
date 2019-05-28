package com.stylefeng.guns.rest.modular.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.api.cinema.CinemaServiceAPI;
import com.stylefeng.api.cinema.vo.FilmInfoVO;
import com.stylefeng.api.cinema.vo.OrderQueryVO;
import com.stylefeng.api.order.OrderServiceAPI;
import com.stylefeng.api.order.vo.OrderVO;
import com.stylefeng.guns.core.util.UUIDUtil;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrder2018TMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrder2018T;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Service(interfaceClass = OrderServiceAPI.class, group = "order2018")
public class DefaultOrderServiceImpl2018 implements OrderServiceAPI {

    @Autowired
    private MoocOrder2018TMapper moocOrder2018TMapper;
    @Autowired
    private FTPUtil ftpUtil;
    @Reference(interfaceClass = CinemaServiceAPI.class, check = false)
    private CinemaServiceAPI cinemaServiceAPI;


    // 验证是否为真实的座位编号
    @Override
    public boolean isTureSeats(String fieldId, String seats) {
        String seatPath = moocOrder2018TMapper.getSeatsByFieldId(fieldId);
        //读取位置图
        String fileStrByAddress = ftpUtil.getFileStrByAddress(seatPath);
        // 将fileStrByAddress 转换为JSON 对象
        JSONObject jsonObject = JSONObject.parseObject(fileStrByAddress);
        String ids = jsonObject.get("ids").toString();
        // 每次匹配上，都给isTrue加1
        String[] seatArrs = seats.split(",");
        String[] idArrs = ids.split(",");
        int isTrue = 0;
        for (String id : idArrs) {
            for (String seat : seatArrs) {
                if (seat.equalsIgnoreCase(id)) {
                    isTrue++;
                }
            }
        }
        // 如果匹配上的数量与已售做为一致，则表示全都匹配上
        if (seatArrs.length == isTrue) {
            return true;
        } else {
            return false;
        }
    }

    // 判断是否为已售座位
    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("field_id", fieldId);
        // 如果有匹配上的，则返回失败
        List<MoocOrder2018T> list = moocOrder2018TMapper.selectList(entityWrapper);
        String[] seatArrs = seats.split(",");
        for (MoocOrder2018T moocOrderT : list) {
            String[] ids = moocOrderT.getSeatsIds().split(",");
            for (String id : ids) {
                for (String seat : seatArrs) {
                    if (id.equalsIgnoreCase(seat)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {

        // 编号
        String uuid = UUIDUtil.genUuid();
        //影片信息
        FilmInfoVO filmInfoVO = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
        Integer filmId = Integer.parseInt(filmInfoVO.getFilmId());
        //获取影院信息
        OrderQueryVO orderQueryVO = cinemaServiceAPI.getOrderNeeds(filmId);
        Integer cinemaId = Integer.parseInt(orderQueryVO.getCinemaId());
        double filmPrice = Double.parseDouble(orderQueryVO.getFilmPrice());
        //求订单总金额
        int solds = soldSeats.split(",").length;
        double totalPrice = getTotalPrice(solds, filmPrice);

        MoocOrder2018T moocOrderT = new MoocOrder2018T();
        moocOrderT.setUuid(uuid);
        moocOrderT.setSeatsName(seatsName);
        moocOrderT.setSeatsIds(soldSeats);
        moocOrderT.setOrderUser(userId);
        moocOrderT.setOrderPrice(totalPrice);
        moocOrderT.setFilmPrice(filmPrice);
        moocOrderT.setFilmId(filmId);
        moocOrderT.setFieldId(fieldId);
        moocOrderT.setCinemaId(cinemaId);

        Integer insert = moocOrder2018TMapper.insert(moocOrderT);
        if (insert > 0) {
            // 返回查询结果
            OrderVO orderVO = moocOrder2018TMapper.getOrderInfoById(uuid);
            if (orderVO == null || orderVO.getOrderId() == null) {
                log.error("订单信息查询失败，订单编号为{}", uuid);
                return null;
            } else {
                return orderVO;
            }
        } else {
            log.error("订单插入失败");
            return null;
        }
    }

    private double getTotalPrice(int solds, double filmPrice) {
        BigDecimal soldDeci = new BigDecimal(solds);
        BigDecimal filmPriceDeci = new BigDecimal(filmPrice);

        BigDecimal result = soldDeci.multiply(filmPriceDeci);
        // 四舍五入，取小数带你后两位
        BigDecimal bigDecimal = result.setScale(2, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }

    @Override
    public Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page) {
        Page<OrderVO> result = new Page<>();
        if (userId == null) {
            log.error("订单业务查询失败,用户编号未传入");
            return null;
        } else {
            List<OrderVO> orderByUserId = moocOrder2018TMapper.getOrderInfoByUserId(userId, page);
            if (orderByUserId == null && orderByUserId.size() == 0) {
                result.setTotal(0);
                result.setRecords(new ArrayList<>());
                return result;
            } else {
                // 获取订单总数
                EntityWrapper<MoocOrder2018T> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("order_user", userId);
                Integer counts = moocOrder2018TMapper.selectCount(entityWrapper);

                result.setTotal(counts);
                result.setRecords(orderByUserId);
                return result;
            }
        }
    }

    // 根据放映场次，获取所有的已售座位
    @Override
    public String getSoldSeatByFieldId(Integer fieldId) {
        if (fieldId == null) {
            log.error("查询已售座位错误未传入任何场次编号");
            return "";
        } else {
            String soldSeatsByFieldId = moocOrder2018TMapper.getSoldSeatsByFieldId(fieldId);
            return soldSeatsByFieldId;
        }
    }


}
