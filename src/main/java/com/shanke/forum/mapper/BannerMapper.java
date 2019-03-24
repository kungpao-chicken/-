package com.shanke.forum.mapper;

import com.shanke.forum.entity.BannerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BannerMapper {

    @Results({@Result(column = "banner_id", property = "bannerId"),
            @Result(column = "banner_url", property = "bannerUrl"),
            @Result(column = "content_url", property = "contentUrl")})
    @Select("select banner_id,banner_url,content_url from banner")
    List<BannerInfo> getBanners();
}
