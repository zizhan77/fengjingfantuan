package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.Article;
import com.mem.vo.business.base.model.vo.ArticleVO;
import com.mem.vo.common.dto.Page;


public interface ArticleService {
    /**
     * 按照条件查询并分页
     * 若赞助商登录，自动根据加上赞助商ID
     * @param token
     * @param page
     * @param article
     * @return
     */
    Page<ArticleVO> queryAllBy(String token, Page page, Article article);

    ArticleVO queryById(Article article);

    Article saveOrUpdate(String token, Article article);

    Article delete(Article article);
}
