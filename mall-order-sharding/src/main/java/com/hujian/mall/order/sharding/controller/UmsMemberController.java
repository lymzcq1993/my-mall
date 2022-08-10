package com.hujian.mall.order.sharding.controller;

import com.hujian.mall.common.api.CommonResult;
import com.hujian.mall.mapper.ums.entity.UmsMember;
import com.hujian.mall.mapper.ums.mapper.UmsMemberMapper;
import com.hujian.mall.order.sharding.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hujian
 * @since 2022-08-10
 */
@RestController
@RequestMapping("/member")
@Api(tags = "会员控制器")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;

    @PostMapping("add")
    public CommonResult<UmsMember> addMember(String name){
        UmsMember member = new UmsMember();
        member.setUsername(name);
        member.setCity("武汉");
        member.setPassword("123131313");
        umsMemberService.save(member);
        return CommonResult.success(member);
    }
}
