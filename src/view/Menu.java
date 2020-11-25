package view;

import db.SystemUsers;
import jdo.DPS;
import jdo.HardMachine;
import jdo.Machine;
import logging.UserExceptions;
import stream.WriteStream;
import controller.MenuCases;
import logging.updatedProperties;


import java.util.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {
 //                     В основном тут выводит собщения только Логгер.
    static Logger LOGGER;
    protected updatedProperties property;

    public Menu(Logger LOGGER,updatedProperties property){
        this.LOGGER = LOGGER;
        this.property = property;
    }


    public void mainMenu()  {
        String defUser="Пользователь";

        LOGGER.log(Level.INFO,"\nПриложение запущено.");

        LOGGER.log(Level.INFO,"Создание локальных данных для работы приложения...");
        WriteStream iS = new WriteStream(); // для загрузки данных
        Scanner in = new Scanner(System.in);
        DPS dps = new DPS(); //подготовка структур данных
        ArrayList<Machine> machineList = new ArrayList<Machine>();//создание списка машин
        ArrayList<HardMachine> hardMachineList=new ArrayList<>();//создание списка грузовых машин

        LinkedList linkedMachineList= new LinkedList(machineList);
        LinkedList linkedHardMachineList= new LinkedList(hardMachineList); // создание линкованных листов

        SystemUsers sU = new SystemUsers(); // проверка пользователя
        LOGGER.log(Level.INFO,"Передача управления системе входа...");
        sU.logIn();

        if(sU.getAccessModifier()==0){
            LOGGER.log(Level.INFO,"Неудачная попытка авторизации.");
            System.exit(0);
        }

        LOGGER.log(Level.INFO,"Добро пожаловать, "+ property.getDefUser() +"\nМодификатор доступа: "+sU.getAccessModifier());

        // создание переменных для пользования программой
        int switchCase;
        int hardM;
        boolean exit=false;

        MenuCases mC = new MenuCases(LOGGER, sU,  machineList, hardMachineList);
        while (true) {
            if (exit)
                break;
          Show.showMenu();

            switchCase = in.nextInt();

            switch (switchCase) {
                case  (1):
                    try {
                        mC.case1();
                    }
                    catch (UserExceptions ex){
                        System.out.println(ex);
                    };
                    break;

                case (2):
                        Show.showMachines(machineList,hardMachineList);
                    break;

                case (3):
                    mC.case3();
                    machineList = mC.getMachineList();
                    hardMachineList = mC.getHardMachineList();
                    linkedHardMachineList= mC.getHardMachineLinkedList();
                    linkedMachineList = mC.getLinkedMachineList();
                    break;

                case (4):
                    try {
                        mC.case4();
                    }
                    catch (UserExceptions ex){
                        System.out.println(ex);
                    }
                break;

                case (5):
                    try {
                        Show.showMachines(machineList,hardMachineList);
                        mC.case5();
                    }
                    catch (UserExceptions ex){
                        System.out.println(ex);
                    }
                    break;
                case (6):
                    mC.generateNewElement(linkedMachineList,linkedHardMachineList);
                    break;
                case (7):
                    mC.showLinkedMachines(linkedMachineList,linkedHardMachineList);
                    break;
                case (8):
                    mC.generateNewElementToArray(machineList,hardMachineList);
                    break;
                case (9):
                    mC.generateArrayListElements(machineList,hardMachineList);
                    break;
                case (10):
                    mC.generateLinkedListElements(linkedMachineList,linkedHardMachineList);
                    break;

                case (0):
                    System.out.println("Спасибо, что пользуетесь нашим продуктом!");
                    exit = true;
                    break;
                default:
                    continue;
            }
        }
        LOGGER.log(Level.INFO,"Сеанс завершён.\n\n");
    }
}
