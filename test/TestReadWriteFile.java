
import java.util.ArrayList;
import model.FileOperations;
import model.InvoiceHeader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Khaled
 */
public class TestReadWriteFile {
    
    public static void main (String [] args)
    {
        FileOperations op = new FileOperations () ;
        ArrayList <InvoiceHeader> out = new ArrayList <InvoiceHeader> () ;
        try {
           out = op.readFile() ;
            for (InvoiceHeader h : out)
            {
                System.out.println("Header: " + h.toString() + " -> lines -> " + h.getInvLines());
                
                        }
        } catch (Exception e)
        {
           e.printStackTrace();
        }
    }
    
}
