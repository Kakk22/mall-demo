package com.cyf.malldemo.dao;

import com.cyf.malldemo.dto.OmsOrderDeliveryParam;
import com.cyf.malldemo.dto.OmsOrderQueryParam;
import com.cyf.malldemo.mbg.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/22.
 */
public interface OmsOrderDao {

    List<OmsOrder>  getList(@Param("queryParam")OmsOrderQueryParam queryParam);

    /**
     * 批量发货
     * @param list
     * @return
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam>  list);
}
