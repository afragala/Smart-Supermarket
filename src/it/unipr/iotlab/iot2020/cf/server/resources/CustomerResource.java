package it.unipr.iotlab.iot2020.cf.server.resources;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class CustomerResource extends CoapResource {

    private static final String customerURI = "coap://localhost:5683/customer";

    public CustomerResource (String name) {
        super(name);
    }

    /**************************************** 
     * PUT METHOD 
     ****************************************/
    @Override
    public void handlePUT(CoapExchange exchange) {
        /* data = "ID - type - deparmentName - lowerBound - upperBound - counter" */
        exchange.respond(ResponseCode.CHANGED);

        CoapClient client = new CoapClient(customerURI);
        client.get(new CoapHandler(){
        
            @Override
            public void onLoad(CoapResponse response) {
                System.out.println(Utils.prettyPrint(response));
            }
        
            @Override
            public void onError() {
                System.err.println("Failed on 'customer' get ");
            }
        });
    }

    /**************************************** 
    * GET METHOD 
    ****************************************/
    @Override
    public void handleGET(CoapExchange exchange){


    }
}