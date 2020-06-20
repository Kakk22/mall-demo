package com.cyf.malldemo.dao;

import com.cyf.malldemo.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;

/** 自定义根据id查询编辑所需要的商品信息
 * @author by cyf
 * @date 2020/6/19.
 */
public interface PmsProductDao {
    PmsProductResult getResultInfo(@Param("id") Long id);
}
