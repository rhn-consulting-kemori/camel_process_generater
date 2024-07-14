package com.redhat.example.type;

import lombok.Data;
import org.springframework.stereotype.Component;

// 入金可能額要求
@Data
@Component
public class CheckAvailableDepositAmountRequestType {

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

}
