package com.work.planningservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="Shift")
public class Shift {

    @Id
    @Column(name = "shift_id")
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long shiftId;

    @ManyToOne
    @JoinColumn(name="worker_id", nullable=false)
    private Worker worker;
}

