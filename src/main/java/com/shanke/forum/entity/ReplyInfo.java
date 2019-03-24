package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyInfo {

    private String replyId;
    private String senderId;
    private String content;
    private String receiverId;
    private String commentId;
    private String createTime;
    private String updateTime;

}
