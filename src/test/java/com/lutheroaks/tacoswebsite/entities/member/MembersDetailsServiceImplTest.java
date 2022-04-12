package com.lutheroaks.tacoswebsite.entities.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
public final class MembersDetailsServiceImplTest {
    
    @InjectMocks
    private MemberDetailsServiceImpl detailsServiceImpl;

    @Mock
    private MemberRepo repository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void loadUserByUsernameMemberFoundTest() {
        Member mockReturn = new Member();
        mockReturn.setEmail("me@icloud.com");
        when(repository.findMemberByEmail(anyString())).thenReturn(mockReturn);
        MemberDetails retVal = (MemberDetails) detailsServiceImpl.loadUserByUsername("me@icloud.com");
        assertEquals("me@icloud.com", retVal.getUsername());
    }

    @Test
    public void loadUserByUsernameMemberNotFoundTest(){
        when(repository.findMemberByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,
        ()->{
            detailsServiceImpl.loadUserByUsername("me@icloud.com");
        });
    }
    
}
