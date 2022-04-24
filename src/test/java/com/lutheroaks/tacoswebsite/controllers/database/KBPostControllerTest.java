package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.kb.KBPost;
import com.lutheroaks.tacoswebsite.entities.kb.KBPostRepo;
import com.lutheroaks.tacoswebsite.entities.kb.KBService;
import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.tag.Tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public final class KBPostControllerTest {
    
    @InjectMocks
    private KbpostController controller;

    @Mock
    private KBPostRepo repository;

    @Mock
    private KBService service;

    @BeforeEach
    public void mockSetup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addKBPostTest() throws IOException {
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        // don't actually create the fake post
        doNothing().when(service).createPost(any(), any());
        // call the method to be tested
        controller.addKBPost(request, response);
        // confirm that the expected method was called
        verify(service, times(1)).createPost(request, response);
    }

    @Test
    public void getKBPostsTest() throws ParseException {
        // create a fake kb post and add it to the list
        List<KBPost> mockList = new ArrayList<KBPost>();
        KBPost fakePost = new KBPost();
        Member fakeMember = new Member();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("23/09/2007");
        long time = date.getTime();
        Timestamp timeStamp = new Timestamp(time);

        List<Tag> emptySet = new ArrayList<>();

        fakePost.setContent("They are wrist iPhones.");
        fakePost.setMember(fakeMember);
        fakePost.setPostTags(emptySet);
        fakePost.setTimeStamp(timeStamp);
        fakePost.setTitle("Explaining Apple Watches");
        
        mockList.add(fakePost);
        when(repository.findAll()).thenReturn(mockList);

        //call the method to be tested and assert the returned value
        List<KBPost> retVal = controller.getKBPosts();
        assertEquals("They are wrist iPhones.", retVal.get(0).getContent());
        assertEquals(fakeMember, retVal.get(0).getMember());
        assertEquals(emptySet, retVal.get(0).getPostTags());
        assertEquals(timeStamp, retVal.get(0).getTimeStamp());
        assertEquals("Explaining Apple Watches", retVal.get(0).getTitle());

    }

    @Test
    void deleteKBPostSuccess() {
        // mock the servlet request and its parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        // don't actually delete the fake post
        doNothing().when(service).removePost(any());
        // call the method to be tested
        controller.deleteKBPost(request);
        // confirm that the expected method was called
        verify(service, times(1)).removePost(request);
    }
}
