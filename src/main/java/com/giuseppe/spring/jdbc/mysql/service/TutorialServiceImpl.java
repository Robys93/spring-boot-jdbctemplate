package com.giuseppe.spring.jdbc.mysql.service;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import com.giuseppe.spring.jdbc.mysql.repository.TutorialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialServiceImpl(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public int save(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @Override
    public int update(Tutorial tutorial) {
        return tutorialRepository.update(tutorial);
    }

    @Override
    public Tutorial findById(Long id) {
        return tutorialRepository.findById(id);
    }

    @Override
    public int deleteById(Long id) {
        return tutorialRepository.deleteById(id);
    }

    @Override
    public List<Tutorial> findAll(String orderBy, Integer limit) {
        List<Tutorial> tutorials = tutorialRepository.findAll();
        return sortAndLimit(tutorials, orderBy, limit);
    }

    @Override
    public List<Tutorial> findByPublished(boolean published, String orderBy, Integer limit) {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(published);
        return sortAndLimit(tutorials, orderBy, limit);
    }

    @Override
    public List<Tutorial> findByTitleContaining(String title, String orderBy, Integer limit) {
        List<Tutorial> tutorials = tutorialRepository.findByTitleContaining(title);
        return sortAndLimit(tutorials, orderBy, limit);
    }

    @Override
    public int deleteAll() {
        return tutorialRepository.deleteAll();
    }

    private List<Tutorial> sortAndLimit(List<Tutorial> tutorials, String orderBy, Integer limit) {
        if (orderBy != null && orderBy.equalsIgnoreCase("title")) {
            tutorials = tutorials.stream()
                    .sorted((t1, t2) -> t1.getTitle().compareToIgnoreCase(t2.getTitle()))
                    .collect(Collectors.toList());
        }
        if (limit != null && limit > 0) {
            tutorials = tutorials.stream()
                    .limit(limit)
                    .collect(Collectors.toList());
        }
        return tutorials;
    }
}