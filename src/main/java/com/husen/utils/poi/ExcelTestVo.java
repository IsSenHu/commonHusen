package com.husen.utils.poi;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by HuSen on 2018/11/9 9:53.
 */
public class ExcelTestVo implements Serializable {
    private static final long serialVersionUID = -4544002190825510002L;
    @ExcelColumn(value = "第一列")
    private String fieldOne;

    @ExcelColumn(value = "第二列")
    private Integer fieldTwo;

    @ExcelColumn(value = "第三列")
    private Double fieldThree;

    @ExcelColumn(value = "第四列", format = "yyyy-MM-dd HH:mm:ss")
    private Date fieldFour;

    @ExcelColumn(value = "第五列", format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fieldFive;

    @ExcelColumn(value = "第六列", format = "yyyy-MM-dd")
    private LocalDate fieldSix;

    public String getFieldOne() {
        return fieldOne;
    }

    public void setFieldOne(String fieldOne) {
        this.fieldOne = fieldOne;
    }

    public Integer getFieldTwo() {
        return fieldTwo;
    }

    public void setFieldTwo(Integer fieldTwo) {
        this.fieldTwo = fieldTwo;
    }

    public Double getFieldThree() {
        return fieldThree;
    }

    public void setFieldThree(Double fieldThree) {
        this.fieldThree = fieldThree;
    }

    public Date getFieldFour() {
        return fieldFour;
    }

    public void setFieldFour(Date fieldFour) {
        this.fieldFour = fieldFour;
    }

    public LocalDateTime getFieldFive() {
        return fieldFive;
    }

    public void setFieldFive(LocalDateTime fieldFive) {
        this.fieldFive = fieldFive;
    }

    public LocalDate getFieldSix() {
        return fieldSix;
    }

    public void setFieldSix(LocalDate fieldSix) {
        this.fieldSix = fieldSix;
    }

    public static void main(String[] args) {
        Field[] fields = ExcelTestVo.class.getDeclaredFields();
        Field.setAccessible(fields, true);
        for(Field field : fields) {
            Annotation annotation = field.getDeclaredAnnotation(ExcelColumn.class);
            if(annotation != null) {
                ExcelColumn excelColumn = (ExcelColumn) annotation;
                System.out.println(excelColumn.value() + "=>" + excelColumn.format());
            }
        }
    }
}
