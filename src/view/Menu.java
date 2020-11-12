package view;

import db.SystemUsers;
import jdo.DPS;
import jdo.HardMachine;
import jdo.Machine;
import stream.WriteStream;
import controller.MenuCases;
import logging.updatedProperties;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {

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
            System.out.println("\n1. Продолжить запись");
            System.out.println("2. Вывести все машины");
            System.out.println("3. Прочесть данные из файла");
            System.out.println("4. Записать данные в файл");
            System.out.println("5. Удалить записи о машине");
            System.out.println("");
            System.out.println("0. Выйти");

            switchCase = in.nextInt();

            switch (switchCase) {
                case  (1):
                    mC.case1();
                    break;

                case (2):
                    showMachines(machineList,hardMachineList);
                    break;

                case (3):
                    mC.case3();
                    machineList = mC.getMachineList();
                    hardMachineList = mC.getHardMachineList();
                    break;

                case (4):
                    mC.case4();
                    break;

                case (5):
                    showMachines(machineList,hardMachineList);
                    mC.case5();
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


    //Далее идут методы для отображения.

    static void showMachines(ArrayList<Machine> machineList, ArrayList<HardMachine> hardMachineList){
        if(machineList.size()!=0){
            System.out.println("Легковые машины:");
            System.out.println("       Марка | макс. скорость |  радиостанция |  включено ли |");
            for (int i =0;i<machineList.size();i++){
                machineList.get(i).show();
            }
        }
        else
            System.out.println("\nНет записей о легковых машинах");


        if(hardMachineList.size()!=0) {
            System.out.println("\nГрузовые машины:");
            System.out.println("       Марка | макс. скорость |  радиостанция |  включено ли | высота |   вес|");
            for (int i = 0; i < hardMachineList.size(); i++) {
                hardMachineList.get(i).show();
            }
        }
        else
            System.out.println("\nНет записей о грузовых машинах");
    }
}
