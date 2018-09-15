package com.wangdao.smzdm.dao;

import com.wangdao.smzdm.bean.News;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface NewsMapper {
    @Select("select * from t_news where id= #{id}")
    News findNewsById(@Param("id") Integer id);

    @Insert("insert into t_news(id,title,link,commentCount,image,createdDate,uid)" +
            " values(null,#{news.title},#{news.link},0,#{news.image},#{news.createdDate},#{news.uid})")
    int addNews(@Param("news") News news);

    @Select("select * from t_news where uid=#{uid} and createdDate=#{cdate}")
    News findNewsByUidAndCDate(@Param("uid") Integer uid, @Param("cdate") Date createdDate);


    @Update("update t_news set commentCount=commentCount+1 where id=#{id}")
    void updateCommentCount(@Param("id") Integer newsId);

}
