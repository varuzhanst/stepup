package net.ddns.varuzhan.demo.model;

public enum ExamStatus {
    INSUFFICIENT_QUESTIONS("Հարցերի անբավարար քանակ",1),
    READY_TO_BE_PUBLISHED("Տեսանելիությունը փակ",2),
    WAITING_FOR_START("Սպասվում է",3),
    STARTED("Սկսված",4),
    FAILED("Ձախողված",5),
    FINISHED("Ավարտված",6);
    public String text;
    public int number;

    ExamStatus(String text, int number) {
        this.text = text;
        this.number = number;
    }
}
