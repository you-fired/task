package inhatc.cse.task.profile;

public class ProfileNotFoundException extends RuntimeException {
    // 생성자 정의
    public ProfileNotFoundException(String message) {
        super(message);  // 부모 클래스(RuntimeException)의 생성자 호출
    }
}
