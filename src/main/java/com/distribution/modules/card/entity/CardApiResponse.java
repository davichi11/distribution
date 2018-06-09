package com.distribution.modules.card.entity;

import lombok.Data;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.entity
 * @Description TODO(描述)
 * @create 2018/6/8-20:45
 */
@Data
public class CardApiResponse {

    private String message;
    private CardApiResults results;
    private String status;

    public boolean isSuccess() {
        return "1".equals(this.status);
    }
}
