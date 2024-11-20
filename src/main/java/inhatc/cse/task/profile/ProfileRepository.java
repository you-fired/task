package inhatc.cse.task.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUsername(String username);

    // 추가: 이름을 기준으로 프로필 중복 확인
    Profile findByName(String name);
}
