package br.ifsp.edu.blackbearbarbearia.domain.usecases.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class ConverterSenhaParaMD5 {
    public static String converterSenhaParaMD5(String senha) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));

        return String.format("%32x", hash).substring(0,30);
    }
}