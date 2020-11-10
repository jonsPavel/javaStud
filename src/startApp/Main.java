package startApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;
import jdo.*;

import stream.WriteStream;
import db.SystemUsers;


public class Main {

    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("C:\\Users\\Asus\\IdeaProjects\\Start\\src\\logging\\logging.properties")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    public static void main(String args[]){
        LOGGER.log(Level.INFO,"\nПриложение запущено.");

        LOGGER.log(Level.INFO,"Создание локальных данных для работы приложения...");
        WriteStream iS = new WriteStream(); // для загрузки данных
        Scanner in = new Scanner(System.in);
        DPS dps = new DPS();

        ArrayList<Machine> machineList = new ArrayList<Machine>();//создание списка машин
        ArrayList<HardMachine> hardMachineList=new ArrayList<>();//создание списка грузовых машин


        try{
            LOGGER.log(Level.INFO,"Загружаем сохранённые данные...");
            machineList = iS.inStreamMachine();//список из бинарника }
            hardMachineList = iS.inStreamHardMachine();//список из бинарника }
            LOGGER.log(Level.INFO,"Данные успешно загружены.");
        }
        catch (Exception e){
            LOGGER.log(Level.WARNING," Не удалось загрузить машины с файла.",e);
        }
//        ArrayList<Machine> machineList = new ArrayList<Machine>();//создание списка машин
//        try{machineList = iS.inStreamMachine();}//список из бинарника }
//        catch (Exception e)     { System.out.println("Не удалось загрузить легковые машины\n" + e.getMessage()); }
//
//        ArrayList<HardMachine> hardMachineList=new ArrayList<>();//создание списка грузовых машин
//        try{ hardMachineList = iS.inStreamHardMachine();}//список из бинарника }
//        catch (Exception e)     { System.out.println("Не удалось загрузить грузовые машины\n" +e.getMessage()); }

        boolean exit=false;

        SystemUsers sU = new SystemUsers(); // проверка пользователя

        LOGGER.log(Level.INFO,"Передача управления системе входа...");
        sU.logIn();
        if(sU.getAccessModifier()==0){
            LOGGER.log(Level.INFO,"Неудачная попытка авторизации.");
            System.exit(0);
        }
        LOGGER.log(Level.INFO,"Вы вошли с модификатором доступа: "+sU.getAccessModifier());



        // создание переменных для пользования программой
        int switchCase;
        int hardM;

        while (true) {
            if (exit)
                break;
            System.out.println("\n1. Продолжить запись");
            System.out.println("2. Вывести все машины");
            System.out.println("");
            System.out.println("0. Выйти");

            switchCase = in.nextInt();

            switch (switchCase) {
                case  (1):
                    if(sU.getAccessModifier()==1 || sU.getAccessModifier()==2){ // проверка на пользователя
                        LOGGER.log(Level.INFO,"Начато создание новой записи");

                        System.out.println("Грузовой ли автомобиль вы хотите ввести?\n "+"1 - да, 0 - нет\n");
                        hardM=in.nextInt();
                        if(hardM==1) {
                            HardMachine hM;
                            hM=InputMachines.inputHM();
                            hardMachineList.add(hM);

                            System.out.println("разрешено ли движение?");
                            System.out.println(dps.pass(hM));
                        }

                        else {
                            Machine m;
                            m=InputMachines.inputM();
                            machineList.add(m);

                            System.out.println("разрешено ли движение? - ");
                            System.out.println(dps.pass(m));
                        }
                    }
                    else {
                        System.out.println("У вас нет прав на запись.");
                        LOGGER.log(Level.WARNING,"Попытка записи непривелегированным пользователем");
                    }

                    break;

                case (2):
                    System.out.println("Легковые машины:");
                    machineList.stream().forEach(s-> System.out.println(s.toString()));
                    System.out.println("\nГрузовые машины:");
                    hardMachineList.stream().forEach(s-> System.out.println(s.toString()));
                    break;

                case (0):
                    System.out.println("Спасибо, что пользуетесь нашим продуктом!");
                    try{
                        WriteStream wS = new WriteStream(hardMachineList,machineList);
                        wS.outStreamMachines();
                        wS.outStreamHardMachines();
                    }
                    catch (Exception e){
                        LOGGER.log(Level.WARNING,"Неудачная попытка сериализации данных.");
                    }
                    exit = true;
                    break;

                default:
                    continue;
            }
        }
    }
}
