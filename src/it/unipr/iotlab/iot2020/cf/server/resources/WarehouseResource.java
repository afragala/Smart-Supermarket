package it.unipr.iotlab.iot2020.cf.server.resources;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONException;
import org.json.JSONObject;

import it.unipr.iotlab.iot2020.cf.object.CustomerObject;
import it.unipr.iotlab.iot2020.cf.object.DepartmentObject;
import it.unipr.iotlab.iot2020.cf.object.GoodsObject;

public class WarehouseResource extends CoapResource {

    DepartmentObject departmentObject;
    GoodsObject goodsObject;
    CustomerObject customerObject;

    private static ArrayList<DepartmentObject> departments = new ArrayList<DepartmentObject>();
    private static ArrayList<GoodsObject> goods = new ArrayList<GoodsObject>();
    private static ArrayList<CustomerObject> customers = new ArrayList<CustomerObject>();

    String goodsList = "";

    public WarehouseResource(String name) {
        super(name);
    }

    /****************************************
     * POST METHOD                          *
     ****************************************/
    @Override
    public void handlePOST(CoapExchange exchange) {
        try {
            JSONObject jsonObject = new JSONObject(exchange.getRequestText());
            if (jsonObject.get("type").equals("DEPARTMENT")) {
                departmentObject = new Gson().fromJson(jsonObject.toString(), DepartmentObject.class);
                departments.add(departmentObject);

                exchange.respond(ResponseCode.CREATED);
            } else if (jsonObject.get("type").equals("GOODS")) {
                goodsObject = new Gson().fromJson(jsonObject.toString(), GoodsObject.class);
                goods.add(goodsObject);

                exchange.respond(ResponseCode.CREATED);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /****************************************
     * PUT METHOD                           *
     ****************************************/
    @Override
    public void handlePUT(CoapExchange exchange) {
        try {
            JSONObject jsonObject = new JSONObject(exchange.getRequestText());
            if (jsonObject.get("type").equals("GOODS")) {
                for(GoodsObject good : goods){
                    if(jsonObject.get("name").equals(good.getName())){
                        good.setCurrentQuantity(good.getCurrentQuantity() - 1);

                        exchange.respond(ResponseCode.CHANGED);
                        changed();
                    }
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /****************************************
     * GET METHOD                           *
     ****************************************/
    @Override
    public void handleGET(CoapExchange exchange) {
        
        goodsList = toJSON(goods);

        System.out.println(goodsList);
                
        exchange.respond(ResponseCode.CONTENT, goodsList, MediaTypeRegistry.APPLICATION_JSON);
    }

    private String toJSON(ArrayList<GoodsObject> goods){
        Gson gson = new Gson();
        StringBuilder stringBuilder = new StringBuilder();

        for(GoodsObject good : goods)
            stringBuilder.append(gson.toJson(good) + "-");

        return stringBuilder.toString();
    }
}