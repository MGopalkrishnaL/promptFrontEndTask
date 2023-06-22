package com.mountBlue.BlogApplication.service;

import com.mountBlue.BlogApplication.dao.TagsDao;
import com.mountBlue.BlogApplication.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagService {
    @Autowired
    TagsDao tagsDao;
    public void saveManyTags(List<Tags> tagsList){
        for(Tags tag:tagsList){
            if(tagsDao.existsByName(tag.getName())){
               continue;
            }
            tagsDao.save(tag);
        }
    }

    public void deleteTagsById(int id) {
        tagsDao.deleteById(id);
    }
}
