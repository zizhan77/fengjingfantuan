package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.PlaceArtist;
import java.util.List;

public interface PlaceArtistMapper {
    int deleteByPrimaryKey(Integer paramInteger);

    int insert(PlaceArtist paramPlaceArtist);

    int insertSelective(PlaceArtist paramPlaceArtist);

    PlaceArtist selectByPrimaryKey(Integer paramInteger);

    int updateByPrimaryKeySelective(PlaceArtist paramPlaceArtist);

    int updateByPrimaryKey(PlaceArtist paramPlaceArtist);

    int insertList(List<String> paramList, int id);

    int deleteByPlaceId(Long paramLong);

    List<PlaceArtist> selectByPlaceId(Long paramLong);
}
