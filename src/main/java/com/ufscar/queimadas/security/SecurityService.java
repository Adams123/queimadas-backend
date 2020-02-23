package com.ufscar.queimadas.security;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}