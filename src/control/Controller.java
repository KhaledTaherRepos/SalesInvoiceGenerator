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
        silenetLoadFiles();
    }

    public Controller(HomeInvoiceFrame frame) {
        this();
        jFrame = frame;

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
            /*try {

                fileHandler.writeFile(this.headerInvoices);

                JOptionPane.showMessageDialog(jFrame, "File Saved Successfully. \n", "Attention", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(jFrame, "This error occured when trying to save your file: \n" + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }*/
        } 
        ///////////////// create invoice case //////////////
        
        else if (action.equals("Create New Invoice")) {
            //ask user to enter number of lines in the invoice and create corresponding rows in the item table with id bigger than the last invoice id.
            String val = JOptionPane.showInputDialog(jFrame, "Enter the number of items in the new invoice: ", "Attention", JOptionPane.INFORMATION_MESSAGE) ;
            int userItemNumber = 0 ;
            if (val != null)  userItemNumber = Integer.parseInt(val) ;   //make sure user entered correct number
            else return ;
            
            if (userItemNumber > 0) {
                Integer size = this.headerInvoices.size();
                jFrame.resetHeaderFields(String.valueOf(size + 1), userItemNumber);
            }
            

        } ///////////////// delete invoice case //////////////
        else if (action.equals("Delete Invoice")) {
            int index = jFrame.getSelectedItemIndex();
            this.headerInvoices.remove(index);         //remvove the item with the selected index from the model
            updateTableView("Headers");

        } ///////////////// edit-create invoice case //////////////
        else if (action.equals("Save")) {

            loadInvoiceFromView();

            updateTableView("Headers");

        } ///////////////// cancel invoice case //////////////
        else if (action.equals("Cancel")) {
            jFrame.resetHeaderFields("", 4);
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
                JOptionPane.showMessageDialog(jFrame, "This error occured when trying to load your file: \n" + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return  ;
            }

        }
    }

    private void loadInvoiceFromView() {    //gets the data in the lines table from the view and load it to the model
        Date date = jFrame.getjDateChooser1().getCalendar().getTime();
        String dateString = new SimpleDateFormat("dd-MM-yyyy").format(date);
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

        InvoiceHeader newInvoice = new InvoiceHeader(num, dateString, customer, invLines); //the new record

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
            JOptionPane.showMessageDialog(jFrame, "This error occured when trying to load your file: \n", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void updateTableView(String table) //updates the given table in the view
    {
        if (table.equals("Headers")) {
            jFrame.updateTable1Data(this.headerInvoices);

        } else if (table.equals("Lines")) {
            int index = jFrame.getSelectedItemIndex();
            if (index < this.headerInvoices.size()) {
                InvoiceHeader SelectedHeaderInvoice = this.headerInvoices.get(index);
                jFrame.updateHeaderInfo(SelectedHeaderInvoice);
                jFrame.updateTable2Data(SelectedHeaderInvoice.getInvLines());
            }
        }
    }
}
