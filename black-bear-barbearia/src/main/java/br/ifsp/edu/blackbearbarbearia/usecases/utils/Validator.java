package br.ifsp.edu.blackbearbarbearia.usecases.utils;

import java.util.Collection;

public abstract class Validator <T>{
    public abstract Notification validate(T type);

    public abstract Notification validate(String login, String password);

    public static boolean nullOrEmpty(String string){
        return string == null || string.isEmpty();
    }

    public static boolean nullOrEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }
}
