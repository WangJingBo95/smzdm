package com.wangdao.smzdm.dao;

import com.wangdao.smzdm.bean.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface NewsMapper {
    @Select("select * from t_news where id= #{id}")
    News findNewsById(@Param("id") Integer id);

    @Insert("insert into t_news(id,title,link,image,createdDate,uid)" +
            " values(null,#{news.title},#{news.link},#{news.image},#{news.createdDate},#{news.uid})")
    int addNews(@Param("news") News news);

    @Select("select * from t_news where uid=#{uid} and createdDate=#{cdate}")
    News findNewsByUidAndCDate(@Param("uid") Integer uid, @Param("cdate") Date createdDate);
}
