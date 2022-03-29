package com.lutheroaks.tacoswebsite.controllers.database;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.kb.KBPost;
import com.lutheroaks.tacoswebsite.kb.KBPostRepo;
import com.lutheroaks.tacoswebsite.member.Member;
import com.lutheroaks.tacoswebsite.member.MemberRepo;

@RestController
public class KbpostController {

    // for logging information to console
    Logger logger = org.slf4j.LoggerFactory.getLogger(KbpostController.class);
    
    private KBPostRepo repository;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    public KbpostController(KBPostRepo repository){
        this.repository = repository;
    }

    @PostMapping("/kbpost")
	public void addKBPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        KBPost toAdd = new KBPost();
        try {
            // get the email (user name) of the member member who is posting from the request details
            Principal principal = request.getUserPrincipal();
            String userName = principal.getName();
            Member poster = memberRepo.findMemberByEmail(userName);
            toAdd.setMember(poster);
            // set the title and content of the kbpost based on the request
            toAdd.setTitle(request.getParameter("title"));
            toAdd.setContent(request.getParameter("content"));
            toAdd.setTags(new ArrayList<>());
            // use the current time as the timestamp
            toAdd.setTimeStamp(Timestamp.from(Instant.now()));
            repository.save(toAdd);
            response.sendRedirect("index");
        }
        catch (Exception e) {
            logger.error("An error occurred while adding a KB Post: ", e);
            response.sendRedirect("error");
        }
	}

    @GetMapping("/kbpost")
    public List<KBPost> getKBPosts() {
        return repository.findAll();
    }

}
