package com.cqcqya.application.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String mail;
    private String tel;
    private String password;
}
