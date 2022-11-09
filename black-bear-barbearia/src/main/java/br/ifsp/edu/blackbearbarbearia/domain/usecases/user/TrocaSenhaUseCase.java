package br.ifsp.edu.blackbearbarbearia.domain.usecases.user;

import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

public class TrocaSenhaUseCase {
    private final UserDAO dao;
    public TrocaSenhaUseCase(UserDAO dao) {
        this.dao = dao;
    }

    public Boolean modifyPassword(User user, String lastPassword, String newPassword, String confirmPassword) {
        if (lastPassword == null || lastPassword.isBlank()) throw new IllegalArgumentException("Last password is null or empty");
        if (newPassword == null || newPassword.isBlank()) throw new IllegalArgumentException("New password is null or empty");
        if (confirmPassword == null || confirmPassword.isBlank()) throw new IllegalArgumentException("Confirm password is null or empty");

        if (!newPassword.equals(confirmPassword)) throw new IllegalArgumentException("New password is not the same as confirm new password");
        if (user.isValidPassword(lastPassword)) throw new IllegalArgumentException("Invalid last password");

        user.setPasswordHash(newPassword);
        user.setLastPassword(newPassword);

        dao.update(user);

        return Boolean.TRUE;
    }
}
