package com.chen.cmsservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.cmsservice.entity.CrmBanner;
import com.chen.cmsservice.mapper.CrmBannerMapper;
import com.chen.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-08-10
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectAllBanner'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        List<CrmBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }

    @Override
    public void pageBanner(Page<CrmBanner> pageParam, Object o) {
        baseMapper.selectPage(pageParam,null);
    }

    @Override
    public CrmBanner getBannerById(String id) {
        return baseMapper.selectById(id);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }
}
