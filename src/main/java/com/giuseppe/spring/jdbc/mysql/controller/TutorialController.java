package com.giuseppe.spring.jdbc.mysql.controller;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import com.giuseppe.spring.jdbc.mysql.service.TutorialService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

  private final TutorialService tutorialService;

  // Dependency injection tramite costruttore
  public TutorialController(TutorialService tutorialService) {
    this.tutorialService = tutorialService;
  }

  // GET: Recupera tutti i tutorial con opzioni di filtro per title, orderBy e limit
  @GetMapping
  public ResponseEntity<List<Tutorial>> getAllTutorials(
          @RequestParam(required = false) String title,
          @RequestParam(required = false) String orderBy,
          @RequestParam(required = false) Integer limit) {

    List<Tutorial> tutorials;

    // Se il title è presente, cerca i tutorial che contengono il titolo fornito
    if (title != null) {
      tutorials = tutorialService.findByTitleContaining(title, orderBy, limit);
    } else {
      // Altrimenti restituisci tutti i tutorial
      tutorials = tutorialService.findAll(orderBy, limit);
    }

    // Se non ci sono risultati, restituisci HTTP 204 (No Content)
    if (tutorials.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    // Restituisci la lista di tutorial con HTTP 200 (OK)
    return ResponseEntity.ok(tutorials);
  }

  // GET: Recupera un tutorial specifico tramite ID
  @GetMapping("/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable Long id) {
    Tutorial tutorial = tutorialService.findById(id);

    // Se il tutorial non esiste, restituisci HTTP 404 (Not Found)
    if (tutorial == null) {
      return ResponseEntity.notFound().build();
    }

    // Restituisci il tutorial con HTTP 200 (OK)
    return ResponseEntity.ok(tutorial);
  }

  // POST: Crea un nuovo tutorial
  @PostMapping
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
    // Salva il tutorial
    int result = tutorialService.save(tutorial);

    // Se il salvataggio fallisce, restituisci HTTP 500 (Internal Server Error)
    if (result <= 0) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // Restituisci il tutorial creato con HTTP 201 (Created)
    return ResponseEntity.status(HttpStatus.CREATED).body(tutorial);
  }

  // PUT: Aggiorna un tutorial esistente
  @PutMapping("/{id}")
  public ResponseEntity<Tutorial> updateTutorial(
          @PathVariable Long id, @RequestBody Tutorial tutorial) {

    // Imposta l'ID del tutorial dato
    tutorial.setId(id);

    // Tenta di aggiornare il tutorial
    int result = tutorialService.update(tutorial);

    // Se il tutorial non viene trovato, restituisci HTTP 404 (Not Found)
    if (result <= 0) {
      return ResponseEntity.notFound().build();
    }

    // Restituisci il tutorial aggiornato con HTTP 200 (OK)
    return ResponseEntity.ok(tutorial);
  }

  // DELETE: Elimina un tutorial tramite ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTutorial(@PathVariable Long id) {
    int result = tutorialService.deleteById(id);

    // Se il tutorial non viene trovato, restituisci HTTP 404 (Not Found)
    if (result <= 0) {
      return ResponseEntity.notFound().build();
    }

    // Se l'eliminazione ha successo, restituisci HTTP 204 (No Content)
    return ResponseEntity.noContent().build();
  }

  // DELETE: Elimina tutti i tutorial
  @DeleteMapping
  public ResponseEntity<Void> deleteAllTutorials() {
    tutorialService.deleteAll();

    // Sempre restituisci HTTP 204 (No Content)
    return ResponseEntity.noContent().build();
  }

  // GET: Recupera tutti i tutorial pubblicati, con opzioni per orderBy e limit
  @GetMapping("/published")
  public ResponseEntity<List<Tutorial>> findByPublished(
          @RequestParam(required = false) String orderBy,
          @RequestParam(required = false) Integer limit) {

    List<Tutorial> tutorials = tutorialService.findByPublished(true, orderBy, limit);

    // Se non ci sono risultati, restituisci HTTP 204 (No Content)
    if (tutorials.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    // Restituisci i tutorial pubblicati con HTTP 200 (OK)
    return ResponseEntity.ok(tutorials);
  }
}