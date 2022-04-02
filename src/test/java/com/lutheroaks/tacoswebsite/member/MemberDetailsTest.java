package com.lutheroaks.tacoswebsite.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@SpringBootTest
public final class MemberDetailsTest {
    
    @InjectMocks
    private MemberDetails details;

    @Mock
    private Member member;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }    

    @Test
    public void getAuthoritiesTest() {
        when(member.getRole()).thenReturn("ROLE_ADMIN");
        Collection<? extends GrantedAuthority> retVal = details.getAuthorities();
        System.out.println(retVal);
        assertEquals(1,retVal.size());
    }

    @Test
    public void getPasswordTest() {
        when(member.getPassword()).thenReturn("password");
        String retVal = details.getPassword();
        assertEquals("password", retVal);
    }
    
    @Test
    public void getUsernameTest() {
        when(member.getEmail()).thenReturn("fakeemail@gmail.com");
        String retVal = details.getUsername();
        assertEquals("fakeemail@gmail.com", retVal);
    }

    @Test
    public void getIsAccountNonExpired() {
        Boolean retVal = details.isAccountNonExpired();
        assertEquals(true, retVal);
    }

    @Test
    public void getIsAccountNonLocked() {
        Boolean retVal = details.isAccountNonLocked();
        assertEquals(true, retVal);
    }
    @Test
    public void getIsCredentialsNonExpired() {
        Boolean retVal = details.isCredentialsNonExpired();
        assertEquals(true, retVal);
    }

    @Test
    public void getIsEnabled() {
        Boolean retVal = details.isEnabled();
        assertEquals(true, retVal);
    }
}
