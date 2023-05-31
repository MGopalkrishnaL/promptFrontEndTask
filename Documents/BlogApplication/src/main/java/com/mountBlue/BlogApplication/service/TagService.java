package com.mountBlue.BlogApplication.service;

import com.mountBlue.BlogApplication.dao.TagsDao;
import com.mountBlue.BlogApplication.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    TagsDao tagsDao;
    public void addtag(Tags tags) {
        tagsDao.save(tags);
    }

    public void saveTags(Tags tags) {
        tagsDao.save(tags);
    }
}
