package com.redhat.example.entity;

import lombok.Data;
import java.util.Map;

// 債権複合情報
@Data
public class SaikenCompositeUnitEntity {

    /** 債権合計 */
    private SaikenSimpleUnitEntity total_amout;

    /** 債権商品マップ */
    private Map<String, SaikenSimpleUnitEntity> products_amount_map;

}
