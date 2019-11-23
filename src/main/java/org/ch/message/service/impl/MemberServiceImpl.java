package org.ch.message.service.impl;

import org.ch.message.mapper.MemberMapper;
import org.ch.message.model.Member;
import org.ch.message.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member getMember(Integer memberSeq) {
        return memberMapper.selectByPrimaryKey(memberSeq);
    }

    @Override
    public List<Member> getMembers() {
        return null;
    }

    @Override
    public Object regist(Member member) {
        return memberMapper.insertSelective(member);
    }

    @Override
    public Member modify(Member member) {
        return memberMapper.updateByPrimaryKeySelective(member) == 1 ? member: null;
    }

    @Override
    public Object remove(Integer memberSeq) {
        return memberMapper.deleteByPrimaryKey(memberSeq);
    }
}
