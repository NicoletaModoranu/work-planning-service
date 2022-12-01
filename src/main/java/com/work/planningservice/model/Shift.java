package com.work.planningservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "Shift", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"shiftDay", "worker_id"})})
public class Shift {

    @Id
    @Column(name = "shift_id")
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long shiftId;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Basic
    private LocalDate shiftDay;

    @Enumerated(EnumType.STRING)
    private ShiftTime shiftTime;

}

