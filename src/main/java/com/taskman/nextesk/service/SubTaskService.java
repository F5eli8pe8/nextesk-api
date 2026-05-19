package com.taskman.nextesk.service;

import com.taskman.nextesk.model.SubTask;
import com.taskman.nextesk.model.Task;
import com.taskman.nextesk.repository.SubTaskRepository;
import com.taskman.nextesk.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubTaskService {

    private final SubTaskRepository subTaskRepository;
    private final TaskRepository taskRepository;

    public SubTaskService(SubTaskRepository subTaskRepository, TaskRepository taskRepository) {
        this.subTaskRepository = subTaskRepository;
        this.taskRepository = taskRepository;
    }

    public List<SubTask> findByTaskId(Long taskId) {
        return subTaskRepository.findByTaskId(taskId);
    }

    public SubTask save(Long taskId, SubTask subTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        subTask.setTask(task);
        return subTaskRepository.save(subTask);
    }

    public SubTask update(Long id, SubTask subTaskAtualizada) {
        SubTask subTask = subTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sub-tarefa não encontrada"));
        subTask.setTitle(subTaskAtualizada.getTitle());
        subTask.setCompleted(subTaskAtualizada.getCompleted());
        return subTaskRepository.save(subTask);
    }

    public void delete(Long id) {
        subTaskRepository.deleteById(id);
    }
}