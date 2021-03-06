package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.UserFacade;
import java.util.Random;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("demouser")
@RolesAllowed("User")
public class UserResource {

    UserFacade uf;

    public UserResource() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu_development");
        this.uf = new UserFacade(emf);
    }
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Random rand = new Random();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSomething() {
        return "{\"message\" : \"Hello User from Server (Accesible by only authenticated USERS)\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("random")
    public String getRandomNumber() {
        int number = rand.nextInt(40);
        return "{\"number\" : \"" + number + "\"}";
    }

}
