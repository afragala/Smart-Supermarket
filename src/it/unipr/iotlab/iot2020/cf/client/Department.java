package it.unipr.iotlab.iot2020.cf.client;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.json.JSONObject;

import it.unipr.iotlab.iot2020.cf.object.DepartmentObject;

import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.MessageObserverAdapter;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;



public class Department extends CoapClient {

    // private static final String boundURL = "coap://localhost:5683/bound";
    private static final String URI = "coap://localhost:5683/people-counter";

    private static String[] departments = { "FRUITANDVEGGIES", "BREAD", "SEAFOOD", "MEAT", "CEREALANDCOFFEE",
            "PASTAANDCONDIMENTS", "DRYGOODS", "BABYANDPET", "PAPERGOODS", "DAIRY", "FROZEN", "PHARMACY",
            "MISCELLANEOUS" };
    private static int upperBound;
    private static int lowerBound;

    private static DepartmentObject departmentObject;

    private static ArrayList<DepartmentObject> department = new ArrayList<DepartmentObject>();

    public static void main(String[] args) {

        /* Creating the department, each one with a minimum and a maximum amount of people that can sustain */
        for (int i=0; i<departments.length; ++i) {
            Random random = new Random();
            
            upperBound = random.nextInt(4) + 5; // Random number between 5 and 8
            lowerBound = upperBound - 3;        // Random number between 2 and 5

            departmentObject = new DepartmentObject(departments[i], lowerBound, upperBound);
            JSONObject jsonObject = new JSONObject(departmentObject);

            department.add(departmentObject);

            CoapClient client = new CoapClient(URI);
            client.put(new CoapHandler() {

                @Override
                public void onLoad(CoapResponse response) {
                    System.out.println(Utils.prettyPrint(response));
                }

                @Override
                public void onError() {
                    System.err.println("Erron on 'people-counter' resource");
                }
            
            }, jsonObject.toString(), MediaTypeRegistry.APPLICATION_JSON);
        }

        /* try {
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        } */

        /* Each department observe its own "people counter" */
        /* CoapClient client = new CoapClient(URI);
        client.observe(new CoapHandler() {

            @Override
            public void onLoad(CoapResponse response) {
               System.out.println(Utils.prettyPrint(response));
            }

            @Override
            public void onError() {
               System.err.println("Failed on 'people-counter' observable resource");
            }
        });*/
        

        try {
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}