package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

//    这个方法查的是多条帖子，所以返回List，offset代表起始行号，limit代表查询的条数,在我们开发社区首页的时候，暂时用不到userId这个参数，这个参数主要是为了以后有一个功能叫做，我的帖子
//    为了实现这个功能就需要userId了，传入userId查出这个userId对应的所有帖子，那我们现在不需要这个参数，就可以给这个参数做成动态的,就是在Mapper映射器里用<if>标签，如果userId!=0，我们才用这个参数，
//    否则不用这个参数，以后做我的帖子功能的时候，这个userId就不等于0了
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);




//    这个方法时查询帖子的总条数，即discuss_post表的总行数，同样这个方法以后还用于我的帖子功能,查询userId对应的帖子的总行数，****注意，如果一个参数被做成动态的，即用if标签，那这个参数必须用@Param参数****
    int selectDiscussPostRows(@Param("userId") int userId);
}
