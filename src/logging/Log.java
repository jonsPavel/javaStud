package logging;

import startApp.Main;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log {
    protected static Logger LOGGER;

    public Log(){
        try (FileInputStream ins = new FileInputStream("C:\\Users\\Asus\\IdeaProjects\\Start\\src\\logging\\logging.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        this.LOGGER = LOGGER;

    }
    public Logger getLOGGER() {
        return LOGGER;
    }

}
