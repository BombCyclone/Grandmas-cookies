package com.lutheroaks.tacoswebsite.controllers.database;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.lutheroaks.tacoswebsite.entities.tag.Tag;
import com.lutheroaks.tacoswebsite.entities.tag.TagRepo;
import com.lutheroaks.tacoswebsite.entities.tag.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    @Autowired
    private TagRepo repository;

    @Autowired
    private TagService utils;

    /**
     * Adds a new Tag to the table
     * @param request
     * @throws IOException
     */
    @PutMapping("/tag")
    public void addTag(final HttpServletRequest request, 
                final HttpServletResponse response) throws IOException{
        utils.createTag(request, response);
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
        utils.deleteTag(request, response);
    }
}
