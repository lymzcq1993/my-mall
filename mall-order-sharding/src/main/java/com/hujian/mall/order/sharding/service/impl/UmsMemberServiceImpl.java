package com.hujian.mall.order.sharding.service.impl;

import com.hujian.mall.mapper.ums.entity.UmsMember;
import com.hujian.mall.mapper.ums.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hujian.mall.order.sharding.service.UmsMemberService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hujian
 * @since 2022-08-10
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

}
