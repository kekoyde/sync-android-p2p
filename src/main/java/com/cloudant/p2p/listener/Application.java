package com.cloudant.p2p.listener;

import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import java.io.File;
import java.io.IOException;

/**
 * Created by snowch on 29/01/15.
 */
public class Application {

    private static String databaseDir = System.getProperty("DB_DIR");

    public static void main(String[] args) throws Exception {

        createDevelopmentDatabase();

        // Set up a Restlet service
        final Router router = new Router();
        router.attachDefault(HttpListener.class);

        org.restlet.Application myApp = new org.restlet.Application() {
            @Override
            public org.restlet.Restlet createInboundRoot() {
                router.setContext(getContext());
                return router;
            };
        };
        Component component = new Component();
        component.getDefaultHost().attach("/", myApp);

        new Server(Protocol.HTTP, 8182, component).start();
    }


    private static void createDevelopmentDatabase() throws IOException {

        // some temporary code for development purposes :)
        File path = new File(databaseDir);
        DatastoreManager manager = new DatastoreManager(path.getAbsolutePath());

        // make sure we have a database for development
        Datastore ds = manager.openDatastore("mydb");

//        MutableDocumentRevision rev = new MutableDocumentRevision();
//        Map<String, Object> json = new HashMap<String, Object>();
//        json.put("description", "Buy milk");
//        json.put("completed", false);
//        json.put("type", "com.cloudant.sync.example.task");
//        rev.body = DocumentBodyFactory.create(json);
//        ds.createDocumentFromRevision(rev);
        ds.close();
    }
}
