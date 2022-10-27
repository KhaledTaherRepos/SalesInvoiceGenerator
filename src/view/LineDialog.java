/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import model.InvoiceHeader;
import model.InvoiceLine;

/**
 *
 * @author Khaled
 */
public class LineDialog extends JDialog  {
    
    private HomeInvoiceFrame parent ;
    private JLabel nameLabel, priceLabel, countLabel ;
    private JTextField nameField, priceField, countField ;
    private JButton okButton, cancelButton ;
    
    
    public LineDialog (HomeInvoiceFrame inv) {
        
        parent = inv ;

        setLayout(new GridLayout(4, 2));  //3 rows and  2 columns layout
        setMinimumSize(new Dimension(300,100));
        setLocationRelativeTo(null) ;
        
        initComponents () ;
        
        setModalityType(Dialog.DEFAULT_MODALITY_TYPE.APPLICATION_MODAL);
        
    }
    
    private void initComponents () {

    nameLabel = new JLabel("Name") ;    
    nameLabel.setHorizontalAlignment(JLabel.CENTER);
    add(nameLabel) ;

    nameField = new JTextField() ;
    add(nameField) ;
    
    priceLabel = new JLabel("Price") ;
    priceLabel.setHorizontalAlignment(JLabel.CENTER);
    add(priceLabel) ;
    
    priceField = new JTextField() ;
    add(priceField) ;
    
    countLabel = new JLabel("Count") ;
    countLabel.setHorizontalAlignment(JLabel.CENTER);
    add(countLabel) ;
    
    countField = new JTextField() ;
    add(countField) ;
    
        
    okButton = new JButton ("OK") ;
    okButton.addActionListener(parent.controller);
    okButton.setActionCommand("OKLine");
    add(okButton) ;

    cancelButton = new JButton ("Cancel") ;
    cancelButton.addActionListener(parent.controller);
    cancelButton.setActionCommand("CancelLine");
    add(cancelButton) ;
    

    pack();
    }
    
    public InvoiceLine getData ()
    {
        return new InvoiceLine(parent.getjTable1().getRowCount(), nameField.getText(),Double.parseDouble(priceField.getText()),Integer.parseInt(countField.getText())) ; 
        
    }
    
    
}