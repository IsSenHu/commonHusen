package com.husen.vo.common;

import com.husen.utils.poi.ExcelVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuSen on 2018/10/30 15:19.
 */
public class CommonMessageVo implements Serializable, ExcelVo {
    private static final long serialVersionUID = -4717677682655929761L;
    private Long id;
    private String message;

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CommonMessageVo{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public List<String> colNames() {
        List<String> colNames = new ArrayList<>();
        colNames.add("ID");
        colNames.add("消息内容");
        return colNames;
    }

    @Override
    public List<Object> cellValues() {
        List<Object> cellValues = new ArrayList<>();
        cellValues.add(id);
        cellValues.add(message);
        return cellValues;
    }
}
