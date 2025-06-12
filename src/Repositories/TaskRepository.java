package Repositories;

import Models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    public Task save(Task task);
    public Optional<Task> getTaskById(long taskId);
    public List<Task> getAllTasks();
    public List<Task> getTasksByPriority(String category);
    public Optional<Task> getTaskByTitle(String Title);
    public List<Task> getTasksByStatus(String category);
    public Task deleteTaskById(long taskId);


}
