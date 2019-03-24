package com.shanke.forum.mapper;

import com.shanke.forum.entity.ReplyInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyMapper {

    @Insert("insert into reply(reply_id,sender_id,content,receiver_id,comment_id,create_time,update_time) values(" +
            "#{replyInfo.replyId,replyInfo.senderId,replyInfo.content,replyInfo.receiverId,replyInfo.commentId,replyInfo.createTime,replyInfo.updateTime})")
    void insert(ReplyInfo replyInfo);

}

