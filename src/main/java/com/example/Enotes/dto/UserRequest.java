package com.example.Enotes.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobNo;

    private String password;

    private List<RoleDto> roles;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RoleDto{
        private int id;
        private String name;
    }
}
