package com.distribution.modules.card.service.impl;

import com.alibaba.fastjson.JSON;
import com.distribution.common.utils.OkHttpUtil;
import com.distribution.modules.api.dao.UserDao;
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
    @Autowired
    private UserDao userDao;

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
        params.put("fatherId", "5710");
        params.put("otherUserId", "0");
        String parentMobile = "0";
        if (member.getDisMemberParent() != null) {
            parentMobile = userDao.queryByMemberId(member.getDisMemberParent().getId()).getMobile();
            params.put("otherUserId", parentMobile);
        }
        String url = OkHttpUtil.attachHttpGetParams("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do",
                params);
//        RequestBody formBody = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), JSON.toJSONString(params));
//        Request request = new Request.Builder().url("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do")
//                .post(formBody).build();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = OkHttpUtil.execute(request)) {
            System.out.println(response.body().string());
            return response.isSuccessful() ? cardInfo.getCardUrl() : null;
        }
    }
}
