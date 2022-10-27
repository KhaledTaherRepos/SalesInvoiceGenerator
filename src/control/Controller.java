/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.* ;
import view.* ;

/**
 *
 * @author Khaled
 */
public class Controller implements ActionListener, ListSelectionListener {

    private HomeInvoiceFrame jFrame;
    private ArrayList<InvoiceHeader> headerInvoices = new ArrayList<InvoiceHeader>();
    private FileOperations fileHandler;
    HeaderDialog headerDialog ;
    LineDialog lineDialog ;

    public Controller() {
        fileHandler = new FileOperations();
    }

    public Controller(HomeInvoiceFrame frame) {
        this();
        jFrame = frame;
        silenetLoadFiles();

    }

    @Override
    public void valueChanged(ListSelectionEvent event) {   //handle selected invoice header action

        updateTableView("Lines");
    }

    @Override
    public void actionPerformed(ActionEvent e) {  // this function handles all the actions on the buttons/menus (load file - save file - create invoice- delete invoice - save invoice - cancel invoice )

        String action = e.getActionCommand();

        ///////////////// load file case //////////////
        if (action.equals("Load File")) {

            if (loadFilesFromUser()) {
                updateTableView("Headers");
            } 
        } ///////////////// save file case //////////////
        else if (action.equals("Save File")) {

            saveToDisk () ;
   
        } 
        ///////////////// create invoice case //////////////
        
        else if (action.equals("Create New Invoice")) {
      
        headerDialog = new HeaderDialog(jFrame) ;
        headerDialog.setVisible(true);
   

        } ///////////////// delete invoice case //////////////
        else if (action.equals("Delete Invoice")) {
            int index = jFrame.getSelectedItemIndex();
            this.headerInvoices.remove(index);         //remvove the item with the selected index from the model
            updateTableView("Headers");

        } ///////////////// edit-create invoice case //////////////
        else if (action.equals("New Item")) {

          
            lineDialog = new LineDialog (jFrame) ;
            lineDialog.setVisible(true);
          

        } ///////////////// cancel invoice case //////////////
        else if (action.equals("Delete Item")) {
           // jFrame.resetHeaderFields("", 4);
        }
        
        else if (action.equals("OKInvoice")) 
        {
           headerInvoices.add(headerDialog.getData()) ;
           updateTableView("Headers");
           jFrame.keepRowSelected(1, headerInvoices.size()-1) ;
           headerDialog.dispose() ;
           
           
        }
        
        else if (action.equals("CancelInvoice")) 
        {
           headerDialog.dispose() ;
        }
        
        else if (action.equals("OKLine")) 
        {
            int selectedItem = jFrame.getSelectedItemIndex();
            InvoiceHeader updatedInvoice = headerInvoices.get(selectedItem);
            updatedInvoice.addInvoiceLine(lineDialog.getData());
            headerInvoices.set(selectedItem, updatedInvoice) ;
            lineDialog.dispose();
            jFrame.updateRightPanel(updatedInvoice);
            jFrame.updateTable1Data(headerInvoices);
            jFrame.keepRowSelected(1, selectedItem) ;
            jFrame.keepRowSelected(2, updatedInvoice.getInvLines().size()-1) ;            

           
        }
        else if (action.equals("CancelLine")) 
        {
           lineDialog.dispose();
        }

    }

    public ArrayList<InvoiceHeader> getHeaderInvoices() {
        return headerInvoices;
    }

