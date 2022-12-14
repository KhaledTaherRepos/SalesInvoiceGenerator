/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;

/**
 *
 * @author pepo
 */
public class FileOperations {
    
    private static File headerFile = null ;
    private static File lineFile = null ; 
    
    //ArrayList <InvoiceLine> lines ;

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
    public ArrayList<InvoiceHeader> readFile() throws FileNotFoundException , InvalidPathException, IOException
    {
        //checking if files exist
       if (!headerFile.exists())
       {
           throw new FileNotFoundException (headerFile.getAbsolutePath().toString()+ " wasn't found!") ;
       }   
       if (!lineFile.exists())
       {
           throw new FileNotFoundException (lineFile.getAbsolutePath().toString()+ " wasn't found!") ;
       }
       
       //checking correct file extension extension
       
       if(!headerFile.toPath().toString().endsWith("csv") ) 
       {
          throw new InvalidPathException (headerFile.toPath().toString(),"Wrong file extension. Only csv allowed");
       } else if (!lineFile.toPath().toString().endsWith("csv") )
       {
           throw new InvalidPathException (lineFile.toPath().toString(),"Wrong file extension. Only csv allowed");
       }
       
    
       
     
         FileReader fileReader = new FileReader(lineFile);
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         
        //reading lines
        //
        //
        
        ArrayList <InvoiceLine> lines = new ArrayList<InvoiceLine>() ;
        
        String [] lineArray ; //userd to store one invoice at a time
        String line = "" ;
        
        while ((line = bufferedReader.readLine()) != null && line.length() != 0) { //second clause to avoid empty lines
            lineArray = line.split(",") ;
            lines.add(new InvoiceLine (Integer.parseInt(lineArray[0]),lineArray[1], Double.parseDouble(lineArray[2]), Integer.parseInt(lineArray[3])));
        }
        
        //reading headers
        //
        //
        fileReader = new FileReader(headerFile);
        bufferedReader = new BufferedReader(fileReader);
        
        ArrayList<InvoiceHeader> headers = new ArrayList<InvoiceHeader> () ;

        while ((line = bufferedReader.readLine()) != null && line.length() != 0) { //second clause to avoid empty lines
            lineArray = line.split(",") ;
            
            if (!lineArray[1].matches("([0-9]{2})-([0-9]{2})-([0-9]{4})"))   //usign regex to validate date format
                throw new IOException("Date format has to be dd-mm-yyyy") ;
            
            ArrayList <InvoiceLine> headerLines = new ArrayList <InvoiceLine> () ;  //getting the specific lines for this invoice header
            
            int k = 0, i ;
            for (i = k ; i<lines.size() ; i++)
            {
                if (Integer.parseInt(lineArray[0]) == lines.get(i).getNum())
                    headerLines.add(lines.get(i)) ;
                
                else k = i ; //start next iteration where you left off
                    
            }
             
            headers.add(new InvoiceHeader (Integer.parseInt(lineArray[0]),lineArray[1], lineArray[2],headerLines ));
        }
        
        
        
        bufferedReader.close(); 
        
        return headers;
    }
    
    public void writeFile(ArrayList<InvoiceHeader> headers) throws FileNotFoundException , InvalidPathException, IOException
    {
        
   
       //checking if path that holds the file exists, without file name 
       String dir = headerFile.getParent() ;
       File fileLocation = new File (dir) ;
       
       if (!fileLocation.exists())
       {
           throw new FileNotFoundException (dir + " wasn't found!") ;
       }   
       dir = lineFile.getParent() ;
       fileLocation = new File (dir) ;
       
       if (!fileLocation.exists())
       {
           throw new FileNotFoundException (dir+ " wasn't found!") ;
       }
       
       //checking correct file extension extension
       
       if(!headerFile.toPath().toString().endsWith("csv") ) 
       {
          throw new InvalidPathException (headerFile.toPath().toString(),"Wrong file extension. Only csv allowed");
       } else if (!lineFile.toPath().toString().endsWith("csv") )
       {
           throw new InvalidPathException (lineFile.toPath().toString(),"Wrong file extension. Only csv allowed");
       }
       
        //Writing the headers..
        //
        //
         
        FileWriter fileWriter = new FileWriter(headerFile);        
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        
        for (int i = 0 ; i<headers.size() ; i++)
        {
            
            bufferedWriter.write(headers.get(i).toFileFomrat());
            bufferedWriter.newLine() ;
            
        }
        
        bufferedWriter.close () ;
        
        //Writing the lines..
        //
        //

        fileWriter = new FileWriter(lineFile);        
        bufferedWriter = new BufferedWriter(fileWriter);
        
        for (int i = 0 ;i<headers.size() ; i++)
        {
         InvoiceHeader header = headers.get(i) ;
        for (int j = 0 ; j<header.getInvLines().size() ; j++)
        {
            bufferedWriter.write(header.getInvLines().get(j).toFileFomrat());
            bufferedWriter.newLine() ;
        }
        
    }
      bufferedWriter.close () ;
    }
    
   

}
