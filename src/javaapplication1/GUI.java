package javaapplication1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener{
    JButton button, b1, b2;
    HuffmanEncoder encoder;
    JTextField txt;
    String filePath = "";

    GUI() {

        b1 = new JButton("COMPRESS");
        b1.addActionListener(this);
        b1.setBounds(25, 250, 200, 50);
        b1.setFont(new Font("Comic Sans", Font.PLAIN, 15));
        b1.setFocusable(false);
        b1.setForeground(new Color(0,0,0));
        b1.setBackground(new Color(158,150,146));

        b2 = new JButton("DECOMPRESS");
        b2.addActionListener(this);
        b2.setBounds(250, 250, 200, 50);
        b2.setFont(new Font("Comic Sans", Font.PLAIN, 15));
        b2.setFocusable(false);
        b2.setForeground(new Color(0,0,0));
        b2.setBackground(new Color(158,150,146));

        button = new JButton("Select File");
        button.addActionListener(this);
        button.setBounds(140, 140, 200, 100);
        button.setFont(new Font("MV Boli", Font.ITALIC, 30));
        button.setFocusable(false);
        button.setForeground(new Color(0,0,0));
        button.setBackground(new Color(158,150,146));

        txt = new JTextField();
        //txt.setPreferredSize(new Dimension(250,40));
        txt.setBounds(50, 350, 400, 50);


        JLabel label = new JLabel();
        label.setText("SHORText");

        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("MV Boli",Font.BOLD,50));

        this.setTitle("SHORText");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500, 500);
        this.setLocation(400, 100);
        this.setVisible(true);
        this.add(button);
        this.add(b1);
        this.add(b2);
        this.add(txt);
        this.getContentPane().setBackground(new Color(181,179,178));
        this.add(label);




    }
    @Override

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button){
            JFileChooser file = new JFileChooser();
            file.setCurrentDirectory(new File("."));
            int response = file.showOpenDialog(null); //open file

            if(response == JFileChooser.APPROVE_OPTION){
                filePath = (file.getSelectedFile().getAbsolutePath());
                encoder = new HuffmanEncoder(filePath);
                txt.setText(filePath);
            }

            System.out.println("____________________________________________________________");
            encoder.tree.printCharFrequencies(encoder.tree.root);
            System.out.println("____________________________________________________________");
        }
        if(e.getSource()==b1){
            encoder.makeCompressedFile();
            JOptionPane.showMessageDialog(null, "Compressed.......");
            txt.setText(filePath.substring(0, filePath.length()-4) + "_compressed" + ".txt");
        }
        if(e.getSource()==b2){
            encoder.makeDecompressedFile();
            JOptionPane.showMessageDialog(null, "DE---Compressed.......");
            txt.setText(filePath.substring(0, filePath.length()-4) + "_decompressed.txt");


        }
    }
}