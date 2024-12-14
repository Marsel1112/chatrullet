package org.chatrullet.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDTO {
    private String name;
    private Gender gender;
    private StatusEnum status;
}
