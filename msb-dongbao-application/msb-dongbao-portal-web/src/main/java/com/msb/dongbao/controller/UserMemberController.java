package com.msb.dongbao.controller;

import com.msb.dongbao.common.JwtUtil;
import com.msb.dongbao.common.base.annotations.TokenCheck;
import com.msb.dongbao.common.base.result.ResultWrapper;
import com.msb.dongbao.ums.entity.UmsMember;
import com.msb.dongbao.ums.entity.dto.UmsMemberLoginParamDTO;
import com.msb.dongbao.ums.entity.dto.UmsMemberRegisterParamDTO;
import com.msb.dongbao.ums.service.UmsMemberService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user-member")
public class UserMemberController {

    @Autowired
    UmsMemberService umsMemberService;

    @GetMapping("/hello")
    public String hello(@Param("id") Long id){
        UmsMember u = umsMemberService.selectOneById(id);
        System.out.println("u:" + u);
        return "hello";
    }

    @GetMapping("/get-captcha")
    public String getCaptcha() {
        return "获取验证码";
    }

    /**
     * 注册：传入验证码信息
     * @param uMsMemberRegisterParamDTO
     * @return
     */
    @RequestMapping("/register")
    public ResultWrapper register(@RequestBody @Valid UmsMemberRegisterParamDTO uMsMemberRegisterParamDTO){

        ResultWrapper register = umsMemberService.register(uMsMemberRegisterParamDTO);
        //return ResultWrapper.getSuccessBuilder().data(null).build();
        System.out.println("你好======："+register.getMsg());
        if (register.getMsg() == "请求成功"){
            return ResultWrapper.getSuccessBuilder().data("恭喜您，注册成功了").build();
        }else {
            return ResultWrapper.getFailBuilder().data("null").build();
        }
    }

    @PostMapping("/login")
    public ResultWrapper login(@RequestBody UmsMemberLoginParamDTO umsMemberLoginParamDTO){

        return umsMemberService.login(umsMemberLoginParamDTO);
    }

    @RequestMapping("/edit")
    @TokenCheck
    public ResultWrapper edit(@RequestBody UmsMember umsMember) {
        System.out.println("edit");
        return umsMemberService.edit(umsMember);
    }

    /**xiaoba
     * 测试
     * @param token
     * @return
     */
    @GetMapping("/test-verify")
    public String verify(String token){
        String s = JwtUtil.parseToken(token);
        String token1 = JwtUtil.createToken(s); //此处是为了解决token延期,也就是再次创建token
        //return s;

        return token1;
    }

    @PostMapping("/updateUmsMember")
    public ResultWrapper updateUmsMember(@RequestBody UmsMember umsMember){
        ResultWrapper resultWrapper = umsMemberService.updateUmsMember(umsMember);
        return resultWrapper;
    }
}
