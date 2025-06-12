import Models.Priority;
import Models.Status;
import Models.Task;
import Repositories.TaskRepositoryImpl;
import Services.TaskServiceImpl;
import Services.TaskServices;

import java.util.Date;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
           System.out.print("Task app running");
        Scanner scanner = new Scanner(System.in);
        TaskServices taskService = new TaskServiceImpl(new TaskRepositoryImpl());

        while (true) {
            System.out.println("\n===== Task Manager Menu =====");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Get Task by ID");
            System.out.println("4. Update Task");
            System.out.println("5. Delete Task");
            System.out.println("6. Get Tasks by Status");
            System.out.println("7. Get Tasks by Priority");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    Task newTask = new Task();
                    System.out.print("Enter Title: ");
                    newTask.setTitle(scanner.nextLine().trim());

                    System.out.print("Enter Description: ");
                    newTask.setDescription(scanner.nextLine().trim());

                    System.out.print("Enter Status (PENDING, IN_PROGRESS, COMPLETED): ");
                    newTask.setStatus(Status.valueOf(scanner.nextLine().trim().toUpperCase()));

                    System.out.print("Enter Priority (LOW, MEDIUM, HIGH): ");
                    newTask.setPriority(Priority.valueOf(scanner.nextLine().trim().toUpperCase()));

                    newTask.setDueDate(new Date());

                    Task addedTask = taskService.addTask(newTask);
                    System.out.println("Task added: " + addedTask);
                    break;

                case 2:
                    System.out.println("All Tasks:");
                    System.out.println(taskService.getAllTasks());
                    break;

                case 3:
                    System.out.print("Enter Task ID: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    try {
                        Task taskById = taskService.getTaskById(id);
                        System.out.println(taskById);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    Task updateTask = new Task();
                    System.out.print("Enter Task ID to update: ");
                    updateTask.setId(Integer.parseInt(scanner.nextLine().trim()));

                    System.out.print("Enter New Title (or press Enter to skip): ");
                    String title = scanner.nextLine().trim();
                    if (!title.isEmpty()) updateTask.setTitle(title);

                    System.out.print("Enter New Description (or press Enter to skip): ");
                    String desc = scanner.nextLine().trim();
                    if (!desc.isEmpty()) updateTask.setDescription(desc);

                    System.out.print("Enter New Status (or press Enter to skip): ");
                    String status = scanner.nextLine().trim();
                    if (!status.isEmpty()) updateTask.setStatus(Status.valueOf(status.toUpperCase()));

                    System.out.print("Enter New Priority (or press Enter to skip): ");
                    String priority = scanner.nextLine().trim();
                    if (!priority.isEmpty()) updateTask.setPriority(Priority.valueOf(priority.toUpperCase()));

                    updateTask.setDueDate(new Date());
                    try {
                        Task updated = taskService.updateTask(updateTask);
                        System.out.println("Updated Task: " + updated);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Enter Task ID to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine().trim());
                    try {
                        Task taskToDelete = taskService.getTaskById(deleteId);
                        taskService.deleteTask(taskToDelete);
                        System.out.println("Task deleted.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    System.out.print("Enter Status to filter (PENDING, IN_PROGRESS, COMPLETED): ");
                    String statusInput = scanner.nextLine().trim();
                    taskService.getTasksByStatus(statusInput).forEach(System.out::println);
                    break;

                case 7:
                    System.out.print("Enter Priority to filter (LOW, MEDIUM, HIGH): ");
                    String priorityInput = scanner.nextLine().trim();
                    taskService.getTasksByPriority(priorityInput).forEach(System.out::println);
                    break;

                case 0:
                    System.out.println("Exiting Task Manager. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        }
    }
