package com.simai.gccx.processmanager.controller;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class TradeParam {


    private String orderNo;

    private BigDecimal amount;

    private String processKey;

}
