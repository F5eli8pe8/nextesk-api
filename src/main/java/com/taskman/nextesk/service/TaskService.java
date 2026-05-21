package com.taskman.nextesk.service;

import com.taskman.nextesk.model.Task;
import com.taskman.nextesk.model.User;
import com.taskman.nextesk.repository.TaskRepository;
import com.taskman.nextesk.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<Task> findAll() {
        return taskRepository.findByUser(getCurrentUser());
    }

    public Task findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        if (!task.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Acesso negado");
        }
        return task;
    }

    public Task save(Task task) {
        task.setUser(getCurrentUser());
        return taskRepository.save(task);
    }

    public Task update(Long id, Task taskAtualizada) {
        Task task = findById(id);
        task.setTitle(taskAtualizada.getTitle());
        task.setDescription(taskAtualizada.getDescription());
        task.setCompleted(taskAtualizada.getCompleted());
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        findById(id);
        taskRepository.deleteById(id);
    }
}