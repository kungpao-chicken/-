package com.shanke.forum.mapper;

import com.shanke.forum.entity.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Results({@Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_url", property = "categoryUrl")})
    @Select("select category_id,category_url,title from category")
    List<CategoryInfo> showCategories();

}
