package com.carpool.bnk.CarpoolServer.domain.carpool.db.entity;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
//@Table(name = "occupants")
@Getter
@Setter
@NoArgsConstructor
public class Occupants {

    @Id
    @Column(name = "relation_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int relationNo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "carpool_no")
    private Carpool carpool;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public Occupants(Carpool carpool, User user){
        this.carpool = carpool;
        this.user = user;
    }
}
