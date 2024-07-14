package com.redhat.example.entity;

import lombok.Data;
import java.util.Map;

// 請求複合情報
@Data
public class SeikyuCompositeUnitEntity {

    /** 請求合計 */
    private SeikyuSimpleUnitEntity total_billing;

    /** 請求商品マップ */
    private Map<String, SeikyuSimpleUnitEntity> products_billing_map;

}
