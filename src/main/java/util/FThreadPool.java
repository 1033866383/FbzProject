package util;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class FThreadPool {
    class Worker implements Runnable{

        Worker(Runnable runnable){
            que.offer(runnable);
        }

        @Override
        public void run() {
            while (RUNNING){
                if(shutdown == true){
                    Thread.interrupted();
                }
                Runnable task = null;
                try {
                    task = getTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.run();
            }
        }

        public Runnable getTask() throws InterruptedException {
            return que.take();
        }

        public void interruptIfIdle(){
            for(Thread thread : threadList){
                System.out.println(thread.getName() + "interrupt");
                Thread.interrupted();
            }
        }
    }

    private volatile boolean RUNNING = true;
    private static BlockingDeque<Runnable> que = new LinkedBlockingDeque<>();
    private final Set<Worker> workers = new HashSet();
    private final List<Thread> threadList = new ArrayList<Thread>();
    int poolSize = 0;
    int coreSize = 0;
    boolean shutdown = false;
    public FThreadPool(int poolSize){
        this.poolSize = poolSize;
    }
    public void exec(Runnable runnable){
        if(runnable == null)
            throw new NullPointerException();
        if(coreSize < poolSize){
            addThead(runnable);
        }else {
            try {
                que.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addThead(Runnable runnable){
        coreSize++;
        Worker worker = new Worker(runnable);
        workers.add(worker);
        Thread thread = new Thread(worker);
        threadList.add(thread);
        thread.start();
    }

    public void shutdown(){
        RUNNING = false;
        if(!workers.isEmpty()){
            for(Worker worker : workers){
                worker.interruptIfIdle();
            }
        }
        shutdown = true;
        Thread.currentThread().interrupt();
    }

    public static void main(String[] args) {
            FThreadPool excutor = new FThreadPool(5);
            for (int i = 0; i < 10; i++) {
                excutor.exec(()->{
                        System.out.println("线程 " + Thread.currentThread().getName() + " run");
                });
            }

    }
}
