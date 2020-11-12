package logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Property {
    FileInputStream fis;
    String defUser = "Пользователь";
    protected Properties property = new Properties();

    public Property(Logger LOGGER){
        try {
            fis = new FileInputStream("C:\\Users\\Asus\\IdeaProjects\\Start\\src\\logging\\config.properties");
            property.load(fis);
            defUser = property.getProperty("db.user");

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            LOGGER.log(Level.WARNING,"Ошибка загрузки файла конфигурации.\n"+e);
        }
    }

    public Properties getProperties(){
        return property;
    }
}
