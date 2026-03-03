package com.coco.modules.user.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


@Getter
@Setter
public class User {

    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;


}