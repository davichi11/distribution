package com.distribution.modules.dis.dao;

import com.distribution.modules.dis.entity.DisProfitRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DisProfitRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profit_record
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profit_record
     *
     * @mbg.generated
     */
    int insert(DisProfitRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profit_record
     *
     * @mbg.generated
     */
    int insertSelective(DisProfitRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profit_record
     *
     * @mbg.generated
     */
    DisProfitRecord selectByPrimaryKey(String id);

    List<DisProfitRecord> selectList(Map param);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profit_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DisProfitRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profit_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DisProfitRecord record);
}