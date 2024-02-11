package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service<T extends Runnable> {

    public void executeTask(T task){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task);
    }
}
