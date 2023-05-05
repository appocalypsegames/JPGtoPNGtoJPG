package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageConverter extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
    private JTextField inputFileField;
    private JButton selectButton;
    private JButton jpgButton;
    private JButton pngButton;

    public ImageConverter() {
        super("Image Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 120);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(panel, BorderLayout.CENTER);

        inputFileField = new JTextField(20);
        panel.add(inputFileField);

        selectButton = new JButton("Select image file");
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(panel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    inputFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        panel.add(selectButton);

        jpgButton = new JButton("Convert to JPG");
        jpgButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertTo("jpg");
            }
        });
        panel.add(jpgButton);

        pngButton = new JButton("Convert to PNG");
        pngButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertTo("png");
            }
        });
        panel.add(pngButton);

        setVisible(true);
    }

    private void convertTo(String format) {
        String inputFilePath = inputFileField.getText();
        if (inputFilePath.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Select an image file first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        File input = new File(inputFilePath);
        try {
            BufferedImage image = ImageIO.read(input);
            String outputFilePath = input.getAbsolutePath().replaceFirst("[.][^.]+$", "") + "." + format;
            File output = new File(outputFilePath);
            ImageIO.write(image, format, output);
            JOptionPane.showMessageDialog(panel, "The image has been successfully converted and saved in " + outputFilePath, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(panel, "Error converting the image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ImageConverter();
    }
}