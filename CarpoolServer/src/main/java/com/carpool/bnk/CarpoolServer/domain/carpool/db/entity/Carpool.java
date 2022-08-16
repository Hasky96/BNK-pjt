package com.carpool.bnk.CarpoolServer.domain.carpool.db.entity;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime carpoolCreated;

    @Override
    public String toString() {
        return  "carpoolNo=" + carpoolNo +
                "\n carpoolWriter=" + carpoolWriter +
                "\n carpoolDriver=" + carpoolDriver +
                "\n carpoolType=" + carpoolType +
                "\n carpoolLocation='" + carpoolLocation +
                "\n carpoolQuota=" + carpoolQuota +
                "\n carpoolInfo='" + carpoolInfo +
                "\n carpoolFee=" + carpoolFee +
                "\n carpoolCreated=" + carpoolCreated +
                '}';
    }

    @Builder
    public Carpool(User carpoolDriver, User carpoolWriter, boolean carpoolType, String carpoolLocation, int carpoolQuota, String carpoolInfo, int carpoolFee){
        this.carpoolDriver = carpoolDriver;
        this.carpoolWriter = carpoolWriter;
        this.carpoolType = carpoolType;
        this.carpoolLocation = carpoolLocation;
        this.carpoolQuota = carpoolQuota;
        this.carpoolInfo = carpoolInfo;
        this.carpoolFee = carpoolFee;
        this.carpoolCreated = LocalDateTime.now();
    }
}
