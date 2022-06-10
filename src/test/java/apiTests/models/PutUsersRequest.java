package apiTests.models;

public class PutUsersRequest {
    public String name;
    public String job;

    public PutUsersRequest(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

}
