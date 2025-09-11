package com.example.Enotes.dto;

import lombok.*;

import java.util.List;

public class UserResponse {
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobNo;

    private StatusDto status;

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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StatusDto{
        private int id;
        private Boolean isActive;
    }
}
