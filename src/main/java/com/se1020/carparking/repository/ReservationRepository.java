package com.se1020.carparking.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.se1020.carparking.model.Reservation;
import com.se1020.carparking.repository.support.JsonDataAccess;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {

    private static final String CLASSPATH = "data/reservations.json";
    private static final String FILE_NAME = "reservations.json";

    public List<Reservation> findAll() {
        return JsonDataAccess.readList(CLASSPATH, FILE_NAME, new TypeReference<List<Reservation>>() {});
    }

    public void saveAll(List<Reservation> reservations) {
        JsonDataAccess.writeList(FILE_NAME, reservations);
    }

    public Reservation findById(String reservationId) {
        for (Reservation r : findAll()) {
            if (r.getReservationId().equals(reservationId)) {
                return r;
            }
        }
        return null;
    }

    public List<Reservation> findByUserId(String userId) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : findAll()) {
            if (r.getUserId().equals(userId)) {
                result.add(r);
            }
        }
        return result;
    }

    public void save(Reservation reservation) {
        List<Reservation> reservations = findAll();
        reservations.add(reservation);
        saveAll(reservations);
    }

    public void update(Reservation updatedReservation) {
        List<Reservation> reservations = findAll();
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId().equals(updatedReservation.getReservationId())) {
                reservations.set(i, updatedReservation);
                break;
            }
        }
        saveAll(reservations);
    }

    public void delete(String reservationId) {
        List<Reservation> reservations = findAll();
        reservations.removeIf(r -> r.getReservationId().equals(reservationId));
        saveAll(reservations);
    }
}
