package com.cyf.malldemo.service;

import com.cyf.malldemo.mbg.model.PmsBrand;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/5/16.
 */

public interface PmsBrandService {

    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand brand);

    int updateBrand(Long id, PmsBrand brand);

    int deleteBrand(Long id);

    List<PmsBrand> listBrand(int pageNum, int pageSize);

    PmsBrand getBrand(Long id);
}
