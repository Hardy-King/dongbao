package com.msb.dongbao.ums.service;

import com.msb.dongbao.common.base.result.ResultWrapper;
import com.msb.dongbao.ums.entity.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.ums.entity.dto.UmsMemberLoginParamDTO;
import com.msb.dongbao.ums.entity.dto.UmsMemberRegisterParamDTO;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author goodking
 * @since 2021-01-05
 */

public interface UmsMemberService extends IService<UmsMember> {

    public ResultWrapper register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO);

    public UmsMember selectOneById(Long id);

    ResultWrapper login(UmsMemberLoginParamDTO umsMemberLoginParamDTO);

    ResultWrapper updateUmsMember(UmsMember umsMember);
    ResultWrapper edit(UmsMember umsMember);

}
