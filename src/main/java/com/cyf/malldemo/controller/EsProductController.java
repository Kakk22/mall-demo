package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.domain.EsProduct;
import com.cyf.malldemo.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/8/4.
 */
@Api(tags = "EsProductController",description = "搜索商品管理")
@RestController
@RequestMapping(value = "/esProduct")
public class EsProductController {
    @Autowired
    private EsProductService esProductService;

    @ApiOperation(value = "导入所有数据")
    @PostMapping(value = "/importAll")
    public CommonResult<Integer> importAll(){
        int i = esProductService.importAll();
        return CommonResult.success(i);
    }


    @ApiOperation(value = "根据id批量删除商品")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(@RequestParam("ids") List<Long> ids){
        esProductService.delete(ids);
        return CommonResult.success("ok",200);
    }


    @ApiOperation(value = "简单搜索")
    @GetMapping(value = "/search/simple")
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam("keywords") String keywords,
                                        @RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize){
        Page<EsProduct> esProductPage = esProductService.search(keywords, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }
}
