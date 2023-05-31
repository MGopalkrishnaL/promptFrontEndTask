package com.mountBlue.BlogApplication.controller;

import com.mountBlue.BlogApplication.model.Tags;
import com.mountBlue.BlogApplication.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TagsController {
    @Autowired
    TagService tagService;
    @PostMapping("/saveTag")
    public String saveTags(@ModelAttribute("tags")Tags tags){
        tagService.saveTags(tags);
        return "TagsSaved";
    }
}
