package com.redhat.example.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import com.redhat.example.entity.AvailableDepositAmountDataEntity;

// Test POJO class:入金可能額応答
public class CheckAvailableDepositAmountResponseTypeTest {
    private CheckAvailableDepositAmountResponseType obj;

    @SuppressWarnings("unchecked")
    @Test
    public void testCheckAvailableDepositAmountResponseType(){
        try {
            obj = (CheckAvailableDepositAmountResponseType.class).getDeclaredConstructor().newInstance();
            Field[] fields = CheckAvailableDepositAmountResponseType.class.getDeclaredFields();
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
                } else if (dataType.isAssignableFrom(CheckAvailableDepositAmountRequestType.class)) {
                    valueToSet = new CheckAvailableDepositAmountRequestType();
                } else if (dataType.isAssignableFrom(AvailableDepositAmountDataEntity.class)) {
                    valueToSet = new AvailableDepositAmountDataEntity();
                } 

                Method getterMethod = CheckAvailableDepositAmountResponseType.class.getMethod(getter);
                Method setterMethod = CheckAvailableDepositAmountResponseType.class.getMethod(setter, getterMethod.getReturnType());
                setterMethod.invoke(obj, valueToSet);
                Object result = getterMethod.invoke(obj);
                assertEquals(result, valueToSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
