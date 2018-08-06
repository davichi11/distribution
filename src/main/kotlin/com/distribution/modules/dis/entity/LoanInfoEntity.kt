package com.distribution.modules.dis.entity

import java.io.Serializable


/**
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
data class LoanInfoEntity(
        /**
         *
         */
        var id: String = "",
        /**
         * 贷款产品名称
         */
        var loanName: String = "",

        /**
         * 产品链接
         */
        var loanUrl: String = "",

        /**
         *
         */
        var loanImg: String = "",

        /**
         *
         */
        var addTime: String = "",

        /**
         *
         */
        var updateTime: String = "",

        /**
         * 是否启用 0否 1是
         */
        var isDelete: Int = 1,

        /**
         *
         */
        var remark: String = ""


) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}