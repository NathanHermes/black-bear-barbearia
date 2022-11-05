package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

public class TrocaSenhaUseCase {
    private UserDAO dao;
    public TrocaSenhaUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public boolean trocarSenha(User user, String password, String confirmPassword) {
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Invalid password.");
        if (confirmPassword == null || confirmPassword.isEmpty())
            throw new IllegalArgumentException("Invalid confirm password.");

        if (!password.equals(confirmPassword))
            return false;
        return true;
    }
}
