package com.distribution.modules.api.entity;

import lombok.Data;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.entity
 * @Description TODO(描述)
 * @create 2018/6/9-13:13
 */
@Data
public class IdCardResponse {

    private String reason;
    private IdCardResults result;
    private int errorCode;


}
