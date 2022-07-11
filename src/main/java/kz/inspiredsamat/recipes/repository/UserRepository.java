package kz.inspiredsamat.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kz.inspiredsamat.recipes.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
