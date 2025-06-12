package Controllers;

import DTOs.ResponseBody;
import DTOs.TaskResponse;
import Exceptions.TaskAlreadyAddedException;
import Exceptions.TaskNotFoundException;
import Models.Task;
import Services.TaskServices;

import java.util.ArrayList;
import java.util.List;

public class TaskCrud {
    private TaskServices taskService;

    public TaskCrud(TaskServices taskService){
        this.taskService = taskService;
    }
    public ResponseBody<List<TaskResponse>> getAllTasks(){
        try{
            List<Task> tasks= taskService.getAllTasks();
            List<TaskResponse> taskResponses = TaskResponse.from(tasks);
            return  new ResponseBody<>("Successsful","task fetched",taskResponses);
        }
        catch(Exception e){
            return  new ResponseBody<>("Errors","Unable to fetch",null);
        }
    }
    public ResponseBody<TaskResponse> getTaskById(int id){
        try {
            Task task=taskService.getTaskById(id);
            TaskResponse taskResponse=TaskResponse.from(task);
            return  new ResponseBody<>("Successsful","task fetched",taskResponse);
        }
        catch(TaskNotFoundException e){
            return  new ResponseBody<>("Failed","Task not found",null);
        }
        catch (Exception e) {
            return  new ResponseBody<>("Errors","Unable to fetch",null);
        }
    }
    public ResponseBody<List<TaskResponse>> getTasksByStatus(String category){
        try {
            List<Task> tasks= taskService.getTasksByStatus(category);
            List<TaskResponse> taskResponses = TaskResponse.from(tasks);
            return  new ResponseBody<>("Successsful","task fetched",taskResponses);
        }
        catch(TaskNotFoundException e){
            return  new ResponseBody<>("Failed","Task not found",null);
        }
        catch (Exception e) {
            return  new ResponseBody<>("Errors","Unable to fetch",null);
        }
    }
    public ResponseBody<List<TaskResponse>> getTasksByPriority(String priority){
       try{
           List<Task> tasks= taskService.getTasksByPriority(priority);
           List<TaskResponse> taskResponses = TaskResponse.from(tasks);
           return  new ResponseBody<>("Successsful","task fetched",taskResponses);
       }
       catch(TaskNotFoundException e){
           return  new ResponseBody<>("Failed","Task not found",null);
       }
       catch (Exception e) {
           return  new ResponseBody<>("Errors","Unable to fetch",null);
       }
    }
    public ResponseBody<TaskResponse> addTask(Task task){
        try{
            TaskResponse taskResponse=TaskResponse.from(taskService.addTask(task));
            return  new ResponseBody<>("Successsful","task added",taskResponse);
        }
        catch(TaskAlreadyAddedException e){
            return  new ResponseBody<>("Failed","Task already added",null);
        }
        catch (Exception e) {
            return  new ResponseBody<>("Errors","Unable to add",null);
        }
    }
    public ResponseBody<TaskResponse> updateTask(Task task){
        try{
            TaskResponse taskResponse=TaskResponse.from(taskService.updateTask(task));
            return  new ResponseBody<>("Successsful","task updated",taskResponse);
        }
        catch(TaskNotFoundException e){
            return  new ResponseBody<>("Failed",e.getMessage(),null);
        }
        catch (Exception e) {
            return  new ResponseBody<>("Errors",e.getMessage(),null);
        }

    }
    public ResponseBody<TaskResponse> deleteTask(int id){
        try{
            Task task=taskService.getTaskById(id);
            taskService.deleteTask(task);

            return  new ResponseBody<>("Successful","task deleted",TaskResponse.from(task));
        }
        catch(TaskNotFoundException e){
            return  new ResponseBody<>("Failed",e.getMessage(),null);
        }
        catch (Exception e) {
            return  new ResponseBody<>("Errors",e.getMessage(),null);
        }
    }

}
