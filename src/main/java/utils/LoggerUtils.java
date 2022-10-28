package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtils {

    //we want this logger to be accessible only from the classes that extends this class
    protected static final Logger log = LogManager.getLogger();

}
