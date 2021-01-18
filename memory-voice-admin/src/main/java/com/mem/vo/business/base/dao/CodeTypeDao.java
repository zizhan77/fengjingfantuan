package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.CodeType;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CodeTypeDao {
    int update(CodeType paramCodeType);

    int insert(CodeType paramCodeType);

    @Select({"select * from codetype where id = #{id}"})
    CodeType selectById(@Param("id") Integer paramInteger);

    List<CodeType> queryBySponsorId(@Param("list") List<String> paramList);

    @Update({"update codetype set count = #{count} where id=#{id}"})
    int updateById(@Param("count") int paramInt, @Param("id") String paramString);

    @Update({"update codetype set count = count + #{count} where id=#{codeTypeId}"})
    int addCount(@Param("codeTypeId") Integer paramInteger, @Param("count") int paramInt);

    @Update({"update codetype set count = count - #{count} where id=#{id}"})
    int desCount(@Param("id") Integer paramInteger, @Param("count") int paramInt);

    List<CodeType> getCodeTypeForAdd(int paramInt);
}
