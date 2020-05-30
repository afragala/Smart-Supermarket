package it.unipr.iotlab.iot2020.cf.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.json.JSONException;
import org.json.JSONObject;

import it.unipr.iotlab.iot2020.cf.object.CustomerObject;
import it.unipr.iotlab.iot2020.cf.object.DepartmentObject;
import it.unipr.iotlab.iot2020.cf.object.GoodsObject;
import it.unipr.iotlab.iot2020.cf.object.ShoppingListObject;

public class Customer extends CoapClient {

    private static final String storeDataURI = "coap://localhost:5683/store-data";

    private static GoodsObject goodsObject;
    private static ArrayList<GoodsObject> goodsList = new ArrayList<GoodsObject>();
    private static String[] goods;

    private static ArrayList<ShoppingListObject> shoppingList = new ArrayList<ShoppingListObject>();

    private static int goodsToBuy;
    private static int departmentToVisit;

    private static final long period = Long.MAX_VALUE;

    private static final String peopleCounterURI = "coap://localhost:5683/people-counter";
    private static final String customerURI = "coap://localhost:5683/customer";

    private static final int CUSTOMERS = 51;

    
    private static String[] departments = { "FRUITANDVEGGIES", "BREAD", "SEAFOOD", "MEAT", "CEREALANDCOFFEE",
            "PASTAANDCONDIMENTS", "DRYGOODS", "BABYANDPET", "PAPERGOODS", "DAIRY", "FROZEN", "PHARMACY",
            "MISCELLANEOUS" };

    public static void main(String[] args) {

        CoapClient client = new CoapClient(storeDataURI);
        client.get(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                goods = response.getResponseText().split("-", 0);

                for(String good : goods) {
                    try {
                        JSONObject jsonObject = new JSONObject(good);
                        goodsObject = new Gson().fromJson(jsonObject.toString(), GoodsObject.class);
                        
                        goodsList.add(goodsObject);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                System.out.println("=========== GOODS LIST ============");
                System.out.println("ID NAME DEPARTMENT PRICE EXPIRATIONDATE CURRENTQUANTITY");
                for(GoodsObject good : goodsList){
                    System.out.println(good.getId() + "\t" + good.getName() + "\t" + good.getPrice() + "\t" + good.getExpirationDate() + "\t" + good.getCurrentQuantity());
            
                }
            }

            @Override
            public void onError() {
                System.err.println("Error on 'store-data' GET resource");
            }
        });


        
        // 

        // /************************************************************
        //  * Cumunicating to the server that there is a new customer, *
        //  * adding also the shopping list in terms of department     *
        //  ************************************************************/
        // for(int i=0; i<CUSTOMERS; ++i){
        //     for(int j=0; j<(departments.length)/2; ++j){
        //         shoppingList.add(departments[rand.nextInt(departments.length)]);
        //     }

        //     CustomerObject customer = new CustomerObject(i, shoppingList);
        //     JSONObject json = new JSONObject(customer);

        //     CoapClient peopleCounter = new CoapClient(peopleCounterURI);
        //     peopleCounter.put(new CoapHandler(){

        //         @Override
        //         public void onLoad(CoapResponse response) {
        //             if(response.getCode() == ResponseCode.CREATED)
        //                 System.out.println(Utils.prettyPrint(response));
        //             else if(response.getCode() == ResponseCode.CONTENT)
        //                 System.out.println("Current position: " + response.getResponseText());
        //             else if(response.getCode() == ResponseCode.CHANGED)
        //                 System.err.println("Please move to " + response.getResponseText());
        //         }

        //         @Override
        //         public void onError() {
        //             System.err.println("Error on 'people-counter' POST resource");
        //         }
            
        //     }, json.toString(), MediaTypeRegistry.APPLICATION_JSON);

        //     shoppingList.clear();
        // }

        try {
             Thread.sleep(period);
        } catch (Exception e) {
             e.printStackTrace();
        }
        
    }
}