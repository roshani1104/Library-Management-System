package com.library.dao;

import com.library.model.Member;
import java.util.List;

public interface MemberDao {

    boolean addMember(Member member);
    
    Member getMemberById(int memberId);
    
    List<Member> getAllMembers();
    
    boolean updateMember(Member member);
    
    boolean deleteMember(int memberId);

}
