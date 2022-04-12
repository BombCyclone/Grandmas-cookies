package com.lutheroaks.tacoswebsite.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public final class AuthenticatedDetailsTest {
    
    @InjectMocks
    private AuthenticatedDetails authenticatedDetails;

    @Mock
    private MemberRepo memberRepo;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLoggedInMemberTest(){
        HttpServletRequest request = mock(HttpServletRequest.class);
        Principal principal = mock(Principal.class);
        when(request.getUserPrincipal()).thenReturn(principal);
        // getName() returns a member's username (email)
        when(principal.getName()).thenReturn("faker1@ilstu.edu");
        Member mockReturn = new Member();
        mockReturn.setFirstName("George");
        when(memberRepo.findMemberByEmail(anyString())).thenReturn(mockReturn);

        // call the method to be tested
        Member retVal = authenticatedDetails.getLoggedInMember(request);
        // confirm that the returned member is the one provided
        assertEquals("George", retVal.getFirstName());
    }
}
