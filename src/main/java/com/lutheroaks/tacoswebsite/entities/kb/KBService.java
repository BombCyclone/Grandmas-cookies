package com.lutheroaks.tacoswebsite.entities.kb;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KBService {
    
    @Autowired
    private KBPostRepo repository;

    @Autowired
    private AuthenticatedDetails authenticatedDetails;

    /**
     * Creates and saves a KBPost
     * @param request
     */
    public void createPost(final HttpServletRequest request) {
        Member poster = authenticatedDetails.getLoggedInMember(request);
        // set the title and content of the kbpost based on the request
        KBPost toAdd = new KBPost();
        toAdd.setMember(poster);
        toAdd.setTitle(request.getParameter("title"));
        toAdd.setContent(request.getParameter("content"));
        toAdd.setAssociatedTags(new ArrayList<>());
        // use the current time as the timestamp
        toAdd.setTimeStamp(Timestamp.from(Instant.now()));
        repository.save(toAdd);
    }

    /**
     * Deletes the specified kbpost
     * @param request
     * @param response
     */
    public void removePost(final HttpServletRequest request) {
        int postId = Integer.parseInt(request.getParameter("postId"));
        repository.deleteKBPostById(postId);
    }

    /**
     * Deletes the specified kbpost
     * @param request
     * @param response
     */
   public void updateKBPost(final HttpServletRequest request, final HttpServletResponse response) throws MessagingException, IOException  {

       int kbpostID = Integer.parseInt(request.getParameter("kbpostID"));
       KBPost originalKBPost = repository.findKBPostById(kbpostID);
       KBPost updatedKBPost = originalKBPost;
       String content = request.getParameter("content");
       String title = request.getParameter("title");
       updatedKBPost.setContent(content);
       updatedKBPost.setTitle(title);
   
       repository.save(updatedKBPost);
       response.sendRedirect("index");

   }

}
