package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.ChangeCode;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ChangeCodeDao {
    @Update({"UPDATE changecode set isChange =#{code} WHERE id =#{id}"})
    int updateById(@Param("id") Integer id, @Param("code") Integer code);

    @Select({"SELECT * from changecode where id= #{id} and isChange =#{code} "})
    List<ChangeCode> selectByChangeCodeId(@Param("id") Integer id, @Param("code") Integer code);

    @Select({"SELECT * from changecode where id= #{id}"})
    ChangeCode selectById(Integer id);

    @Select({"SELECT * from changecode where codeType= #{codeTypeId} and isChange =#{code} limit 1,10 "})
    List<ChangeCode> selectByCodeTypeId(@Param("codeTypeId") Integer codeTypeId, @Param("code") Integer code);

    int inserts(@Param("list") List<ChangeCode> list);
}
