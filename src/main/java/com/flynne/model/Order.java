package com.flynne.model;

/**
 * @author xiaoti
 * @date 2024/10/27 21:44
 */

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private Long id;
    private Long userId;
    private BigDecimal amount;

}
