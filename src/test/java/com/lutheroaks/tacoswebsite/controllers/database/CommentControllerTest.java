package com.lutheroaks.tacoswebsite.controllers.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.lutheroaks.tacoswebsite.comment.Comment;
import com.lutheroaks.tacoswebsite.comment.CommentRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class CommentControllerTest {
    
    @InjectMocks
    private CommentController controller;

    @Mock
    private CommentRepo repository;

    @BeforeEach
    public void mockSetup() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // public void addCommentTest() {
    //     // we don't actually want to save to our database here
    //     doReturn(null).when(repository).save(any(Comment.class));
    //     String retVal = controller.addComment("test");
    //     assertEquals("A new comment was added!", retVal);
    // }

    @Test
    public void getCommentsTest() {
        List<Comment> mockList = new ArrayList<>();
        Comment toAdd = new Comment();
        toAdd.setContent("this is a test");
        mockList.add(toAdd);
        when(repository.findAll()).thenReturn(mockList);
        List<Comment> retVal = controller.getComments();
        assertEquals("this is a test", retVal.get(0).getContent());
    }
}
