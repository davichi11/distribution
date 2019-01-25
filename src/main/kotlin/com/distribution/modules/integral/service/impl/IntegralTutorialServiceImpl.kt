package com.distribution.modules.integral.service.impl

import com.distribution.modules.integral.dao.IntegralTutorialMapper
import com.distribution.modules.integral.entity.IntegralTutorial
import com.distribution.modules.integral.service.IntegralTutorialService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IntegralTutorialServiceImpl : IntegralTutorialService {
    @Autowired
    private lateinit var integralTutorialMapper: IntegralTutorialMapper

    override fun findByParam(param: Map<String, Any>): List<IntegralTutorial>? {
        return integralTutorialMapper.selectList(param)
    }
    override fun save(integralTutorial: IntegralTutorial) {
        integralTutorialMapper.insertSelective(integralTutorial)
    }

    override fun update(integralTutorial: IntegralTutorial) {
        integralTutorialMapper.updateByPrimaryKeySelective(integralTutorial)
    }

    override fun batchSave(integralTutorials: List<IntegralTutorial>) {
        integralTutorialMapper.batchInsert(integralTutorials)
    }
}