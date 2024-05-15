package org.sinedmv.Cats.ApiMicroservice.Models;

import java.io.Serializable;

public class ResponseDto implements Serializable {
    private final String jwt;

    public ResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}