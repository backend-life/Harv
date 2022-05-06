package com.nowcoder.community.service;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //因为下面这个方法要查的是user表，所以要用UserMapper接口
    @Autowired
    private UserMapper userMapper;

    //这个方法的作用就是以后在controller层中，调用service层的DiscussPostService类里面的方法把很多个DiscussPost查出来后，
    // 我们根据每一个DiscussPost实例的userId再调用UserService类里面的该方法找到它对应的User实例,然后在controller层将User实例和对应的DiscussPost实例拼接一起返回给浏览器
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

}
