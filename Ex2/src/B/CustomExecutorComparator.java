package B;

import java.util.Comparator;

 class CustomExecutorComparator implements Comparator<Task> {
    @Override
    public int compare(Task t, Task o) {
        return t.compareTo(o);
    }
}