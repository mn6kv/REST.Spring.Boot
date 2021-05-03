package ru.itis.javalab.restjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.javalab.restjwt.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndHashPassword(String email, String hashPassword);

//    @Query("select User.refreshToken from User where User.email like concat('%', :email, '%')")
//    Optional<String> findRefreshTokenByEmail(@Param("email") String email);

//    @Modifying
//    @Query("update User user set refresh_token = concat('%', :token, '%') where user.email like concat('%', :email, '%')")
//    void setRefreshTokenToUser(@Param("email") String email, @Param("token") String token);
}
