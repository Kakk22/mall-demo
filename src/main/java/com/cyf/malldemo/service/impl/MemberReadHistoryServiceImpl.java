package com.cyf.malldemo.service.impl;

import com.cyf.malldemo.domain.MemberReadHistory;
import com.cyf.malldemo.repository.MemberReadHistoryRepository;
import com.cyf.malldemo.service.MemberReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by cyf
 * @date 2020/8/6.
 */
@Service
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {
    @Autowired
    private MemberReadHistoryRepository memberReadHistoryRepository;

    @Override
    public int create(MemberReadHistory readHistory) {
        readHistory.setId(null);
        readHistory.setCreateTime(new Date());
        memberReadHistoryRepository.save(readHistory);
        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<MemberReadHistory> historyList = ids.stream().map(id -> {
                MemberReadHistory memberReadHistory = new MemberReadHistory();
                memberReadHistory.setId(id);
                return memberReadHistory;
            }).collect(Collectors.toList());
            memberReadHistoryRepository.deleteAll(historyList);
            return historyList.size();
        }
        return 0;
    }

    @Override
    public List<MemberReadHistory> list(Long memberId) {
        return memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(memberId);
    }
}
