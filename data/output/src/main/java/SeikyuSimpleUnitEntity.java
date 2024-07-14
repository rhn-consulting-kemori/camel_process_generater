package com.redhat.example.entity;

import lombok.Data;
import java.math.BigDecimal;

// 請求商品情報
@Data
public class SeikyuSimpleUnitEntity {

    /** 請求元本額 */
    private BigDecimal billing_principal_amount;

    /** 請求利息額 */
    private BigDecimal billing_interest_amount;

    /** 入金元本額 */
    private BigDecimal deposit_principal_amount;

    /** 入金利息額 */
    private BigDecimal deposit_interest_amount;

}
