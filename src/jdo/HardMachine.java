package jdo;


public class HardMachine extends Machine implements java.io.Serializable {
    protected Integer weight;
    protected Integer height;

    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public Integer getHeight() {
        return height;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    DPS dps = new DPS();
    public HardMachine(String mark, Integer maxSpeed, Double currentStation, boolean status, Integer weight,
                       Integer height) {
        super(mark, maxSpeed, currentStation, status);
        this.weight = weight;
        this.height = height;
    }
    void move() {
//		return dps.Pass(this);
    }
    @Override
    public String toString(){
        return ("Марка: "+ this.mark+"\t\tСкорость: " + this.maxSpeed+"\t\tРадио: "+ this.radio.currentStation+
                "\t\tРазрешено движение: "+this.radio.status+"\t\tВес: "+this.weight+"\t\tВысота: "+this.height);
    }

    public void show(){
        System.out.format("%12s %17d %15.2f %14b %5d %9d", getMark(), getMaxSpeed(), getRadio().currentStation, getRadio().status,getHeight(),getWeight());
        System.out.println();
    }

    public static HardMachine generateRandomElement(){
    HardMachine hm =new HardMachine("KAMAZ",(int) (Math.random() * 250), (Math.random() * 120),true,(int) (Math.random() * 5000),(int) (Math.random() * 3));
    return hm;
    }
}
