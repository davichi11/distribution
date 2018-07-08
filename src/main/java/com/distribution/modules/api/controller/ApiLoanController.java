package com.distribution.modules.api.controller;

import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.pojo.Tables;
import com.distribution.pojo.tables.pojos.LoanInfo;
import com.distribution.pojo.tables.pojos.LoanOrderInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @Description TODO(描述)
 * @create 2018/7/6-11:20
 */
@Slf4j
@Api("贷款接口")
@RestController
@RequestMapping("/api")
public class ApiLoanController {
    @Autowired
    private DSLContext create;

    @AuthIgnore
    @GetMapping("/loanInfo")
    @ApiOperation("查询所有贷款详情")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "贷款产品ID", value = "id")
    public Result getLoanInfo(String id) {
        if (StringUtils.isBlank(id)) {
            return Result.ok().put("loanInfos", create.selectFrom(Tables.LOAN_INFO).fetchInto(LoanInfo.class));
        }
        return Result.ok().put("loanInfo", create.selectFrom(Tables.LOAN_INFO).where(Tables.LOAN_INFO.ID.eq(id))
                .fetchInto(LoanInfo.class));
    }

    @GetMapping("/loanOrder/{mobile}")
    @ApiOperation("查询用户贷款订单记录")
    @ApiImplicitParam(paramType = "patch", dataType = "string", name = "用户手机号", value = "mobile")
    public Result getLoanOrderInfo(@PathVariable("mobile") String mobile) {
        return Result.ok().put("loanOrder", create.selectFrom(Tables.LOAN_ORDER_INFO)
                .where(Tables.LOAN_ORDER_INFO.ORDER_MOBILE.eq(mobile)).fetchInto(LoanOrderInfo.class));
    }


}
