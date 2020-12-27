package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.InternetResourcesDao;
import com.mem.vo.business.base.model.po.InternetResources;
import com.mem.vo.business.base.model.po.InternetResourcesQuery;
import com.mem.vo.business.base.service.InternetResourcesService;
import com.mem.vo.common.constant.ResourceSuffixConstant;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>InternetResourcesService<br>
 */
@Service("internetResourcesService")
public class InternetResourcesServiceImpl implements InternetResourcesService {
    private final static Logger log = LogManager.getLogger(InternetResourcesServiceImpl.class);


    @Resource
    private InternetResourcesDao internetResourcesDao;
    @Resource
    private FileUtil fileUtil;

    @Override
    public int insert(InternetResources internetResources) {
        return internetResourcesDao.insert(internetResources);
    }

    @Override
    public int updateById(InternetResources internetResources) {
        return internetResourcesDao.updateById(internetResources);
    }

    @Override
    public int deleteById(Long id) {
        return internetResourcesDao.deleteById(id);
    }

    @Override
    public InternetResources findById(Long id) {
        return internetResourcesDao.findById(id);
    }

    @Override
    public List<InternetResources> findByCondition(InternetResourcesQuery query) {
        return internetResourcesDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page, InternetResourcesQuery query) {
        internetResourcesDao.findByCondition(page, query);
    }

    @Override
    public List<InternetResources> findQiniuResources(int type) {
        List<InternetResources> result = new ArrayList<>();
        List<String> resourcesList = fileUtil.findFiles();
        if (resourcesList.size() == 0) {
            throw new BizException("资源文件列表为空！");
        }
//        for (String item : resourcesList) {
//            if (item.endsWith(ResourceSuffixConstant.resourceMap.get(type))) {
//                result.add(new InternetResources(item, fileUtil.getFileUrl(item), type));
//            }
//        }
        if (!ResourceSuffixConstant.resourceMap.containsKey(type)) {
            throw new BizException("不存在type为:" + type + "的定义");
        }
        resourcesList.stream()
                .filter(item -> item.endsWith(ResourceSuffixConstant.resourceMap.get(type)))
                .forEach(item -> result.add(new InternetResources(item, fileUtil.getFileUrl(item), type)));
        return result;
    }
}
