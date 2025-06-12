package Repositories;

import Exceptions.TaskNotFoundException;
import Models.Priority;
import Models.Status;
import Models.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {

    private Map<Long,Task> taskStorage=new HashMap<>();
    private long taskId=0;
    @Override
    public Task save(Task task) {
        taskId++;
        task.setId(taskId);
        taskStorage.put(taskId,task);
        System.out.println(taskId+" "+task);
        return task;
    }
    @Override
    public Optional<Task> getTaskById(long taskId){
        Optional<Task> taskop= Optional.ofNullable(taskStorage.get(taskId));
        return taskop;

    }
    @Override
    public List<Task> getAllTasks(){
        List<Task> allTasks=taskStorage.values().stream().collect(Collectors.toList());
        return allTasks;
    }
    @Override
    public List<Task> getTasksByPriority(String priority){
        Priority getPriority=Priority.valueOf(priority);
        List<Task> filteredTask=taskStorage.values().stream().filter(task -> task.getPriority().equals(getPriority)).collect(Collectors.toList());
        return filteredTask;
    }
    @Override
    public List<Task> getTasksByStatus(String status){
        Status getStatus=Status.valueOf(status);
        List<Task> filteredTask=taskStorage
                .values()
                .stream()
                .filter(task -> task.getStatus().equals(getStatus))
                .collect(Collectors.toList());
        return filteredTask;
    }
    @Override
    public Optional<Task> getTaskByTitle(String title){
        return taskStorage.values()
                .stream()
                .filter(task -> task.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }
    public Task deleteTaskById(long taskId){
        return taskStorage.remove(taskId);
    }

}
