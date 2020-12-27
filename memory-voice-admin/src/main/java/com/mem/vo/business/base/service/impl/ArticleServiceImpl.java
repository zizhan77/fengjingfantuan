package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.ArticleMapper;
import com.mem.vo.business.base.model.po.Article;
import com.mem.vo.business.base.model.vo.ArticleVO;
import com.mem.vo.business.base.service.ArticleService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
   @Resource
   private TokenService tokenService;

   @Resource
   private ArticleMapper articleMapper;
    @Override
    public Page<ArticleVO> queryAllBy(String token, Page page, Article article) {
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        if(contextByken.getSourceCode().equals(SourceType.SPONSOR.getCode())){
            //赞助商登录，就只查询状态为1的
            //contextByken.getUserId()
            article.setStatus("0");
        }
        if(contextByken.getSourceCode().equals(SourceType.ORGENIZER.getCode())){
            //主办方登录，就只查主办方的
            //contextByken.getUserId()
            article.setOrganizerid(contextByken.getUserId().intValue());
        }
        List<ArticleVO> list=articleMapper.selectBy(page,article);
        BizAssert.notEmpty(list, BizCode.BIZ_1102.getMessage());
        System.out.println(list.get(0));
        page.setData(list);
        return page;
    }

    @Override
    public ArticleVO queryById(Article article) {
        BizAssert.notNull(article,BizCode.PARAM_NULL.getMessage());
        if(null!=article.getId()){
            ArticleVO article1 = articleMapper.selectById(article.getId());
            BizAssert.notNull(article1,BizCode.BIZ_1103.getMessage());
            return article1;
        }else{
            throw new BizException("ID为空");
        }

    }

    @Override
    public Article saveOrUpdate(String token, Article article) {
        System.out.println("请求");
        BizAssert.notNull(article,BizCode.PARAM_NULL.getMessage());
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        Long userId = contextByken.getUserId();



        //判断增加和修改
        if(null!=article.getId()){
            //修改
            int row =articleMapper.updateByArticle(article);
            if(row==0){
                throw new BizException("项目修改失败");
            }
        }else {
            //增加
            //初始化各种参数
            BizAssert.notNull(article.getArtistid(),BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(article.getCityid(),BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(article.getPlaceid(),BizCode.PARAM_NULL.getMessage());
            article.setStatus("0");
            article.setIsDelete("0");
            article.setOrganizerid(userId.intValue());
            Article article1=articleMapper.selectByArticle(article);
            if(article1!=null){
                throw new BizException("不要重复操作");
            }

            int insert = articleMapper.insert(article);
            if(insert==0){
                throw new BizException("项目增加失败");
            }
        }
        return article;
    }

    @Override
    public Article delete(Article article) {
        BizAssert.notNull(article,BizCode.PARAM_NULL.getMessage());
        if(null==article.getId()){
                throw new BizException("Id为空");
        }else{
            int i = articleMapper.deleteById(article.getId());
            if(i==0){
                throw new BizException("项目删除失败");
            }
            return article;
        }
    }
}
