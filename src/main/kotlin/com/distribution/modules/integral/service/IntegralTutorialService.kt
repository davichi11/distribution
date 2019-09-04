package com.distribution.modules.integral.service

import com.distribution.modules.integral.entity.IntegralTutorial

/**
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.integral.service
 
 * @author ChunLiang Hu
 * @create 2018-12-04-19:37
 */
interface IntegralTutorialService  {
    fun findByParam(param: Map<String, Any>): List<IntegralTutorial>?

    fun save(integralTutorial: IntegralTutorial)

    fun update(integralTutorial: IntegralTutorial)

    fun batchSave(integralTutorials: List<IntegralTutorial>)
}