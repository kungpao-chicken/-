package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostImgInfo {

    private String imageId;
    private String imageUrl;
    private String createTime;
    private String updateTime;

    private String postId;

}
