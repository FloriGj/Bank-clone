            package gui;
import dbObjects.User;

import javax.swing.*;

public abstract class MainFrame extends JFrame {
    User user;
    
    public MainFrame(String title) {
        initalize(title);
    }

    public MainFrame(String title, User user) {
        this.user = user;
        initalize(title);
    }

    private void initalize(String title) {
        setTitle(title);   
        setSize(420, 600);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);      
        setLayout(null);   
        setResizable(false);
        setLocationRelativeTo(null); //centers gui in middle of screen
        addGuiComponents();                                                     
    }

    protected abstract void addGuiComponents();
    //abstract allow other inheriting classes to define what this function will do
}
