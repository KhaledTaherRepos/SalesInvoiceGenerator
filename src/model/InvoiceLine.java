/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Khaled
 */
public class InvoiceLine {
    
    private Integer num ;
    private String name;
    private Double price ;
    private Integer count ;
   

     public InvoiceLine() {}

    public InvoiceLine(Integer num, String name, double price, Integer count) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.num = num ;
       // this.invoicesHeaders = invoicesHeaders;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }
  
    
    public double calculateItemTotal() {
        return ((double)count*price) ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
/*
    public InvoiceHeader getInvoicesHeaders() {
        return invoicesHeaders;
    }

    public void setInvoicesHeaders(InvoiceHeader invoicesHeaders) {
        this.invoicesHeaders = invoicesHeaders;
    }
*/
    
   public String toString() //used to update table model
   {
        return this.num.toString() + "," + this.name + "," + this.price + "," + this.count + "," + calculateItemTotal() ;

   }
   
     public String toFileFomrat ()  //used to write to file, so no total
     {
        return this.num.toString() + "," + this.name + "," + this.price + "," + this.count ;
     }
}
