package com.interview;

import com.interview.Common.ServiceConfiguration;
import com.interview.SongGathering.Controller.*;
import com.interview.SongGathering.Endpoints.WordQueryResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.knowm.dropwizard.sundial.SundialBundle;
import org.knowm.dropwizard.sundial.SundialConfiguration;
import org.knowm.dropwizard.sundial.tasks.*;
import org.skife.jdbi.v2.DBI;

/**
 * http://www.dropwizard.io/1.0.6/docs/manual/core.html#application
 */
public class ServiceApplication extends Application<ServiceConfiguration> {
    public static void main(String[] args) throws Exception {
        new ServiceApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new FlywayBundle<ServiceConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }

            @Override
            public FlywayFactory getFlywayFactory(ServiceConfiguration configuration) {
                return configuration.getFlywayFactory();
            }
        });

        bootstrap.addBundle(new SundialBundle<ServiceConfiguration>() {
            @Override
            public SundialConfiguration getSundialConfiguration(ServiceConfiguration configuration) {
                return configuration.getSundialConfiguration();
            }
        });
    }

    @Override
    public void run(ServiceConfiguration config, Environment env) {
//        config.getFlywayFactory().build(config.getDataSourceFactory().build(env.metrics(), "db")).clean();
//        config.getFlywayFactory().build(config.getDataSourceFactory().build(env.metrics(), "db")).migrate();

        final DBIFactory factory = new DBIFactory();
        final DBI dbi = factory.build(env, config.getDataSourceFactory(), "mysql");

        SongDao songDao = dbi.onDemand(SongDao.class);
        WordCountDao wordCountDao = dbi.onDemand(WordCountDao.class);

        dbi.registerMapper(new SongResultMapper());
        dbi.registerMapper(new WordCountMapper());

        SongDaoImpl songDaoImpl = new SongDaoImpl(songDao);
        WordCountDaoImpl wordCountDaoImpl = new WordCountDaoImpl(wordCountDao);

        WordQueryResource wordQueryResource = new WordQueryResource(songDaoImpl, wordCountDaoImpl);

        env.jersey().register(wordQueryResource);

        env.getApplicationContext().setAttribute("dbi", dbi);
        env.admin().addTask(new LockSundialSchedulerTask());
        env.admin().addTask(new UnlockSundialSchedulerTask());
        env.admin().addTask(new RemoveJobTriggerTask());
        env.admin().addTask(new AddCronJobTriggerTask());
        env.admin().addTask(new StartJobTask());
        env.admin().addTask(new StopJobTask());
        env.admin().addTask(new RemoveJobTask());
        env.admin().addTask(new AddJobTask());
    }
}
