package com.redhat.example.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

// Test POJO class:債権商品情報
public class SaikenSimpleUnitEntityTest {

    // Target POJO Class
    private SaikenSimpleUnitEntity obj;

    // Expected Type Map
    private Map<String, String> expected_type_map;

    @BeforeEach
    public void beforeEach() {
        expected_type_map = new HashMap<String, String>();
        expected_type_map.put("principal_amount", "BigDecimal");
        expected_type_map.put("interest_amount", "BigDecimal");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSaikenSimpleUnitEntity(){
        try {
            obj = (SaikenSimpleUnitEntity.class).getDeclaredConstructor().newInstance();
            Field[] fields = SaikenSimpleUnitEntity.class.getDeclaredFields();
            for(Field field : fields){

                String fieldName = field.getName();
                String name = StringUtils.capitalize(fieldName);
                String getter = "get" + name;
                String setter = "set" + name;

                Object valueToSet = null;
                Class<?> dataType = field.getType();
                if (dataType.isAssignableFrom(String.class)) {
                    valueToSet = "str";
                } else if (dataType.isAssignableFrom(Long.class) || dataType.isAssignableFrom(long.class)) {
                    valueToSet = Long.valueOf("1");
                } else if (dataType.isAssignableFrom(Integer.class) || dataType.isAssignableFrom(int.class)) {
                    valueToSet = Integer.valueOf("1");
                } else if (dataType.isAssignableFrom(Double.class) || dataType.isAssignableFrom(double.class)) {
                    valueToSet = Double.valueOf("1.0");
                } else if (dataType.isAssignableFrom(Date.class)) {
                    valueToSet = new Date();
                } else if (dataType.isAssignableFrom(Boolean.class)) {
                    valueToSet = Boolean.FALSE;
                } else if (dataType.isAssignableFrom(BigDecimal.class)) {
                    valueToSet = new BigDecimal("1");
                } else if (dataType.isAssignableFrom(Map.class)) {
                    Map<String, String> map = new HashMap<String, String>();
                    valueToSet = map;
                } else if (dataType.isAssignableFrom(List.class)) {
                    List<String> list = new ArrayList<String>();
                    valueToSet = list;
                } 

                Method getterMethod = SaikenSimpleUnitEntity.class.getMethod(getter);
                Method setterMethod = SaikenSimpleUnitEntity.class.getMethod(setter, getterMethod.getReturnType());
                setterMethod.invoke(obj, valueToSet);
                Object result = getterMethod.invoke(obj);

                // Assert Getter, Setter
                assertEquals(result, valueToSet);

                // Field Type
                assertEquals(expected_type_map.get(fieldName), dataType.getSimpleName());
            }
            // Field Count
            assertEquals(expected_type_map.size(),fields.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
