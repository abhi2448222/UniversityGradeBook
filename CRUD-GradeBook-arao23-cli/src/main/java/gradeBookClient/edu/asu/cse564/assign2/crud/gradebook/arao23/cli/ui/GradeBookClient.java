package gradeBookClient.edu.asu.cse564.assign2.crud.gradebook.arao23.cli.ui;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;

public class GradeBookClient {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/CRUD-GradeBook-arao23-srv/webapi/webresources/grades";

    public GradeBookClient() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI);
    }

    public ClientResponse create(String id, Object requestEntity) throws UniformInterfaceException {
        return webResource.path(id).type(MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public <T> T read(Class<T> responseType, String id, String grItemName) throws UniformInterfaceException {
        return webResource.path(id).path(grItemName).accept(MediaType.APPLICATION_XML).get(responseType);
    }

    public ClientResponse update(String id, Object requestEntity) throws UniformInterfaceException {
        return webResource.path(id).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }

    public ClientResponse updateAppealGradeBookCl(String id, Object requestEntity) throws UniformInterfaceException {
        return webResource.path("appeals").path(id).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }

    public ClientResponse delete(String id, String grItemName) throws UniformInterfaceException {
        return webResource.path(id).path(grItemName).accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
    }

    public void close() {
        client.destroy();
    }

}
