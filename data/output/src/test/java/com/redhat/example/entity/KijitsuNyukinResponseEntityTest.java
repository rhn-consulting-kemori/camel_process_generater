package com.redhat.example.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import com.redhat.example.entity.SaikenCompositeUnitEntity;
import com.redhat.example.entity.SeikyuCompositeUnitEntity;

// Test POJO class:期日入金応答
public class KijitsuNyukinResponseEntityTest {
    private KijitsuNyukinResponseEntity obj;

    @SuppressWarnings("unchecked")
    @Test
    public void testKijitsuNyukinResponseEntity(){
        try {
            obj = (KijitsuNyukinResponseEntity.class).getDeclaredConstructor().newInstance();
            Field[] fields = KijitsuNyukinResponseEntity.class.getDeclaredFields();
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
                } else if (dataType.isAssignableFrom(KijitsuNyukinRequestEntity.class)) {
                    valueToSet = new KijitsuNyukinRequestEntity();
                } else if (dataType.isAssignableFrom(SaikenCompositeUnitEntity.class)) {
                    valueToSet = new SaikenCompositeUnitEntity();
                } else if (dataType.isAssignableFrom(SeikyuCompositeUnitEntity.class)) {
                    valueToSet = new SeikyuCompositeUnitEntity();
                } 

                Method getterMethod = KijitsuNyukinResponseEntity.class.getMethod(getter);
                Method setterMethod = KijitsuNyukinResponseEntity.class.getMethod(setter, getterMethod.getReturnType());
                setterMethod.invoke(obj, valueToSet);
                Object result = getterMethod.invoke(obj);
                assertEquals(result, valueToSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
