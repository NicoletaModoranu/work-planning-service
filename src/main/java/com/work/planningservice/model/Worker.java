package com.work.planningservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "Worker")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Worker {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    @Column(name = "worker_id")
    private Long workerId;

    private String name;

    @OneToMany(mappedBy="worker")
    private Set<Shift> shifts;
}