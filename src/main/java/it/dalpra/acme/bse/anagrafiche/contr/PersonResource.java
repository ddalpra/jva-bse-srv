package it.dalpra.acme.bse.anagrafiche.contr;

import java.util.List;

import it.dalpra.acme.bse.anagrafiche.entity.Person;
import it.dalpra.acme.bse.anagrafiche.service.AddSomeData;
import it.dalpra.acme.bse.anagrafiche.service.PersonService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/person")
public class PersonResource {

    @Inject
    AddSomeData addSomeData;

    @Inject
    PersonService personService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/init")
    @Produces(MediaType.TEXT_PLAIN)
    public String init() {
        addSomeData.doAddSomeDataToDatabase();
        return "DB initialized and populated";
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    /**
     * This will work out of the box because there is a default
     * converter from POJO to JSON!
     * @return
     */
    @GET
    @Path("/all/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersonsAsJson() {
        return personService.getAllPersons();
    }


    /**
     * This Won't work (out of the box) because there is no default
     * converter from POJO to XML
     * @return
     */
    @GET
    @Path("/all/xml")
    @Produces(MediaType.APPLICATION_XML)
    public List<Person> getAllPersonsAsXml() {
        return personService.getAllPersons();
    }


    /**
     * This will work out of the box because there is a default
     * converter from POJO to JSON!
     * @return
     * http://localhost:8080/person/lastName/Marley
     */
    @GET
    @Path("/lastName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersonsWithLastNameAsJson(@PathParam("name") String lastName) {
        return personService.getAllPersonsWithLastName(lastName);
    }

    @GET
    @Path("/dao/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersonsFromDao() {
        return personService.getAll();
    }


    @GET
    @Path("/bornAfter/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersonsBornAfterYear(@PathParam("year") Integer year) {
        return personService.findBornAfter(year);
    }


    @GET
    @Path("/age/average")
    @Produces(MediaType.APPLICATION_JSON)
    public Double averageAge() {
        return personService.calculateAverageOfAgesOfPersonsBornAfter(1800);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(Person person) {
        // Salva la persona tramite il servizio
        Person created = personService.createPerson(person);
        
        // Restituisce uno stato HTTP 201 Created insieme all'oggetto appena salvato
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") Long id, Person personDetails) {
        // Aggiorna la persona tramite il servizio passando l'ID e i nuovi dettagli
        Person updated = personService.updatePerson(id, personDetails);
        
        if (updated == null) {
            // Se la persona con quell'ID non esiste, restituisce 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        // Altrimenti restituisce lo stato 200 OK con l'oggetto modificato
        return Response.ok(updated).build();
    }
}