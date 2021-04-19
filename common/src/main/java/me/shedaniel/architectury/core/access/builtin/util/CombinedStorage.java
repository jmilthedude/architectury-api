package me.shedaniel.architectury.core.access.builtin.util;

import me.shedaniel.architectury.core.access.builtin.Storage;
import me.shedaniel.architectury.core.access.builtin.Transaction;

public class CombinedStorage<T> implements Storage<T> {
    private final Iterable<Storage<T>> storages;
    
    public CombinedStorage(Iterable<Storage<T>> storages) {
        this.storages = storages;
    }
    
    @Override
    public long extract(T type, long maxAmount, Transaction transaction) {
        long totalExtracted = 0;
        for (Storage<T> storage : storages) {
            long extracted = storage.extract(type, maxAmount - totalExtracted, transaction);
            totalExtracted += extracted;
            if (totalExtracted >= maxAmount) {
                return totalExtracted;
            }
        }
        return totalExtracted;
    }
    
    @Override
    public long insert(T type, long amount, Transaction transaction) {
        long totalInserted = 0;
        for (Storage<T> storage : storages) {
            long extracted = storage.insert(type, amount - totalInserted, transaction);
            totalInserted += extracted;
            if (totalInserted >= amount) {
                return totalInserted;
            }
        }
        return totalInserted;
    }
}