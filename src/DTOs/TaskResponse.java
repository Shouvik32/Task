package DTOs;

import Models.Priority;
import Models.Status;
import Models.Task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskResponse {
    private long id;
    private String title;
    private String description;
    private Date dueDate;
    private Priority priority;
    private Status status;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
 public static TaskResponse from(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setDueDate(task.getDueDate());
        taskResponse.setPriority(task.getPriority());
        taskResponse.setStatus(task.getStatus());
        return taskResponse;
 }
    public static List<TaskResponse> from(List<Task> tasks) {
        return tasks.stream()
                .map(TaskResponse::from)
                .collect(Collectors.toList());
    }

}
