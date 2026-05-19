package com.taskman.nextesk.controller;

import com.taskman.nextesk.model.SubTask;
import com.taskman.nextesk.service.SubTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/subtasks")
@CrossOrigin(origins = "http://localhost:3000")
public class SubTaskController {

    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }

    @GetMapping
    public List<SubTask> findByTaskId(@PathVariable Long taskId) {
        return subTaskService.findByTaskId(taskId);
    }

    @PostMapping
    public ResponseEntity<SubTask> save(@PathVariable Long taskId, @RequestBody SubTask subTask) {
        return ResponseEntity.ok(subTaskService.save(taskId, subTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubTask> update(@PathVariable Long id, @RequestBody SubTask subTask) {
        return ResponseEntity.ok(subTaskService.update(id, subTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subTaskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}