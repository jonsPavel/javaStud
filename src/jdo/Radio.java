package jdo;

public class Radio implements java.io.Serializable{
    Double currentStation;
    boolean status;

    void PlayTunes() {

    }

    public Radio(Double currentStation, boolean status) {
        super();
        this.currentStation = currentStation;
        this.status = status;
    }


}
