
package org.sviperll.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 *
 * @author grinning
 * 
 * Anything written by grinning is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation version 3.
 *
 *   Anything written by grinning is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Anything written by grinning.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class RepeatedReturnableTask<T> {
    
    private T t;
    private ExecutorService executor;
    private Callable<T> task;
    private Map<Integer, Future<T>> tasks = new HashMap<Integer, Future<T>>();
    private int b = -1;
    
    public RepeatedReturnableTask(Callable<T> task) {
        this.executor = Executors.newCachedThreadPool();
        this.task = task;
    }
    
    public Callable<T> getTask() {
        return this.task;
    }
    
    /*
     * @return int this integer is the ID in the local "database" of tasks
     */
    
    public int sumbitAnother() {
        b++;
        tasks.put(b, executor.submit(task));
        return b;
    }
    
    public T getResult(int id) throws InterruptedException, ExecutionException {
        Future<T> job = tasks.get(id);
        if(!job.isDone()) {
            return null;
        }
        return job.get();
    }
    
    @Override
    public void finalize() throws Throwable {
        super.finalize();
        this.t = null;
        this.executor = null;
        this.task = null;
        this.tasks = null;
    }
    
    public T getType() {
        return t;
    }
    
    
}
