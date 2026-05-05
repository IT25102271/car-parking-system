package com.se1020.carparking.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.se1020.carparking.model.Bill;
import com.se1020.carparking.repository.support.JsonDataAccess;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BillRepository {

    private static final String CLASSPATH = "data/bills.json";
    private static final String FILE_NAME = "bills.json";

    public List<Bill> findAll() {
        return JsonDataAccess.readList(CLASSPATH, FILE_NAME, new TypeReference<List<Bill>>() {});
    }

    public void saveAll(List<Bill> bills) {
        JsonDataAccess.writeList(FILE_NAME, bills);
    }

    public Bill findById(String billId) {
        for (Bill b : findAll()) {
            if (b.getBillId().equals(billId)) {
                return b;
            }
        }
        return null;
    }

    public List<Bill> findByUserId(String userId) {
        List<Bill> result = new ArrayList<>();
        for (Bill b : findAll()) {
            if (b.getUserId().equals(userId)) {
                result.add(b);
            }
        }
        return result;
    }

    public void save(Bill bill) {
        List<Bill> bills = findAll();
        bills.add(bill);
        saveAll(bills);
    }

    public void update(Bill updatedBill) {
        List<Bill> bills = findAll();
        for (int i = 0; i < bills.size(); i++) {
            if (bills.get(i).getBillId().equals(updatedBill.getBillId())) {
                bills.set(i, updatedBill);
                break;
            }
        }
        saveAll(bills);
    }

    public void delete(String billId) {
        List<Bill> bills = findAll();
        bills.removeIf(b -> b.getBillId().equals(billId));
        saveAll(bills);
    }
}
