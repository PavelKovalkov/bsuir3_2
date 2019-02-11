package lab2.service.chain.impl;

import lab2.service.chain.Middleware;

import java.util.Set;

public class ReadWriteAccessesMiddleware extends Middleware {
    private Set<String> readWriteAccessEmployees;

    public ReadWriteAccessesMiddleware(Set<String> readWriteAccessEmployees) {
        this.readWriteAccessEmployees = readWriteAccessEmployees;
    }

    @Override
    public boolean check(String employeeName) {
        if (!readWriteAccessEmployees.contains(employeeName)) return false;
        return checkNext(employeeName);
    }
}
