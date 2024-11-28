package ru.job4j.cache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        boolean rsl = false;
        if (accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        if (getById(fromId).isPresent() && getById(toId).isPresent()
                && getById(fromId).get().amount() >= amount) {
            Account fromAcc = new Account(accounts.get(fromId).id(),
                    accounts.get(fromId).amount() - amount);
            accounts.put(fromAcc.id(), fromAcc);
            Account toAcc = new Account(accounts.get(toId).id(),
                    accounts.get(toId).amount() + amount);
            accounts.put(toAcc.id(), toAcc);
            rsl = true;
        }
        return rsl;
    }
}

