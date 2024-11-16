package inhatc.cse.task.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    // 프로필 조회 (로그인된 사용자 기준)
    public ProfileDto getProfile() {
        // 현재 로그인된 사용자의 username을 SecurityContext에서 가져옴
        String username = getLoggedInUsername();

        // 프로필을 DB에서 조회
        Profile profile = profileRepository.findByUsername(username);

        if (profile != null) {
            // Profile 엔티티를 ProfileDto로 변환하여 반환
            return convertToDto(profile);
        } else {
            // 프로필이 없다면 null을 반환하거나 예외를 던질 수 있음
            return null;
        }
    }

    public ProfileDto getProfileByUsername(String username) {
        // 프로필을 DB에서 조회
        Profile profile = profileRepository.findByUsername(username);

        if (profile != null) {
            // Profile 엔티티를 ProfileDto로 변환하여 반환
            return convertToDto(profile);
        } else {
            // 프로필이 없다면 null을 반환하거나 예외를 던질 수 있음
            return null;
        }
    }

    // 프로필 업데이트
    public Profile updateProfile(ProfileDto profileDto) {
        // 현재 로그인된 사용자의 username을 SecurityContext에서 가져옴
        String username = getLoggedInUsername();

        // 해당 username을 가진 프로필을 DB에서 찾음
        Profile profile = profileRepository.findByUsername(username);

        if (profile != null) {
            // ProfileDto에서 받은 값으로 Profile 엔티티를 업데이트
            profile.setName(profileDto.getName());
            profile.setCareer(profileDto.getCareer());
            profile.setEducation(profileDto.getEducation());
            profile.setCertificates(profileDto.getCertificates());
            profile.setPreferredLocation(profileDto.getPreferredLocation());
            profile.setSkills(profileDto.getSkills());

            // 업데이트된 Profile 객체 저장
            return profileRepository.save(profile);
        } else {
            // 프로필이 없다면 예외를 던짐
            throw new RuntimeException("Profile not found for username: " + username);
        }
    }

    // 프로필 생성
    public Profile createProfile(ProfileDto profileDto) throws IllegalArgumentException {
        // 중복된 프로필 이름이 있는지 확인
        if (profileRepository.findByUsername(profileDto.getName()) != null) {
            throw new IllegalArgumentException("이미 있는 프로필입니다.");
        }

        // 새 프로필 객체 생성
        Profile profile = new Profile();

        // 현재 로그인된 사용자의 username을 SecurityContext에서 가져옴
        String username = getLoggedInUsername();

        // 프로필 데이터 설정
        profile.setUsername(username);
        profile.setName(profileDto.getName());
        profile.setCareer(profileDto.getCareer());
        profile.setEducation(profileDto.getEducation());
        profile.setCertificates(profileDto.getCertificates());
        profile.setPreferredLocation(profileDto.getPreferredLocation());
        profile.setSkills(profileDto.getSkills());

        // 새로운 프로필 저장
        return profileRepository.save(profile);
    }

    // 현재 로그인된 사용자의 username을 반환하는 메소드
    public String getLoggedInUsername() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    // Profile 엔티티를 ProfileDto로 변환
    private ProfileDto convertToDto(Profile profile) {
        return ProfileDto.builder()
                .name(profile.getName())
                .career(profile.getCareer())
                .education(profile.getEducation())
                .certificates(profile.getCertificates())
                .preferredLocation(profile.getPreferredLocation())
                .Skills(profile.getSkills())
                .build();
    }
}
