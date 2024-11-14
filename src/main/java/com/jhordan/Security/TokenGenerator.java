package com.jhordan.Security;

public interface TokenGenerator {
    String build(Object id, Object role);
}