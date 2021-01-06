package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.PlaceArtistMapper;
import com.mem.vo.business.base.model.po.PlaceArtist;
import com.mem.vo.business.base.service.PlaceArtistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("placeArtistService")
@Transactional
public class PlaceArtistServiceImpl implements PlaceArtistService {

    @Resource
    private PlaceArtistMapper placeArtistMapper;

    @Override
    public List<String> findByPlaceId(Long id) {
        List<PlaceArtist> list = placeArtistMapper.selectByPlaceId(id);
        List<String> list2 = new ArrayList<>();
        for (PlaceArtist placeArtist : list) {
            list2.add(placeArtist.getArtistId() + "");
        }
        return list2;
    }
}
