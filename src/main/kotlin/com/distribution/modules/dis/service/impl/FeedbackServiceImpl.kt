package com.distribution.modules.dis.service.impl

import com.distribution.modules.dis.dao.FeedbackDao
import com.distribution.modules.dis.entity.FeedbackEntity
import com.distribution.modules.dis.service.FeedbackService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * @author huchunliang
 */
@Service("feedbackService")
class FeedbackServiceImpl : FeedbackService {
    @Autowired
    private lateinit var feedbackDao: FeedbackDao

    override fun queryObject(id: Long): FeedbackEntity? {
        return feedbackDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<FeedbackEntity>? {
        return feedbackDao.queryList(map)
    }


    @Transactional(rollbackFor = [Exception::class])
    @Throws(Exception::class)
    override fun save(feedback: FeedbackEntity) {
        feedbackDao.save(feedback)
    }

    @Transactional(rollbackFor = [Exception::class])
    @Throws(Exception::class)
    override fun update(feedback: FeedbackEntity) {
        feedbackDao.update(feedback)
    }

    @Transactional(rollbackFor = [Exception::class])
    @Throws(Exception::class)
    override fun delete(id: Long) {
        feedbackDao.delete(id)
    }

    @Transactional(rollbackFor = [Exception::class])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<Long>) {
        feedbackDao.deleteBatch(ids)
    }

}
