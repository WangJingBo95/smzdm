package com.wangdao.smzdm.service.impl;

import com.wangdao.smzdm.bean.News;
import com.wangdao.smzdm.dao.NewsMapper;
import com.wangdao.smzdm.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;

    @Override
    public News findNewsById(Integer id) {
        return newsMapper.findNewsById(id);
    }

    @Override
    public boolean addNews(News news) {
        return newsMapper.addNews(news) == 1;
    }

    @Override
    public News findNewsByUidAndCDate(Integer uid, Date createdDate) {
        return newsMapper.findNewsByUidAndCDate(uid,createdDate);
    }


    @Override
    public void updateCommentCount(Integer newsId) {
        newsMapper.updateCommentCount(newsId);
    }
}
