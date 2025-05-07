package com.example.demo.DTO.user;

import java.util.List;

import com.example.demo.model.User.Role;

public record UserDTOBasic(
                Long id,
                String username,
                String email,
                String date,
                List<Role> roles) {
}
