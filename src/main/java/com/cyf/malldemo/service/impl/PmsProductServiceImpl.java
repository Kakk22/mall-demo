package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.dao.*;
import com.cyf.malldemo.dto.PmsProductParam;
import com.cyf.malldemo.dto.PmsProductQueryParam;
import com.cyf.malldemo.mbg.mapper.PmsProductMapper;
import com.cyf.malldemo.mbg.model.PmsProduct;
import com.cyf.malldemo.mbg.model.PmsProductExample;
import com.cyf.malldemo.mbg.model.PmsProductVertifyRecord;
import com.cyf.malldemo.mbg.model.PmsSkuStock;
import com.cyf.malldemo.service.PmsProductService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/17.
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PmsProductService.class);
    @Autowired
    private PmsProductMapper pmsProductMapper;
    @Autowired
    private PmsProductLadderDao pmsProductLadderDao;
    @Autowired
    private PmsMemberPriceDao pmsMemberPriceDao;
    @Autowired
    private PmsProductAttributeValueDao pmsProductAttributeValueDao;
    @Autowired
    private PmsProductFullReductionDao pmsProductFullReductionDao;
    @Autowired
    private CmsSubjectProductRelationDao cmsSubjectProductRelationDao;
    @Autowired
    private CmsPrefrenceAreaProductRelationDao cmsPrefrenceAreaProductRelationDao;
    @Autowired
    private PmsSkuStockDao pmsskuStockDao;
    @Autowired
    private PmsProductVertifyRecordDao pmsProductVertifyRecordDao;

    @Override
    public int create(PmsProductParam param) {
        int count;
        //创建商品
        PmsProduct product = new PmsProduct();
        product = param;
        product.setId(null);
        pmsProductMapper.insertSelective(product);
        //根据促销类型设置价格：会员价格、阶梯价格、满减价格
        Long productId = product.getId();
        //添加商品阶梯价格设置
        relationInsertList(pmsProductLadderDao, param.getProductLadderList(), productId);
        //添加会员价格设置
        relationInsertList(pmsMemberPriceDao, param.getMemberPriceList(), productId);
        //添加满减价格设置
        relationInsertList(pmsProductFullReductionDao, param.getProductFullReductionList(), productId);
        //添加商品参数,添加自定义商品规格
        relationInsertList(pmsProductAttributeValueDao, param.getProductAttributeValueList(), productId);
        //关联专题
        relationInsertList(cmsSubjectProductRelationDao, param.getSubjectProductRelationList(), productId);
        //关联优选
        relationInsertList(cmsPrefrenceAreaProductRelationDao, param.getPrefrenceAreaProductRelationList(), productId);
        //处理sku的编码
        handleSkuStockCode(param.getSkuStockList(), productId);
        //添加sku库存信息
        relationInsertList(pmsskuStockDao, param.getSkuStockList(), productId);
        count = 1;
        return count;
    }

    @Override
    public List<PmsProduct> list(PmsProductQueryParam pmsProductQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(pmsProductQueryParam.getKeyword())) {
            criteria.andNameLike("%" + pmsProductQueryParam.getKeyword() + "%");
        }
        if (pmsProductQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(pmsProductQueryParam.getBrandId());
        }
        if (pmsProductQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(pmsProductQueryParam.getProductCategoryId());
        }
        if (pmsProductQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(pmsProductQueryParam.getPublishStatus());
        }
        if (pmsProductQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(pmsProductQueryParam.getVerifyStatus());
        }
        if (!StringUtils.isEmpty(pmsProductQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(pmsProductQueryParam.getProductSn());
        }
        return pmsProductMapper.selectByExample(example);
    }

    /**
     * 批量更改删除状态
     *
     * @param ids
     * @return
     */
    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setDeleteStatus(deleteStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(pmsProduct, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
       if (newStatus != 0 && newStatus != 1) {
            return -1;
        }
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setNewStatus(newStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(pmsProduct,example);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setVerifyStatus(verifyStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        int count = pmsProductMapper.updateByExampleSelective(pmsProduct, example);
        List<PmsProductVertifyRecord> list = new ArrayList<>(10);
        //修改审核后 要加入审核记录
        for (Long id : ids) {
            PmsProductVertifyRecord record = new PmsProductVertifyRecord();
            record.setCreateTime(new Date());
            record.setDetail(detail);
            record.setProductId(id);
            record.setVertifyMan("test");
            record.setStatus(verifyStatus);
            list.add(record);
        }
        pmsProductVertifyRecordDao.insertList(list);
        return count;
    }

    /**
     * 处理sku的编码
     *
     * @param skuStockList
     * @param productId
     */
    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if (CollectionUtils.isEmpty(skuStockList)) {
            return;
        }
        for (int i = 0; i < skuStockList.size(); i++) {
            PmsSkuStock pmsSkuStock = skuStockList.get(i);
            if (StringUtils.isEmpty(pmsSkuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //商品id 4位
                sb.append(String.format("%04d", productId));
                //3位索引id
                sb.append(String.format("%03d", i + 1));
                pmsSkuStock.setSkuCode(sb.toString());
            }
        }
    }

    /**
     * 利用反射 插入和建立关系表
     *
     * @param dao       可操作的dao
     * @param dataList  要插入的数据
     * @param productId 商品的id
     */
    private void relationInsertList(Object dao, List dataList, Long productId) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        try {
            //遍历把数据id设置为null，设置对应的商品id
            for (Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            //反射使用dao内方法批量插入
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            LOGGER.warn("创建商品出错 : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
