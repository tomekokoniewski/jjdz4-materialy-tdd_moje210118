package com.infoshare.junit.banking;

import com.google.common.base.Stopwatch;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class LoggingActivityMonitor implements AccountMonitor {

    @Override
    public void connect() {
        Stopwatch watch = Stopwatch.createStarted();
        try {
            Thread.sleep(1000);
            watch.stop();
            System.out.println("");
            System.out.println("LoggingActivityMonitor connected after " + watch.elapsed(TimeUnit.MILLISECONDS)+"ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        System.out.println("");
        System.out.println("LoggingActivityMonitor closed ");
    }

    @Override
    public void update(Observable o, Object arg) {
//        System.out.println(o + " updated with " + arg);
    }
}
