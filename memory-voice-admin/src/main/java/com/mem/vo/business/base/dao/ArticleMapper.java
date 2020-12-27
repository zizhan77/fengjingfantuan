package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.Article;
import com.mem.vo.business.base.model.po.ArticleExample;

import com.mem.vo.business.base.model.vo.ArticleVO;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ArticleMapper {
    long countByExample(ArticleExample example);

    int deleteByExample(ArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    List<Article> selectByExample(ArticleExample example);

    Article selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    /**
     * 条件查询并分页
     * @param page
     * @param article
     * @return
     */
    List<ArticleVO> selectBy(@Param("page") Page page, @Param("article") Article article);



    int updateByArticle(Article article);

    @Update("update article set is_delete = 1 where id = #{id}")
    int deleteById(Integer id);

    ArticleVO selectById(Integer id);

    Article selectByArticle(Article article);
}