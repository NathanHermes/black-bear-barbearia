package br.ifsp.edu.blackbearbarbearia.domain.usecases.utils;

import java.util.Collection;

public abstract class Validator <T>{
    public abstract Notification validate(T type);
    public Notification validate(String lastPassword, String newPassword, String confirmNewPassword) {return null;};

    public static boolean nullOrEmpty(String string){
        return string == null || string.isEmpty();
    }
    public static boolean nullOrEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }
}
