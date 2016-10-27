package com.bootstrap.jimas.task;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 定义3个同步任务
 * 
 * @author weqinjia.liu
 * @version v.0.1
 */
@Component
public class SyncTask {
    // 定义一个随机对象.
    public static Random random = new Random();
    private static final Logger logger=Logger.getLogger(SyncTask.class);

    // 任务一;
    public void doTaskOne() throws Exception {
        logger.debug("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        logger.debug("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    // 任务二;
    public void doTaskTwo() throws Exception {
        logger.debug("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        logger.debug("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    // 任务3;
    public void doTaskThree() throws Exception {
        logger.debug("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        logger.debug("完成任务三，耗时：" + (end - start) + "毫秒");
    }

}
