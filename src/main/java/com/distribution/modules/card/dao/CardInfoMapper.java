package com.distribution.modules.card.dao;

import com.distribution.modules.card.entity.CardInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author huchunliang
 */
@Mapper
public interface CardInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteBatch(String[] ids);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_info
     *
     * @mbg.generated
     */
    int insert(CardInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_info
     *
     * @mbg.generated
     */
    int insertSelective(CardInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_info
     *
     * @mbg.generated
     */
    CardInfo selectByPrimaryKey(String id);

    /**
     * 查询列表
     *
     * @param param
     * @return
     */
    List<CardInfo> selectList(Map<String, Object> param);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CardInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CardInfo record);
}