package com.fosun.storekeeper.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 上传进度返回信息
 * @author hengjb
 * @date 2019/08/26
 */
public class ProgressResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String progress;

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
