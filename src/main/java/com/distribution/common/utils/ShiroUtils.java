package com.distribution.common.utils;

import com.distribution.common.exception.RRException;
import com.distribution.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Optional;

/**
 * Shiro工具类
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月12日 上午9:49:19
 */
public final class ShiroUtils {

    private ShiroUtils() {
    }

    public static String getKaptcha(String key) {
        Object kaptcha = Optional.ofNullable(getSessionAttribute(key)).orElseThrow(() -> new RRException("验证码已失效"));
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static SysUserEntity getUserEntity() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    public static Long getUserId() {
        return getUserEntity().getUserId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }


}
