package com.citi;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.apache.log4j.Logger;

//import java.util.logging.Logger;

/**
 * @author: Matthew
 * @date: 2019/9/15 23:22
 */
public class Test {
//    private static Logger logger = LoggerFactory.getLogger(Test.class);
    private static Logger logger = Logger.getLogger(Test.class);
//    private static Logger logger = Logger.getLogger("Test");
    public static void main(String[] args) {
        logger.info("start");
        System.out.println("dada");
        logger.info("end");
    }
}
