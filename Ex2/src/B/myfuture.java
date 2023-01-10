package B;

import java.util.concurrent.*;


public class myfuture<V> extends FutureTask<V> implements Comparable<myfuture> {

    private int priority;

    public myfuture(Callable<V> callable, int p) {
        super(callable);
        setPriority(p);
    }
    public int getPriority(){
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return super.toString() + " Priority: "+getPriority();
    }
    public String printPriority()
    {
        return ""+getPriority();
    }


    @Override
    public int compareTo(myfuture o) {
        if (getPriority()> o.getPriority())
            return 1;
        else if (this.getPriority() < o.getPriority())
            return -1;
        return 0;
    }
}
