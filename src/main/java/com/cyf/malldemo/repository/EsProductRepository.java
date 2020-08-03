package com.cyf.malldemo.repository;

import com.cyf.malldemo.domain.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**商品ES操作类
 * @author by cyf
 * @date 2020/8/3.
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct,Long> {
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);
}
