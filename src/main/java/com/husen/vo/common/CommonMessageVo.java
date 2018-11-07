package com.husen.vo.common;

import java.io.Serializable;

/**
 * Created by HuSen on 2018/10/30 15:19.
 */
public class CommonMessageVo implements Serializable {
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
}
