package it.unipr.iotlab.iot2020.cf.server;

import it.unipr.iotlab.iot2020.cf.server.resources.CustomerResource;
import it.unipr.iotlab.iot2020.cf.server.resources.PeopleCounterResource;
import it.unipr.iotlab.iot2020.cf.server.resources.StoreData;
import it.unipr.iotlab.iot2020.cf.server.resources.WarehouseResource;

import org.eclipse.californium.core.CoapServer;

public class SupermarketHub extends CoapServer {

    public static void main (String[] args) {
        SupermarketHub server = new SupermarketHub();

        WarehouseResource warehouse = new WarehouseResource("store-data");
        warehouse.setObservable(true);
        warehouse.getAttributes().setObservable();

        CustomerResource customer = new CustomerResource("customer");
        customer.setObservable(true);
        customer.getAttributes().setObservable();

        PeopleCounterResource peopleCounter = new PeopleCounterResource("people-counter");
        peopleCounter.setObservable(true);
        peopleCounter.getAttributes().setObservable();

        //StoreData storeData = new StoreData("store-data");

        server.add(warehouse, peopleCounter, customer);

        server.start();
    }

}