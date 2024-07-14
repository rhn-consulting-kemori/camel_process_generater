package com.redhat.example.type;

import lombok.Data;
import org.springframework.stereotype.Component;
import com.redhat.example.entity.DepositAllocationDataEntity;

// 入金要求
@Data
@Component
public class DepositRequestType {

    /** 入金要求番号 */
    private String request_id;

    /** 顧客契約番号 */
    private String customer_contract_number;

    /** 入金年月日 */
    private String deposit_date;

    /** 顧客請求締年月日 */
    private String customer_billing_due_date;

    /** 約定決済年月日 */
    private String contract_settlement_date;

    /** 入金種類区分 */
    private String deposit_category_code;

    /** 入金額 */
    private BigDecimal deposit_amount;

    /** 過剰金取扱区分 */
    private String excess_money_handling_category;

    /** 入金充当試算情報 */
    private DepositAllocationDataEntity deposit_allocation_data;

}
