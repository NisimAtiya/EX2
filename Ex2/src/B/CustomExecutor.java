package B;

import java.util.concurrent.*;

/**
 * A new ThreadPool which supports owning tasks priority.
 */

public class CustomExecutor extends ThreadPoolExecutor {
    private int[] Prioritytask = new int[10];
    private boolean isshutdown = false;

    /**
     * Constructor used in super
     * The minimum number of threads will be half the number of processors
     * which are available for the benefit of the jvm
     * The maximum number of threads will be the number of processors available to the jvm minus 1
     * The period of time allowed for a thread to be idle is 300 milliseconds
     * The queue is PriorityBlockingQueue
     */
    public CustomExecutor() {
        super((Runtime.getRuntime().availableProcessors())/2, (Runtime.getRuntime().availableProcessors())-1,
                300L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
    }

    /**
     * Change task to myfuture
     * @param task-The task for change
     * @return- the task as myfuture
     */
    protected <T> myfuture<T> maketask(Task task){
        return new myfuture<T>(task.getCallable(),task.getTaskType().getPriorityValue());
    }

    /**
     * A method for submitting Task instances to a priority task queue, and puts it in the task queue.
     * If a shutdown was performed, it will not be possible to insert a task and a message will be printed accordingly and null will be returned
     * @param task - the task to submit
     * @return - The value returned from task
     */
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

    /**
     *Sends the task to the submitT function
     * @param task - the task to send
     * @return The value returned from task
     */
    public Future submit(Task task){
        if (task == null) throw new NullPointerException();
        return submitT(task);
    }

    /**
     *Receives Callable and TaskType and sends to the submitT function
     * @param callable - the callable to send
     * @param taskType - The taskType of callable
     * @return The value returned from task
     */
    public Future submit(Callable callable, TaskType taskType) {//2
        if (callable == null || taskType == null) {
            throw new NullPointerException();
        }
        return submitT(Task.createTask(callable,taskType));
    }
    /**
     *Receives Callable and sends to the submitT function
     * @param callable - the callable to send
     * @return The value returned from task
     */
    public Future submit(Callable callable){//3
        if (callable == null) {
            throw new NullPointerException();
        }
        return submitT(Task.createTask(callable));
    }

    /**
     *Updates the Prioritytask that r will run task
     * @param t the thread that will run task
     * @param r the task that will be executed
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        myfuture r1 = (myfuture) r;
        this.Prioritytask[r1.getPriority()]--;
    }

    /**
     * @return the highest priority of a found task Now in line
     */
    public int getCurrentMax() {//10
        for (int i = 1; i < 10; i++) {
            if(this.Prioritytask[i]!=0) return i;
        }
        return -1;
    }

    /**
     *Update isshutdown to true, wait 2 minutes and perform shutdown
     * @throws InterruptedException
     */
    public void gracefullyTerminate() throws InterruptedException {
        this.isshutdown=true;
        TimeUnit.SECONDS.sleep(2);
        super.shutdown();
    }
}



