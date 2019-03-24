package com.shanke.forum.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PraiseCommentMapper {

    @Insert("insert into praise_comment(sender_id,praise_post,receiver_id,state,create_time,update_time) values(" +
            "#{userId},#{postId},#{receiverId},1,#{createTime},#{updateTime})")
    void insertPraise(@Param("userId") String userId, @Param("postId") String postId, @Param("receiverId") String receiverId, @Param("createTime") String createTime, @Param("updateTime") String updateTime);

    @Insert("insert into praise_comment(sender_id,comment_id,receiver_id,state,create_time,update_time) values(" +
            "#{senderId},#{commentId},#{receiverId},1,#{createTime},#{updateTime})")
    void insertComment(@Param("senderId") String senderId, @Param("commentId") String commentId, @Param("receiverId") String receiverId, @Param("createTime") String createTime, @Param("updateTime") String updateTime);

    @Insert("insert into praise_comment(sender_id,reply_id,receiver_id,state,create_time,update_time) values(" +
            "#{senderId},#{replyId},#{receiverId},1,#{createTime},#{updateTime})")
    void insertReply(@Param("senderId") String senderId, @Param("replyId") String replyId, @Param("receiverId") String receiverId, @Param("createTime") String createTime, @Param("updateTime") String updateTime);
}
