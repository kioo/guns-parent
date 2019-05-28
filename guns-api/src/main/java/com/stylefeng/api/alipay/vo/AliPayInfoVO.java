package com.stylefeng.api.alipay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AliPayInfoVO implements Serializable {

    private String orderId;
    private String QRCodeAddress;

}
