/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sun.media.sound.InvalidDataException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author pepo
 */
public class FileOperations {
    
    private static File headerFile = null ;
    private static File lineFile = null ; 
    
    public ArrayList<InvoiceHeader> readFile()
    {
        return new ArrayList<InvoiceHeader>();
    }
    
    public void writeFile(ArrayList<InvoiceHeader> headers) 
    {
   
     
    }
    public static void setHeaderFile(File headerFile) {
        FileOperations.headerFile = headerFile;
    }

    public static void setLineFile(File lineFile) {
        FileOperations.lineFile = lineFile;
    }
    
    
    public FileOperations () 
    {
        headerFile = new File ("CSV Files\\InvoiceHeader.csv") ;
        lineFile = new File ("CSV Files\\InvoiceLine.csv") ; 
        
    
    }

    
   

}
