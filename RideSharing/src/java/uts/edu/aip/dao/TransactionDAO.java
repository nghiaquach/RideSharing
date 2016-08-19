/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.dao;

import java.util.List;
import uts.edu.aip.model.Transaction;

/**
 *
 * @author NQ
 */
public interface TransactionDAO {
   public List<Transaction> getAllTransactions();
   public Transaction getTransaction(int trasactionId);
   public void addTransaction(Transaction transaction);
   public void updateTransaction(Transaction transaction);
   public void deleteTransaction(Transaction transaction);
   
}
