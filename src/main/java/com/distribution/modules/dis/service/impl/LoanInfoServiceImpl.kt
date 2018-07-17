package com.distribution.modules.dis.service.impl

import com.distribution.common.utils.OkHttpUtil
import com.distribution.modules.card.service.CardInfoService
import com.distribution.modules.dis.dao.LoanInfoDao
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.entity.LoanInfoEntity
import com.distribution.modules.dis.service.DisFansService
import com.distribution.modules.dis.service.LoanInfoService
import okhttp3.Request
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service("loanInfoService")
class LoanInfoServiceImpl : LoanInfoService {
    @Autowired
    private lateinit var loanInfoDao: LoanInfoDao
    @Autowired
    private lateinit var cardInfoService: CardInfoService
    @Autowired
    private lateinit var fansService: DisFansService
    private val log = LoggerFactory.getLogger(LoanInfoServiceImpl::class.java)

    override fun queryObject(id: String): LoanInfoEntity {
        return loanInfoDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<LoanInfoEntity> {
        return loanInfoDao.queryList(map)
    }


    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(loanInfo: LoanInfoEntity) {
        if (StringUtils.isBlank(loanInfo.id)) {
            return
        }
        val apiResponse = cardInfoService.getProductInfo(loanInfo.id)
        if (apiResponse.isSuccess) {
            loanInfo.loanImg = apiResponse.results.icon
            loanInfo.loanUrl = apiResponse.results.link
            loanInfo.remark = apiResponse.results.content
            loanInfoDao.save(loanInfo)
            return
        }
        loanInfoDao.save(loanInfo)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(loanInfo: LoanInfoEntity) {
        val apiResponse = cardInfoService.getProductInfo(loanInfo.id)
        if (apiResponse.isSuccess) {
            loanInfo.loanImg = apiResponse.results.icon
            loanInfo.loanUrl = apiResponse.results.link
            loanInfo.remark = apiResponse.results.content
            loanInfoDao.update(loanInfo)
            return
        }
        loanInfoDao.update(loanInfo)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: String) {
        loanInfoDao.delete(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        loanInfoDao.deleteBatch(ids)
    }

    @Throws(Exception::class)
    override fun getProductUrl(member: DisMemberInfoEntity, prodId: String): String {
        val loanInfo = loanInfoDao.queryObject(prodId)
        val params = HashMap<String, String>()
        params["name"] = member.disUserName!!
        params["phone"] = member.userEntity!!.mobile!!
        params["type"] = "1"
        params["goodsId"] = prodId
        params["idCard"] = member.idCode!!
        params["fatherId"] = "5710"
        val fans = fansService.queryByOpenId(member.openId!!)
        params["otherUserId"] = fans!!.workerId.toString()
        val url = OkHttpUtil.attachHttpGetParams("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do", params)
        //        RequestBody formBody = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), JSON.toJSONString(params));
        //        Request request = new Request.Builder().url("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do")
        //                .post(formBody).build();
        val request = Request.Builder().url(url).get().build()
        OkHttpUtil.execute(request).use { response ->
            log.info(response.body()!!.string())
            return if (response.isSuccessful) loanInfo.loanUrl else ""
        }
    }

}
