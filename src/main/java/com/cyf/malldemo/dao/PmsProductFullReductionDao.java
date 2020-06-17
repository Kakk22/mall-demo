package com.cyf.malldemo.dao;

import com.cyf.malldemo.mbg.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/17.
 */
public interface PmsProductFullReductionDao {

    /**
     * 批量插入
     * @return
     */
    int insertList(@Param("list") List<PmsProductFullReduction> list);
}
