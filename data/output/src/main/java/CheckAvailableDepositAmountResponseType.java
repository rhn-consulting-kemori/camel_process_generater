package com.redhat.example.type;

import lombok.Data;
import org.springframework.stereotype.Component;
import com.redhat.example.entity.AvailableDepositAmountDataEntity;

// 入金可能額応答
@Data
@Component
public class CheckAvailableDepositAmountResponseType {

    /** 入金可能額試算要求 */
    private CheckAvailableDepositAmountRequestType service_request;

    /** 結果 */
    private String response_result;

    /** エラーコード */
    private String err_code;

    /** エラー内容 */
    private String err_context;

    /** 入金可能額情報 */
    private AvailableDepositAmountDataEntity deposit_available_amount_data;

}
