package com.ilkaygunel.wrapper;

import com.ilkaygunel.entities.Member;

import javax.validation.Valid;
import java.util.List;

public class MemberWrapper {

    @Valid
    private List<Member> memberList;

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }
}
