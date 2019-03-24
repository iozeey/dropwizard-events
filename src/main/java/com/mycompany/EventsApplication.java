package com.mycompany;

import com.mycompany.api.Event;
import com.mycompany.core.DummyEventRepository;
import com.mycompany.core.EventRepository;
import com.mycompany.resources.EventResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class EventsApplication extends Application<EventsConfiguration> {

    public static void main(final String[] args) throws Exception {
        new EventsApplication().run(args);
    }

    @Override
    public String getName() {
        return "Events";
    }

    @Override
    public void initialize(final Bootstrap<EventsConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final EventsConfiguration configuration,
                    final Environment environment) {
       // DateFormat eventDateFormat = new SimpleDateFormat(configuration.getDateFormat());
       // environment.getObjectMapper().setDateFormat(eventDateFormat);

        //connection through factory jdbi - pratice
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql").installPlugin(new PostgresPlugin());
//
        List<Event> users = jdbi.withHandle(handle -> {

            return handle.createQuery("SELECT * FROM events")
                    .mapToBean(Event.class)
                    .list();
        });

        // direct connection with database - pratice
/*        Jdbi jdbi = Jdbi.create("jdbc:postgresql://localhost:5435/dropwizard_events","usercare","usercare")
                .installPlugin(new PostgresPlugin());
        List<Event> users = jdbi.withHandle(handle -> {

        return handle.createQuery("SELECT * FROM events")
                .mapToBean(Event.class)
                .list();
        });*/

//
//        //dependency inversion principle
//        EventRepository repository = new DummyEventRepository(jdbi);
//
//        //registering our resource
//        EventResource eventResource = new EventResource(repository);
//        environment.jersey().register(eventResource);
    }

}
