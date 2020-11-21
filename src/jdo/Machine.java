package jdo;

public class Machine implements java.io.Serializable{


    protected String mark;
    protected Integer maxSpeed;
    protected Radio radio;

    DPS dps = new DPS();

    public Machine(String mark, Integer maxSpeed,Double currentStation, boolean status) {
        super();
        this.mark = mark;
        this.maxSpeed = maxSpeed;
        this.radio = new Radio(currentStation, status);
    }

    void move() {
//		return dps.Pass(this);
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Radio getRadio() {
        return radio;
    }

    public void setRadio(Radio radio) {
        this.radio = radio;
    }

    public DPS getDps() {
        return dps;
    }

    public void setDps(DPS dps) {
        this.dps = dps;
    }
    @Override
    public String toString(){
        return ("Марка: "+ this.mark+"\t\tСкорость: " + this.maxSpeed+"\t\tРадио: "+ this.radio.currentStation+ "\t\tРазрешено движение: "+this.radio.status);
    }

    public void show(){
        System.out.format("%12s %17d %15.2f %14b ", getMark(), getMaxSpeed(), getRadio().currentStation, getRadio().status);
        System.out.println();
    }

    public static Machine generateRandomElement(){
        Machine hm =new Machine("Lada",(int) (Math.random() * 250), (Math.random() * 120),true);
        return hm;
    }
}
