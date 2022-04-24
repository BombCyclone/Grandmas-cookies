package com.lutheroaks.tacoswebsite.controllers.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.kb.KBPost;
import com.lutheroaks.tacoswebsite.entities.kb.KBPostRepo;
import com.lutheroaks.tacoswebsite.entities.kb.KBService;

@RestController
public class KbpostController {
    
    @Autowired
    private KBPostRepo repository;

    @Autowired
    private KBService service;

    /**
     * Adds a KB post to the table
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/kbpost")
	public void addKBPost(final HttpServletRequest request, 
            final HttpServletResponse response) throws IOException {
        service.createPost(request, response);
	}
    /**
     * Returns a list of all KB posts in the table
     * @return
     */
    @GetMapping("/kbpost")
    public List<KBPost> getKBPosts() {
        return repository.findAll();
    }

    /**
     * Deletes the specified kbpost
     * @param request
     * @param response
     * @throws IOException
     */
    @Transactional
    @DeleteMapping("/kbpost")
    public void deleteKBPost(final HttpServletRequest request) {
        service.removePost(request);
    }

}
