package com.scriptql.scriptqlapi.rest.mappers.response;

import com.scriptql.scriptqlapi.domain.entities.User;
import lombok.Data;

@Data
public class UserResponseMapper {
    private long id;
    private String email;
    private String name;

    public static UserResponseMapper fromUser(User user) {
        var mapper = new UserResponseMapper();

        mapper.setId(user.getId());
        mapper.setEmail(user.getEmail());
        mapper.setName(user.getName());

        return mapper;
    }
}
