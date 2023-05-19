package com.matteo.ToDo_app.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matteo.ToDo_app.Entitys.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonIgnore
    private User user;
    @NonNull
    @Column(name = "description")
    private String description;
    @NonNull
    @Column(name = "completed")
    private Boolean completed;
    @NonNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

}
