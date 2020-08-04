package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.dao.EsProductDao;
import com.cyf.malldemo.domain.EsProduct;
import com.cyf.malldemo.repository.EsProductRepository;
import com.cyf.malldemo.service.EsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

/**
 * @author by cyf
 * @date 2020/8/3.
 */
public class EsProductServiceImpl implements EsProductService {

    @Autowired
    private EsProductDao esProductDao;

    @Autowired
    private EsProductRepository esProductRepository;

    @Override
    public int importAll() {
        List<EsProduct> allEsProductList = esProductDao.getAllEsProductList(null);
        Iterable<EsProduct> esProductsIterable = esProductRepository.saveAll(allEsProductList);
        Iterator<EsProduct> iterator = esProductsIterable.iterator();
        int count = 0;
        while (iterator.hasNext()){
            count++;
            iterator.next();
        }
        return count;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public EsProduct create(Long id) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public Page<EsProduct> serach(String keywords, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Page<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        return null;
    }

    @Override
    public Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize) {
        return null;
    }
}
