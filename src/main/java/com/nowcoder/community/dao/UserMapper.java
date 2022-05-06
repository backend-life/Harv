package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
//Mybatis里面有Mapper注解，它的作用和@Repository一样，更习惯用这个来注解Mapper接口，虽然@Autowired注入的地方属性名会标红，但不影响使用
public interface UserMapper {

    //Mabatis的Mapper接口里面都是抽象方法

    User selectById(int id);//根据用户id查询用户，返回的是User类的实例

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);//插入user表中一个用户User，返回插入的行数

    int updateStatus(@Param("id") int id, @Param("status") int status);//根据id更改对应用户的status字段,返回更改了几行

    int updateHeader(@Param("id") int id,@Param("headerUrl") String headerUrl);

    int updatePassword(@Param("id") int id,@Param("password") String password);
}
