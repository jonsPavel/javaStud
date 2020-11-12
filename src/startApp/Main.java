package startApp;

import logging.updatedProperties;
import view.Menu;
import logging.Log;


public class Main {
    public static Log LOGGER = new Log(); //создание лога
    static updatedProperties Property = new updatedProperties(LOGGER.getLOGGER()); // загрузка свойств конфигурации в логер
    public static Menu mainMenu = new Menu(LOGGER.getLOGGER(), Property); //создание объекта Menu

    public static void main(String args[]){
        mainMenu.mainMenu();//Вход в главное меню
    }

}
