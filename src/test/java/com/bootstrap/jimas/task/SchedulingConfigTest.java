package com.bootstrap.jimas.task;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootstrap.jimas.db.mongodb.BaseTest;

public class SchedulingConfigTest extends BaseTest {

    @Autowired
    private SchedulingConfig schedulingConfig;
    
    @Test
    public void testSchedulerLog() {
        fail("Not yet implemented");
    }

    @Test
    public void testLogIpCount() {
        schedulingConfig.logIpCount();
    }
    @Test
    public void testLogUrlCount() {
        schedulingConfig.logUrlCount();
    }

}
