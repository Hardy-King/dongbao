package com.msb.dongbao.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msb.dongbao.common.JwtUtil;
import com.msb.dongbao.common.base.result.ResultWrapper;
import com.msb.dongbao.ums.entity.UmsMember;
import com.msb.dongbao.ums.entity.dto.UmsMemberLoginParamDTO;
import com.msb.dongbao.ums.entity.dto.UmsMemberRegisterParamDTO;
import com.msb.dongbao.ums.entity.response.UserMemberLoginResponse;
import com.msb.dongbao.ums.service.UmsMemberService;
import com.msb.dongbao.ums.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author goodking
 * @since 2021-01-05
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResultWrapper register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO){
        UmsMember umsMember = new UmsMember();
        BeanUtils.copyProperties(umsMemberRegisterParamDTO,umsMember);
        // 查询u是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        UmsMember u1 = new UmsMember();
        u1.setUsername(umsMember.getUsername());
        queryWrapper.setEntity(u1);
        UmsMember umsMember1 = umsMemberMapper.selectOne(queryWrapper);
        // 判断umsMember是否存在
        if (umsMember1 == null){

            String password = umsMember.getPassword();
            String encode = bCryptPasswordEncoder.encode(password);
            umsMember.setPassword(encode);
            umsMemberMapper.insert(umsMember);
            return ResultWrapper.getSuccessBuilder().build();
        }else {
            System.out.println("名字不能重复！");
            return ResultWrapper.getFailBuilder().build();
        }
    }

    @Override
    public UmsMember selectOneById(Long id) {
        QueryWrapper qw = new QueryWrapper();
        UmsMember u = new UmsMember();
        u.setId(id);
        qw.setEntity(u);
        UmsMember umsMember = umsMemberMapper.selectOne(qw);
        return umsMember;
    }

    @Override
    public ResultWrapper login(UmsMemberLoginParamDTO umsMemberLoginParamDTO) {
        UmsMember umsMember = umsMemberMapper.selectByName(umsMemberLoginParamDTO.getUsername());
        if (umsMember != null){
            String password = umsMember.getPassword();
            if (!bCryptPasswordEncoder.matches(umsMemberLoginParamDTO.getPassword(),password)){
                System.out.println("密码不对！");
                return ResultWrapper.getFailBuilder().data("密码不对！").build();
            }
        }else {
            return ResultWrapper.getFailBuilder().data("用户不存在！").build();
        }

        UserMemberLoginResponse userMemberLoginResponse = new UserMemberLoginResponse();

        //登陆的时候创建token，后续操作所有登陆后的接口都需要
        String token = JwtUtil.createToken(umsMember.getId()+"");
        userMemberLoginResponse.setToken(token);
        userMemberLoginResponse.setUmsMember(umsMember);

        System.out.println("登陆成功！");
        return ResultWrapper.getSuccessBuilder().data(userMemberLoginResponse).build();
    }

    @Override
    public ResultWrapper updateUmsMember(UmsMember umsMember) {
        umsMemberMapper.updateById(umsMember);
        return ResultWrapper.getSuccessBuilder().data(umsMember).build();
    }

    @Override
    public ResultWrapper edit(UmsMember umsMember) {
        umsMemberMapper.updateById(umsMember);
        return ResultWrapper.getSuccessBuilder().data(umsMember).build();
    }
}
