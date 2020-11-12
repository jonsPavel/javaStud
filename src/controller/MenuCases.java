package controller;

import db.SystemUsers;
import jdo.HardMachine;
import jdo.InputMachines;
import jdo.Machine;
import stream.WriteStream;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MenuCases {
    public static SystemUsers sU;
    static Logger LOGGER;
    protected ArrayList<Machine> machineList;
    protected ArrayList<HardMachine> hardMachineList;
    private static int hardM;
    private static int switchCase;

    public MenuCases(Logger LOGGER, SystemUsers sU, ArrayList<Machine> machineList, ArrayList<HardMachine> hardMachineList){
        this.LOGGER = LOGGER;
        this.sU = sU;
        this.machineList = machineList;
        this.hardMachineList = hardMachineList;
    }

    Scanner in = new Scanner(System.in);
    WriteStream iS = new WriteStream(); // для загрузки данных
    String defUser = "Пользователь";

    public void case1() {
        if (sU.getAccessModifier() == 1 || sU.getAccessModifier() == 2) { // проверка на пользователя
            LOGGER.log(Level.INFO, "Начато создание новой записи");
            System.out.println("Грузовой ли автомобиль вы хотите ввести?\n " + "1 - да, 0 - нет\n");
            hardM = in.nextInt();
            if (hardM == 1) {
                HardMachine hM;
                hM = InputMachines.inputHM();
                hardMachineList.add(hM);

            } else {
                Machine m;
                m = InputMachines.inputM();
                machineList.add(m);
            }
        } else {
            System.out.println(defUser + ", у вас нет прав на запись.");
            LOGGER.log(Level.WARNING, "Попытка записи непривелегированным пользователем");
        }

    }

    public void case3() {
        try { //считывания с файла
            LOGGER.log(Level.INFO, "Загружаем сохранённые данные...");
            machineList = iS.inStreamMachine();//список из бинарника }
            hardMachineList = iS.inStreamHardMachine();//список из бинарника }
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

    public void case4() {
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
            System.out.println(defUser + ", у вас нет прав на запись.");
            LOGGER.log(Level.WARNING, "Попытка записи непривелегированным пользователем");
        }
    }

    public void case5() {
        System.out.println("Какую таблицу вы ходите изменить?\n1 - легковые, 2 - грузовые");
        try{ switchCase = in.nextInt(); }
        catch (Exception ex) { System.out.println("Неправильный ввод данных." + ex); }

        switch (switchCase){
            case(1):
                System.out.println("Какую по счёту запись вы ходите удалить?");
                try{ switchCase = in.nextInt();machineList.remove(switchCase-1); }
                catch (Exception ex) { System.out.println("Неправильный ввод данных." + ex); }
                break;

            case(2):
                System.out.println("Какую по счёту запись вы ходите удалить?");
                try{ switchCase = in.nextInt(); hardMachineList.remove(switchCase-1);}
                catch (Exception ex) { System.out.println("Неправильный ввод данных." + ex); }
                break;
        }
    }

}

