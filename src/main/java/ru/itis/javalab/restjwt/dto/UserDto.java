package ru.itis.javalab.restjwt.dto;

import lombok.*;
import ru.itis.javalab.restjwt.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String hashPassword;
    private String email;
    private String name;
    private User.State state;
    private User.Role role;
    private String refreshToken;

    public static UserDto from(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}