/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.assign2.crud.gradebook.arao23.srv.resources;

import edu.asu.cse564.assign2.crud.gradebook.arao23.srv.model.GradeItem;
import edu.asu.cse564.assign2.crud.gradebook.arao23.srv.model.Student;
import edu.asu.cse564.assign2.crud.gradebook.arao23.srv.utils.Converter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Abhishek
 */
@Path("webresources")
public class GradeBookResource {

    private static final Logger LOG = LoggerFactory.getLogger(GradeBookResource.class);
    private static HashMap<Integer, Student> map = new HashMap<Integer, Student>();
    @Context
    private UriInfo context;

    /* static{
        LOG.info("Craeting 50 students in a map");
        for(int i=1;i<=50;i++){
            Student student= new Student();
            student.setId(i);
            map.put(i, student);
        }
    }*/
    @Path("gradeitems")
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response addGradeItems(Student student) {
        LOG.info("Add grade Items");
        Response response;
        List<GradeItem> gradeItems = student.getGradeitems();
        for (GradeItem g : gradeItems) {
            LOG.info("GRADEITEMS ={}", g.getGradeItemName());
            LOG.info("GRADEITEMS ={}", g.getMarksObtained());
            LOG.info("GRADEITEMS ={}", g.getPercentageAllocation());
        }

        Iterator itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = (Map.Entry) itr.next();
            Integer id = (Integer) pair.getKey();
            Student s = (Student) pair.getValue();
            s.setGradeitems(gradeItems);
            map.put(id, s);
        }
        response = Response.status(Response.Status.CREATED).entity(student).build();
        LOG.info("Returning response");
        return response;
    }

    @Path("/grades/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response addStudentGrade(@PathParam("id") String idStr, String gradeItem) {
        Response response = null;
        boolean gradeAlreadyPresent = false;
        try {
            GradeItem gra = (GradeItem) Converter.convertFromXmlToObject(gradeItem, GradeItem.class);
            Integer id = Integer.parseInt(idStr);
            if (map.containsKey(id)) {
                LOG.info("In add studentGrdes : {}", id);
                //Student databaseStu=map.get(id);
                Student s = map.get(id);
                List<GradeItem> graItems = s.getGradeitems();
                for (GradeItem g : graItems) {
                    if ((g.getGradeItemName().equals(gra.getGradeItemName()))) {
                        LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                        LOG.debug("Cannot create Student Resource as values is already set to {}", gradeItem);
                        response = Response.status(Response.Status.CONFLICT).entity(gradeItem).build();
                        gradeAlreadyPresent = true;
                        break;
                    }
                }
                if (!gradeAlreadyPresent) {
                    graItems.add(gra);
                    s.setGradeitems(graItems);
                    map.put(id, s);
                    URI locationURI = URI.create(context.getAbsolutePath().toString());
                    LOG.info("URI : {}", locationURI);
                    response = Response.status(Response.Status.CREATED).location(locationURI).entity(gradeItem).build();
                }

            } else {
                Student s = new Student();
                s.setId(id);
                List<GradeItem> graItems = new ArrayList<>();
                graItems.add(gra);
                s.setGradeitems(graItems);
                map.put(id, s);
                URI locationURI = URI.create(context.getAbsolutePath().toString());
                LOG.info("URI : {}", locationURI);
                response = Response.status(Response.Status.CREATED).location(locationURI).entity(gradeItem).build();

            }
        } catch (NumberFormatException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("Exception got :", e);
            response = Response.status(Response.Status.BAD_REQUEST).entity("Invalid Student ID").build();
        } catch (JAXBException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with Student Resource", gradeItem);
            response = Response.status(Response.Status.BAD_REQUEST).entity(gradeItem).build();
        } catch (RuntimeException e) {
            LOG.debug("Catch All exception = {}", e);
            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gradeItem).build();
        }

        LOG.info("Returning Response ={}", response);
        return response;
    }

    @Path("/grades/{id}/{gradeItem}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getStudentGradeforItem(@PathParam("id") String idStr, @PathParam("gradeItem") String gradeItemName) {
        Response response = null;
        boolean gradeIemGot = false;
        try {
            Integer id = Integer.parseInt(idStr);
            if (map.containsKey(id)) {
                LOG.info("In get studentGrdes : {}", id);
                Student s = map.get(id);
                List<GradeItem> graItems = s.getGradeitems();
                for (GradeItem g : graItems) {
                    if (g.getGradeItemName().equals(gradeItemName)) {
                        response = Response.status(Response.Status.OK).entity(g).build();
                        gradeIemGot = true;
                        break;
                    }
                }
                if (!gradeIemGot) {
                    LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                    LOG.debug("No student Resource to return");
                    response = Response.status(Response.Status.GONE).entity("No GradeItem Resource to return").build();
                }
            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                LOG.debug("No student Resource to return");
                response = Response.status(Response.Status.NOT_FOUND).entity("No Student Resource to return").build();
            }
        } catch (NumberFormatException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("Exception got :", e);
            response = Response.status(Response.Status.BAD_REQUEST).entity("Invalid Student ID").build();
        }
        LOG.info("Returning Response ={}", response);
        return response;
    }

    @Path("/grades/{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getStudentGrade(@PathParam("id") String idStr) {
        Response response = null;
        try {
            Integer id = Integer.parseInt(idStr);
            if (map.containsKey(id)) {
                LOG.info("In get studentGrdes : {}", id);
                Student s = map.get(id);
                //List<GradeItem> graItems = s.getGradeitems();
                response = Response.status(Response.Status.OK).entity(s).build();

            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                LOG.debug("No student Resource to return");
                response = Response.status(Response.Status.NOT_FOUND).entity("No Student Resource to return").build();
            }
        } catch (NumberFormatException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("Exception got :", e);
            response = Response.status(Response.Status.BAD_REQUEST).entity("Invalid Student ID").build();
        }
        LOG.info("Returning Response ={}", response);
        return response;

    }

    @Path("/grades/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateStudentGrade(@PathParam("id") String idStr, String gradeItem) {
        LOG.info("In update studentGrdes : {}", idStr);
        Response response = null;
        boolean gradeAlreadyPresent = false;
        try {
            GradeItem gra = (GradeItem) Converter.convertFromXmlToObject(gradeItem, GradeItem.class);
            Integer id = Integer.parseInt(idStr);
            if (map.containsKey(id)) {
                LOG.info("In add studentGrdes : {}", id);
                //Student databaseStu=map.get(id);
                Student s = map.get(id);
                List<GradeItem> graItems = s.getGradeitems();
                for (GradeItem g : graItems) {
                    if ((g.getGradeItemName().equals(gra.getGradeItemName()))) {
                        graItems.remove(g);
                        graItems.add(gra);
                        s.setGradeitems(graItems);
                        map.put(id, s);
                        gradeAlreadyPresent = true;
                        LOG.debug("Updated gardeItem Resource to {}", gradeItem);
                        response = Response.status(Response.Status.OK).entity(gradeItem).build();
                        break;
                    }
                }
                if (!gradeAlreadyPresent) {
                    LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                    LOG.debug("Cannot update Gradeitem Resource {} as it has not yet been created", gradeItem);
                    response = Response.status(Response.Status.CONFLICT).entity(gradeItem).build();
                }

            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                LOG.debug("Cannot update Gradeitem Resource {} as it has not yet been created", gradeItem);
                response = Response.status(Response.Status.CONFLICT).entity(gradeItem).build();
            }

        } catch (NumberFormatException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("Exception got :", e);
            response = Response.status(Response.Status.BAD_REQUEST).entity("Invalid Student ID").build();
        } catch (JAXBException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with Student Resource", gradeItem);
            response = Response.status(Response.Status.BAD_REQUEST).entity(gradeItem).build();
        } catch (RuntimeException e) {
            LOG.debug("Catch All exception = {}", e);
            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gradeItem).build();
        }
        LOG.info("Returning Response ={}", response);
        return response;
    }
    
    @Path("/grades/appeals/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateStudentGradeAppeal(@PathParam("id") String idStr, String gradeItem) {
        LOG.info("In update studentGrdes : {}", idStr);
        Response response = null;
        boolean gradeAlreadyPresent = false;
        try {
            GradeItem gra = (GradeItem) Converter.convertFromXmlToObject(gradeItem, GradeItem.class);
            Integer id = Integer.parseInt(idStr);
            if (map.containsKey(id)) {
                LOG.info("In add studentGrdes : {}", id);
                //Student databaseStu=map.get(id);
                Student s = map.get(id);
                List<GradeItem> graItems = s.getGradeitems();
                for (GradeItem g : graItems) {
                    if ((g.getGradeItemName().equals(gra.getGradeItemName()))) {
                        graItems.remove(g);
                        graItems.add(gra);
                        s.setGradeitems(graItems);
                        map.put(id, s);
                        gradeAlreadyPresent = true;
                        LOG.debug("Updated gardeItem Resource to {}", gradeItem);
                        response = Response.status(Response.Status.OK).entity(gradeItem).build();
                        break;
                    }
                }
                if (!gradeAlreadyPresent) {
                    LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                    LOG.debug("Cannot update Gradeitem Resource {} as it has not yet been created", gradeItem);
                    response = Response.status(Response.Status.CONFLICT).entity(gradeItem).build();
                }

            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                LOG.debug("Cannot update Gradeitem Resource {} as it has not yet been created", gradeItem);
                response = Response.status(Response.Status.CONFLICT).entity(gradeItem).build();
            }

        } catch (NumberFormatException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("Exception got :", e);
            response = Response.status(Response.Status.BAD_REQUEST).entity("Invalid Student ID").build();
        } catch (JAXBException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with Student Resource", gradeItem);
            response = Response.status(Response.Status.BAD_REQUEST).entity(gradeItem).build();
        } catch (RuntimeException e) {
            LOG.debug("Catch All exception = {}", e);
            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gradeItem).build();
        }
        LOG.info("Returning Response ={}", response);
        return response;
    }


    @Path("/grades/{id}/{gradeItem}")
    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteStudentGrade(@PathParam("id") String idStr, @PathParam("gradeItem") String gradeItemName) {
        Response response = null;
        boolean gradeIemGot = false;
        try {
            Integer id = Integer.parseInt(idStr);
            if (map.containsKey(id)) {
                LOG.info("In delete studentGrdes : {}", idStr);
                Student s = map.get(id);
                List<GradeItem> graItems = s.getGradeitems();
                for (GradeItem g : graItems) {
                    if (g.getGradeItemName().equals(gradeItemName)) {
                        graItems.remove(g);
                        s.setGradeitems(graItems);
                        map.put(id, s);
                        response = Response.status(Response.Status.NO_CONTENT).build();
                        gradeIemGot = true;
                        break;
                    }
                }
                if (!gradeIemGot) {
                    LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                    LOG.debug("No student Resource to return");
                    response = Response.status(Response.Status.GONE).entity("No GradeItem Resource to return").build();
                }
            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                LOG.debug("No student Resource to return");
                response = Response.status(Response.Status.NOT_FOUND).entity("No Student Resource to return").build();
            }
        } catch (NumberFormatException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("Exception got :", e);
            response = Response.status(Response.Status.BAD_REQUEST).entity("Invalid Student ID").build();
        }
        LOG.info("Returning Response ={}", response);
        return response;
    }

}
