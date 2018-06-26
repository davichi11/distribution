package com.distribution.modules.dis.entity;

import com.distribution.modules.api.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 用户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-03
 */
@Data
public class DisMemberInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     *
     */
    private String disPlatformId;
    /**
     * 用户id
     */
    private UserEntity userEntity;
    /**
     * 上级会员
     */
    private DisMemberInfoEntity disMemberParent;
    /**
     * 下级集合
     */
    private List<DisMemberInfoEntity> disMemberChildren;
    /**
     * 全路径
     */
    private String disFullIndex;
    /**
     *
     */
    private String disUserName;
    /**
     * 级别
     */
    private Integer disLevel;
    /**
     * 身份类型(0 代理商 1会员)
     */
    private String disUserType;
    /**
     * 备注
     */
    private String disNote;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 删除状态
     */
    private String isDelete;
}
