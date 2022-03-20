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

    private CommentRepo repository;

    @Autowired
    public CommentController(CommentRepo repository){
        this.repository = repository;
    }

    // this method adds a new row to the comment table
	@PostMapping("/comment")
	public String addComment(String content) {
			Comment toAdd = new Comment();
			toAdd.setContent(content);
			repository.save(toAdd);
			return "A new comment was added!";
	}

	// this method returns a list of all rows in the member table
	@GetMapping("/comment")
	public List<Comment> getComments() {
		return repository.findAll();
	}
    
}
 