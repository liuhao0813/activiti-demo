package com.simai.gccx.processmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Table(name = "trade")
@Entity
public class Trade {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no")
    private String orderNo;

    private BigDecimal amount;

    /**
     *  1，提交审批   2，一级审批，   3 二级审批   4关闭   0 保存草稿      等等
     */
    private Integer status;


}
