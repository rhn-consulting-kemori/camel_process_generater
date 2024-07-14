package com.redhat.example.entity;

import lombok.Data;
import java.math.BigDecimal;

// 入金充当情報
@Data
public class DepositAllocationDataEntity {

    /** 入金充当額 */
    private SaikenCompositeUnitEntity deposit_allocation_amount;

    /** 残請求予定額 */
    private SeikyuCompositeUnitEntity estimated_billing_amount;

    /** 過剰金 */
    private BigDecimal excess_money;

}
