package Services;

import Models.Task;
import Repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

public interface TaskServices {
    public Task addTask(Task task);
    public List<Task> getAllTasks();
    public Task getTaskById(long id);
    public List<Task> getTasksByStatus(String category);
    public List<Task> getTasksByPriority(String priority);
    public Task updateTask(Task task);
    public Task deleteTask(Task task);
}
