package com.mem.vo.controller.article;

import com.mem.vo.business.base.model.po.Article;
import com.mem.vo.business.base.model.vo.ArticleVO;
import com.mem.vo.business.base.service.ArticleService;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/article")
@Slf4j

public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @CommonExHandler(key = "项目查询异常")
    @PostMapping("/queryAll")
    /**
     * 按照条件模糊查询并分页
     * 若赞助商登录，设置赞助商ID进去
     */
    public ResponseDto<T> queryAll(@RequestHeader("token") String token, Page page, Article article) {
        ResponseDto<Page<ArticleVO>> responseDto = ResponseDto.successDto();

        try {
            Page<ArticleVO> list = articleService.queryAllBy(token, page, article);
            return responseDto.successData(list);

        } catch (BizException e) {

            log.error("项目查询业务异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("项目查询异常", e);
            return responseDto.failSys();

        }
    }

    @CommonExHandler(key = "项目查询异常")
    @PostMapping("/queryById")
    public ResponseDto<T> queryById(Article article) {
        ResponseDto<ArticleVO> responseDto = ResponseDto.successDto();

        try {
            ArticleVO list = articleService.queryById(article);
            return responseDto.successData(list);

        } catch (BizException e) {

            log.error("项目查询业务异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("项目查询异常", e);
            return responseDto.failSys();

        }
    }

    @CommonExHandler(key = "项目编辑异常")
    @PostMapping("/saveOrUpdate")
    public ResponseDto<T> saveOrUpdate(@RequestHeader("token") String token, Article article) {
        ResponseDto<Article> responseDto = ResponseDto.successDto();

        try {
            Article list = articleService.saveOrUpdate(token, article);
            return responseDto.successData(list);

        } catch (BizException e) {

            log.error("项目编辑业务异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("项目编辑异常", e);
            return responseDto.failSys();
        }
    }

    @CommonExHandler(key = "项目删除异常")
    @PostMapping("/delete")
    public ResponseDto<T> saveOrUpdate(Article article) {
        ResponseDto<Article> responseDto = ResponseDto.successDto();

        try {
            Article list = articleService.delete(article);
            return responseDto.successData(list);

        } catch (BizException e) {

            log.error("项目删除业务异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("项目删除异常", e);
            return responseDto.failSys();

        }
    }
}
