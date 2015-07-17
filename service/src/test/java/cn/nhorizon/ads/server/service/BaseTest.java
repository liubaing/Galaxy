package cn.nhorizon.ads.server.service;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public abstract class BaseTest {

    @BeforeClass
    public static void init() {
        PropertyConfigurator.configure("classpath:logback-test.xml");
    }

}
