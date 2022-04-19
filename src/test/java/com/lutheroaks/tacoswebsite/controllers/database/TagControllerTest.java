package com.lutheroaks.tacoswebsite.controllers.database;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.tag.TagRepo;
import com.lutheroaks.tacoswebsite.entities.tag.TagService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public final class TagControllerTest {
    
    @InjectMocks
    private TagController controller;

    @Mock
    private TagRepo repository;

    @Mock
    private TagService tagService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addTagCalled() throws IOException{
        // call the method in the controller
        controller.addTag("tag");
        // assert that the expected method was called
        verify(tagService, times(1)).createTag(any());
    }

    @Test
    void getTagsCalled(){
        doReturn(null).when(repository).findAll();
        controller.getTags();
        // assert that the expected method was called
        verify(repository, times(1)).findAll();
    }

    @Test
    void deleteTagsCalled() throws IOException{
        HttpServletRequest  mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        // don't actually delete a tag
        doNothing().when(tagService).deleteTag(any(), any());
        controller.deleteTag(mockRequest, mockResponse);
        // assert that the expected method was called
        verify(tagService, times(1)).deleteTag(any(), any());
    }
}
