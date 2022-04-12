package com.lutheroaks.tacoswebsite.controllers.database;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.lutheroaks.tacoswebsite.entities.kb.KBPost;
import com.lutheroaks.tacoswebsite.entities.kb.KBPostRepo;
import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.member.MemberRepo;

@RestController
public class KbpostController {

    // for logging information to console
    private Logger logger = org.slf4j.LoggerFactory.getLogger(KbpostController.class);
    
    @Autowired
    private KBPostRepo repository;

    @Autowired
    private MemberRepo memberRepo;

    /**
     * Adds a KB post to the table
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/kbpost")
	public void addKBPost(final HttpServletRequest request, 
                        final HttpServletResponse response) throws IOException {
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
        } catch (Exception e) {
            logger.error("An error occurred while adding a KB Post: ", e);
            response.sendRedirect("error");
        }
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
    public void deleteKBPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        int postId = Integer.parseInt(request.getParameter("postId"));
        repository.deleteKBPostById(postId);
        response.sendRedirect("kbpost");
    }

}
