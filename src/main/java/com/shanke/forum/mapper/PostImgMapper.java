package com.shanke.forum.mapper;

import com.shanke.forum.entity.PostImgInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostImgMapper {

    @Insert("insert into post_img(image_id,image_url,post_id,create_time,update_time) values(" +
            "#{postImgInfo.imageId},#{postImgInfo.imageUrl},#{postImgInfo.postId},#{postImgInfo.createTime},#{postImgInfo.updateTime})")
    void insert(@Param("postImgInfo") PostImgInfo postImgInfo);

    @Select("select image_id from post_img where post_id = #{postId}")
    List<String> getImgByPostId(@Param("postId") String postId);

    @Delete("delete from post_img where image_id = #{imageId}")
    void deleteByImageId(@Param("imageId") String imageId);

    @Results({@Result(column = "image_id", property = "imageId"),
            @Result(column = "image_url", property = "imageUrl"),
            @Result(column = "update_time", property = "updateTime")})
    @Select("select image_id,image_url,update_time from post_img where post_id = #{postId}")
    List<PostImgInfo> getByPostId(@Param("postId") String postId);

    @Delete("delete from user where post_id = #{postId}")
    void deleteByPostId(@Param("postId") String postId);
}
