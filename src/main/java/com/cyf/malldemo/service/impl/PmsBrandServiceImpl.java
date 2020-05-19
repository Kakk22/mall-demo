package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.config.RedisConfig;
import com.cyf.malldemo.mbg.mapper.PmsBrandMapper;
import com.cyf.malldemo.mbg.model.PmsBrand;
import com.cyf.malldemo.mbg.model.PmsBrandExample;
import com.cyf.malldemo.service.PmsBrandService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/5/16.
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        List<PmsBrand> pmsBrands = pmsBrandMapper.selectByExample(new PmsBrandExample());
        return pmsBrands;
    }

    @Override
    public int createBrand(PmsBrand brand) {
        return pmsBrandMapper.insert(brand);
    }

    @CacheEvict(value = RedisConfig.REDIS_DATABASE_MALL,key = "'pms:brand:'+#id")
    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return pmsBrandMapper.updateByPrimaryKeySelective(brand);
    }

    @CacheEvict(value = RedisConfig.REDIS_DATABASE_MALL,key = "'pms:brand:'+#id")
    @Override
    public int deleteBrand(Long id) {
        return pmsBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listBrand(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }

    @Cacheable(value = RedisConfig.REDIS_DATABASE_MALL,key ="'pms:brand:'+#id",unless  = "#result==null")
    @Override
    public PmsBrand getBrand(Long id) {
        return pmsBrandMapper.selectByPrimaryKey(id);
    }
}
