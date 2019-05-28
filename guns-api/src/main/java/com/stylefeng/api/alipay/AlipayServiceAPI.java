package com.stylefeng.api.alipay;

import com.stylefeng.api.alipay.vo.AliPayInfoVO;
import com.stylefeng.api.alipay.vo.AliPayResultVO;

public interface AlipayServiceAPI {

   AliPayInfoVO getQRCode(String orderId);

   AliPayResultVO getOrderStatus(String orderId);

}
