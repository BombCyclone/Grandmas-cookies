package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.lutheroaks.tacoswebsite.tag.Tag;
import com.lutheroaks.tacoswebsite.tag.TagRepo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    // for logging information to console
    private Logger logger = org.slf4j.LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagRepo repository;

    /**
     * Adds a new Tag to the table
     * @param request
     * @throws IOException
     */
    @PostMapping("/tag")
    public void addTag(final HttpServletRequest request, 
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
            toAdd.setTaggedPosts(new HashSet<>());
            toAdd.setTaggedTickets(new HashSet<>());
            repository.save(toAdd);
            response.sendRedirect("index");
        }
    }

    /**
     * Returns a list of all tags
     * @param request
     * @return
     */
    @GetMapping("/tag")
    public List<Tag> getTags(final HttpServletRequest request){
        return repository.findAll();
    }

    /**
     * Deletes a specified Tag
     * @param request
     * @param response
     * @throws IOException
     */
    @Transactional
    @DeleteMapping("/tag")
    public void deleteTag(final HttpServletRequest request, 
                    final HttpServletResponse response) throws IOException{
        String tagString = request.getParameter("tagString");
        repository.deleteTag(tagString);
        response.sendRedirect("index");
    }
}
