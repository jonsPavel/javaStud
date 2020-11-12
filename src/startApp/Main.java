package startApp;

import logging.Property;
import view.Menu;
import logging.Log;


public class Main {
    public static Log LOGGER = new Log();
    static Property property = new Property(LOGGER.getLOGGER()); // загрузка свойств конфигурации
    public static Menu mainMenu = new Menu(LOGGER.getLOGGER(),property.getProperties()); //создание объекта Menu

    public static void main(String args[]){
        mainMenu.mainMenu();//Вход в главное меню
    }

}
