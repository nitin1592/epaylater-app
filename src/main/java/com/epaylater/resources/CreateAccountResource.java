package com.epaylater.resources;

import com.epaylater.database.CreditLimitDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

// this is dummy resource for creating accounts in DB for testing, please ignore this.
@Path("/")
public class CreateAccountResource {

  private CreditLimitDAO creditLimitDAO;


  public CreateAccountResource(CreditLimitDAO creditLimitDAO) {
    this.creditLimitDAO = creditLimitDAO;
  }

  @POST
  @Path("createAccount")
  @Consumes(MediaType.APPLICATION_JSON)
  public String createAccount(Account account) {
    creditLimitDAO.createAccount(account.phoneNumber, account.creditLimit);
    return "successful";

  }

  public static class Account {
    private String phoneNumber;
    private Double creditLimit;

    public String getPhoneNumber() {
      return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }

    public Double getCreditLimit() {
      return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
      this.creditLimit = creditLimit;
    }
  }
}
