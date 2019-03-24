package com.shanke.forum.service.impl;

import com.shanke.forum.entity.*;
import com.shanke.forum.mapper.*;
import com.shanke.forum.remote.UploadSrv;
import com.shanke.forum.service.PostService;
import com.shanke.forum.service.UserService;
import com.shanke.forum.utils.TokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;

    @Resource
    private UploadSrv uploadSrv;

    @Resource
    private PostImgMapper postImgMapper;

    @Resource
    private UserService userService;

    @Resource
    private PraiseCommentMapper praiseCommentMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ReplyMapper replyMapper;

    @Override
    public ResultInfo publish(List<MultipartFile> files, String userId, String categoryId, PostInfo postInfo) {

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        postInfo.setPostId(TokenUtil.getMd5(UUID.randomUUID().toString()));
        postInfo.setCreateTime(currentDate);
        postInfo.setUpdateTime(currentDate);

        PostImgInfo postImgInfo = new PostImgInfo();
        postImgInfo.setPostId(postInfo.getPostId());
        postImgInfo.setCreateTime(currentDate);
        postImgInfo.setUpdateTime(currentDate);

        if (files != null) {
            for (MultipartFile file : files) {

                postImgInfo.setImageId(TokenUtil.getMd5(UUID.randomUUID().toString()));
                String img = uploadSrv.uploadFile(file);

                if (StringUtils.isEmpty(img)) {
                    return new ResultInfo(1, "系统错误，上传图片失败");
                }

                if ("not-image".equals(img)) {
                    return new ResultInfo(1, "上传文件中存在非图片");
                }

                postImgInfo.setImageUrl(String.format("http://60.205.187.142:9999/forum/%s", img));

                postImgMapper.insert(postImgInfo);
            }
        }

        postMapper.insert(userId, categoryId, postInfo);

        return new ResultInfo(0, new PostInfo(postInfo.getPostId()));
    }

    @Override
    public ResultInfo update(List<MultipartFile> files, String categoryId, PostInfo postInfo) {

        List<String> ImgIds = postImgMapper.getImgByPostId(postInfo.getPostId());

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        postInfo.setUpdateTime(currentDate);

        PostImgInfo postImgInfo = new PostImgInfo();
        postImgInfo.setPostId(postInfo.getPostId());
        postImgInfo.setCreateTime(currentDate);
        postImgInfo.setUpdateTime(currentDate);

        if (files != null) {
            for (MultipartFile file : files) {

                postImgInfo.setImageId(TokenUtil.getMd5(UUID.randomUUID().toString()));
                String img = uploadSrv.uploadFile(file);

                if (StringUtils.isEmpty(img)) {
                    return new ResultInfo(1, "系统错误，上传图片失败");
                }

                if ("not-image".equals(img)) {
                    return new ResultInfo(1, "上传文件中存在非图片");
                }

                postImgInfo.setImageUrl(String.format("http://60.205.187.142:9999/forum/%s", img));

                postImgMapper.insert(postImgInfo);
            }
        }

        for (String imgId : ImgIds) {
            postImgMapper.deleteByImageId(imgId);
        }

        postMapper.update(categoryId, postInfo);
        return new ResultInfo(0);
    }

    @Override
    public ResultInfo getOnePost(String postId, String userId) {
        PostInfo onePost = postMapper.getOnePost(postId, userId);

        List<PostImgInfo> postImgInfos = postImgMapper.getByPostId(postId);
        onePost.setPostImgInfos(postImgInfos);

        return new ResultInfo(0, onePost);
    }

    @Override
    public ResultInfo delete(PostInfo postInfo) {
        String postId = postInfo.getPostId();
        postImgMapper.deleteByPostId(postId);
        postMapper.delete(postId);
        return new ResultInfo(0);
    }

    @Override
    public ResultInfo praise(String userId, String postId) {
        PostInfo postInfo = postMapper.getByPostId(postId);
        int praiseNum = postInfo.getPraiseNum();
        postMapper.updatePraise(praiseNum + 1, postId, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        String receiverId = postInfo.getUserInfo().getUserId();
        userService.updatePraiseNum(receiverId);

        praiseCommentMapper.insertPraise(userId, postId, receiverId, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return new ResultInfo(0);
    }

    @Override
    public ResultInfo comment(CommentInfo commentInfo) {
        PostInfo postInfo = postMapper.getByPostId(commentInfo.getPostId());
        int commentNum = postInfo.getCommentNum();
        postMapper.updateComment(commentNum + 1, commentInfo.getPostId(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        String receiverId = postInfo.getUserInfo().getUserId();
        userService.updateCommentNum(receiverId);

        commentInfo.setCommentId(TokenUtil.getMd5(UUID.randomUUID().toString()));
        commentInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        commentInfo.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        commentMapper.insert(commentInfo);

        praiseCommentMapper.insertComment(commentInfo.getSenderId(), commentInfo.getCommentId(), receiverId, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return new ResultInfo(0);
    }

    @Override
    public ResultInfo reply(ReplyInfo replyInfo) {
        String receiverId = replyInfo.getReceiverId();
        userService.updateCommentNum(receiverId);

        replyInfo.setReplyId(TokenUtil.getMd5(UUID.randomUUID().toString()));
        replyInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        replyInfo.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        replyMapper.insert(replyInfo);

        praiseCommentMapper.insertComment(replyInfo.getSenderId(), replyInfo.getReplyId(), receiverId, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        return new ResultInfo(0);
    }
}
