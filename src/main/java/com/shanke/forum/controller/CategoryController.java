package com.shanke.forum.controller;

import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResultInfo showCategories() {
        return categoryService.showCategories();
    }

}
