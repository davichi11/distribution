package com.distribution.modules.api.service;

import java.io.IOException;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.service
 * @Description TODO(描述)
 * @create 2018/6/9-13:15
 */
public interface IdCardQueryService {
    /**
     * 身份证实名认证
     * @param idCard
     * @param name
     * @return
     */
    boolean isMatched(String idCard, String name) throws IOException;
}
