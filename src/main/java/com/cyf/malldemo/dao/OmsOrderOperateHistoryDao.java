package com.cyf.malldemo.dao;

import com.cyf.malldemo.mbg.model.OmsOrderOperateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/23.
 */
public interface OmsOrderOperateHistoryDao {

    int insertList(@Param("list") List<OmsOrderOperateHistory> list);

}
