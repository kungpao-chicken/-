package com.shanke.forum.service.impl;

import com.shanke.forum.entity.BannerInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.mapper.BannerMapper;
import com.shanke.forum.service.BannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerMapper bannerMapper;

    @Override
    public ResultInfo showBanners() {
        List<BannerInfo> bannerInfos = bannerMapper.getBanners();
        return new ResultInfo(0, bannerInfos);
    }

}
