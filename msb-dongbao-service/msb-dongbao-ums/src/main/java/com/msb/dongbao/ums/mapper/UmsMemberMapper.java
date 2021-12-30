package com.msb.dongbao.ums.mapper;

import com.msb.dongbao.ums.entity.UmsMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author goodking
 * @since 2021-01-05
 */
@Repository
public interface UmsMemberMapper extends BaseMapper<UmsMember> {
    @Select("select * from ums_member where username = #{umsMember.username}")
    public UmsMember selectByName(String username);
}
