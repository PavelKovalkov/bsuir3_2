package lab2.service.chain.impl;

import lab2.service.chain.Middleware;

import java.util.Set;

public class UserExistsMiddleware extends Middleware {
    private Set<String> employees;

    public UserExistsMiddleware(Set<String> employees) {
        this.employees = employees;
    }

    @Override
    public boolean check(String employeeName) {
        if (!employees.contains(employeeName)) return false;
        return checkNext(employeeName);
    }
}
