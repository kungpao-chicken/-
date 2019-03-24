package com.shanke.forum.service.impl;

import com.shanke.forum.entity.CategoryInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.mapper.CategoryMapper;
import com.shanke.forum.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ResultInfo showCategories() {
        List<CategoryInfo> CategoryInfos = categoryMapper.showCategories();
        return new ResultInfo(0, CategoryInfos);
    }

}
