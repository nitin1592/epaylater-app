package com.epaylater;

import com.epaylater.configuration.AppConfiguration;
import com.epaylater.database.CreditLimitDAO;
import com.epaylater.database.CreditTransactionsDAO;
import com.epaylater.exceptionmapper.ConcurrentTransactionExceptionMapper;
import com.epaylater.exceptionmapper.CreditLimitCrossedExceptionMapper;
import com.epaylater.exceptionmapper.PhoneNumberNotExistExceptionMapper;
import com.epaylater.resources.CreateAccountResource;
import com.epaylater.resources.SpendResource;
import com.epaylater.service.ISpendService;
import com.epaylater.service.SpendService;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

import org.skife.jdbi.v2.DBI;

public class App extends Application<AppConfiguration> {


  public static void main(String[] args) throws Exception {

    new App().run(new String[] {"server", "config.yml"});
  }

  @Override
  public void run(AppConfiguration configuration, Environment environment)
      throws Exception {


    final DBIFactory factory = new DBIFactory();
    final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");

    CreditTransactionsDAO creditTransactionTable = jdbi.onDemand(CreditTransactionsDAO.class);

    creditTransactionTable.createTable();
    creditTransactionTable.createIndex();

    CreditLimitDAO creditLimitTable = jdbi.onDemand(CreditLimitDAO.class);
    creditLimitTable.createCreditLimitTable();

    ISpendService spendService = new SpendService(creditTransactionTable, creditLimitTable);
    SpendResource spendResource =
        new SpendResource(spendService);
    environment.jersey().register(spendResource);

    environment.jersey().register(new ConcurrentTransactionExceptionMapper());
    environment.jersey().register(new CreditLimitCrossedExceptionMapper());
    environment.jersey().register(new PhoneNumberNotExistExceptionMapper());

    environment.jersey().register(new CreateAccountResource(creditLimitTable));
  }
}
