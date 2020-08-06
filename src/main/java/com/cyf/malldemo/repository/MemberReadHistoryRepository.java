package com.cyf.malldemo.repository;

import com.cyf.malldemo.domain.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author by cyf
 * @date 2020/8/6.
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory,String> {
}
