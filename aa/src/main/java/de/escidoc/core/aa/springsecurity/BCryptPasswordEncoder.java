package de.escidoc.core.aa.springsecurity;

import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.encoding.PasswordEncoder;

public class BCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encodePassword(String rawPass, Object salt) throws DataAccessException {
        return BCrypt.hashpw(rawPass, BCrypt.gensalt());
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) throws DataAccessException {
        return BCrypt.checkpw(rawPass, encPass);
    }

}
