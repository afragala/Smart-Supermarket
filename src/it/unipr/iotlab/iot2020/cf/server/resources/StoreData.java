package it.unipr.iotlab.iot2020.cf.server.resources;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONException;
import org.json.JSONObject;

import it.unipr.iotlab.iot2020.cf.object.DepartmentObject;

public class StoreData extends CoapResource {

    public StoreData(String name) {
        super(name);
    }

    /****************************************
     * POST METHOD *
     ****************************************/
    @Override
    public void handlePOST(CoapExchange exchange) {
        System.out.println(exchange.getRequestText());

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(exchange.getRequestText());
            if(jsonObject.has("Department")){


            } else if (jsonObject.has("shoppingList")){
                System.out.println(jsonObject.get("shoppingList"));
               // String[] shoppingList = 
    
                //System.out.println(jsonObject.getJSONArray("shoppingList"));                
            }
    
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
                
        
        exchange.respond(ResponseCode.CREATED);
    }
  
    /**************************************** 
     * GET METHOD                           *
     ****************************************/

}