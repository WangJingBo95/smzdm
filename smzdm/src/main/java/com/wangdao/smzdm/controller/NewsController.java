package com.wangdao.smzdm.controller;

import com.aliyun.oss.OSSClient;
import com.wangdao.smzdm.bean.*;
import com.wangdao.smzdm.service.CommentService;
import com.wangdao.smzdm.service.CommentVoService;
import com.wangdao.smzdm.service.NewsService;
import com.wangdao.smzdm.service.VoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.plugin.com.event.COMEventHandler;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class NewsController {

    @Autowired
    NewsService newsService;
    @Autowired
    VoService voService;
    @Autowired
    CommentVoService commentVoService;
    @Autowired
    CommentService commentService;

    /**
     * 打开新闻详情
     *
     * @param id
     * @param mv
     * @return mv
     */
    @RequestMapping("/news/{id}")
    public ModelAndView find_news(@PathVariable Integer id, ModelAndView mv) {

        Vo vo = voService.findVoByNid(id);
        List<CommentVo> commentVos = commentVoService.findCommentVosByNid(id);
        mv.addObject("news", vo.getNews());
        mv.addObject("like", vo.getLike());
        mv.addObject("owner", vo.getUser());
        mv.addObject("comments", commentVos);

        mv.setViewName("detail");
        return mv;
    }

    /**
     * 分享时的图片上传
     *
     * @param file
     * @param mv
     * @return
     */
    @RequestMapping("/uploadImage/")
    public Map<String, Object> uploadImage(MultipartFile file, ModelAndView mv) {
        //取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.split("\\.")[1];
        //UUID
        UUID uuid = UUID.randomUUID();
        String objectName = uuid.toString() + "." + suffix;


        // Endpoint为深圳。
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云RAM账号。
        String accessKeyId = "LTAIlru73Jiqy26H";
        String accessKeySecret = "aHvlthgqFDYYmHeAjjdbL1iP9TBXTx";
        // 仓库名。
        String bucketName = "toutiaonewsimages";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传文件。
        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();

        //上传成功。将数据库存储的，以及首页显示的图片压缩100*80
        String imageUrl = "http://" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName + "?x-oss-process=image/resize,m_fixed,h_100,w_80";

        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", imageUrl);
        map.put("code", 0);
        return map;
    }

    /**
     * 分享一个新的新闻
     *
     * @param news
     * @param session
     * @return
     */
    @Transactional
    @RequestMapping("/user/addNews/")
    public Map<String, Object> addNews(News news, HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        //获取创建新闻的用户信息
        User user = (User) session.getAttribute("user");
        //获取当前时间
        Date createdDate = getCreatedDate();

        //数据库中插入一个新闻
        news.setCreatedDate(createdDate);
        news.setUid(user.getId());
        newsService.addNews(news);
        //获取刚刚插入的新闻在数据库中的id
        News news_find = newsService.findNewsByUidAndCDate(news.getUid(), createdDate);

        //数据库中插入一个Vo
        Vo vo = new Vo();
        vo.setUid(user.getId());
        vo.setNid(news_find.getId());
        vo.setLike(0);
        voService.addVo(vo);

        map.put("status", "200");
        return map;
    }

    private Date getCreatedDate() {
        //将日期格式转换，便于据此格式查询数据库中的date
        Date createdDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(createdDate);
        Date parse = null;
        try {
            parse = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    @Transactional
    @RequestMapping("/addComment")
    public ModelAndView addComment(Integer newsId, String content, HttpSession session, ModelAndView mv) {
        //获取评论用户
        User user = (User) session.getAttribute("user");
        //获取当前时间
        Date createdDate = getCreatedDate();

        //新增一个评论项
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedDate(createdDate);
        comment.setUid(user.getId());
        commentService.addComment(comment);
        //获取刚刚插入的评论在数据库中的id
        Comment comment_find = commentService.findCommentByUidAndCDate(comment.getUid(), createdDate);

        //新增一个CommentVo
        CommentVo commentVo = new CommentVo();
        commentVo.setCid(comment_find.getId());
        commentVo.setUid(comment_find.getUid());
        commentVo.setNid(newsId);
        commentVoService.addCommentVo(commentVo);

        mv.setViewName("redirect:/news/" + newsId);
        return mv;
    }
}
