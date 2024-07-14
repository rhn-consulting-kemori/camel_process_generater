package com.redhat.example.entity;

import lombok.Data;
import java.math.BigDecimal;

// 債権商品情報
@Data
public class SaikenSimpleUnitEntity {

    /** 元本額 */
    private BigDecimal principal_amount;

    /** 利息額 */
    private BigDecimal interest_amount;

}
