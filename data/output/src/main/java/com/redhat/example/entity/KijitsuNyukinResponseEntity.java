package com.redhat.example.entity;

import lombok.Data;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import com.redhat.example.entity.SaikenCompositeUnitEntity;
import com.redhat.example.entity.SeikyuCompositeUnitEntity;

// 期日入金応答
@Data
@Component
public class KijitsuNyukinResponseEntity {

    /** 期日入金要求 */
    private KijitsuNyukinRequestEntity deposit_request;

    /** 入金結果 */
    private String deposit_result;

    /** エラーコード */
    private String err_code;

    /** エラー理由 */
    private String err_context;

    /** 入金種類区分 */
    private String deposit_category_code;

    /** 入金充当額 */
    private SaikenCompositeUnitEntity deposit_allocation_amount;

    /** 過剰金 */
    private BigDecimal excess_money;

    /** JECCS預り金 */
    private BigDecimal jeccs_deposit;

    /** 残請求予定額 */
    private SeikyuCompositeUnitEntity estimated_billing_amount;

    /** 残高 */
    private SaikenCompositeUnitEntity balance_amount;

}
