package com.cyf.malldemo.dao;

import com.cyf.malldemo.mbg.model.PmsProductVertifyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/17.
 */
public interface PmsProductVertifyRecordDao {


    int insertList(@Param("list") List<PmsProductVertifyRecord> list);
}
