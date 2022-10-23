/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.HomeInvoiceFrame;

/**
 *
 * @author Khaled
 */
public class Controller implements ActionListener, ListSelectionListener {

    private HomeInvoiceFrame jFrame;
    private ArrayList<InvoiceHeader> headerInvoices = new ArrayList<InvoiceHeader>();
    private FileOperations fileHandler;

    public Controller() {
        fileHandler = new FileOperations();
    }

    public Controller(HomeInvoiceFrame frame) {
        this();
        jFrame = frame;

    }

    @Override
    public void valueChanged(ListSelectionEvent event) {   //handle selected invoice header action

    }

    @Override
    public void actionPerformed(ActionEvent e) {  // this function handles all the actions on the buttons/menus (load file - save file - create invoice- delete invoice - save invoice - cancel invoice )

        String action = e.getActionCommand();

        ///////////////// load file case //////////////
        if (action.equals("Load File")) {

           
        } ///////////////// save file case //////////////
        else if (action.equals("Save File")) {

      
        } 
        ///////////////// create invoice case //////////////
        
        else if (action.equals("Create New Invoice")) {
            
            
        } ///////////////// delete invoice case //////////////
        
        else if (action.equals("Delete Invoice")) {
           
        } ///////////////// edit-create invoice case //////////////
        else if (action.equals("Save")) {

      
        } ///////////////// cancel invoice case //////////////
        else if (action.equals("Cancel")) {
            
        }

    }

    public ArrayList<InvoiceHeader> getHeaderInvoices() {
        return headerInvoices;
    }

   
}
