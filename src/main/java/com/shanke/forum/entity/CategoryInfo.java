package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryInfo {

    private String categoryId;
    private String categoryUrl;
    private String title;
    private String createTime;
    private String updateTime;
}
