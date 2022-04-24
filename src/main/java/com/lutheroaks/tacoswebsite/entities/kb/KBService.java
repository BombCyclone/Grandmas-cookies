package com.lutheroaks.tacoswebsite.entities.kb;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lutheroaks.tacoswebsite.entities.member.Member;
import com.lutheroaks.tacoswebsite.entities.tag.Tag;
import com.lutheroaks.tacoswebsite.entities.tag.TagService;
import com.lutheroaks.tacoswebsite.utils.AuthenticatedDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KBService {
    
    @Autowired
    private KBPostRepo repository;

    @Autowired
    private AuthenticatedDetails authenticatedDetails;

    @Autowired
    private TagService tagService;

    /**
     * Creates and saves a KBPost
     * @param request
     * @throws IOException
     */
    public void createPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        Member poster = authenticatedDetails.getLoggedInMember(request);
        // get the tags to apply
		List<Tag> appliedTags = tagService.retrieveTags(request.getParameterValues("tagSelect"));

        // set the title and content of the kbpost based on the request
        KBPost toAdd = new KBPost();
        toAdd.setMember(poster);
        toAdd.setTitle(request.getParameter("title"));
        toAdd.setContent(request.getParameter("content"));
        toAdd.setPostTags(appliedTags);
        // use the current time as the timestamp
        toAdd.setTimeStamp(Timestamp.from(Instant.now()));
        repository.save(toAdd);
        response.sendRedirect("faq");
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
