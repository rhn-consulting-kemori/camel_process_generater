package com.redhat.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

// 期日入金要求
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KijitsuNyukinRequestEntity {

    /** 入金要求番号 */
    @JsonProperty(value = "REQUEST_ID")
    private String request_id;

    /** カード番号 */
    @JsonProperty(value = "CARD_NUMBER")
    private String card_number;

    /** 顧客契約番号 */
    @JsonProperty(value = "CUSTOMER_CONTRACT_NUMBER")
    private String customer_contract_number;

    /** 顧客請求締年月日 */
    @JsonProperty(value = "CUSTOMER_BILLING_DUE_DATE")
    private String customer_billing_due_date;

    /** 約定決済年月日 */
    @JsonProperty(value = "CONTRACT_SETTLEMENT_DATE")
    private String contract_settlement_date;

    /** 入金年月日 */
    @JsonProperty(value = "DEPOSIT_DATE")
    private String deposit_date;

    /** 入金額 */
    @JsonProperty(value = "DEPOSIT_AMOUNT")
    private BigDecimal deposit_amount;

    /** 過剰金取扱区分 */
    @JsonProperty(value = "EXCESS_MONEY_HANDLING_CATEGORY")
    private String excess_money_handling_category;

}
