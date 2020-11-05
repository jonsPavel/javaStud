package jdo;

public class DPS implements java.io.Serializable{

    public boolean pass(HardMachine m) {

        if (m.getMaxSpeed()>80)
            return false;

        if (m.getWeight()>2000 || m.getHeight()>2)
            return false;

        return true;
    }

    public boolean pass(Machine m) {

        if (m.getMaxSpeed()>80)
            return false;

        return true;
    }

}

