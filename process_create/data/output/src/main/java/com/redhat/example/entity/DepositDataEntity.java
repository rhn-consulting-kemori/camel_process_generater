package com.redhat.example.entity;

import lombok.Data;
import java.math.BigDecimal;

// 入金情報
@Data
public class DepositDataEntity {

    /** 入金充当情報 */
    private SaikenCompositeUnitEntity deposit_allocation_amount;

    /** 過剰金 */
    private BigDecimal excess_money;

    /** 残請求予定額 */
    private SeikyuCompositeUnitEntity estimated_billing_amount;

    /** 残高 */
    private SaikenCompositeUnitEntity balance_amount;

}
