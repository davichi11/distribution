package com.distribution.modules.dis.service;

import com.distribution.modules.dis.entity.LoanOrderInfoEntity;

import java.util.List;
import java.util.Map;


/**
 * 贷款订单
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
public interface LoanOrderInfoService {
    /**
     * 根据ID查询
     *
     * @return
     */
    LoanOrderInfoEntity queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<LoanOrderInfoEntity> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(LoanOrderInfoEntity loanOrderInfo) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(LoanOrderInfoEntity loanOrderInfo) throws Exception;

    /**
     * 删除
     *
     * @throws Exception
     */
    void delete(String id) throws Exception;

    /**
     * 批量删除
     *
     * @throws Exception
     */
    void deleteBatch(String[] ids) throws Exception;
}
