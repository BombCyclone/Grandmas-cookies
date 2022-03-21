package com.lutheroaks.tacoswebsite.controllers.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutheroaks.tacoswebsite.kb.KBPost;
import com.lutheroaks.tacoswebsite.kb.KBPostRepo;

@RestController
public class KbpostController {
    
    private KBPostRepo repository;

    @Autowired
    public KbpostController(KBPostRepo repository){
        this.repository = repository;
    }

    @PostMapping("/kbpost")
	public String addKBPost(String title, String content) {
			KBPost toAdd = new KBPost();
            toAdd.setTitle(title);
			toAdd.setContent(content);
			repository.save(toAdd);
			return "A new post was added!";
	}

}
