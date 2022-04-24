package com.lutheroaks.tacoswebsite.entities.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public final class TagServiceTest {
    
    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepo repository;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTagSuccessTest() throws IOException{

        // return null when searching for the Tag, indicating it does not already exist
        doReturn(null).when(repository).findTag(anyString());
        // don't save the new Tag to the database
        doReturn(null).when(repository).save(any(Tag.class));
        // call the method to be tested
        Tag retVal = tagService.createTag("tag");
        // confirm the request was successful
        assertEquals("tag", retVal.getTagString());
    }

    @Test
    void createTagAlreadyExists() throws IOException{
        // return a tag when searching, indicating the tag already exists
        doReturn(new Tag()).when(repository).findTag(anyString());
        // call the method to be tested
        Tag retVal = tagService.createTag("tag");
        // confirm the request was unsuccessful
        assertEquals(null, retVal);
    }

    @Test
    void createTagMissingParameter() throws IOException{
        // call the method to be tested
        Tag retVal = tagService.createTag("");
        // confirm the request was unsuccessful
        assertEquals(null, retVal);
    }

    @Test
    void deleteTagTest() throws IOException{
        // mock the parameters
        HttpServletRequest  mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        // mock the tagString return
        when(mockRequest.getParameter("tagString")).thenReturn("email");
        // don't actually try to delete from the database
        doReturn(null).when(repository).save(any(Tag.class));
        // call the method to be tested
        tagService.deleteTag(mockRequest, mockResponse);
        // confirm the request was successful by what page it is redirected to
        verify(mockResponse, times(1)).sendRedirect("index");
    }

    @Test
    void retrieveTagsTest() {
        doReturn(null).when(repository).findTag("email");
        doReturn(new Tag()).when(repository).findTag("iPhone");
        TagService serviceSpy = spy(tagService);
        doReturn(new Tag()).when(serviceSpy).createTag(any());
        String[] params = {"email", "iPhone"};
        List<Tag> retVal = serviceSpy.retrieveTags(params);
        assertEquals(2, retVal.size(), 0);
    }
}
