package com.company;
// Реализовать программу, которая выполняет размен монет. На вход
// программе подается сумма, которую нужно разменять, и номинал
// монет. Реализовать с помощью рекурсии. Пример: нужно разменять 10
// с доступными номиналами 4 и 1, в результате работы программы
// должно быть указано 10 -> 4[2], 1[2] – две монеты по 4 и две монеты по
// 1. Если размен невозможен – программа должна написать, что размен
// невозможен. Пользовательский интерфейс для программы
// необязателен.
// 100 -> 20, 6, 1
// 100 -20 = 80-20 = 60-20=40-20=20-6=12-6=6-1...-1=0

import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Vector<Nominal> nominal = new Vector<>();
        int count=0;

        System.out.println("Enter sum of money");
        try{
            count = in.nextInt();
            int exit = 1;

            System.out.println("Enter numbers for nominal; enter 0 to end the input");
            exit = in.nextInt();

            while (exit != 0) {
                Nominal nom = new Nominal(exit);
                nominal.add(nom);
                exit = in.nextInt();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        if (nominal.size() == 0) {
            return;
        }
        nominal.sort(new Comparator<Nominal>() {
            public int compare(Nominal o1, Nominal o2) {
                return o1.compareTo(o2);
            }
        });

        int error = getNominal(nominal, count, 0);
        if (error == 0) {
            for (int i = 0; i < nominal.size(); i++)
                nominal.elementAt(i).printNominal();
        } else
            System.out.println("exchange impossible");

    }

    // getNominal is a recursive function for search exchange
    static public int getNominal(Vector<Nominal> nom, int count, int index) {
        int err = 0;
        if (count - nom.elementAt(index).getNumber() > 0) {
            nom.elementAt(index).incNominal();
            err = getNominal(nom, count - nom.elementAt(index).getNumber(), index);
        } else if (index + 1 != nom.size()) {
            index++;
            nom.elementAt(index).incNominal();
            err = getNominal(nom, count - nom.elementAt(index).getNumber(), index);
        } else if (index + 1 == nom.size()) {
            if (count - nom.elementAt(index).getNumber() == 0) {
                nom.elementAt(index).incNominal();
                return 0;
            } else
                return 1;
        }
        return err;
    }
}
