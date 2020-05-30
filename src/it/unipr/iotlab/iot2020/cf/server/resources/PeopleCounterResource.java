package it.unipr.iotlab.iot2020.cf.server.resources;

import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.unipr.iotlab.iot2020.cf.object.CustomerObject;
import it.unipr.iotlab.iot2020.cf.object.DepartmentObject;

public class PeopleCounterResource extends CoapResource {

    private static ArrayList<DepartmentObject> departments = new ArrayList<DepartmentObject>();
    private static ArrayList<CustomerObject> customers = new ArrayList<CustomerObject>();

    private static final long period = 1000;

    private static int pCounter = Integer.MAX_VALUE;
    private static String currentDepartment = null;

    public PeopleCounterResource(String name) {
        super(name);
    }

    /****************************************
     * POST METHOD *
     ****************************************/
    @Override
    public void handlePUT(CoapExchange exchange) {
        /* Responding with the code CREATED to the client */
        try {
            JSONObject jsonObject = new JSONObject(exchange.getRequestText());

            if (jsonObject.get("type").equals("DEPARTMENT")) {
                /* Request from a DEPARTMENT */
                DepartmentObject departmentObject = new ObjectMapper().readValue(jsonObject.toString(),
                        DepartmentObject.class);
                departments.add(departmentObject);

                exchange.respond(ResponseCode.CREATED);
            } else if (jsonObject.get("type").equals("CUSTOMER")) {
                /* Request from a CUSTOMER */
                CustomerObject customerObject = new ObjectMapper().readValue(jsonObject.toString(),
                        CustomerObject.class);
                customers.add(customerObject);

                exchange.respond(ResponseCode.CREATED);

                /* Getting the latest item of the queue */
                CustomerObject customer = customers.get(customers.size() - 1);

                for (String shoppingList : customer.getShoppingList()) {
                    for (DepartmentObject department : departments) {
                        if (shoppingList.equals(department.getName())) {
                            if (department.getPeopleCounter() == department.getUpperBound()) {
                                System.err.println("ALERT!\t\tDepartment: " + department.getName() + ", PeopleCounter: "
                                        + department.getPeopleCounter());
                                /********************************************************************************
                                 * If the upper bound is reached, is necessary to move the customer to his next *
                                 * department                                                                   *
                                 ********************************************************************************/
                                for (String shList : customer.getShoppingList()) {
                                    for (DepartmentObject d : departments) {
                                        if (shList.equals(d.getName())) {
                                            if (d.getPeopleCounter() < d.getUpperBound()) {
                                                if (d.getUpperBound() < pCounter) {
                                                    currentDepartment = d.getName();
                                                }
                                            }
                                        }
                                    }
                                }
                                if (currentDepartment == null) {
                                    System.out.println("All the departments are full, the customer " + customer.getId()
                                            + " must wait!");
                                    Thread.sleep(period - customer.getId());
                                } else {
                                    System.out.println(
                                            "Moving the customer " + customer.getId() + " to " + currentDepartment);
                                    /* Incrementare PeopleCounter del diparmento */
                                    customer.setCurrentDepartment(currentDepartment);
                                    exchange.respond(ResponseCode.CHANGED, customer.getId() + " " + currentDepartment);
                                    changed();
                                }
                            } else if (department.getPeopleCounter() > department.getLowerBound()) {
                                department.setPeopleCounter(department.getPeopleCounter() + 1);
                                customer.setCurrentDepartment(department.getName());
                                System.out.println("WARNING!\tDepartment: " + department.getName() + ", PeopleCounter: "
                                        + department.getPeopleCounter());
                                exchange.respond(ResponseCode.CHANGED, customer.getId() + " " + department.getName(),
                                        MediaTypeRegistry.TEXT_PLAIN);
                                /***************************************************************************
                                 * After a certain amount of time the customer move to another department, *
                                 * then is necessary to decrease the counter of the department. *
                                 ***************************************************************************/
                                Thread.sleep(period - customer.getId());
                                department.setPeopleCounter(department.getPeopleCounter() - 1);
                                changed();
                            } else {
                                department.setPeopleCounter(department.getPeopleCounter() + 1);
                                customer.setCurrentDepartment(department.getName());
                                System.out.println("INFO!\t\tDepartment: " + department.getName() + ", PeopleCounter: "
                                        + department.getPeopleCounter());
                                exchange.respond(ResponseCode.CHANGED, customer.getId() + " " + department.getName(),
                                        MediaTypeRegistry.TEXT_PLAIN);
                                /***************************************************************************
                                 * After a certain amount of time the customer move to another department, *
                                 * then is necessary to decrease the counter of the department. *
                                 ***************************************************************************/
                                Thread.sleep(period - customer.getId());
                                department.setPeopleCounter(department.getPeopleCounter() - 1);
                                changed();
                            }
                        }
                    }
                }

                customers.remove(customer); // Removing the customer from the queue
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}