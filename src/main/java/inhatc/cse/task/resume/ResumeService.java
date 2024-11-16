package inhatc.cse.task.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume saveResume(Resume resume) {
        return resumeRepository.save(resume);
    }
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll(); // Retrieve all resumes from the database
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
