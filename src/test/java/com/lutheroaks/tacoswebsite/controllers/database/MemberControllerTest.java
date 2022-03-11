package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;
import com.lutheroaks.tacoswebsite.tickets.Tickets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberControllerTest {

    @Autowired
    private MemberController controller;
    @Mock
    private MemberRepo repository;

    @BeforeEach 
    public void mockSetup() {
        controller = new MemberController(repository);
    }
    
    @Test
    public void addMemberTest(){
        // we don't actually want to save to our database here, just return null
        Mockito.doReturn(null).when(repository).save(Mockito.any(Member.class));
        // call the method to test
        String retVal = controller.addMember("Rupert", "Thompson", "getoffmylawn@noemail.com");
        // make sure the return type matches the expected string
        assertEquals("A new member was added!", retVal);
    }

    @Test
    public void getMembersTest(){
        // mockList will be returned by the findAll method
        List<Member> mockList = new ArrayList<Member>();
        Set<Tickets> associatedTickets = new HashSet<>();
        Member fakeMember = new Member(1, "Herbert", "Malcolm", "idonthaveanemail@fakemail.com", associatedTickets);
        mockList.add(fakeMember);
        // return the mockList instead of querying the database
        Mockito.when(repository.findAll()).thenReturn(mockList);
        List<Member> retVal = controller.getMembers();
        // make sure that the returned data is our mockList
        assertEquals("Herbert", retVal.get(0).getFirstname());
        assertEquals("Malcolm", retVal.get(0).getLastname());
        assertEquals("idonthaveanemail@fakemail.com", retVal.get(0).getEmail());
        assertEquals(1, retVal.get(0).getMemberid());
    }
}
