package com.interview.SongGathering;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.args.JodaDateTimeArgumentFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class UseTestDb {
    private Environment env;
    private DBI dbi;

    public void setup() {
        env = new Environment("test-env", Jackson.newObjectMapper(), null, new MetricRegistry(), null);
        dbi = new DBIFactory().build(env, getDataSourceFactory(), "mysql");
    }

    public DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("com.mysql.jdbc.Driver");
        dataSourceFactory.setUrl("jdbc:mysql://localhost:3306/spotify");
        dataSourceFactory.setUser("root");
        dataSourceFactory.setPassword("");
        return dataSourceFactory;
    }

    public DBI getDbi() {
        return dbi;
    }

    public Environment getEnvironment() {
        return env;
    }
}
