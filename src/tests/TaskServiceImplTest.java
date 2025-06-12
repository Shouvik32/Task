package tests;

import Exceptions.TaskAlreadyAddedException;
import Exceptions.TaskNotFoundException;
import Models.Priority;
import Models.Status;
import Models.Task;
import Repositories.TaskRepository;
import Services.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceImplTest {

    private TaskServiceImpl service;
    private FakeTaskRepository fakeRepo;

    // A simple fake repository to simulate data storage
    static class FakeTaskRepository implements TaskRepository {

        private Map<Long, Task> taskMap = new HashMap<>();
        private int idCounter = 1;

        @Override
        public Optional<Task> getTaskByTitle(String title) {
            return taskMap.values().stream()
                    .filter(task -> task.getTitle().equalsIgnoreCase(title))
                    .findFirst();
        }

        @Override
        public Task save(Task task) {
            if (task.getId() == 0) {
                task.setId(idCounter++);
            }
            taskMap.put(task.getId(), task);
            return task;
        }

        @Override
        public List<Task> getAllTasks() {
            return new ArrayList<>(taskMap.values());
        }

        @Override
        public Optional<Task> getTaskById(long id) {
            return Optional.ofNullable(taskMap.get(id));
        }

        @Override
        public List<Task> getTasksByStatus(String status) {
            List<Task> result = new ArrayList<>();
            for (Task task : taskMap.values()) {
                if (task.getStatus() != null && task.getStatus().toString().equalsIgnoreCase(status)) {
                    result.add(task);
                }
            }
            return result;
        }

        @Override
        public Task deleteTaskById(long taskId) {
            return taskMap.remove(taskId);
        }

        @Override
        public List<Task> getTasksByPriority(String priority) {
            List<Task> result = new ArrayList<>();
            for (Task task : taskMap.values()) {
                if (task.getPriority() != null && task.getPriority().toString().equalsIgnoreCase(priority)) {
                    result.add(task);
                }
            }
            return result;
        }
    }

    private Task createSampleTask() {
        Task task = new Task();
        task.setTitle("Sample Task");
        task.setDescription("Sample Description");
        task.setStatus(Status.PENDING);
        task.setPriority(Priority.HIGH);
        task.setDueDate(new Date());
        return task;
    }

    @BeforeEach
    public void setup() {
        fakeRepo = new FakeTaskRepository();
        service = new TaskServiceImpl(fakeRepo);
    }

    @Test
    public void testAddTask_Success() {
        Task task = createSampleTask();
        Task savedTask = service.addTask(task);

        assertNotNull(savedTask);
        assertTrue(savedTask.getId() > 0);
        assertEquals(task.getTitle(), savedTask.getTitle());
    }

    @Test
    public void testAddTask_AlreadyExists() {
        Task task1 = createSampleTask();
        service.addTask(task1);

        Task task2 = createSampleTask();
        TaskAlreadyAddedException exception = assertThrows(TaskAlreadyAddedException.class, () -> {
            service.addTask(task2);
        });

        assertTrue(exception.getMessage().contains(task2.getTitle()));
    }

    @Test
    public void testGetTaskById_Success() {
        Task task = createSampleTask();
        Task saved = service.addTask(task);

        Task fetched = service.getTaskById(saved.getId());
        assertEquals(saved.getId(), fetched.getId());
        assertEquals(saved.getTitle(), fetched.getTitle());
    }

    @Test
    public void testGetTaskById_NotFound() {
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            service.getTaskById(999);
        });
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    public void testUpdateTask_Success() {
        Task task = createSampleTask();
        Task saved = service.addTask(task);

        Task toUpdate = new Task();
        toUpdate.setId(saved.getId());
        toUpdate.setTitle("Updated Title");
        toUpdate.setStatus(Status.COMPLETED);

        Task updated = service.updateTask(toUpdate);

        assertEquals("Updated Title", updated.getTitle());
        assertEquals(Status.COMPLETED, updated.getStatus());
    }

    @Test
    public void testUpdateTask_NotFound() {
        Task toUpdate = new Task();
        toUpdate.setId(999);
        toUpdate.setTitle("Nonexistent Task");

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            service.updateTask(toUpdate);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    public void testDeleteTask_Success() {
        Task task = createSampleTask();
        Task saved = service.addTask(task);

        Task deleted = service.deleteTask(saved);

        assertEquals(saved.getId(), deleted.getId());

        // Verify task is removed
        assertThrows(TaskNotFoundException.class, () -> {
            service.getTaskById(saved.getId());
        });
    }

    @Test
    public void testDeleteTask_NotFound() {
        Task task = createSampleTask();
        task.setId(999);

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            service.deleteTask(task);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = createSampleTask();
        Task task2 = createSampleTask();
        task2.setTitle("Another Task");

        service.addTask(task1);
        service.addTask(task2);

        List<Task> allTasks = service.getAllTasks();
        assertEquals(2, allTasks.size());
    }

    @Test
    public void testGetTasksByStatus() {
        Task task1 = createSampleTask(); // Pending
        Task task2 = createSampleTask();
        task2.setTitle("Another Task");
        task2.setStatus(Status.COMPLETED);

        service.addTask(task1);
        service.addTask(task2);

        List<Task> pendingTasks = service.getTasksByStatus("Pending");
        List<Task> completedTasks = service.getTasksByStatus("Completed");

        assertEquals(1, pendingTasks.size());
        assertEquals(Status.PENDING, pendingTasks.get(0).getStatus());

        assertEquals(1, completedTasks.size());
        assertEquals(Status.COMPLETED, completedTasks.get(0).getStatus());
    }

    @Test
    public void testGetTasksByPriority() {
        Task task1 = createSampleTask(); // High
        Task task2 = createSampleTask();
        task2.setTitle("Another Task");
        task2.setPriority(Priority.LOW);

        service.addTask(task1);
        service.addTask(task2);

        List<Task> highPriority = service.getTasksByPriority("HIGH");
        List<Task> lowPriority = service.getTasksByPriority("LOW");

        assertEquals(1, highPriority.size());
        assertEquals(Priority.HIGH, highPriority.get(0).getPriority());

        assertEquals(1, lowPriority.size());
        assertEquals(Priority.LOW, lowPriority.get(0).getPriority());
    }
}