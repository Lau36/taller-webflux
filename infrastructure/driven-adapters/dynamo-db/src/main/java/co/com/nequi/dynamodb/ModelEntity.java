package co.com.nequi.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class ModelEntity {

    private String id;
    private String apiId;
    private String firstName;
    private String lastName;
    private String email;

    public ModelEntity(String id, String apiId, String firstName, String lastName, String email) {
        this.id = id;
        this.apiId = apiId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public ModelEntity() {
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    @DynamoDbAttribute("apiId")
    public String getApiId() {
        return apiId;
    }

    @DynamoDbAttribute("firstName")
    public String getFirstName() {
        return firstName;
    }

    @DynamoDbAttribute("lastName")
    public String getLastName() {
        return lastName;
    }

    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
