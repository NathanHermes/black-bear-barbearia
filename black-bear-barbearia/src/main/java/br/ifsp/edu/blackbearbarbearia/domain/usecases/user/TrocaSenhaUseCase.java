package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

public class TrocaSenhaUseCase {
    private UserDAO dao;
    public TrocaSenhaUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public boolean trocarSenha(User user, String senha) {
        user.setPasswordHash(senha);
        return true;
    }
}
