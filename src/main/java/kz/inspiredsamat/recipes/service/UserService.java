package kz.inspiredsamat.recipes.service;

import kz.inspiredsamat.recipes.model.User;

public interface UserService {
    User save(User user);

    User findUserByEmail(String username);
}
