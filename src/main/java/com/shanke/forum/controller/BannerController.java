package com.shanke.forum.controller;

import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.service.BannerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BannerController {

    @Resource
    private BannerService bannerService;

    @GetMapping("/banners")
    public ResultInfo showBanners() {
        return bannerService.showBanners();
    }

}
