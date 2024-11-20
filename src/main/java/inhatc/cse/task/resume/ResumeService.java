package inhatc.cse.task.resume;

import inhatc.cse.task.profile.Profile;
import inhatc.cse.task.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository, ProfileRepository profileRepository) {
        this.resumeRepository = resumeRepository;
        this.profileRepository = profileRepository;
    }

    public void saveResume(ResumeDto resumeDto, String username) {
        Profile profile = profileRepository.findByUsername(username);
        if (profile == null) {
            throw new NoSuchElementException("Profile not found for username: " + username);
        }

        // 새로운 Resume 생성 및 Profile 설정
        Resume resume = new Resume();
        resume.setTitle(resumeDto.getTitle());
        resume.setProjectExperience(resumeDto.getProjectExperience());
        resume.setAwards(resumeDto.getAwards());
        resume.setLanguageScores(resumeDto.getLanguageScores());
        resume.setCommunityActivities(resumeDto.getCommunityActivities());
        resume.setSelfIntroduction(resumeDto.getSelfIntroduction());
        resume.setProfile(profile); // 기존 Profile 설정

        resumeRepository.save(resume);
    }
    public List<Resume> getResumesByUsername(String username) {
        // 사용자에 해당하는 이력서를 DB에서 조회
        return resumeRepository.findByProfile_Username(username);
    }
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));
    }
    public void updateResume(Resume resume) {
        resumeRepository.save(resume); // Assuming save handles both new and updated entities
    }
    public void deleteResume(Long id) {
        if (resumeRepository.existsById(id)) {
            resumeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("해당 ID의 이력서를 찾을 수 없습니다: " + id);
        }
    }
}
