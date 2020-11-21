package view;

import jdo.HardMachine;
import jdo.Machine;

import java.util.ArrayList;

public class ShowMachines {
    //Отображение меню и машин.

    static void showMenu(){
        System.out.println("\n1. Продолжить запись");
        System.out.println("2. Вывести все машины");
        System.out.println("3. Прочесть данные из файла");
        System.out.println("4. Записать данные в файл");
        System.out.println("5. Удалить записи о машине");
        System.out.println("6. Добавить случайные элементы в LinkedList");
        System.out.println("7. Вывести link списки");
        System.out.println("8. Добавить случайные элементы в ArrayList");
        System.out.println("9. Добавить случайные элементы в ArrayList в количестве..");
        System.out.println("10. Добавить случайные элементы в LinkedList в количестве..");
        System.out.println("");
        System.out.println("0. Выйти");
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
