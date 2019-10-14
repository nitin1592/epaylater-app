package com.epaylater.database;

import java.sql.Timestamp;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface CreditTransactionsDAO {

  @SqlUpdate(
      "CREATE TABLE IF NOT EXISTS credit_transactions (phone_number character varying(10) NOT NULL,"
          + "transaction_amount numeric(10, 2) NOT NULL,transaction_time time without time zone "
          + "NOT NULL, description character varying(500))")
  void createTable();

  @SqlUpdate("CREATE INDEX IF NOT EXISTS phone_number_index ON credit_transactions (phone_number)")
  void createIndex();

  @SqlQuery(
      "select sum(transaction_amount) from credit_transactions where phone_number=:phoneNumber")
  Double getTotalSpendAmount(@Bind("phoneNumber") String phoneNumber);

  @SqlUpdate(
      "INSERT INTO credit_transactions(phone_number, transaction_amount, transaction_time,"
          + " description)"
          + " VALUES (:phoneNumber, :amount, :timestamp, :description)")
  void makeTransactionEntry(@Bind("phoneNumber") String phoneNumber,
      @Bind("amount") Double amount, @Bind("timestamp") Timestamp timestamp,
      @Bind("description") String description);

}
