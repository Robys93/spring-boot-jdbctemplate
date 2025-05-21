// TutorialService.java
package com.giuseppe.spring.jdbc.mysql.service;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import java.util.List;

public interface TutorialService {
    List<Tutorial> findAll(String title, String orderBy, Integer limit);
    Tutorial findById(long id);
    Tutorial create(Tutorial tutorial);
    Tutorial update(long id, Tutorial tutorial);
    void deleteById(long id);
    void deleteAll();
    List<Tutorial> findByPublished(boolean published);
}