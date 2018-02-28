package com.zuora.test.exam.model;

import java.lang.reflect.Field;

import org.junit.Assert;

import com.zuora.test.exam.Mandatory;
import com.zuora.test.exam.NumRange;

public abstract class BaseModel {

    /**
     * Check the class fields have valid values according to its annotations.
     *
     * @param msg
     *            - the heading message to show when assertion fails.
     */
    public void assertFieldsValid(String msg) {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Mandatory.class) != null) {
                checkMandatoryFieldNotNull(field, msg);
            }

            NumRange numRange = field.getAnnotation(NumRange.class);
            if (numRange != null) {
                checkFieldValueInNumRange(field, numRange, msg);
            }
        }
    }

    /**
     * Check the object and the other have the equal value on specified fields.
     *
     * @param other
     * @param fieldNames
     */
    public <T extends BaseModel> void assertEqualsWith(T other, String... fieldNames) {
        for (String fieldName : fieldNames) {
            try {
                Field field = this.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                String msg = String.format("Values of field %s are not equal", fieldName);
                Assert.assertEquals(msg, field.get(this), field.get(other));
            } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkMandatoryFieldNotNull(Field field, String msg) {
        try {
            String className = this.getClass().getSimpleName();
            String fieldName = field.getName();
            String fmt = msg + ": Field %s of class %s is mandatory and should not be null";
            field.setAccessible(true);
            Assert.assertNotNull(String.format(fmt, fieldName, className), field.get(this));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkFieldValueInNumRange(Field field, NumRange numRange, String msg) {
        try {
            String className = this.getClass().getSimpleName();
            String fieldName = field.getName();
            field.setAccessible(true);
            Object value = field.get(this);

            String fmt = msg + ": Field %s of class %s should be a number type";
            Assert.assertTrue(String.format(fmt, fieldName, className), value instanceof Number);

            double doubleValue = ((Number) value).doubleValue();
            double min = numRange.min(), max = numRange.max();
            fmt = msg + ": Field %s of class %s have value %f beyond the range [%f, %f]";
            Assert.assertTrue(String.format(fmt, fieldName, className, doubleValue, min, max),
                    doubleValue >= min && doubleValue <= max);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
