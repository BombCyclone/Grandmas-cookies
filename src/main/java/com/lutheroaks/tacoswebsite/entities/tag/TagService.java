package com.lutheroaks.tacoswebsite.entities.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * Saves a new tag and returns the tag
     * @param request
     * @param response
     * @throws IOException
     */
    public Tag createTag(final String tagString, final HttpServletResponse response)throws IOException {
        Tag addedTag = new Tag();
        // if the required information is missing
        if(tagString == null || "".equals(tagString)){
            logger.info("Missing String for new tag!");
            addedTag = null;
            response.sendRedirect("error");
        // if the specified tag already exists
        } else if(repository.findTag(tagString) != null){
            logger.info("The specified tag already exists!");
            addedTag = null;
            response.sendRedirect("error");
        // add the Tag if the information checks out
        } else {
            addedTag.setTagString(tagString);
            addedTag.setTaggedPosts(new ArrayList<>());
            addedTag.setTaggedTickets(new ArrayList<>());
            repository.save(addedTag);
        }
        response.sendRedirect("tags-table");
        return addedTag;
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

    /**
     * Parses and returns tag objects based on the input which is an array of tagStrings
     * @param tags
     * @return
     */
    public List<Tag> retrieveTags(final String[] tags) throws IOException{
        // get the tags to apply to the ticket
        List<Tag> appliedTags = new ArrayList<>();
        if(tags != null && tags.length < 4){
            for(String tagString : tags){
                // find the tag by the given string
                Tag toAdd = repository.findTag(tagString);
                if(toAdd != null){
                    // add the Tag to the list
                    appliedTags.add(toAdd);  
                }
            }
        }
        return appliedTags;
    }
}
