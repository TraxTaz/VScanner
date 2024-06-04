package com.example.vscanner.Business.UserImpl;


import com.example.vscanner.Domain.User;
import com.example.vscanner.Persistence.Entity.UserEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserConverter {
    public static User convertToUser(UserEntity userEntity){
        return User.builder()
                .id(userEntity.getId())
                .password(userEntity.getPassword())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .role(userEntity.getRole())
                .build();
    }

    public static UserEntity convertToUserEntity(User user){
        return UserEntity.builder()
                .id(user.getId())
                .password(user.getPassword())
                .username(user.getUsername())
                .email(user.getEmail())
                .FirstName(user.getFirstName())
                .LastName(user.getLastName())
                .role(user.getRole())
                .build();
    }
}
