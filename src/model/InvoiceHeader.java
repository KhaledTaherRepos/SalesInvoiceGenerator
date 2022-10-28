/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Khaled
 */
public class InvoiceHeader {
    
    private Integer number ;
    private String date ;
    private String name ;
    private ArrayList <InvoiceLine> invLines ;

    public InvoiceHeader (){
    }
    public InvoiceHeader(Integer number, String date, String name, ArrayList<InvoiceLine> invLines) {
        this.number = number;
        this.date = date;
        this.name = name;
        this.invLines = invLines;
    }
    
    public int getInvLinesCount ()
    {
        if (invLines==null)
            return 0 ;
        else return invLines.size() ;
    }

    public ArrayList<InvoiceLine> getInvLines() {
  
        return invLines;
    }

    public Integer getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    } 

    public String getName() {
        return name;
    }
    
      public Double getInvoiceTotal () {
        Double total = 0.0;
        if (invLines == null)
            return total ;
        for (int i=0 ; i<invLines.size() ; i++)
            total += invLines.get(i).calculateItemTotal() ;
        return total ;
    }
     
     public String toString ()  //used to update table model
     {
         return this.number.toString() + "," + this.date + "," + name + "," + getInvoiceTotal().toString() ;
     }
     
     public String toFileFomrat ()  //used to write to file, so no total
     {
         return this.number.toString() + "," + this.date + "," + name ;
     }
     
     public void addInvoiceLine (InvoiceLine line)
     {
         if (invLines == null)
             invLines = new ArrayList<InvoiceLine> () ;
         invLines.add(line );
     }
     
     public void removeInvoiceLine (int index)
     {
         if (invLines == null)
             invLines = new ArrayList<InvoiceLine> () ;
         invLines.remove(index) ;
     }
    
}
