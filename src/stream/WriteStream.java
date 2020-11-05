package stream;

import jdo.Machine;
import jdo.HardMachine;

import java.io.*;
import java.util.*;



public class WriteStream implements java.io.Serializable{
    String fileMachines = "machines.dat";
    String fileHardMachines = "hardMachines.dat";
//    String fileMachines = "./db/machines.dat";
//    String fileHardMachines = "./db/hardMachines.dat";

    
    ArrayList<Machine> machineList;//создание нового списка
    ArrayList<HardMachine> hardMachineList;//создание нового списка



    public WriteStream(ArrayList<HardMachine> hardMachineList,ArrayList<Machine> machineList) {
        this.hardMachineList = hardMachineList;
        this.machineList = machineList;
    }

//    public WriteStream(ArrayList<Machine> MachineList) {
//        this.machineList = MachineList;
//    }

    public WriteStream() {
        this.machineList = machineList;
        this.hardMachineList = hardMachineList;
    }



    public void outStreamMachines(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileMachines))) {
            oos.writeObject(machineList);
            System.out.println("File Machines has been written");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void outStreamHardMachines(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileHardMachines))) {
            oos.writeObject(hardMachineList);
            System.out.println("File HardMachines has been written");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }




    public ArrayList<Machine> inStreamMachine (){
        ArrayList<Machine> newMachines= new ArrayList<Machine>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileMachines))) {
            newMachines=((ArrayList<Machine>)ois.readObject());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        for(Machine p : newMachines)
            System.out.println(p.toString());
        return newMachines;
    }


    public ArrayList<HardMachine> inStreamHardMachine (){
        ArrayList<HardMachine> newHardMachines= new ArrayList<HardMachine>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileHardMachines))) {
            newHardMachines=((ArrayList<HardMachine>)ois.readObject());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        for(Machine p : newHardMachines)
            System.out.println(p.toString());
        return newHardMachines;
    }
}