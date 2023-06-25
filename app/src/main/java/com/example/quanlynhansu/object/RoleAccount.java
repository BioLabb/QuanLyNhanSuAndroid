package com.example.quanlynhansu.object;

public class RoleAccount {
    private int roleId;
    private int accountId;

    public RoleAccount(int roleId, int accountId) {
        this.roleId = roleId;
        this.accountId = accountId;
    }
    public RoleAccount( int accountId) {

        this.accountId = accountId;
    }
    public int getRoleId() {
        return roleId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}

