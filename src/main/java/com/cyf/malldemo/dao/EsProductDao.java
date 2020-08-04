package com.cyf.malldemo.dao;

import com.cyf.malldemo.domain.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**  搜索系统自定义商品Dao
 * @author by cyf
 * @date 2020/8/3.
 */
public interface EsProductDao {
    /**
     * 查询EsProduct
     * @param id
     * @return
     */
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
