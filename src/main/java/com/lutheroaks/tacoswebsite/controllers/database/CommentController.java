package com.lutheroaks.tacoswebsite.controllers.database;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutheroaks.tacoswebsite.entities.comment.Comment;
import com.lutheroaks.tacoswebsite.entities.comment.CommentRepo;
import com.lutheroaks.tacoswebsite.entities.comment.CommentService;

@RestController
public class CommentController {

	@Autowired
    private CommentRepo repository;

	@Autowired
	private CommentService service;

	/**
	 * Adds a comment to the table
	 * @param content
	 * @return
	 */
	@PostMapping("/comment")
	public void addComment(final HttpServletRequest request) {
		service.createComment(request);
	}

	/**
	 * Returns a list of all comments in the table
	 * @return
	 */
	@GetMapping("/comment")
	public List<Comment> getComments() {
		return repository.findAll();
	}

	/**
	 * Deletes a comment from the table
	 * @param request
	 * @param response
	 */
	@Transactional
	@DeleteMapping("/comment")
	public void deleteComment(final HttpServletRequest request){
		service.removeComment(request);
	}
    
} 
