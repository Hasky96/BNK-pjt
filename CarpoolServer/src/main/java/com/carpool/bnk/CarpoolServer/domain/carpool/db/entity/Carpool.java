package com.carpool.bnk.CarpoolServer.domain.carpool.db.entity;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "carpool")
public class Carpool {

    @Id
    @Column(name = "carpool_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int carpoolNo;

    @ManyToOne
    @JoinColumn(name = "carpool_writer")
    private User carpoolWriter;

    @ManyToOne
    @JoinColumn(name = "carpool_driver")
    private User carpoolDriver;

    @Column(name = "carpool_type")
    private boolean carpoolType;  // true : 퇴근, false : 출근

    @Column(name = "carpool_location")
    private String carpoolLocation;

    @Column(name = "carpool_quota")
    private int carpoolQuota;

    @Column(name = "carpool_info")
    private String carpoolInfo;

    @Column(name = "carpool_fee")
    private int carpoolFee;

    @Column(name = "capool_created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime carpoolCreated;

    @Column(name = "carpool_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime carpoolTime;

    @JsonManagedReference
    @OneToMany(mappedBy = "carpool", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Occupants> occupants = new ArrayList<Occupants>();

    @JsonManagedReference
    @OneToMany(mappedBy = "carpool", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comments> comments = new ArrayList<Comments>();

    @Builder
    public Carpool(User carpoolDriver, User carpoolWriter, boolean carpoolType, String carpoolLocation, int carpoolQuota, String carpoolInfo, int carpoolFee, LocalDateTime carpoolTime){
        this.carpoolDriver = carpoolDriver;
        this.carpoolWriter = carpoolWriter;
        this.carpoolType = carpoolType;
        this.carpoolLocation = carpoolLocation;
        this.carpoolQuota = carpoolQuota;
        this.carpoolInfo = carpoolInfo;
        this.carpoolFee = carpoolFee;
        this.carpoolCreated = LocalDateTime.now();
        this.carpoolTime = carpoolTime;
    }
}
