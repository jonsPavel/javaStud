package controller;

import db.SystemUsers;
import jdo.HardMachine;
import jdo.InputMachines;
import jdo.Machine;
import stream.WriteStream;

import javax.swing.*;
import java.util.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import logging.UserExceptions;


public class MenuCases {
    public static SystemUsers sU;
    static Logger LOGGER;
    protected ArrayList<Machine> machineList;
    protected ArrayList<HardMachine> hardMachineList;
    private static int hardM;
    private static int switchCase;

    protected LinkedList<Machine> linkedMachineList;                                                     // 4 lab
    protected LinkedList<HardMachine> linkedHardMachineList;

    public MenuCases(Logger LOGGER, SystemUsers sU, ArrayList<Machine> machineList, ArrayList<HardMachine> hardMachineList){
        this.LOGGER = LOGGER;
        this.sU = sU;
        this.machineList = machineList;
        this.hardMachineList = hardMachineList;

        this.linkedMachineList = new LinkedList(machineList);                                           //  4  lab
        this.linkedHardMachineList = new LinkedList(hardMachineList);
    }

    Scanner in = new Scanner(System.in);
    WriteStream iS = new WriteStream(); // для загрузки данных
    String defUser = "Пользователь";

    public void case1() throws UserExceptions {                     // метод case1 будет обязан завернуться в try/catch
        if (sU.getAccessModifier() == 1 || sU.getAccessModifier() == 2) { // проверка на пользователя
            LOGGER.log(Level.INFO, "Начато создание новой записи");
            System.out.println("Грузовой ли автомобиль вы хотите ввести?\n " + "1 - да, 0 - нет\n");
            hardM = in.nextInt();
            if (hardM == 1) {
                HardMachine hM;
                hM = InputMachines.inputHM();
                hardMachineList.add(hM);

                linkedHardMachineList.add(hM);                                                  // 4 lab

            } else {
                Machine m;
                m = InputMachines.inputM();
                machineList.add(m);

                linkedMachineList.add(m);                                                       // 4 lab
            }
        } else {
            LOGGER.log(Level.WARNING, "Попытка записи непривелегированным пользователем");
            throw new UserExceptions(sU.getAccessModifier());
        }

    }

    public void case3() {
        try { //считывания с файла
            LOGGER.log(Level.INFO, "Загружаем сохранённые данные...");
            machineList = iS.inStreamMachine();//список из бинарника }
            hardMachineList = iS.inStreamHardMachine();//список из бинарника }

                                                                                                         //4 lab
            linkedMachineList = new LinkedList<>(machineList);
            linkedHardMachineList = new LinkedList<>(hardMachineList);

            LOGGER.log(Level.INFO, "Данные успешно загружены.");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, " Не удалось загрузить машины с файла.\n Ошибка " + e);
        }
    }

    public ArrayList<Machine> getMachineList() { // нужны для возвращения коллекций в класс Menu
        return machineList;
    }

    public ArrayList<HardMachine> getHardMachineList() {
        return hardMachineList;
    }
    public LinkedList<Machine> getLinkedMachineList() {
        return linkedMachineList;
    }
    public LinkedList<HardMachine> getHardMachineLinkedList() {
        return linkedHardMachineList;
    }


    public void case4() throws UserExceptions {
        if (sU.getAccessModifier() == 1 || sU.getAccessModifier() == 2) {
            try {
                WriteStream wS = new WriteStream(hardMachineList, machineList);
                wS.outStreamMachines();
                wS.outStreamHardMachines();
                LOGGER.log(Level.WARNING, "Данные ЗАПИСАНЫ");
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Неудачная попытка сериализации данных.\n Ошибка " + e);
            }
        } else {
            LOGGER.log(Level.WARNING, "Попытка записи непривелегированным пользователем");
            throw new UserExceptions(sU.getAccessModifier());

        }
    }

    public void case5() throws  UserExceptions {
        if (sU.getAccessModifier() == 1 || sU.getAccessModifier() == 2) {
            System.out.println("Какую таблицу вы ходите изменить?\n1 - легковые, 2 - грузовые");
            try {
                switchCase = in.nextInt();
            } catch (Exception ex) {
                System.out.println("Неправильный ввод данных." + ex);
            }

            switch (switchCase) {
                case (1):
                    System.out.println("Какую по счёту запись вы ходите удалить?");
                    try {
                        switchCase = in.nextInt();
                        machineList.remove(switchCase - 1);
                        linkedMachineList.remove(switchCase - 1);                                 // 4 lab
                    } catch (Exception ex) {
                        System.out.println("Неправильный ввод данных." + ex);
                    }
                    break;

                case (2):
                    System.out.println("Какую по счёту запись вы ходите удалить?");
                    try {
                        switchCase = in.nextInt();
                        hardMachineList.remove(switchCase - 1);
                        linkedHardMachineList.remove(switchCase - 1);                              // 4 lab
                    } catch (Exception ex) {
                        System.out.println("Неправильный ввод данных." + ex);
                    }
                    break;
            }
        }
        else {
            LOGGER.log(Level.INFO,defUser+" пытается удалить данные");
            throw new UserExceptions(sU.getAccessModifier());
        }
    }

    public void showLinkedMachines(LinkedList<Machine> machineList, LinkedList<HardMachine> hardMachineList){
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

    public void generateNewElement(LinkedList linkedMachineList,LinkedList linkedHardMachineList){
        linkedHardMachineList.add(HardMachine.generateRandomElement());
        linkedMachineList.add(Machine.generateRandomElement());
//        LOGGER.log(Level.INFO,"Добавлено");
    }

    public void generateNewElementToArray(ArrayList linkedMachineList,ArrayList linkedHardMachineList){
        linkedHardMachineList.add(HardMachine.generateRandomElement());
        linkedMachineList.add(Machine.generateRandomElement());
//        LOGGER.log(Level.INFO,"Добавлено");
    }
    public void generateArrayListElements(ArrayList linkedMachineList,ArrayList linkedHardMachineList) {
        System.out.println("Какое количество элементов вы хотите добавить?");
        int sizelinHM=linkedHardMachineList.size();
        int sizelinM=linkedMachineList.size();

        long time = System.currentTimeMillis();

        switchCase = in.nextInt();
        long timeStart = System.currentTimeMillis();
        for(int i=0;i<switchCase;i++){
            try {
                generateNewElementToArray( linkedMachineList, linkedHardMachineList);
                LOGGER.log(Level.INFO,"add,"+sizelinHM+i+", "+(System.currentTimeMillis()-timeStart)+"мс");
            }
            catch (Exception e ){
                LOGGER.log(Level.WARNING,"Ошибка.\n "+e);
            }

        }
        long elapsedTime =  System.currentTimeMillis() - timeStart;
        System.out.println("Затраченное время: "+elapsedTime+"мс");
    }
    public void generateLinkedListElements(LinkedList linkedMachineList,LinkedList linkedHardMachineList) {
        int sizelinHM=linkedHardMachineList.size();
        int sizelinM=linkedMachineList.size();
        System.out.println("Какое количество элементов вы хотите добавить?");
        switchCase = in.nextInt();
        long timeStart = System.currentTimeMillis();
        for(int i=0;i<switchCase;i++){
            generateNewElement( linkedMachineList, linkedHardMachineList);
            LOGGER.log(Level.INFO,"add,"+sizelinHM+i+", "+(System.currentTimeMillis()-timeStart)+"мс");

        }
        long elapsedTime =  System.currentTimeMillis() - timeStart;
        System.out.println("Затраченное время: "+elapsedTime+"мс");
    }
}

