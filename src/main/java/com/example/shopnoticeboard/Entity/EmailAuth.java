package com.example.shopnoticeboard.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "emailAuth")
public class EmailAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String email;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createdDate;

    @Column
    private String randomCode;
}
