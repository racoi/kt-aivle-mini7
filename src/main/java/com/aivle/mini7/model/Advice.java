package com.aivle.mini7.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "advice")
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer advice_id;

    @Column(name = "advice", nullable = false)
    private String advice;

    @Column(name = "em_class", nullable = false)
    private int emClass;

    @OneToOne
    @JoinColumn(name = "input_id")
    private Input input;
}
