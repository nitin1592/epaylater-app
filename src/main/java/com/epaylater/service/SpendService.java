package com.epaylater.service;

import com.epaylater.database.CreditLimitDAO;
import com.epaylater.database.CreditTransactionsDAO;
import com.epaylater.exceptions.ConcurrentTransactionException;
import com.epaylater.exceptions.CreditLimitCrossedException;
import com.epaylater.exceptions.PhoneNumberNotExistException;
import com.epaylater.exceptions.SingleTransactionLimitException;

import java.sql.Timestamp;
import java.time.Instant;

public class SpendService implements ISpendService {

  private CreditTransactionsDAO creditTransactionsDAO;
  private CreditLimitDAO creditLimitDAO;

  public SpendService(CreditTransactionsDAO creditTransactionsDAO,
      CreditLimitDAO creditLimitDAO) {
    this.creditTransactionsDAO = creditTransactionsDAO;
    this.creditLimitDAO = creditLimitDAO;

    creditLimitDAO.createCreditLimitTable();
    creditTransactionsDAO.createTable();
    creditTransactionsDAO.createIndex();
  }


  @Override
  public void creditAmount(String phoneNumber, Double amount, String description)
      throws ConcurrentTransactionException, CreditLimitCrossedException,
      PhoneNumberNotExistException, SingleTransactionLimitException {

    boolean exists = creditLimitDAO.checkIfAccountExists(phoneNumber);

    if (!exists) {
      throw new PhoneNumberNotExistException();
    }
    
    Double totalLimit = creditLimitDAO.getCreditLimitByPhoneNumberIfNotLocked(phoneNumber,
        new Timestamp(Instant.now().toEpochMilli()));

    if (totalLimit == null) {
      throw new ConcurrentTransactionException();
    }
    
    if(amount > 100000.00) {
    	throw new SingleTransactionLimitException();
    }

    Double totalSpent = creditTransactionsDAO.getTotalSpendAmount(phoneNumber);

    if(totalSpent  == null) {
    	totalSpent = 0.0;
    }
    if (totalSpent+amount > totalLimit) {
    	creditLimitDAO.unlock(phoneNumber);
      throw new CreditLimitCrossedException();
    }


    creditTransactionsDAO
        .makeTransactionEntry(phoneNumber, amount, new Timestamp(Instant.now().toEpochMilli()),
            description);

    creditLimitDAO.unlock(phoneNumber);
  }
}
