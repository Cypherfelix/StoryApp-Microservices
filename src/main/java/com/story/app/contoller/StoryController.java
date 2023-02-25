package com.story.app.contoller;

import com.story.app.Exception.ResourceNotFoundException;
import com.story.app.model.Story;
import com.story.app.repository.StoryRepository;
import com.story.app.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoryController {

    @Autowired
    private StoryRepository storyRepository;


    @GetMapping("/")
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Object> createStory(@RequestBody Story story) {
        StoryService service = new StoryService(storyRepository);
        String ret = service.addStory(story);
        return ResponseEntity.ok(ret);
    }

    @GetMapping("/{id}")
    public Story getStoryById(@PathVariable(value = "id") Long id) {
        return storyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Story with id " + id + " not found", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Story updateStory(@PathVariable(value = "id") Long id,
                             @RequestBody Story storyDetails) {
        Story story = storyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Story with id " + id + " not found",HttpStatus.NOT_FOUND));

        story.setTitle(storyDetails.getTitle());
        story.setContent(storyDetails.getContent());

        return storyRepository.save(story);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStory(@PathVariable(value = "id") Long id) {
        Story story = storyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Story with id " + id + " not found", HttpStatus.NOT_FOUND));

        storyRepository.delete(story);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/all")
    public ResponseEntity<Object> addStories(@RequestBody List<Story> stories) {
        StoryService storyService = new StoryService(storyRepository);
        List<String> ret = storyService.addStories(stories);
        return new ResponseEntity<>(ret, HttpStatus.ACCEPTED);
    }

    @GetMapping("/genre/{genre}")
    public List<Story> getStoriesByGenre(@PathVariable String genre) {
        return storyRepository.findByGenreEqualsIgnoreCase(genre);
    }


}
