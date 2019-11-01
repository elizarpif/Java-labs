package com.company;

public class Nominal implements Comparable<Nominal> {
    private int number; //номинал
    private int count; //какое кол-во раз

    public Nominal(int c) {
        number = c;
        count = 0;
    }
    public int getNumber(){
        return number;
    }
    public void incNominal() {
        count++;
    }

    public void printNominal() {
        System.out.println(number + "[" + count + "]");
    }
    @Override
    public int compareTo(Nominal o) {
        if (o.number > number)
            return 1;
        else if (o.number < number)
            return -1;
        else
            return 0;
    }
}
