package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.kb.KBPost;
import com.lutheroaks.tacoswebsite.kb.KBPostRepo;
import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@SpringBootTest
public final class KBPostControllerTest {
    
    @InjectMocks
    private KbpostController controller;

    @Mock
    private KBPostRepo repository;

    @Mock
    private MemberRepo memberRepo;

    @BeforeEach
    public void mockSetup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addKBPostTest() throws IOException {
        doReturn(null).when(repository).save(any(KBPost.class));
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Principal mockPrince = Mockito.mock(Principal.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        when(request.getParameter("title")).thenReturn("Explaining Apple Watches");
        when(request.getParameter("content")).thenReturn("They're wrist iPhones.");
        when(request.getUserPrincipal()).thenReturn(mockPrince);
        when(mockPrince.getName()).thenReturn("Charles");
        when(memberRepo.findMemberByEmail(anyString())).thenReturn(new Member());

        doNothing().when(response).sendRedirect(any());

        controller.addKBPost(request, response);

        verify(response, times(1)).sendRedirect("index");

    }

    @Test
    public void getKBPostsTest() throws ParseException {
        List<KBPost> mockList = new ArrayList<KBPost>();
        KBPost fakePost = new KBPost();
        Member fakeMember = new Member();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("23/09/2007");
        long time = date.getTime();
        Timestamp timeStamp = new Timestamp(time);

        ArrayList<String> emptyList = new ArrayList<>();

        fakePost.setContent("They are wrist iPhones.");
        fakePost.setMember(fakeMember);
        fakePost.setTags(emptyList);
        fakePost.setTimeStamp(timeStamp);
        fakePost.setTitle("Explaining Apple Watches");
        
        mockList.add(fakePost);

        when(repository.findAll()).thenReturn(mockList);
        List<KBPost> retVal = controller.getKBPosts();

        assertEquals("They are wrist iPhones.", retVal.get(0).getContent());
        assertEquals(fakeMember, retVal.get(0).getMember());
        assertEquals(emptyList, retVal.get(0).getTags());
        assertEquals(timeStamp, retVal.get(0).getTimeStamp());
        assertEquals("Explaining Apple Watches", retVal.get(0).getTitle());

    }

    @Test
    void deleteKBPostSuccess() throws IOException{
        HttpServletRequest  request = mock(HttpServletRequest.class);
        HttpServletResponse  response = mock(HttpServletResponse.class);
        when(request.getParameter("postId")).thenReturn("1");
        doNothing().when(repository).deleteKBPostById(anyInt());
        controller.deleteKBPost(request, response);
        verify(response, times(1)).sendRedirect("kbpost");
    }
}
