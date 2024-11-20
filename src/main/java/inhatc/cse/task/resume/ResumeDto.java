package inhatc.cse.task.resume;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {
    private String title;
    private String selfIntroduction;
    private String projectExperience;
    private String awards;
    private String languageScores;
    private String communityActivities;
}
