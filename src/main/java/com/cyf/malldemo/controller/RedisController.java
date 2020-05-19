package com.cyf.malldemo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.mbg.model.PmsBrand;
import com.cyf.malldemo.service.PmsBrandService;
import com.cyf.malldemo.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author by cyf
 * @date 2020/5/19.
 */
@Api(tags = "RedisController", description = "redis测试类")
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PmsBrandService pmsBrandService;

    @ApiOperation("获取所有商品品牌，并存入redis")
    @RequestMapping(value = "/simpleTest",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> simpleTest(){
        List<PmsBrand> brandList = pmsBrandService.listBrand(1, 3);
        PmsBrand pmsBrand = brandList.get(0);
        String key = "redis:simple:"+pmsBrand.getId();
        redisService.set(key,pmsBrand);
        PmsBrand cacheBrand = (PmsBrand) redisService.get(key);
        return CommonResult.success(cacheBrand);
    }

    @ApiOperation("测试hash结构缓存")
    @RequestMapping(value = "/testHash",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> testHash(){
        List<PmsBrand> brandList = pmsBrandService.listBrand(1, 3);
        PmsBrand pmsBrand = brandList.get(1);
        String key = "redis:hash:"+pmsBrand.getId();
        Map<String, Object> map = BeanUtil.beanToMap(pmsBrand);
        redisService.hSetAll(key,map);
        Map<Object, Object> cashmap = redisService.hGetAll(key);
        PmsBrand cacheBrand = BeanUtil.mapToBean(cashmap,PmsBrand.class,true);
        return CommonResult.success(cacheBrand);
    }

    @ApiOperation("测试set结构缓存")
    @RequestMapping(value = "/testSet",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Set<Object>> testSet(){
        List<PmsBrand> brandList = pmsBrandService.listBrand(1, 3);
        String key = "redis:set:all";
        redisService.sAdd(key,(Object[]) ArrayUtil.toArray(brandList,PmsBrand.class));
        Set<Object> objects = redisService.sMembers(key);
        return CommonResult.success(objects);
    }


    @ApiOperation("测试List结构缓存")
    @RequestMapping(value = "/testList",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Object>> testList(){
        List<PmsBrand> brandList = pmsBrandService.listBrand(1, 3);
        String key = "redis:List:all";
        redisService.lPushAll(key,ArrayUtil.toArray(brandList,PmsBrand.class));
        redisService.lRemove(key,1,brandList.get(0));
        List<Object> objects = redisService.lRange(key, 0, 3);
        return CommonResult.success(objects);
    }

}
