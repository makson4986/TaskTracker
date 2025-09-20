package com.makson.tasktracker.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String text;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.NOT_DONE;

    @Builder.Default
    @Column(name = "performed_at")
    private LocalDateTime performedAt = LocalDateTime.now();
}
