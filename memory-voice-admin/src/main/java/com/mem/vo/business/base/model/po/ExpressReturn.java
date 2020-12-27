package com.mem.vo.business.base.model.po;

import lombok.Data;

/**
 * https://market.aliyun.com/products/56928004/cmapi021863.html?spm=5176.730006-56956004-56928004-cmapi021863.content.8.2cd576d7ch1IBi&accounttraceid=271d8574-15f4-4a67-a087-60846b2aa9d7#sku=yuncode1586300000
 * @author lvxiao
 * @version V1.0
 * @date 2019-08-09 21:23
 */
@Data
public class ExpressReturn {
    /**
     * 状态
     */
    private String status;
    /**
     * 消息
     */
    private String msg;
    /**
     * 结果集
     */
    private Express result;
}
