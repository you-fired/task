package inhatc.cse.task.profile;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {
    private String name;
    private String career;
    private String education;
    private String certificates;
    private String preferredLocation;


}
