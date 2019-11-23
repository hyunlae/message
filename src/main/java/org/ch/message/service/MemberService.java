package org.ch.message.service;

import org.ch.message.model.Member;

import java.util.List;

public interface MemberService {

	Member getMember(Integer memberSeq);

	List<Member> getMembers();

	Object regist(Member member);

	Member modify(Member member);

	Object remove(Integer memberSeq);
}
