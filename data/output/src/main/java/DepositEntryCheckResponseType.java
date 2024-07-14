package com.redhat.example.type;

import lombok.Data;
import org.springframework.stereotype.Component;

// 入金受付チェック応答
@Data
@Component
public class DepositEntryCheckResponseType {

    /** 入金受付チェック要求 */
    private DepositEntryCheckRequestType service_request;

    /** 結果 */
    private String response_result;

    /** エラーコード */
    private String err_code;

    /** エラー内容 */
    private String err_context;

}
