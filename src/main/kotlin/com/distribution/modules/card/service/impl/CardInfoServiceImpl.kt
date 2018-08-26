package com.distribution.modules.card.service.impl

import com.alibaba.fastjson.JSON
import com.distribution.common.utils.OkHttpUtil
import com.distribution.modules.card.dao.CardInfoMapper
import com.distribution.modules.card.entity.CardApiResponse
import com.distribution.modules.card.entity.CardInfo
import com.distribution.modules.card.service.CardInfoService
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.service.DisFansService
import okhttp3.Request
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.service.impl
 * @Description TODO(描述)
 * @create 2018/5/8-20:23
 */

@Service
class CardInfoServiceImpl : CardInfoService {
    @Autowired
    private lateinit var cardInfoMapper: CardInfoMapper
    @Autowired
    private lateinit var fansService: DisFansService
    private val log = LoggerFactory.getLogger(CardInfoServiceImpl::class.java)

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    override fun queryObject(id: String): CardInfo {
        return cardInfoMapper.selectByPrimaryKey(id)
    }

    override fun queryByBankNum(bankNum: String): CardInfo {
        return cardInfoMapper.selectByBankNum(bankNum)
    }

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    override fun queryList(map: Map<String, Any>): List<CardInfo> {
        return cardInfoMapper.selectList(map)
    }

    /**
     * 保存
     *
     * @param cardInfo
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun save(cardInfo: CardInfo) {
        if (StringUtils.isBlank(cardInfo.bankNum)) {
            return
        }
        when {
            cardInfo.cardImg.isNotEmpty() && cardInfo.cardUrl.isNotEmpty() -> cardInfoMapper.insertSelective(cardInfo)
            else -> {
                val apiResponse = getProductInfo(cardInfo.bankNum)
                if (apiResponse.isSuccess) {
                    cardInfo.cardImg = apiResponse.results.backgroundImg
                    cardInfo.cardUrl = apiResponse.results.link
                    cardInfoMapper.insertSelective(cardInfo)
                }
            }
        }
    }

    /**
     * 更新
     *
     * @param cardInfo
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun update(cardInfo: CardInfo) {
        if (StringUtils.isBlank(cardInfo.bankNum)) {
            return
        }
        val apiResponse = getProductInfo(cardInfo.bankNum)
        if (apiResponse.status == "1") {
            cardInfo.cardImg = apiResponse.results.icon
            if (cardInfo.useThirdPart == 1) {
                cardInfo.cardUrl = apiResponse.results.link
            }
            cardInfoMapper.updateByPrimaryKeySelective(cardInfo)
        }
    }

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun delete(id: String) {
        cardInfoMapper.deleteByPrimaryKey(id)
    }

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        cardInfoMapper.deleteBatch(ids)
    }

    @Throws(Exception::class)
    override fun getProductInfo(prodId: String): CardApiResponse {

        val url = OkHttpUtil.attachHttpGetParams("http://www.qichangkeji.vip/qckjgzhManager/DownSingleLoan/selectById.do",
                mapOf("id" to prodId, "fatherId" to "5710"))
        val request = Request.Builder().url(url).get().build()
        val response = OkHttpUtil.execute(request)
        return if (response.isSuccessful) {
            JSON.parseObject(response.body()!!.string(), CardApiResponse::class.java)
        } else CardApiResponse()
    }

    @Throws(Exception::class)
    override fun getProductUrl(member: DisMemberInfoEntity, prodId: String): String {
        val cardInfo = cardInfoMapper.selectByBankNum(prodId)
        //如果不推送数据直接返回URL
        if (cardInfo.useThirdPart == 0) {
            return cardInfo.cardUrl
        }
        val params = HashMap<String, String>()
        params["name"] = member.disUserName!!
        params["phone"] = member.userEntity!!.mobile!!
        params["type"] = "1"
        params["goodsId"] = prodId
        params["idCard"] = member.idCode!!
        params["fatherId"] = "5710"
        val fans = fansService.queryByOpenId(member.openId!!)!!
        params["otherUserId"] = fans.workerId.toString()
        val url = OkHttpUtil.attachHttpGetParams("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do", params)
        //        RequestBody formBody = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), JSON.toJSONString(params));
        //        Request request = new Request.Builder().url("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do")
        //                .post(formBody).build();
        val request = Request.Builder().url(url).get().build()
        OkHttpUtil.execute(request).use { response ->
            log.info(response.body()!!.string())
            return if (response.isSuccessful) cardInfo.cardUrl else ""
        }
    }
}
