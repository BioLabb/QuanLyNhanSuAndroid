package com.example.quanlynhansu.store;

import com.example.quanlynhansu.object.Account;

public class AccountStore {
    private static Account user;

    public AccountStore(Account user) {
        this.user = user;
    }

    public static Account getUser() {
        return user;
    }

    public static void setUser(Account user) {
        AccountStore.user = user;
    }
}
