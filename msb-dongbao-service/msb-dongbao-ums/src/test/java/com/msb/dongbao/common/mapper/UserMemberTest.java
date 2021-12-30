package com.msb.dongbao.common.mapper;

import com.msb.dongbao.ums.entity.UmsMember;
import com.msb.dongbao.ums.mapper.UmsMemberMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@MapperScan("com.msb.dongbao")
@ComponentScan("com.msb.dongbao.ums.config")
public class UserMemberTest {

    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Test
    void testInsert(){

        UmsMember t = new UmsMember();
        t.setUsername("lisi101");
        t.setStatus(1);
        t.setPassword("fdfd");
        t.setNote("note");
        t.setNickName("nick_li");
        t.setEmail("fdfd@QQ.com");

        umsMemberMapper.insert(t);
    }

    @Test
    void testUpdate(){
        UmsMember t = new UmsMember();
        t.setId(61L);
        t.setNickName("尴尬1");
        int i = umsMemberMapper.updateById(t);
        System.out.println(i);
    }


    @Test
    void testSelectOne(){
        UmsMember umsMember = umsMemberMapper.selectById(79);
        System.out.println(umsMember);
    }

    @Test
    void testSelectList(){

        List<UmsMember> umsMembers = umsMemberMapper.selectBatchIds(Arrays.asList(1, 2));
        for (UmsMember umsMember : umsMembers) {
            System.out.println(umsMember);
        }
    }
}
