package com.infoshare.junit.banking;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class TransactionsBuilder {
    private final Random rand = new Random();
    private int total = 20;
    private LocalDateTime before = LocalDateTime.now();
    private LocalDateTime after = before.minus(Period.ofMonths(1));
    private long diffMinutes = ChronoUnit.MINUTES.between(after, before);
    private int minValue = 100;
    private int maxValue = 100;
    private DateGenerator dateGenerator = new LinearDateGenerator();
    private TransferBank bank;

    public TransactionsBuilder after(LocalDateTime dateTime) {
        after = dateTime;
        return this;
    }

    public TransactionsBuilder withRandomDates() {
        dateGenerator = new RandomDateGenerator();
        return this;
    }

    public TransactionsBuilder withLinearDates() {
        dateGenerator = new LinearDateGenerator();
        return this;
    }

    public TransactionsBuilder before(LocalDateTime dateTime) {
        before = dateTime;
        diffMinutes = ChronoUnit.MINUTES.between(after, before);
        return this;
    }

    public TransactionsBuilder totalOf(int transactionCount) {
        total = transactionCount;
        return this;
    }

    public TransactionsBuilder valueBetween(int min, int max) {
        minValue = min;
        maxValue = max;
        return this;
    }

    public TransactionsBuilder value(int value) {
        minValue = maxValue = value;
        return this;
    }

    public List<Transaction> build() {
        IntStream integers = valueStream();
        long d = diffMinutes / total;
        final int[] transactionCount = {0};

        List<Transaction> transactionList = integers.mapToObj(value -> {
            LocalDateTime nextDate = dateGenerator.getNextDate(after, d, transactionCount[0]);
            Transaction transaction = new Transaction(value, nextDate);
            transactionCount[0]++;
            return transaction;
        }).collect(Collectors.toList());

        return transactionList;
    }

    private IntStream valueStream() {
        if (minValue == maxValue) {
            return IntStream.iterate(minValue, i -> minValue).limit(total);
        }
        Random rand = new Random();
        return rand.ints(total, minValue, maxValue);
    }

    public void register(Account account) {
        for (Transaction t : build()) {
            try {
                account.register(t);
            } catch (DuplicatedTransactionException e) {
                e.printStackTrace();
            } catch (InvalidTransactionException e) {
                e.printStackTrace();
            }
        }
    }

    public Set<Transaction> transferBetween(Account sourceAccount, Account targetAccount) {
        IntStream doubles = valueStream();
        long d = diffMinutes / total;
        final int[] transactionCount = {0};
        Set<Transaction> transactions = doubles.mapToObj(value -> {
            LocalDateTime nextDate = dateGenerator.getNextDate(after, d, transactionCount[0]);
            Transaction transaction = null;
            try {
                transaction = sourceAccount.transferTo(targetAccount, value, nextDate);
                transactionCount[0]++;
            } catch (InvalidTransactionException e) {
                e.printStackTrace();
            }
            return transaction;
        })
                .filter(t -> t != null)
                .collect(Collectors.toSet());

        if (bank != null) {
            for (Transaction transaction : transactions) {
                bank.register(transaction);
            }
        }
        return transactions;
    }

    public TransactionsBuilder using(TransferBank bank) {
        this.bank = bank;
        return this;
    }
}

interface DateGenerator {
    LocalDateTime getNextDate(LocalDateTime start, long periodBetweenDates, int transactionNum);
}

class LinearDateGenerator implements DateGenerator {
    public LocalDateTime getNextDate(LocalDateTime start, long periodBetweenDates, int transactionNum) {
        return start.plusMinutes(periodBetweenDates * transactionNum);
    }
}

class RandomDateGenerator implements DateGenerator {
    public LocalDateTime getNextDate(LocalDateTime start, long periodBetweenDates, int transactionNum) {
        return start.plusMinutes(new Random().nextInt((int) periodBetweenDates + 1));
    }
}