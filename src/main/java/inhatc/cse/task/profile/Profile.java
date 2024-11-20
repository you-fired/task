package inhatc.cse.task.profile;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String name;
    private String career;
    private String education;
    private String certificates;
    private String preferredLocation;
    private String skills;



}