package it.dalpra.acme.bse.anagrafiche.contr;

import java.util.List;

import it.dalpra.acme.bse.anagrafiche.entity.Person;
import it.dalpra.acme.bse.anagrafiche.service.AddSomeData;
import it.dalpra.acme.bse.anagrafiche.service.PersonService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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

}