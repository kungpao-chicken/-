package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentInfo {

    private String commentId;
    private String content;
    private String senderId;
    private String postId;
    private String createTime;
    private String updateTime;

}
