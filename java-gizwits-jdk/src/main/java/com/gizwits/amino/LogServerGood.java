package com.gizwits.amino;

import org.amino.ds.lockfree.LockFreeDeque;

import java.io.IOException;
import java.util.Queue;


public class LogServerGood {
    /*Standard Queue interface*/
    private Queue<String> queue;

    public LogServerGood() throws IOException {
        /*Amino components are compatible with standard interface whenever
         * possible*/
        queue = new LockFreeDeque<String>();

    }

}