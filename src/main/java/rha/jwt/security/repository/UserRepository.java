package rha.jwt.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rha.jwt.model.security.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
