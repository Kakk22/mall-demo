package com.cyf.malldemo.controller;

import com.cyf.malldemo.common.CommonResult;
import com.cyf.malldemo.domain.MemberReadHistory;
import com.cyf.malldemo.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/8/6.
 */
@Api(tags = "MemberReadHistoryController",description = "会员浏览历史记录管理")
@RestController
@RequestMapping(value = "/memberReadHistory")
public class MemberReadHistoryController {
    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @ApiOperation("新增记录")
    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody MemberReadHistory memberReadHistory) {
        int count = memberReadHistoryService.create(memberReadHistory);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("操作失败");
    }

    @ApiOperation("批量删除记录")
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam List<String> ids) {
        int count = memberReadHistoryService.delete(ids);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed("操作失败");
    }
    @ApiOperation("根据会员id获取浏览记录")
    @GetMapping(value = "/{memberId}")
    public CommonResult list(@PathVariable Long memberId){
        List<MemberReadHistory> list = memberReadHistoryService.list(memberId);
        return CommonResult.success(list);
    }
}
