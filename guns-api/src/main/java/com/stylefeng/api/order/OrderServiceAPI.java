package com.stylefeng.api.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.api.order.vo.OrderVO;

import java.util.List;

public interface OrderServiceAPI {
    // 验证售出的票是否为真
    boolean isTureSeats(String fieldId,String seats);
    // 已经消失的座位里，有没有这些座位
    boolean isNotSoldSeats(String fieldId,String seats);
    // 创建订单信息
    OrderVO saveOrderInfo(Integer fieldId,String soldSeats,String seatsName,Integer userId);

    // 获取当前登录人的信息
    Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page);

    // 根据FieldId 获取所有已经销售的座位编号
    String getSoldSeatByFieldId(Integer fieldId);
}
