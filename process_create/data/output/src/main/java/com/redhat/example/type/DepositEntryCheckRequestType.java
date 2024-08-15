package com.redhat.example.type;

import lombok.Data;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

// 入金受付チェック要求
@Data
@Component
public class DepositEntryCheckRequestType {

    /** 入金要求番号 */
    private String request_id;

    /** カード番号 */
    private String card_number;

    /** 顧客契約番号 */
    private String customer_contract_number;

    /** 顧客請求締年月日 */
    private String customer_billing_due_date;

    /** 約定決済年月日 */
    private String contract_settlement_date;

    /** 入金年月日 */
    private String deposit_date;

    /** 入金額 */
    private BigDecimal deposit_amount;

    /** 過剰金取扱区分 */
    private String excess_money_handling_category;

}
