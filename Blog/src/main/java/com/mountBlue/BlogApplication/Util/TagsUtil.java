package com.mountBlue.BlogApplication.Util;

import com.mountBlue.BlogApplication.model.Tags;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class TagsUtil {
    public List<Tags> stringToTags(String tagsData){
        String[] tagsNamesList = tagsData.split(",");
        List<Tags> tagsList = new ArrayList<>();
        for(String tagName : tagsNamesList) {
            Tags tag = new Tags();
            if (tagName.startsWith("#")) {
                tag.setName(tagName);
                tagsList.add(tag);
            }
        }
        return tagsList;
    }

    public String tagsToString(List<Tags> tagsList) {
        StringBuilder TagsName = new StringBuilder();
        for(Tags tags:tagsList){
            TagsName.append(tags.getName()).append(",");
        }
        return TagsName.toString();
    }
}
