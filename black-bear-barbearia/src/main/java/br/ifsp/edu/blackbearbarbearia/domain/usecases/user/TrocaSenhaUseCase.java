package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

public class TrocaSenhaUseCase {
    private UserDao dao;
    public TrocaSenhaUseCase(UserDao dao) {
        this.dao = dao;
    }

    public boolean trocarSenha(User user, String senha) {
        user.setPasswordHash(senha);
        return true;
    }
}
