package com.wei.myblog.entity;

import java.io.Serializable;

/**
 * 标签类
 */
public class Label implements Serializable {
    /**
     * 标签id
     */
    private Integer labelId;
    /**
     * 标签名字
     */
    private String labelName;

    public Label() {
    }

    public Label(Integer labelId, String labelName) {
        this.labelId = labelId;
        this.labelName = labelName;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    @Override
    public String toString() {
        return "Label{" +
                "labelId=" + labelId +
                ", labelName='" + labelName + '\'' +
                '}';
    }
}
