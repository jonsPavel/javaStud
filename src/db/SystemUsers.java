package db;

import java.util.Scanner;

public class SystemUsers {

    Scanner in = new Scanner(System.in);

    String rootLogin =      "c1c224b03cd9bc7b6a86d77f5dace40191766c485cd55dc48caf9ac873335d6f";
    String rootPassword =   "c1c224b03cd9bc7b6a86d77f5dace40191766c485cd55dc48caf9ac873335d6f";

    String moderatorLogin =      "2a6250d5144da3510c8c9e39cdec1eff96bc1c96e8d5c9fa3ea99d730e479be1";
    String moderatorPassword =   "102b6cf185c26a6e866593b4473520b029d286f4d7478189212b7f9c4be7f4ac";

    String guestLogin =      "5ed8944a85a9763fd315852f448cb7de36c5e928e13b3be427f98f7dc455f141";
    String guestPassword =   "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";

    private int accessModifier=0;

    public int getAccessModifier() {
        return accessModifier;
    }
    public void setAccessModifier(int accessModifier) {
        this.accessModifier = accessModifier;
    }



    String testStringHash;
    GetHash testHash = new GetHash();
    // 0 - вход неудачный, 1 - вход root, 2 - вход модератора, 3 - вход гостя.

    public void logIn() {
        while (accessModifier == 0){
            System.out.println("Введите логин: (0 - выход)");
            testStringHash = in.nextLine();
            if (rootLogin.equalsIgnoreCase(testHash.returnHash(testStringHash))) {
                System.out.println("Введите пароль:");
                testStringHash = in.nextLine();
                if (rootPassword.equalsIgnoreCase(testHash.returnHash(testStringHash))){
                    accessModifier = 1;
                    System.out.println("Привет, админ!");
                }
            }

            if (moderatorLogin.equalsIgnoreCase(testHash.returnHash(testStringHash))) {
                System.out.println("Введите пароль:");
                testStringHash = in.nextLine();
                if (moderatorPassword.equalsIgnoreCase(testHash.returnHash(testStringHash))){
                    accessModifier = 2;
                    System.out.println("Привет, модератор!");
                }
            }

            if (guestLogin.equalsIgnoreCase(testHash.returnHash(testStringHash))) {
                System.out.println("Введите пароль:");
                testStringHash = in.nextLine();
                if (guestPassword.equalsIgnoreCase(testHash.returnHash(testStringHash))){
                    accessModifier = 3;
                    System.out.println("Привет, проходимец!!");
                }
            }

            if (testStringHash.equalsIgnoreCase("0"))
                    break;

        }
    }
}
