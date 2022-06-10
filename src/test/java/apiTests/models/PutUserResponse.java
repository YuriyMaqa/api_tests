package apiTests.models;


import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class PutUserResponse {
    public String name;
    public String job;
    public String updatedAt;

}
