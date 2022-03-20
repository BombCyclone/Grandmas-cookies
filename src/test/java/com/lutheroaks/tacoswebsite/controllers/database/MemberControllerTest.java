package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;

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
    public void addMemberTestMemberAlreadyExists(){
        // we don't actually want to save to our database here, just return null
        Mockito.doReturn(null).when(repository).save(Mockito.any(Member.class));
        HttpServletRequest  newReq = Mockito.mock(HttpServletRequest.class);
        // mock adding a new member 
        Mockito.when(newReq.getParameter("fname")).thenReturn("Dorothy");
        Mockito.when(newReq.getParameter("lname")).thenReturn("Jenkins");
        Mockito.when(newReq.getParameter("email")).thenReturn("fakeemail@gmail.com");
        List<Object> mockReturn = new ArrayList<>();
        Object emailToAdd = new Object();
        mockReturn.add(emailToAdd);
        Mockito.when(repository.findMemberByEmail(anyString())).thenReturn(mockReturn);
        String retVal = controller.addMember(newReq);
        assertEquals("Member already in system", retVal);
    }

    @Test
    public void addMemberTestMemberDNE(){
        HttpServletRequest  newReq = Mockito.mock(HttpServletRequest.class);
        // mock adding a new member 
        Mockito.when(newReq.getParameter("fname")).thenReturn("Dorothy");
        Mockito.when(newReq.getParameter("lname")).thenReturn("Jenkins");
        Mockito.when(newReq.getParameter("email")).thenReturn("fakeemail@gmail.com");
        List<Object> mockReturn = new ArrayList<>();

        Mockito.when(repository.findMemberByEmail(anyString())).thenReturn(mockReturn);
        String retVal = controller.addMember(newReq);
        assertEquals("a new member was added!", retVal);
    }
    @Test
    public void getMembersTest(){
        // mockList will be returned by the findAll method
        List<Member> mockList = new ArrayList<Member>();
        //Member fakeMember = new Member(1, "Herbert", "Malcolm", "idonthaveanemail@fakemail.com", null, null);
        Member fakeMember = new Member();
        fakeMember.setMemberId(1);
        fakeMember.setFirstName("Herbert");
        fakeMember.setLastName("Malcolm");
        fakeMember.setEmail("idonthaveanemail@fakemail.com");
        mockList.add(fakeMember);
        // return the mockList instead of querying the database
        Mockito.when(repository.findAll()).thenReturn(mockList);
        List<Member> retVal = controller.getMembers();
        // make sure that the returned data is our mockList
        assertEquals("Herbert", retVal.get(0).getFirstName());
        assertEquals("Malcolm", retVal.get(0).getLastName());
        assertEquals("idonthaveanemail@fakemail.com", retVal.get(0).getEmail());
        assertEquals(1, retVal.get(0).getMemberId());
    }

}
