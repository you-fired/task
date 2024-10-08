package inhatc.cse.jaeseokshop.member.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String name;
    private String email;
    private String password;
    private String address;

}
