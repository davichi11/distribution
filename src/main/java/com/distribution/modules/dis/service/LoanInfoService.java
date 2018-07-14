package com.distribution.modules.dis.service;

import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.entity.LoanInfoEntity;

import java.util.List;
import java.util.Map;


/**
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
public interface LoanInfoService {
    /**
     * 根据ID查询
     *
     * @return
     */
    LoanInfoEntity queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<LoanInfoEntity> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(LoanInfoEntity loanInfo) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(LoanInfoEntity loanInfo) throws Exception;

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

    /**
     * 提交用户信息并返回办卡链接,失败返回空
     * @param member
     * @param prodId
     * @return
     * @throws Exception
     */
    String getProductUrl(DisMemberInfoEntity member, String prodId) throws Exception;
}
