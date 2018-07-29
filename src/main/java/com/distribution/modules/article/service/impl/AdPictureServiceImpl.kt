package com.distribution.modules.article.service.impl

import com.distribution.modules.article.dao.AdPictureDao
import com.distribution.modules.article.entity.AdPictureEntity
import com.distribution.modules.article.service.AdPictureService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("adPictureService")
class AdPictureServiceImpl : AdPictureService {
    @Autowired
    private lateinit var adPictureDao: AdPictureDao

    override fun queryObject(id: Int): AdPictureEntity {
        return adPictureDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<AdPictureEntity> {
        return adPictureDao.queryList(map)
    }


    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(adPicture: AdPictureEntity) {
        adPictureDao.save(adPicture)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(adPicture: AdPictureEntity) {
        adPictureDao.update(adPicture)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: Int) {
        adPictureDao.delete(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<Int>) {
        adPictureDao.deleteBatch(ids)
    }

}
