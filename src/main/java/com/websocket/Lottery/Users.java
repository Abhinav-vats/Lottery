package com.websocket.Lottery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "booking_ref")
    private String bookingRef;

    @Column(name = "user_name")
    private String username;

    @Column(name = "creation_date")
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updateDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_selected")
    private Boolean isSelected;


    @Column(name = "is_published")
    private Boolean isPublished;

    public Users(String bookingRef, String username) {
        this.bookingRef = bookingRef;
        this.username = username;
        this.isSelected = Boolean.FALSE;
        this.isActive = Boolean.TRUE;
        this.isPublished = Boolean.FALSE;
    }
}
