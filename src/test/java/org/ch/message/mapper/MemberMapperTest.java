package org.ch.message.mapper;

import static org.junit.Assert.assertTrue;

import org.ch.message.model.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
//@ActiveProfiles("dev")
public class MemberMapperTest {

	@Autowired
	private MemberMapper memberMapper;
	
	@Test
	public void testSelectByPrimaryKey() {
		
		Integer memberSeq = 1;
		Member member = memberMapper.selectByPrimaryKey(memberSeq);
		assertTrue("채성문".equals(member.getUserId()));
	}
}
