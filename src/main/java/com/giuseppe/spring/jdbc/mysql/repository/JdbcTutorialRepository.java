// JdbcTutorialRepository.java
package com.giuseppe.spring.jdbc.mysql.repository;

import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;

@Repository
public class JdbcTutorialRepository implements TutorialRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTutorialRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public int save(Tutorial tutorial) {
    return jdbcTemplate.update("INSERT INTO tutorials (title, description, published) VALUES(?,?,?)",
            tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
  }

  @Override
  public int update(Tutorial tutorial) {
    return jdbcTemplate.update("UPDATE tutorials SET title=?, description=?, published=? WHERE id=?",
            tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId());
  }

  @Override
  public Tutorial findById(Long id) {
    try {
      return jdbcTemplate.queryForObject("SELECT * FROM tutorials WHERE id=?",
              BeanPropertyRowMapper.newInstance(Tutorial.class), id);
    } catch (IncorrectResultSizeDataAccessException e) {
      return null;
    }
  }

  @Override
  public int deleteById(Long id) {
    return jdbcTemplate.update("DELETE FROM tutorials WHERE id=?", id);
  }

  @Override
  public List<Tutorial> findAll() {
    return jdbcTemplate.query("SELECT * FROM tutorials",
            BeanPropertyRowMapper.newInstance(Tutorial.class));
  }

  @Override
  public List<Tutorial> findByPublished(boolean published) {
    return jdbcTemplate.query("SELECT * FROM tutorials WHERE published=?",
            BeanPropertyRowMapper.newInstance(Tutorial.class), published);
  }

  @Override
  public List<Tutorial> findByTitleContaining(String title) {
    String q = "SELECT * FROM tutorials WHERE title LIKE CONCAT('%', ?, '%')";
    return jdbcTemplate.query(q,
            BeanPropertyRowMapper.newInstance(Tutorial.class), title);
  }

  @Override
  public int deleteAll() {
    return jdbcTemplate.update("DELETE FROM tutorials");
  }
}