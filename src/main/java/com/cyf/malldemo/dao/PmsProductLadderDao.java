package com.cyf.malldemo.dao;

import com.cyf.malldemo.mbg.model.PmsProductLadder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/17.
 */
public interface PmsProductLadderDao {
    /**
     * 批量插入
     * @return
     */
    int insertList(@Param("list") List<PmsProductLadder> productLadderList);
}
