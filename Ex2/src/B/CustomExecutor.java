package B;

import java.util.concurrent.*;

import static java.lang.Integer.MAX_VALUE;

public class CustomExecutor extends ThreadPoolExecutor {
    private int[] Prioritytask = new int[10];
    private boolean isshutdown = false;

    public CustomExecutor() {
        super((Runtime.getRuntime().availableProcessors())/2, (Runtime.getRuntime().availableProcessors())-1,
                300L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }

    protected <T> myfuture<T> maketask(Task task){
        return new myfuture<T>(task.getCallable(),task.getTaskType().getPriorityValue());
    }


    public <T> Future<T> submitT(Task task) {//1
        if(isshutdown==false) {
            if (task == null) throw new NullPointerException();
            this.Prioritytask[task.getTaskType().getPriorityValue()]++;
            myfuture<T> t = maketask(task);
            execute(t);
            return t;
        }else{
            System.err.println("CustomExecutor is shutdown");
            return null;
        }
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
    protected void beforeExecute(Thread t, Runnable r) {
        myfuture r1 = (myfuture) r;
        this.Prioritytask[r1.getPriority()]--;
    }


    public int getCurrentMax() {//10
        for (int i = 1; i < 10; i++) {
            if(this.Prioritytask[i]!=0) return i;
        }
        return -1;
    }


    public void gracefullyTerminate() throws InterruptedException {
        this.isshutdown=true;
        TimeUnit.SECONDS.sleep(1);
        super.shutdown();


    }
}



