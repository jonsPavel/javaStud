package logging;

public class UserExceptions extends Exception {
    private int modAcces;

    public UserExceptions(String message){      //Класс должен содержать конструктор со строковой переменной, описывающей
        super(message); // детали проблемы Исключения. В конструкторе вызывается супер-конструктор с передачей сообщения.
    }

    public UserExceptions(int num){ // исключение модификатора доступа
        this.modAcces=num;
        modAccesException();
    }
    public static void modAccesException(){
        System.out.println("Ошибка прав доступа к базе данных.");
    }

}
