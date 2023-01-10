# OOP_EX2
*
### This project includes three approaches for counting the lines in a text file:
**
* - Regular, without any threads
* - Using Threads
* - Using ThreadPool
*
## Classes:
**
### ThreadFile:

#### ThreadFile extends the Thread class and is used to calculate the number of lines in a text file using a single thread.

##### Methods:
* - `ThreadFile(String fileName)`: Constructor that assigns the name of the file and creates a thread with that name.
* - `calcLines()`: Private method for calculating the number of lines in the file with the given name.
* - `getLines()`: Returns the number of lines in the file.
* - `run()`: Executes the thread. In this case, run() calls the helper function calcLines().

### ThreadFilePool:
#### ThreadFilePool implements the Callable interface and is used to calculate the number of lines in a text file using a thread pool.

##### Methods:
*  `ThreadFilePool(String fileName) `: Constructor that assigns the name of the file and creates a thread with that name.
* - `calcLines()`: Private method for calculating the number of lines in the file with the given name.
* - `call()`: Calls the helper function calcLines() and returns the number of lines in the file.

## differentiation running times:
**
- first we will create the files...
- in our example number of files that been created is- 1000
- total number of lines is- 99896879
***
- running `getNumofLines`- its took by 4028 milliseconds
- running `getNumofLinesThreads`- its took by 2749 milliseconds
- running `getNumofLinesThreadsPool`- its took by 1849 milliseconds
##### we can see that using ThreadsPool saves as almost half of the time(45.9%) , and using Thread  save as third the time(38.45%)

## summarize of running time using the three methods
**
#### using a Threads and ThreadsPool save as many of time - that is because Using Threads or a ThreadPool can improve the performance of a program by allowing it to perform multiple tasks concurrently.
#### The regular function was slow because it executed tasks sequentially.
#### The function with Threads was faster because it used separate Threads for each task.
#### The function with a ThreadPool was the fastest because it used a fixed number of worker threads to execute tasks from a queue.
#### A ThreadPool is particularly useful for executing a large number of tasks because it allows for control of the number of concurrent tasks.
#### Threads and ThreadPools allow for the utilization of idle CPU time and overlap of task execution.