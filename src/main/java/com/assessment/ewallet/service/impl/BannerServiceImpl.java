package com.assessment.ewallet.service.impl;

import com.assessment.ewallet.entity.Banner;
import com.assessment.ewallet.repository.BannerRepository;
import com.assessment.ewallet.service.BannerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public List<Banner> getAllBanner() {
        return bannerRepository.selectAllBanner();
    }
}
