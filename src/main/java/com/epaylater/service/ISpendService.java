package com.epaylater.service;

import com.epaylater.exceptions.ConcurrentTransactionException;
import com.epaylater.exceptions.CreditLimitCrossedException;
import com.epaylater.exceptions.PhoneNumberNotExistException;

public interface ISpendService {

  void creditAmount(String phoneNumber, Double amount, String description)
      throws ConcurrentTransactionException,
      CreditLimitCrossedException, PhoneNumberNotExistException;
}
