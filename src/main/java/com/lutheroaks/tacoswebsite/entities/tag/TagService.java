package com.lutheroaks.tacoswebsite.entities.tag;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    // for logging information to console
    private Logger logger = org.slf4j.LoggerFactory.getLogger(TagService.class);

    @Autowired
    private TagRepo repository;

    /**
     * Adds a new tag
     * @param request
     * @param response
     * @throws IOException
     */
    public void createTag(final HttpServletRequest request, 
                final HttpServletResponse response) throws IOException{

        String tagString = request.getParameter("tagString");
        
        // if the required information is missing
        if(tagString == null || "".equals(tagString)){
            logger.info("Missing String for new tag!");
            response.sendRedirect("error");
        // if the specified tag already exists
        } else if(repository.findTag(tagString) != null){
            logger.info("The specified tag already exists!");
            response.sendRedirect("error");
        // add the Tag if the information checks out
        } else {
            Tag toAdd = new Tag();
            toAdd.setTagString(tagString);
            toAdd.setTaggedPosts(new ArrayList<>());
            toAdd.setTaggedTickets(new ArrayList<>());
            repository.save(toAdd);
            response.sendRedirect("index");
        }
    }
    
    /**
     * Deletes a specified Tag
     * @param request
     * @param response
     * @throws IOException
     */
    public void deleteTag(final HttpServletRequest request, 
                final HttpServletResponse response) throws IOException{
        String tagString = request.getParameter("tagString");
        repository.deleteTag(tagString);
        response.sendRedirect("index");
    }
}
