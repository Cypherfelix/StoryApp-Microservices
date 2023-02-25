package com.story.app.repository;

import com.story.app.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByGenreEqualsIgnoreCase(String genre);

    Story findStoryByAuthorEqualsIgnoreCaseAndTitleEqualsIgnoreCaseAndContentEqualsIgnoreCase(String author, String title, String content);
}