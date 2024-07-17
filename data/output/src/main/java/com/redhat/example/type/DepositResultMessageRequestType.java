package com.redhat.example.type;

import lombok.Data;
import org.springframework.stereotype.Component;
import com.redhat.example.entity.KijitsuNyukinRequestEntity;
import com.redhat.example.entity.DepositDataEntity;

// 入金結果メッセージ要求
@Data
@Component
public class DepositResultMessageRequestType {

    /** 入金依頼 */
    private KijitsuNyukinRequestEntity deposit_request;

    /** 入金結果 */
    private String deposit_result;

    /** エラーコード */
    private String err_code;

    /** エラー内容 */
    private String err_context;

    /** 入金種類区分 */
    private String deposit_category_code;

    /** 入金結果情報 */
    private DepositDataEntity deposit_data;

}
