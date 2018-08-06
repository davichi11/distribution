package com.distribution.modules.article.service.impl

import com.distribution.modules.article.dao.ArticleInfoDao
import com.distribution.modules.article.entity.ArticleInfoEntity
import com.distribution.modules.article.service.ArticleInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("articleInfoService")
class ArticleInfoServiceImpl : ArticleInfoService {
    @Autowired
    private lateinit var articleInfoDao: ArticleInfoDao

    override fun queryObject(id: Int): ArticleInfoEntity {
        return articleInfoDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<ArticleInfoEntity> {
        return articleInfoDao.queryList(map)
    }


    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(articleInfo: ArticleInfoEntity) {
        articleInfoDao.save(articleInfo)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(articleInfo: ArticleInfoEntity) {
        articleInfoDao.update(articleInfo)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: Int) {
        articleInfoDao.delete(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<Int>) {
        articleInfoDao.deleteBatch(ids)
    }

}
