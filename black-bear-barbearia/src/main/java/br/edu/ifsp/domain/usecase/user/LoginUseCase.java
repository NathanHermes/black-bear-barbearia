package br.edu.ifsp.domain.usecase.user;
import br.edu.ifsp.domain.entities.user.User;
public class LoginUseCase {
    private UserDao dao;

    public LoginUseCase(UserDao dao) {
        this.dao = dao;
    }

    public boolean login(String login, String password) {
        if (dao.findOneByLogin(login) == null) return false;

        User user = dao.findOneByLogin(login);
        if (!password.equals(user.getPasswordHash())) return false;
        return true;
    }
}