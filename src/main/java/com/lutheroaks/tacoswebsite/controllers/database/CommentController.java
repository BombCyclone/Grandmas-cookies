package com.lutheroaks.tacoswebsite.controllers.database;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutheroaks.tacoswebsite.comment.Comment;
import com.lutheroaks.tacoswebsite.comment.CommentRepo;

@RestController
public class CommentController {

	@Autowired
    private CommentRepo repository;

	/**
	 * Adds a comment to the table
	 * @param content
	 * @return
	 */
	@PostMapping("/comment")
	public String addComment(final String content) {
		Comment toAdd = new Comment();
		toAdd.setContent(content);
		repository.save(toAdd);
		return "A new comment was added!";
	}

	/**
	 * Returns a list of all comments in the table
	 * @return
	 */
	@GetMapping("/comment")
	public final List<Comment> getComments() {
		return repository.findAll();
	}
    
} 
