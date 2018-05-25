package com.distribution.modules.memeber.entity;

import lombok.Data;

/**  会员信息表
 * @param
 * @author liuxinxin
 * @date 16:08
 * @return
 */
@Data
public class MemeberInfo {

    private String id;
    private String memberCode;
    private String openId;
    private String memberName;
    private String memberPwd;
    private String memberSource;
    private String isDelete;
    private String addTime;
    private String updateTime;
}
