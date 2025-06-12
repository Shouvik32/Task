package Services;

import Exceptions.TaskAlreadyAddedException;
import Exceptions.TaskNotFoundException;
import Models.Task;
import Repositories.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TaskServiceImpl implements TaskServices{

    private TaskRepository repository;
    public TaskServiceImpl(TaskRepository repository) {
        this.repository=repository;
    }
    @Override
    public Task addTask(Task task) {
        Optional<Task> taskOp=repository.getTaskByTitle(task.getTitle());
        if(taskOp.isPresent()){
            throw new TaskAlreadyAddedException(task.getTitle()+" already exists");
        }

        return repository.save(task);

    }
    @Override
    public List<Task> getAllTasks(){
        List<Task> tasks=repository.getAllTasks();
        return tasks;
    }
    @Override
    public Task getTaskById(long id) {
        Optional<Task> task=repository.getTaskById(id);
        if(task.isEmpty()){
            throw new TaskNotFoundException("Task not found");
        }
        return task.get();
    }
    @Override
    public List<Task> getTasksByStatus(String status){
        List<Task> tasks=repository.getTasksByStatus(status);
        return tasks;
    }
    @Override
    public List<Task> getTasksByPriority(String priority){
        List<Task> tasks=repository.getTasksByPriority(priority);
        return tasks;
    }
    @Override
    public Task updateTask(Task task){
        Optional<Task> taskOp = repository.getTaskById(task.getId());
        if (taskOp.isEmpty()) {
            throw new TaskNotFoundException(task.getTitle() + " not found");
        }
        Task existingTask = taskOp.get();
        if (task.getTitle() != null) existingTask.setTitle(task.getTitle());
        if (task.getPriority() != null) existingTask.setPriority(task.getPriority());
        if (task.getStatus() != null) existingTask.setStatus(task.getStatus());
        if (task.getDescription() != null) existingTask.setDescription(task.getDescription());
        if (task.getDueDate() != null) existingTask.setDueDate(task.getDueDate());
        return repository.save(existingTask);
    }
    @Override
    public Task deleteTask(Task task){
        Optional<Task> taskOp = repository.getTaskById(task.getId());
        if (taskOp.isEmpty()) {
            throw new TaskNotFoundException("Task with id " + task.getId()+ " not found");
        }
        Task existingTask = taskOp.get();
        repository.deleteTaskById(task.getId());
        return existingTask;
    }


}
