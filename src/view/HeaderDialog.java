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

/**
 *
 * @author Khaled
 */
public class HeaderDialog extends JDialog  {
    
    private HomeInvoiceFrame parent ;
    private JLabel dateLabel, nameLabel ;
    private JTextField dateField, nameField ;
    private JButton okButton, cancelButton ;
    public boolean okClicked = false ;
    

    public HeaderDialog (HomeInvoiceFrame inv) {
        
        parent = inv ;

        setLayout(new GridLayout(3, 2));  //3 rows and  2 columns layout
        setMinimumSize(new Dimension(300,100));
        setLocationRelativeTo(null) ;
        
        initComponents () ;
        
        setModalityType(Dialog.DEFAULT_MODALITY_TYPE.APPLICATION_MODAL);
        
    }
    
    private void initComponents () {

    dateLabel = new JLabel("Date") ;    
    dateLabel.setHorizontalAlignment(JLabel.CENTER);
    add(dateLabel) ;

    dateField = new JTextField() ;
    add(dateField) ;
    
    nameLabel = new JLabel("Customer") ;
    nameLabel.setHorizontalAlignment(JLabel.CENTER);
    add(nameLabel) ;
    
    nameField = new JTextField() ;
    add(nameField) ;
    
        
    okButton = new JButton ("OK") ;
    okButton.addActionListener(parent.controller);
    okButton.setActionCommand("OKInvoice");
    add(okButton) ;

    cancelButton = new JButton ("Cancel") ;
    cancelButton.addActionListener(parent.controller);
    cancelButton.setActionCommand("CancelInvoice");
    add(cancelButton) ;
    

    pack();
    }
    
    public InvoiceHeader getData ()
    {
        return new InvoiceHeader (parent.getjTable1().getRowCount()+1, dateField.getText(),nameField.getText(),null) ; 
        
    }
    
    
}
