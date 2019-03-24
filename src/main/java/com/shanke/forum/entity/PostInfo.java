package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostInfo {
    private String postId;
    private String content;
    private Integer praiseNum;
    private Integer commentNum;
    private String createTime;
    private String updateTime;

    private UserInfo userInfo;
    private CategoryInfo categoryInfo;

    private List<PostImgInfo> postImgInfos;

    public PostInfo() {
    }

    public PostInfo(String postId) {
        this.postId = postId;
    }
}
