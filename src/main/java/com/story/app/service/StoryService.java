package com.story.app.service;

import com.story.app.model.Story;
import com.story.app.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoryService {
    @Autowired
    private final StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository){
        this.storyRepository = storyRepository;
    }

    public String addStory(Story story) {
        if (storyRepository.findStoryByAuthorEqualsIgnoreCaseAndTitleEqualsIgnoreCaseAndContentEqualsIgnoreCase(story.getAuthor(),story.getTitle(),story.getContent()) != null) {
            return "Story with title " + story.getTitle() + " already exists in the database";
        } else {
            storyRepository.save(story);
            return "Story with title " + story.getTitle() + " inserted successfully";
        }
    }

    public List<String> addStories(List<Story> stories) {
        List<String> results = new ArrayList<>();
        for (Story story : stories) {
            if (storyRepository.findStoryByAuthorEqualsIgnoreCaseAndTitleEqualsIgnoreCaseAndContentEqualsIgnoreCase(story.getAuthor(),story.getTitle(),story.getContent()) != null) {
                results.add("Story with title " + story.getTitle() + " already exists in the database");
            } else {
                storyRepository.save(story);
                results.add("Story with title " + story.getTitle() + " inserted successfully");
            }
        }
        return results;
    }
}
