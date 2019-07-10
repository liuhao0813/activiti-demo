package com.simai.gccx.processmanager.respository;

import com.simai.gccx.processmanager.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRespository extends JpaRepository<Trade, Long> {


    Trade findByOrderNo(String orderNo);
}
