package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.domain.EsProduct;
import com.cyf.malldemo.domain.EsProductRelatedInfo;
import com.cyf.malldemo.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/8/4.
 */
@Api(tags = "EsProductController", description = "搜索商品管理")
@RestController
@RequestMapping(value = "/esProduct")
public class EsProductController {
    @Autowired
    private EsProductService esProductService;

    @ApiOperation(value = "导入所有数据")
    @PostMapping(value = "/importAll")
    public CommonResult<Integer> importAll() {
        int i = esProductService.importAll();
        return CommonResult.success(i);
    }


    @ApiOperation(value = "根据id批量删除商品")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(@RequestParam("ids") List<Long> ids) {
        esProductService.delete(ids);
        return CommonResult.success("ok", 200);
    }


    @ApiOperation(value = "简单搜索")
    @GetMapping(value = "/search/simple")
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam("keywords") String keywords,
                                                      @RequestParam("pageNum") Integer pageNum,
                                                      @RequestParam("pageSize") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keywords, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    @ApiOperation(value = "综合搜索、筛选、排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywords", value = "搜索关键字", paramType = "query"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", paramType = "query"),
            @ApiImplicitParam(name = "productCategoryId", value = "分类id", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低",
                    defaultValue = "0", allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")})
    @GetMapping(value = "/search")
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(value = "keywords", required = false) String keywords,
                                                      @RequestParam(value = "brandId", required = false) Long brandId,
                                                      @RequestParam(value = "productCategoryId", required = false) Long productCategoryId,
                                                      @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                                                      @RequestParam(value = "sort", required = false, defaultValue = "0") Integer sort) {
        Page<EsProduct> esProductPage = esProductService.search(keywords, brandId, productCategoryId, pageNum, pageSize, sort);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }


    @ApiOperation(value = "推荐")
    @GetMapping(value = "/recommend")
    public CommonResult<CommonPage<EsProduct>> recommend(@RequestParam(value = "id") Long id,
                                                         @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                                         @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.recommend(id, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    @ApiOperation(value = "获取搜索的相关品牌、分类及筛选属性")
    @RequestMapping(value = "/search/relate", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<EsProductRelatedInfo> searchRelatedInfo(@RequestParam(required = false) String keyword) {
        EsProductRelatedInfo productRelatedInfo = esProductService.searchRelatedInfo(keyword);
        return CommonResult.success(productRelatedInfo);
    }
}
