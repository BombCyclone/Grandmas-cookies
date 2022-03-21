package com.lutheroaks.tacoswebsite.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepo repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = repository.findMemberByEmail(email);

        if (member == null){
            throw new UsernameNotFoundException("Could not find member with email address: " + email);
        }
        return new MemberDetails(member);
    }
    
}
