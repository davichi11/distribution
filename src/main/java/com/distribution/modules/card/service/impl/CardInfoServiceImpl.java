package com.distribution.modules.card.service.impl;

import com.alibaba.fastjson.JSON;
import com.distribution.common.utils.OkHttpUtil;
import com.distribution.modules.card.dao.CardInfoMapper;
import com.distribution.modules.card.entity.CardApiResponse;
import com.distribution.modules.card.entity.CardInfo;
import com.distribution.modules.card.service.CardInfoService;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.service.impl
 * @Description TODO(描述)
 * @create 2018/5/8-20:23
 */
@Service
public class CardInfoServiceImpl implements CardInfoService {
    @Autowired
    private CardInfoMapper cardInfoMapper;

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public CardInfo queryObject(String id) {
        return cardInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    @Override
    public List<CardInfo> queryList(Map<String, Object> map) {
        return cardInfoMapper.selectList(map);
    }

    /**
     * 保存
     *
     * @param cardInfo
     * @throws Exception
     */
    @Override
    public void save(CardInfo cardInfo) throws Exception {
        if (StringUtils.isBlank(cardInfo.getBankNum())) {
            return;
        }
        CardApiResponse apiResponse = getProductInfo(cardInfo.getBankNum());
        if (apiResponse.isSuccess()) {
            cardInfo.setCardImg(apiResponse.getResults().getBackgroundImg());
            cardInfo.setCardUrl(apiResponse.getResults().getLink());
            cardInfoMapper.insertSelective(cardInfo);
        }
    }

    /**
     * 更新
     *
     * @param cardInfo
     * @throws Exception
     */
    @Override
    public void update(CardInfo cardInfo) throws Exception {
        if (StringUtils.isBlank(cardInfo.getBankNum())) {
            return;
        }
        CardApiResponse apiResponse = getProductInfo(cardInfo.getBankNum());
        if (apiResponse.isSuccess()) {
            cardInfo.setCardImg(apiResponse.getResults().getIcon());
            cardInfo.setCardUrl(apiResponse.getResults().getLink());
            cardInfoMapper.updateByPrimaryKeySelective(cardInfo);
        }
    }

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(String id) throws Exception {
        cardInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    @Override
    public void deleteBatch(String[] ids) throws Exception {
        cardInfoMapper.deleteBatch(ids);
    }

    @Override
    public CardApiResponse getProductInfo(String prodId) throws Exception {

        String url = OkHttpUtil.attachHttpGetParam("http://www.qichangkeji.vip/qckjgzhManager/DownSingleLoan/selectById.do",
                "id", prodId);
        Request request = new Request.Builder().url(url).get().build();
        Response response = OkHttpUtil.execute(request);
        if (response.isSuccessful()) {
            return JSON.parseObject(response.body().string(), CardApiResponse.class);
        }
        return null;
    }

    @Override
    public String getProductUrl(DisMemberInfoEntity member, String prodId) throws Exception {
        CardInfo cardInfo = cardInfoMapper.selectByBankNum(prodId);
        Map<String, String> params = new HashMap<>();
        params.put("name", member.getDisUserName());
        params.put("phone", member.getUserEntity().getMobile());
        params.put("type", "1");
        params.put("goodsId", prodId);
        params.put("idCard", member.getIdCode());
        params.put("fatherId", member.getId());
        params.put("otherUserId", member.getDisMemberParent().getId());
        String url = OkHttpUtil.attachHttpGetParams("http://www.qichangkeji.vip/qckjgzhManager/DownSingleLoan/selectById.do",
                params);
        Request request = new Request.Builder().url(url).get().build();
        Response response = OkHttpUtil.execute(request);
        if (response.isSuccessful()) {
            //只有提交数据成功才返回办卡URL
            CardApiResponse apiResponse = JSON.parseObject(response.body().string(), CardApiResponse.class);
            if (apiResponse.isSuccess()) {
                return cardInfo.getCardUrl();
            }
        }
        return null;
    }
}
