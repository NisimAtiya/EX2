package B;

import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    private int Prioritytask;


    public CustomExecutor() {
        super((Runtime.getRuntime().availableProcessors())/2, (Runtime.getRuntime().availableProcessors())-1,
                300L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }

    protected <T> myfuture<T> maketask(Task task){
        return new myfuture<T>(task.getCallable(),task.getTaskType().getPriorityValue());
    }


    public <T> Future<T> submitT(Task task) {//1
        if (task == null) throw new NullPointerException();
        myfuture<T> t = maketask(task);
        execute(t);
        return t;
    }
    public Future submit(Task task){
        if (task == null) throw new NullPointerException();
        return submitT(task);
    }
    public Future submit(Callable callable, TaskType taskType) {//2
        if (callable == null || taskType == null) {
            throw new NullPointerException();
        }
        return submitT(Task.createTask(callable,taskType));
    }

    public Future submit(Callable callable){//3
        if (callable == null) {
            throw new NullPointerException();
        }
        return submitT(Task.createTask(callable));
    }


    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if(!super.getQueue().isEmpty()){
            if (((myfuture) r).getPriority() < ((myfuture) super.getQueue().peek()).getPriority()) {
                setPrioritytask(((myfuture) super.getQueue().peek()).getPriority());
            }
        } else{setPrioritytask(0);}
    }

    public void setPrioritytask(int prioritytask) {
        Prioritytask = prioritytask;
    }
    public int getCurrentMax() {//10
        return this.Prioritytask;
    }


    public void gracefullyTerminate() {
        super.shutdown();


    }
}



