package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.ChangeCode;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ChangeCodeDao {
    @Update({"UPDATE changeCode set isChange =#{code} WHERE id =#{id}"})
    int updateById(@Param("id") Integer paramInteger1, @Param("code") Integer paramInteger2);

    @Select({"SELECT * from changeCode where id= #{id} and isChange =#{code} "})
    List<ChangeCode> selectByChangeCodeId(@Param("id") Integer paramInteger1, @Param("code") Integer paramInteger2);

    @Select({"SELECT * from changeCode where id= #{id}"})
    ChangeCode selectById(Integer paramInteger);

    @Select({"SELECT * from changeCode where codeType= #{codeTypeId} and isChange =#{code} limit 1,10 "})
    List<ChangeCode> selectByCodeTypeId(@Param("codeTypeId") Integer paramInteger1, @Param("code") Integer paramInteger2);

    int inserts(@Param("list") List<ChangeCode> paramList);
}
