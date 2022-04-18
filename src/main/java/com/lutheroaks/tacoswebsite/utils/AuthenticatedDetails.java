package com.lutheroaks.tacoswebsite.utils;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedDetails {

    @Autowired
    private MemberRepo memberRepo;

    /**
     * returns the member that submitted a request
     * @param request
     * @return
     */
    public Member getLoggedInMember(final HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();
        Member submitter = memberRepo.findMemberByEmail(userName);
        return submitter;
    }
    
}
