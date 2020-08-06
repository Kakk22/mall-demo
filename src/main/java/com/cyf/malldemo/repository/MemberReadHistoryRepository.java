package com.cyf.malldemo.repository;

import com.cyf.malldemo.domain.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/8/6.
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory,String> {

    /**
     * 根据用户id按创建时间倒序查询浏览记录
     * @param memberId
     * @return
     */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);

}
