package com.distribution.config

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommonConfig {


    @Bean
    @Qualifier("commonMapper")
    fun commonMapper(): ModelMapper {
        val modelMapper = ModelMapper()
        modelMapper.configuration.setPropertyCondition { context -> context.source != null }
        modelMapper.configuration.isAmbiguityIgnored = false
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        return modelMapper
    }
}
