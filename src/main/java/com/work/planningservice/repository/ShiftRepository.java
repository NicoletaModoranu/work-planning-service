package com.work.planningservice.repository;

import com.work.planningservice.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    @Query("select s from Shift s left join s.worker w where w.workerId = ?1 and s.shiftDay > ?2 and s.shiftDay < ?3")
    Set<Shift> findShifts(Long workerId, LocalDate startDate, LocalDate endDate);

}