    private boolean loadFilesFromUser() { //handles loading file flow witht the user
        File tempHeaderFile, tempLineFile;
        JFileChooser chooser = new JFileChooser();

        //reading the headers first..
        //
        //
        JOptionPane.showMessageDialog(jFrame, "Select your header file.", "Attention", JOptionPane.INFORMATION_MESSAGE);
        int res = chooser.showOpenDialog(jFrame);
        tempHeaderFile = chooser.getSelectedFile();

        if (tempHeaderFile == null || res != JFileChooser.APPROVE_OPTION) //continue ony when the user chose file and clicked approve
        {
            return false;
        }

        //then reading the lines..
        //
        //
        JOptionPane.showMessageDialog(jFrame, "Select your line file.", "Attention", JOptionPane.INFORMATION_MESSAGE);

        res = chooser.showOpenDialog(jFrame);
        tempLineFile = chooser.getSelectedFile();

        // validating both files are selected
        if (tempLineFile == null || res != JFileChooser.APPROVE_OPTION) //continue ony when the user chose file and clicked approve
        {
            return false;
        } else {
            FileOperations.setHeaderFile(tempHeaderFile);
            FileOperations.setLineFile(tempLineFile);

            // reading the chosen files
            try {
                this.headerInvoices = fileHandler.readFile();
                return true;

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(jFrame, "This error occured when trying to load your file: \n" + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false ;
            }

        }
    }
    
    private void saveToDisk() { //handles loading file flow witht the user
        File tempHeaderFile, tempLineFile;
        JFileChooser chooser = new JFileChooser();

        //writing the headers first..
        //
        //
        JOptionPane.showMessageDialog(jFrame, "Select your header file destination folder and enter file name.", "Attention", JOptionPane.INFORMATION_MESSAGE);
        int res = chooser.showSaveDialog(jFrame);
        tempHeaderFile = chooser.getSelectedFile();

        if (tempHeaderFile == null || res != JFileChooser.APPROVE_OPTION) //continue ony when the user chose file and clicked approve
        {
            return ;
        }

        //then reading the lines..
        //
        //
        JOptionPane.showMessageDialog(jFrame, "Select your line file destination folder and enter file name..", "Attention", JOptionPane.INFORMATION_MESSAGE);

        res = chooser.showSaveDialog(jFrame);
        tempLineFile = chooser.getSelectedFile();

        // validating both files are selected
        if (tempLineFile == null || res != JFileChooser.APPROVE_OPTION) //continue ony when the user chose file and clicked approve
        {
            return ;
        } else {
            FileOperations.setHeaderFile(tempHeaderFile);
            FileOperations.setLineFile(tempLineFile);

            // reading the chosen files
            try {
                fileHandler.writeFile(this.headerInvoices);
                JOptionPane.showMessageDialog(jFrame, "File saved successfully!", "Attention", JOptionPane.INFORMATION_MESSAGE);               
                return ;

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(jFrame, "This error occured when trying to save your file: \n" + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return  ;
            }

        }
    }

    private void loadInvoiceFromView() {    //gets the data in the lines table from the view and load it to the model
        String date = jFrame.getDate() ;
        String customer = jFrame.getjTextField1().getText();
        Integer num = this.headerInvoices.size();     //set the default value as the size, in case of create new
        String name;
        Double price;
        Integer count;

        ArrayList<InvoiceLine> invLines = new ArrayList<InvoiceLine>();   //reading the data from the table

        for (int i = 0; i < jFrame.getjTable2().getRowCount(); i++) {
            num = Integer.parseInt(jFrame.getjTable2().getModel().getValueAt(i, 0).toString());
            name = jFrame.getjTable2().getModel().getValueAt(i, 1).toString();
            price = Double.parseDouble(jFrame.getjTable2().getModel().getValueAt(i, 2).toString());
            count = Integer.parseInt(jFrame.getjTable2().getModel().getValueAt(i, 3).toString());

            invLines.add(new InvoiceLine(num, name, price, count));
        }

        InvoiceHeader newInvoice = new InvoiceHeader(num, date, customer, invLines); //the new record

        if (num <= this.headerInvoices.size() && num != 0) //id in range of existing invoices, it's edit
        {
            this.headerInvoices.set(num - 1, newInvoice);
            JOptionPane.showMessageDialog(jFrame, "Invoice Changed Successfully. \n", "Attention", JOptionPane.INFORMATION_MESSAGE);
        } else if (num >= this.headerInvoices.size()) //id equals to upper limit, it's a new invoice
        {
            this.headerInvoices.add(newInvoice);
            JOptionPane.showMessageDialog(jFrame, "Invoice Added Successfully. \n", "Attention", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void silenetLoadFiles() { //loads the data of files to the model without displaying them

        try {
            this.headerInvoices = fileHandler.readFile();
        } catch (Exception e) {
                JOptionPane.showMessageDialog(jFrame, "This error occured when trying to load your file: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void updateTableView(String table) //updates the given table in the view
    {
        if (table.equals("Headers")) {
            jFrame.updateTable1Data(this.headerInvoices);

        } else if (table.equals("Lines")) {
            int index = jFrame.getSelectedItemIndex();
            if (index < this.headerInvoices.size()) {
                InvoiceHeader SelectedHeaderInvoice = headerInvoices.get(index);
                jFrame.updateRightPanel(SelectedHeaderInvoice) ;
            }
        }
    }
}
