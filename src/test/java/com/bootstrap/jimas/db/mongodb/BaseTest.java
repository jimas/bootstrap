package com.bootstrap.jimas.db.mongodb;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bootstrap.jimas.BootStrapApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootStrapApplication.class)
@WebAppConfiguration
public class BaseTest{

}
