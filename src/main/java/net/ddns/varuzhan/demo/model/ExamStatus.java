package net.ddns.varuzhan.demo.model;

public enum ExamStatus {
    INSUFFICIENT_QUESTIONS("Հարցերի անբավարար քանակ"),
    READY_TO_BE_PUBLISHED("Տեսանելիությունը բացելուն պատրաստ"),
    WAITING_FOR_START("Սպասվում է"),
    STARTED("Սկսված"),
    FAILED("Ձախողված"),
    FINISHED("Ավարտված");
    public String text;
    ExamStatus(String name){
        this.text=name;
    }
}
