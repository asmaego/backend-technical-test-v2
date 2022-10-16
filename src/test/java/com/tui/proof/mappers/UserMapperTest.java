package com.tui.proof.mappers;

import com.tui.proof.config.enums.RoleName;
import com.tui.proof.model.Role;
import com.tui.proof.model.User;
import com.tui.proof.security.models.UserPrincipal;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void userToPrincipal() {
        User user = new User(1L, "u", "pwd", true, List.of(new Role(1L, RoleName.USER)));

        UserPrincipal principal = UserMapper.userToPrincipal(user);

        assertEquals("u", principal.getUsername());
        assertEquals("pwd", principal.getPassword());
        assertTrue(principal.isEnabled());
        assertEquals(1, principal.getAuthorities().size());
        assertEquals("ROLE_" + RoleName.USER, principal.getAuthorities().toArray()[0].toString());
    }
}