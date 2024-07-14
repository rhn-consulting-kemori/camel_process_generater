package com.redhat.example.type;

import lombok.Data;
import org.springframework.stereotype.Component;
import com.redhat.example.entity.DepositAllocationDataEntity;

// 入金充当試算応答
@Data
@Component
public class DepositAllocationResponseType {

    /** 入金充当試算要求 */
    private DepositAllocationRequestType service_request;

    /** 結果 */
    private String response_result;

    /** エラーコード */
    private String err_code;

    /** エラー内容 */
    private String err_context;

    /** 入金充当情報 */
    private DepositAllocationDataEntity deposit_allocation_data;

}
