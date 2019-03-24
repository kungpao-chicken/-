package com.shanke.forum.controller;

import com.shanke.forum.entity.CommentInfo;
import com.shanke.forum.entity.PostInfo;
import com.shanke.forum.entity.ReplyInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/post")
@Slf4j
@RestController
public class PostController {

    @Resource
    private PostService postService;

    @PostMapping("/publish")
    public ResultInfo publishPost(HttpServletRequest request, String userId, String categoryId, PostInfo postInfo) {
        if (StringUtils.isEmpty(userId)) {
            return new ResultInfo(1, "用户未登录");
        }

        if (StringUtils.isEmpty(categoryId)) {
            return new ResultInfo(1, "请选择分类");
        }

        List<MultipartFile> files = getMultipartFiles(request);
        return postService.publish(files, userId, categoryId, postInfo);
    }

    @GetMapping("/edit")
    public ResultInfo getOnePost(String postId, String userId) {
        if (StringUtils.isEmpty(userId)) {
            return new ResultInfo(1, "用户未登录");
        }

        if (StringUtils.isEmpty(postId)) {
            return new ResultInfo(1, "请选择要编辑的帖子");
        }

        return postService.getOnePost(postId, userId);
    }

    @PostMapping("/update")
    public ResultInfo updatePost(HttpServletRequest request, String userId, String categoryId, PostInfo postInfo) {

        if (StringUtils.isEmpty(userId)) {
            return new ResultInfo(1, "用户未登录");
        }

        if (StringUtils.isEmpty(categoryId)) {
            return new ResultInfo(1, "请选择分类");
        }

        List<MultipartFile> files = getMultipartFiles(request);
        return postService.update(files, categoryId, postInfo);
    }

    // todo delete comment
    @PostMapping("/delete")
    public ResultInfo delete(PostInfo postInfo) {
        if (StringUtils.isEmpty(postInfo.getUserInfo()) || StringUtils.isEmpty(postInfo.getUserInfo().getUserId())) {
            return new ResultInfo(1, "用户未登录");
        }

        return postService.delete(postInfo);
    }


    /*
        点赞
     */
    @PostMapping("/praise")
    public ResultInfo praise(String userId, String postId) {
        if (StringUtils.isEmpty(userId)) {
            return new ResultInfo(1, "用户未登录");
        }

        if (StringUtils.isEmpty(postId)) {
            return new ResultInfo(1, "请选择帖子");
        }
        return postService.praise(userId, postId);
    }

    @PostMapping("/comment")
    public ResultInfo comment(String userId, CommentInfo commentInfo) {
        if (StringUtils.isEmpty(userId)) {
            return new ResultInfo(1, "用户未登录");
        }

        if (StringUtils.isEmpty(commentInfo.getPostId())) {
            return new ResultInfo(1, "请选择帖子");
        }

        if (StringUtils.isEmpty(commentInfo.getContent())) {
            return new ResultInfo(1, "评论不能为空");
        }
        commentInfo.setSenderId(userId);
        return postService.comment(commentInfo);
    }

    @PostMapping("/reply")
    public ResultInfo reply(String userId, ReplyInfo replyInfo) {
        if (StringUtils.isEmpty(userId)) {
            return new ResultInfo(1, "用户未登录");
        }
        if (StringUtils.isEmpty(replyInfo.getReceiverId())) {
            return new ResultInfo(1, "请选择要回复的评论");
        }
        replyInfo.setSenderId(userId);
        return postService.reply(replyInfo);

    }


    private List<MultipartFile> getMultipartFiles(HttpServletRequest request) {
        List<MultipartFile> files = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
                    MultipartHttpServletRequest.class);
            files = multipartRequest.getFiles("file");
        }
        return files;
    }


}
