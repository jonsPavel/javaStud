package jdo;

import java.util.Scanner;
public class InputMachines{

    public static HardMachine inputHM() { //Создание грузовой Машины
        Scanner in = new Scanner(System.in);

        String mark;
        Integer maxSpeed;
        Double currentStation;
        boolean status;
        boolean hardM;
        Integer weight;
        Integer height;

        System.out.println("Введите марку машины:");
        mark=in.nextLine();

        System.out.println("Введите максимальную скорость: ");
        maxSpeed=in.nextInt();

        System.out.println("Введите возможную станцию радио: ");
        currentStation = in.nextDouble();

        System.out.println("Включено ли радио?\n "+"1 - да, 0 - нет\n");
        Integer statInt= in.nextInt();
        if(statInt==1)
            status = true;
        else
            status= false;

        System.out.println("Введите его вес (в килограммах)");
        weight=in.nextInt();

        System.out.println("Введите его высоту (в метрах)");
        height=in.nextInt();

        hardM=true;
        HardMachine hM = new HardMachine( mark,  maxSpeed,  currentStation,  status,  weight,height);
        return hM;

    }

    public static Machine inputM() { //Создание легковой Машины
        Scanner in = new Scanner(System.in);

        String mark;
        Integer maxSpeed;
        Double currentStation;
        boolean status;
        boolean hardM;
        //	Integer weight;
        //	Integer height;

        System.out.println("Введите марку машины:");
        mark=in.nextLine();

        System.out.println("Введите максимальную скорость: ");
        maxSpeed=in.nextInt();

        System.out.println("Введите возможную станцию радио: ");
        currentStation = in.nextDouble();

        System.out.println("Включено ли радио?\n "+"1 - да, 0 - нет\n");
        Integer statInt= in.nextInt();
        if(statInt==1)
            status = true;
        else
            status= false;

        hardM = false;
        Machine M = new Machine(mark, maxSpeed, currentStation,  status);
        return M;
    }
}