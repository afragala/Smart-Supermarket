package it.unipr.iotlab.iot2020.cf.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONException;
import org.json.JSONObject;

import it.unipr.iotlab.iot2020.cf.object.DepartmentObject;
import it.unipr.iotlab.iot2020.cf.object.GoodsObject;

public class FruitAndVeggies extends CoapClient {

    private static final String department = "FRUITANDVEGGIES";

    private static DepartmentObject departmentObject;
    private static GoodsObject goodsObject;

    private static final String storeDataURI = "coap://localhost:5683/store-data";
    private static final String peopleCounterURI = "coap://localhost:5683/people-counter";

    private static int upperBound;
    private static int lowerBound;

    private static String[] goods = { "potatoes", "zucchini", "broccoli", "apples", "water melons", "peaches" };
    private static ArrayList<GoodsObject> goodsList = new ArrayList<GoodsObject>();

    private static String date = "";
    private static int day;
    private static int month;
    private static int year;

    private static float price;
    private static int quantity;

    private static final long period = Long.MAX_VALUE;

    public static void main(String[] args) {

        Random random = new Random();
        upperBound = random.nextInt(4) + 5;
        lowerBound = upperBound - (random.nextInt(3) + 1);

        departmentObject = new DepartmentObject(department, lowerBound, upperBound);
        JSONObject jsonObject = new JSONObject(departmentObject);

        CoapClient client = new CoapClient(storeDataURI);
        client.post(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                System.out.println(Utils.prettyPrint(response));
            }

            @Override
            public void onError() {
                System.err.println("Error on 'store-data' PUT resource (DepartmentObject)");
            }
        }, jsonObject.toString(), MediaTypeRegistry.APPLICATION_JSON);

        CoapClient client2 = new CoapClient(storeDataURI);
        for (String good : goods) {
            month = random.nextInt(12) + 1;
            if (month == 2)
                day = random.nextInt(28) + 1;
            else if ((month % 2) == 0)
                day = random.nextInt(30) + 1;
            else
                day = random.nextInt(31) + 1;
            year = random.nextInt(5) + 2021;

            date = day + "/" + month + "/" + year;

            price = random.nextFloat() + random.nextInt(5);
            BigDecimal finalPrice = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);

            quantity = random.nextInt(6) + 2;

            goodsObject = new GoodsObject(good, department, finalPrice, date, quantity);
            JSONObject jsonObject2 = new JSONObject(goodsObject);

            client2.post(new CoapHandler() {
                @Override
                public void onLoad(CoapResponse response) {
                    System.out.println(Utils.prettyPrint(response));
                }

                @Override
                public void onError() {
                    System.err.println("Error on 'store-data' PUT resource (GoodsObject)");
                }
            }, jsonObject2.toString(), MediaTypeRegistry.APPLICATION_JSON);
        }

        CoapClient client3 = new CoapClient(peopleCounterURI);
        client3.observe(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                System.out.println(Utils.prettyPrint(response));
            }

            @Override
            public void onError() {
                System.err.println("Error on 'people-counter' OBSERVEVABLE resource");
            }
        });

        CoapClient client4 = new CoapClient(storeDataURI);
        client4.observeAndWait(new CoapHandler() {
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
                    if(good.getDepartment() == department)
                        System.out.println(good.getId() + "\t" + good.getName() + "\t" + good.getPrice() + "\t" + good.getExpirationDate() + "\t" + good.getCurrentQuantity());
            
                }
            }
            
            @Override
            public void onError() {
                System.err.println("Error on 'store-data' OBSERVEVABLE resource");
            }
        });

        try {
            Thread.sleep(period);
       } catch (Exception e) {
            e.printStackTrace();
       }
    }

}