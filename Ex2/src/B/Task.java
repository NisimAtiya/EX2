package B;

import java.util.concurrent.Callable;

public class Task<T> implements Comparable<Task<T>>, Callable<T> {
    private TaskType taskType;
    private Callable<T> callable;
    //A regular constructor
    private Task(Callable<T> callable1,TaskType taskType1){
        setTaskType(taskType1);
        setCallable( callable1);
    }
    //constructor without B.TaskType
    private Task(Callable<T> callable1){
        setCallable(callable1);
        setTaskType(TaskType.OTHER);
    }
    public static <T> Task createTask(Callable callable1, TaskType taskType1){
        return new Task(callable1,taskType1);
    }
    public static <T> Task createTask(Callable callable1){
        return new Task(callable1);
    }

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

    @Override
    public T call() throws Exception {
        try {
            return this.callable.call();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public  Callable<T> getCallable() {
        return callable;
    }

    public void setCallable(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    public String toString() {
        return "B.Task{" +
                "taskType=" + getTaskType() +
                ", callable=" + getCallable() +
                '}';
    }
public static void main(String[] args) {
        var task = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i; }
            return sum;
        }, TaskType.IO);
        var task2 = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i; }
            return sum;
        }, TaskType.COMPUTATIONAL);
    System.out.println(task.toString());
    }



}
