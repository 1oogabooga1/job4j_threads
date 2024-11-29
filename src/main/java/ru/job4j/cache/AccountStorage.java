package ru.job4j.cache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized void add(Account account) {
        accounts.putIfAbsent(account.id(), account);
    }

    public synchronized void update(Account account) {
         accounts.replace(account.id(), account);
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        if (from.isPresent() && to.isPresent()
                && from.get().amount() >= amount) {
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

