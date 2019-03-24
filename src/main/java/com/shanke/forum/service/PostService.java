package com.shanke.forum.service;

import com.shanke.forum.entity.CommentInfo;
import com.shanke.forum.entity.PostInfo;
import com.shanke.forum.entity.ReplyInfo;
import com.shanke.forum.entity.ResultInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    ResultInfo publish(List<MultipartFile> files, String userId, String categoryId, PostInfo postInfo);

    ResultInfo update(List<MultipartFile> files, String categoryId, PostInfo postInfo);

    ResultInfo getOnePost(String postId, String userId);

    ResultInfo delete(PostInfo postInfo);

    ResultInfo praise(String userId, String postId);

    ResultInfo comment(CommentInfo commentInfo);

    ResultInfo reply(ReplyInfo replyInfo);
}
