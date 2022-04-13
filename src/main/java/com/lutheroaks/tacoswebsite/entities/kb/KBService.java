package com.lutheroaks.tacoswebsite.entities.kb;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

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
        toAdd.setAssociatedTags(new HashSet<>());
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
}
