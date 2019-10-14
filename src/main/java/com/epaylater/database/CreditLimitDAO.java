package com.epaylater.database;

import java.sql.Timestamp;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface CreditLimitDAO {

  @SqlUpdate(
      "CREATE TABLE IF NOT EXISTS credit_limit (phone_number character varying(10)  NOT NULL,"
          + "amount "
          + "numeric(10,2) NOT NULL,locked boolean NOT NULL,locked_time timestamp without time "
          + "zone "
          + ",CONSTRAINT \"primary key\" PRIMARY KEY (phone_number))")
  void createCreditLimitTable();

  @SqlQuery(
      "update credit_limit set locked=true , locked_time=:lockedTime where "
          + "phone_number=:phoneNumber and locked=false "
          + "returning amount")
  Double getCreditLimitByPhoneNumberIfNotLocked(@Bind("phoneNumber") String phoneNumber, @Bind(
      "lockedTime")
      Timestamp timestamp);

  @SqlUpdate(
      "update credit_limit set locked=false , locked_time=NULL where "
          + "phone_number=:phoneNumber")
  void unlock(@Bind("phoneNumber") String phoneNumber);

  @SqlQuery("select exists(select 1 from credit_limit where phone_number=:phoneNumber)")
  boolean checkIfAccountExists(@Bind("phoneNumber") String phoneNumber);

  @SqlUpdate(
      "INSERT INTO credit_limit(phone_number, amount, locked, locked_time) VALUES (:phoneNumber, "
          + ":creditLimit, false, NULL)")
  void createAccount(@Bind("phoneNumber") String phoneNumber,
      @Bind("creditLimit") Double creditLimit);

}