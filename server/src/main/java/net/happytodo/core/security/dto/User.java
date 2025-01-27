package net.happytodo.core.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.happytodo.core.security.constant.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static net.happytodo.core.security.constant.UserRole.SYSADMIN;

//유저 정보
public class User {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Principal {
        private int id;
        private String email;
        private String name;
        private Set<GrantedAuthority> role;

        public UserResponse toResponse() {
            Set<String> authoritySet = role.stream()
                    .map(r -> r.getAuthority())
                    .collect(Collectors.toSet());

            return UserResponse.builder()
                    .id(id)
                    .email(email)
                    .name(name)
                    .role(authoritySet)
                    .isSysAdmin(authoritySet.stream()
                            .anyMatch(a -> a.equals(SYSADMIN.toString())))
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse {
        private int id;
        private String email;
        private String name;
        private Set<String> role;
        private boolean isSysAdmin;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserAccount {
        private int id;
        private String email;
        private String name;
        private Set<GrantedAuthority> role;
        private String password;

        public void setRole(int roleId) {
            this.role = Set.of(new SimpleGrantedAuthority(UserRole.getUserRoleById(roleId).toString()));
        }
    }
}
