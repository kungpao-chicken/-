package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BannerInfo {

    private String bannerId;
    private String bannerUrl;
    private String contentUrl;
    private String createTime;
    private String updateTime;

}
