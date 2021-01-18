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
    CodeType selectById(@Param("id") Integer id);

    List<CodeType> queryBySponsorId(@Param("list") List<String> paramList);

    @Update({"update codetype set count = #{count} where id=#{id}"})
    int updateById(@Param("count") int count, @Param("id") String id);

    @Update({"update codetype set count = count + #{count} where id=#{codeTypeId}"})
    int addCount(@Param("codeTypeId") Integer codeTypeId, @Param("count") int count);

    @Update({"update codetype set count = count - #{count} where id=#{id}"})
    int desCount(@Param("id") Integer id, @Param("count") int count);

    List<CodeType> getCodeTypeForAdd(int paramInt);
}
