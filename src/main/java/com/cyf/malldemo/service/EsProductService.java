package com.cyf.malldemo.service;

import com.cyf.malldemo.domain.EsProduct;
import com.cyf.malldemo.domain.EsProductRelatedInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 商品搜索管理Service
 *
 * @author by cyf
 * @date 2020/8/3.
 */
public interface EsProductService {
    /**
     * 从数据库中导入所有数据进Es
     *
     * @return
     */
    int importAll();

    /**
     * 根据id删除商品
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id创建商品
     *
     * @param id
     * @return
     */
    EsProduct create(Long id);

    /**
     * 根据id批量删除商品
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索名称或者副标题
     *
     * @param keywords 关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<EsProduct> search(String keywords, Integer pageNum, Integer pageSize);

    /**
     * 根据关键字搜索名称或者副标题复合查询
     */
    Page<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * 根据商品id推荐相关商品
     *
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize);

    /**
     * 获取搜索词相关品牌、分类、属性
     *
     * @param keyword 关键字
     * @return
     */
    EsProductRelatedInfo searchRelatedInfo(String keyword);
}
