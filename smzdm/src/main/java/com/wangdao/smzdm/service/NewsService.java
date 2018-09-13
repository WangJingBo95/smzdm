package com.wangdao.smzdm.service;

import com.wangdao.smzdm.bean.News;

import java.util.Date;

public interface NewsService {
    News findNewsById(Integer id);

    boolean addNews(News news);

    News findNewsByUidAndCDate(Integer uid, Date createdDate);
}
