package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Main {


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("укажите имя файла конфигурации");

        String name = in.nextLine();
        //System.out.println("-> condition.json");
        Condition con = new Condition(name);

        Map<String, String> leftBracket = new HashMap<>();
        Map<String, String> rightBracket = new HashMap<>();
        con.readCondition(leftBracket, rightBracket);

        System.out.println("укажите имя файла для проверки скобок");
        name = in.nextLine();// "brackets.txt";
        //System.out.println("-> brackets.txt");
        //name = "brackets.txt";

        try(FileReader f = new FileReader(name)){
            Stack<String> brackets = new Stack<>();
            int c; //а если скобка составная?
            while ( (c = f.read() ) != -1){
                String s = Character.toString((char)c);
                if (leftBracket.get(s) != null || rightBracket.get(s) != null ){
                    //стек не пуст, тогда

                    if (!brackets.isEmpty()){
                        String str = brackets.peek().toString(); //берем верхнее со стека
                        String str2 = leftBracket.get(str); //берем по ключу
                        if (str2!=null && str2.equals(s)) //если строки равны
                            brackets.pop();
                        else
                            brackets.push(s);
                    }
                    else
                        brackets.push(s);
                }

            }
            if (brackets.isEmpty())
                System.out.println("скобки в файле расставлены правильно");
            else
                System.out.println("скобки в файле расставлены НЕ правильно");

        }
        catch(Exception e){
            System.out.println("error");
        }

    }
}
