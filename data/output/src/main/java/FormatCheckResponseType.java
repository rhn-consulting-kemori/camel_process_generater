package com.redhat.example.type;

import lombok.Data;
import org.springframework.stereotype.Component;

// フォーマットチェック応答
@Data
@Component
public class FormatCheckResponseType {

    /** 結果 */
    private String response_result;

    /** エラーコード */
    private String err_code;

    /** エラー内容 */
    private String err_context;

}
