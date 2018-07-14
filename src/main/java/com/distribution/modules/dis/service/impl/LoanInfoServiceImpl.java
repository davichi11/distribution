package com.distribution.modules.dis.service.impl;

import com.distribution.common.utils.OkHttpUtil;
import com.distribution.modules.card.entity.CardApiResponse;
import com.distribution.modules.card.service.CardInfoService;
import com.distribution.modules.dis.dao.LoanInfoDao;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.entity.LoanInfoEntity;
import com.distribution.modules.dis.service.DisFansService;
import com.distribution.modules.dis.service.LoanInfoService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("loanInfoService")
public class LoanInfoServiceImpl implements LoanInfoService {
    @Autowired
    private LoanInfoDao loanInfoDao;
    @Autowired
    private CardInfoService cardInfoService;
    @Autowired
    private DisFansService fansService;

    @Override
    public LoanInfoEntity queryObject(String id) {
        return loanInfoDao.queryObject(id);
    }

    @Override
    public List<LoanInfoEntity> queryList(Map<String, Object> map) {
        return loanInfoDao.queryList(map);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(LoanInfoEntity loanInfo) throws Exception {
        if (StringUtils.isBlank(loanInfo.getId())) {
            return;
        }
        CardApiResponse apiResponse = cardInfoService.getProductInfo(loanInfo.getId());
        if (apiResponse.isSuccess()) {
            loanInfo.setLoanImg(apiResponse.getResults().getIcon());
            loanInfo.setLoanUrl(apiResponse.getResults().getLink());
            loanInfo.setRemark(apiResponse.getResults().getContent());
            loanInfoDao.save(loanInfo);
            return;
        }
        loanInfoDao.save(loanInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LoanInfoEntity loanInfo) throws Exception {
        CardApiResponse apiResponse = cardInfoService.getProductInfo(loanInfo.getId());
        if (apiResponse.isSuccess()) {
            loanInfo.setLoanImg(apiResponse.getResults().getIcon());
            loanInfo.setLoanUrl(apiResponse.getResults().getLink());
            loanInfo.setRemark(apiResponse.getResults().getContent());
            loanInfoDao.update(loanInfo);
            return;
        }
        loanInfoDao.update(loanInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        loanInfoDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) throws Exception {
        loanInfoDao.deleteBatch(ids);
    }

    @Override
    public String getProductUrl(DisMemberInfoEntity member, String prodId) throws Exception {
        LoanInfoEntity loanInfo = loanInfoDao.queryObject(prodId);
        Map<String, String> params = new HashMap<>();
        params.put("name", member.getDisUserName());
        params.put("phone", member.getUserEntity().getMobile());
        params.put("type", "1");
        params.put("goodsId", prodId);
        params.put("idCard", member.getIdCode());
        params.put("fatherId", "5710");
        DisFans fans = fansService.queryByOpenId(member.getOpenId());
        params.put("otherUserId", fans.getWorkerId().toString());
        String url = OkHttpUtil.attachHttpGetParams("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do", params);
//        RequestBody formBody = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), JSON.toJSONString(params));
//        Request request = new Request.Builder().url("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do")
//                .post(formBody).build();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = OkHttpUtil.execute(request)) {
            log.info(response.body().string());
            return response.isSuccessful() ? loanInfo.getLoanUrl() : null;
        }
    }

}
