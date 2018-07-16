package com.distribution.modules.sys.entity

import lombok.Data

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.sys.entity
 * @Description TODO(描述)
 * @create 2018/6/20-21:59
 */

class District {
    var id: Int? = null
        set(id) {
            field = this.id
        }
    var name: String? = null
        set(name) {
            field = this.name
        }
    var parent: District? = null
        set(parent) {
            field = this.parent
        }
    var code: String? = null
        set(code) {
            field = this.code
        }
    var order: Int? = null
        set(order) {
            field = this.order
        }
}
