package com.cyf.malldemo.service;

import com.cyf.malldemo.domain.MemberReadHistory;

import java.util.List;

/**会员浏览记录管理Service
 * @author by cyf
 * @date 2020/8/6.
 */
public interface MemberReadHistroyService {

    /**
     * 生成浏览记录
     * @param readHistory
     * @return
     */
    int create(MemberReadHistory readHistory);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int delete(List<String> ids);

    /**
     * 获取用户浏览历史记录
     * @param memberId
     * @return
     */
    List<MemberReadHistory> list(Long memberId);

}
