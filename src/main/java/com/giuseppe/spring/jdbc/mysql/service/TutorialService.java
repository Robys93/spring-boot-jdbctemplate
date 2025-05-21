package com.giuseppe.spring.jdbc.mysql.service;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import java.util.List;

public interface TutorialService {
    int save(Tutorial tutorial);
    int update(Tutorial tutorial);
    Tutorial findById(Long id);
    int deleteById(Long id);
    List<Tutorial> findAll(String orderBy, Integer limit);
    List<Tutorial> findByPublished(boolean published, String orderBy, Integer limit);
    List<Tutorial> findByTitleContaining(String title, String orderBy, Integer limit);
    int deleteAll();
}