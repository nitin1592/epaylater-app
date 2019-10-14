package com.epaylater.resources;

import com.epaylater.exceptions.ConcurrentTransactionException;
import com.epaylater.exceptions.CreditLimitCrossedException;
import com.epaylater.exceptions.PhoneNumberNotExistException;
import com.epaylater.requests.SpendRequest;
import com.epaylater.service.ISpendService;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class SpendResource {

  private ISpendService spendService;

  public SpendResource(ISpendService spendService) {
    this.spendService = spendService;
  }

  @POST
  @Path("spend")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String getPerson(SpendRequest spendRequest,
      @HeaderParam("Authorization") String authorizationValue)
      throws ConcurrentTransactionException, PhoneNumberNotExistException,
      CreditLimitCrossedException {


    spendService.creditAmount(spendRequest.getPhoneNumber(), spendRequest.getAmount(),
        spendRequest.getDescription());

    return "successful";
  }
}
