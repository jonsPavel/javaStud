package startApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;
import jdo.*;

import stream.WriteStream;
import db.SystemUsers;
import java.util.Formatter;

import java.util.Properties;


public class Main {

    static void showMachines(ArrayList<Machine> machineList,ArrayList<HardMachine> hardMachineList){
        if(machineList.size()!=0){
            System.out.println("Легковые машины:");
            System.out.println("       Марка | макс. скорость |  радиостанция |  включено ли |");
            for (int i =0;i<machineList.size();i++){
                machineList.get(i).show();
            }
        }
        else{
            System.out.println("\nНет записей о легковых машинах");
        }


        if(hardMachineList.size()!=0) {
            System.out.println("\nГрузовые машины:");
            System.out.println("       Марка | макс. скорость |  радиостанция |  включено ли | высота |   вес|");
            for (int i = 0; i < hardMachineList.size(); i++) {
                hardMachineList.get(i).show();
            }
        }
        else{
            System.out.println("\nНет записей о грузовых машинах");
        }
    }

    //Логгер
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("C:\\Users\\Asus\\IdeaProjects\\Start\\src\\logging\\logging.properties")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }


    public static void main(String args[])  {

        //Файлы конфигурации
        FileInputStream fis;
        Properties property = new Properties();
        String defUser="Пользователь";
        try {
            fis = new FileInputStream("C:\\Users\\Asus\\IdeaProjects\\Start\\src\\logging\\config.properties");
            property.load(fis);

            defUser = property.getProperty("db.user");

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            LOGGER.log(Level.INFO,"Ошибка загрузки файла конфигурации.\n"+e);
        }


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

        LOGGER.log(Level.INFO,"Добро пожаловать, "+ defUser +"\nМодификатор доступа: "+sU.getAccessModifier());


        // создание переменных для пользования программой
        int switchCase;
        int hardM;
        boolean exit=false;

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
                    showMachines(machineList,hardMachineList);
                    break;
                case (3):
                    try{ //считывания с файла
                        LOGGER.log(Level.INFO,"Загружаем сохранённые данные...");
                        machineList = iS.inStreamMachine();//список из бинарника }
                        hardMachineList = iS.inStreamHardMachine();//список из бинарника }
                        LOGGER.log(Level.INFO,"Данные успешно загружены.");
                    }
                    catch (Exception e){
                        LOGGER.log(Level.WARNING," Не удалось загрузить машины с файла.\n Ошибка "+e);
                    }
                    break;
                case (4):
                    try{
                        WriteStream wS = new WriteStream(hardMachineList,machineList);
                        wS.outStreamMachines();
                        wS.outStreamHardMachines();
                        LOGGER.log(Level.WARNING,"Данные ЗАПИСАНЫ");
                    }
                    catch (Exception e){
                        LOGGER.log(Level.WARNING,"Неудачная попытка сериализации данных.\n Ошибка "+e);
                    }
                    break;
                case (5):
                    showMachines(machineList,hardMachineList);
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
