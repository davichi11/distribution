package com.distribution.modules.dis.entity;

import com.distribution.modules.api.entity.UserEntity;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

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
public class DisMemberInfoEntity implements Serializable, Comparable<DisMemberInfoEntity> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     *
     */
    private Long disPlatformId;
    /**
     * 微信openID
     */
    private String openId;
    /**
     * 用户id
     */
    private UserEntity userEntity;
    /**
     * 上级会员ID
     */
    private String parentId;
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
     * 真实姓名
     */
    private String disUserName;
    /**
     * 身份证号码
     */
    private String idCode;
    /**
     * 级别
     */
    private Integer disLevel;
    /**
     * 身份类型(0 非会员 1会员)
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

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(@NotNull DisMemberInfoEntity o) {
        return this.getDisLevel().compareTo(o.disLevel);
    }
}
