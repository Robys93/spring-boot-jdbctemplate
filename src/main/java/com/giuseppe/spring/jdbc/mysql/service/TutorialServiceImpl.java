// TutorialServiceImpl.java
package com.giuseppe.spring.jdbc.mysql.service;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import com.giuseppe.spring.jdbc.mysql.repository.TutorialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialServiceImpl(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public List<Tutorial> findAll(String title, String orderBy, Integer limit) {
        if (title != null) {
            return tutorialRepository.findByTitleContaining(title);
        }

        List<Tutorial> tutorials = tutorialRepository.findAll();

        if (limit != null && limit > 0 && limit < tutorials.size()) {
            tutorials = tutorials.subList(0, limit);
        }

        return tutorials;
    }

    @Override
    public Tutorial findById(long id) {
        return tutorialRepository.findById(id);
    }

    @Override
    public Tutorial create(Tutorial tutorial) {
        tutorialRepository.save(tutorial);
        return tutorial;
    }

    @Override
    public Tutorial update(long id, Tutorial tutorial) {
        Tutorial existingTutorial = findById(id);
        if (existingTutorial != null) {
            existingTutorial.setTitle(tutorial.getTitle());
            existingTutorial.setDescription(tutorial.getDescription());
            existingTutorial.setPublished(tutorial.isPublished());
            tutorialRepository.update(existingTutorial);
            return existingTutorial;
        }
        return null;
    }

    @Override
    public void deleteById(long id) {
        tutorialRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        tutorialRepository.deleteAll();
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        return tutorialRepository.findByPublished(published);
    }
}