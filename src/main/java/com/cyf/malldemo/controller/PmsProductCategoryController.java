package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonPage;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.dto.PmsProductCategoryParam;
import com.cyf.malldemo.dto.PmsProductCategoryWithChildren;
import com.cyf.malldemo.mbg.model.PmsProductCategory;
import com.cyf.malldemo.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/6/14.
 */
@Api(tags = "PmsProductCategoryController", description = "商品分类管理")
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

    @ApiOperation("创建商品分类")
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductCategoryParam param) {
        int count = pmsProductCategoryService.create(param);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("创建失败");
    }

    @ApiOperation("查询所有一级分类及子分类")
    @GetMapping("/list/withChildren")
    public CommonResult listWithChildren() {
        List<PmsProductCategoryWithChildren> withChildren = pmsProductCategoryService.listWithChildren();
        return CommonResult.success(withChildren);
    }

    @ApiOperation("根据parentId分页查找")
    @GetMapping("/list/{parentId}")
    public CommonResult list(@PathVariable Long parentId,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProductCategory> list = pmsProductCategoryService.list(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation("删除分类")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int i = pmsProductCategoryService.delete(id);
        return i > 0 ? CommonResult.success(i) : CommonResult.failed("删除失败 id="+ i);
    }

    @ApiOperation("通过id获取分类")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable Long id) {
        PmsProductCategory item = pmsProductCategoryService.getItem(id);
        return CommonResult.success(item);
    }

    @ApiOperation("更新")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,
                               @RequestBody PmsProductCategoryParam param) {
        int i = pmsProductCategoryService.update(id, param);
        return i > 0 ? CommonResult.success(i) : CommonResult.failed("更新失败");
    }

}
