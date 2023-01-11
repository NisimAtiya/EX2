package B;

import java.util.concurrent.Callable;

/**
 * Represents an operation that can be run asynchronously and can return a value of some type with some priority
 */
public class Task<T> implements Comparable<Task<T>>, Callable<T> {
    private TaskType taskType;
    private Callable<T> callable;

    /**
     * A private constructor that accepts a Callable and a TaskType
     */
    private Task(Callable<T> callable1,TaskType taskType1){
        setTaskType(taskType1);
        setCallable( callable1);
    }
    /**
     * A private constructor that accepts a Callable and puts TaskType OTHER
     */
    private Task(Callable<T> callable1){
        setCallable(callable1);
        setTaskType(TaskType.OTHER);
    }

    /**
     * Factory method
     * @return Task
     */
    public static <T> Task createTask(Callable callable1, TaskType taskType1){
        return new Task(callable1,taskType1);
    }
    /**
     * Factory method
     * @return Task
     */
    public static <T> Task createTask(Callable callable1){
        return new Task(callable1);
    }

    /**
     * Compares the taskType
     * @param o the object to be compared.
     * @return 1 if I am better, -1 if he is better, 0 if we are equal
     */
    @Override
    public int compareTo(Task<T> o) {
        if(this.getTaskType().getPriorityValue()<o.getTaskType().getPriorityValue()){
            return 1;
        } else if (this.getTaskType().getPriorityValue()>o.getTaskType().getPriorityValue()) {
            return -1;
        }else {
            return 0;
        }
    }

    /**
     * Activates the call function
     * @throws Exception
     */
    @Override
    public T call() throws Exception {
        try {
            return this.callable.call();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * function getTaskType
     * @return the TaskType
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * function setTaskType
     * @param taskType to set
     */
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    /**
     * function getCallable
     * @return the Callable
     */
    public  Callable<T> getCallable() {
        return callable;
    }
    /**
     * function setCallable
     * @param callable to set
     */
    public void setCallable(Callable<T> callable) {
        this.callable = callable;
    }

    /**
     * toString function
     * @return A string representing the task
     */
    @Override
    public String toString() {
        return "B.Task{" +
                "taskType=" + getTaskType() +
                ", callable=" + getCallable() +
                '}';
    }
}
