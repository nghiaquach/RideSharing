/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.dao;

import java.util.List;
import uts.edu.aip.model.Ride;

/**
 *
 * @author NQ
 */
public interface RideDAO {
   public List<Ride> getAllTransactions();
   public Ride getTransaction(int trasactionId);
   public void addTransaction(Ride transaction);
   public void updateTransaction(Ride transaction);
   public void deleteTransaction(Ride transaction);
   
}