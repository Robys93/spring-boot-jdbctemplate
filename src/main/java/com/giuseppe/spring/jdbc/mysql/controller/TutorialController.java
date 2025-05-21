// TutorialController.java
package com.giuseppe.spring.jdbc.mysql.controller;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import com.giuseppe.spring.jdbc.mysql.service.TutorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutorials")
@Tag(name = "Tutorial API", description = "API for managing tutorials")
public class TutorialController {

  private final TutorialService tutorialService;

  public TutorialController(TutorialService tutorialService) {
    this.tutorialService = tutorialService;
  }

  @GetMapping
  @Operation(summary = "Get all tutorials", description = "Retrieve all tutorials with optional filtering and pagination")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved tutorials"),
          @ApiResponse(responseCode = "204", description = "No tutorials found")
  })
  public ResponseEntity<List<Tutorial>> getAllTutorials(
          @RequestParam(required = false) String title,
          @RequestParam(required = false) String orderBy,
          @RequestParam(required = false) Integer limit) {

    List<Tutorial> tutorials = tutorialService.findAll(title, orderBy, limit);

    if (tutorials.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(tutorials);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get tutorial by ID", description = "Retrieve a specific tutorial by its ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Tutorial found"),
          @ApiResponse(responseCode = "404", description = "Tutorial not found")
  })
  public ResponseEntity<Tutorial> getTutorialById(
          @Parameter(description = "ID of tutorial to be retrieved")
          @PathVariable long id) {

    Tutorial tutorial = tutorialService.findById(id);
    return tutorial != null ?
            ResponseEntity.ok(tutorial) :
            ResponseEntity.notFound().build();
  }

  @PostMapping
  @Operation(summary = "Create a new tutorial", description = "Add a new tutorial to the system")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Tutorial created successfully"),
          @ApiResponse(responseCode = "400", description = "Invalid input"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Tutorial> createTutorial(
          @Parameter(description = "Tutorial object to be created")
          @RequestBody Tutorial tutorial) {

    Tutorial createdTutorial = tutorialService.create(tutorial);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTutorial);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update tutorial", description = "Update an existing tutorial")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Tutorial updated successfully"),
          @ApiResponse(responseCode = "404", description = "Tutorial not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Tutorial> updateTutorial(
          @Parameter(description = "ID of tutorial to be updated")
          @PathVariable long id,
          @RequestBody Tutorial tutorial) {

    Tutorial updatedTutorial = tutorialService.update(id, tutorial);
    return updatedTutorial != null ?
            ResponseEntity.ok(updatedTutorial) :
            ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete tutorial", description = "Delete a tutorial by its ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Tutorial deleted successfully"),
          @ApiResponse(responseCode = "404", description = "Tutorial not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Void> deleteTutorial(
          @Parameter(description = "ID of tutorial to be deleted")
          @PathVariable long id) {

    Tutorial tutorial = tutorialService.findById(id);
    if (tutorial == null) {
      return ResponseEntity.notFound().build();
    }

    tutorialService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  @Operation(summary = "Delete all tutorials", description = "Remove all tutorials from the system")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "All tutorials deleted successfully"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Void> deleteAllTutorials() {
    tutorialService.deleteAll();
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/published")
  @Operation(summary = "Get published tutorials", description = "Retrieve all published tutorials")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved published tutorials"),
          @ApiResponse(responseCode = "204", description = "No published tutorials found")
  })
  public ResponseEntity<List<Tutorial>> findByPublished() {
    List<Tutorial> tutorials = tutorialService.findByPublished(true);
    return tutorials.isEmpty() ?
            ResponseEntity.noContent().build() :
            ResponseEntity.ok(tutorials);
  }
}