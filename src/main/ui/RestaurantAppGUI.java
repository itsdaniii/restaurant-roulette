package ui;

import model.Event;
import model.EventLog;
import model.LogPrinter;
import model.Restaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Restaurant Tracker App GUI
// Logo image was a royalty-free vector from:
//      https://www.vectorstock.com/royalty-free-vector/red-restaurant-icons-vector-1891120
public class RestaurantAppGUI extends JFrame implements LogPrinter {
    private static final String JSON_STORE = "./data/restList.json";
    private Restaurant rest;
    private RestaurantList restList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private JPanel controlPanel;
    private JSplitPane controlPanelSplitPane;
    private JSplitPane resultsViewSplitPane;
    private JPanel restaurantRoulettePanel;
    private JPanel results;
    private JList restListOptions;
    private DefaultListModel<Restaurant> model;
    private JLabel display;
    private BufferedImage logo;
    private JLabel logoLabel;

    // EFFECTS: Runs the restaurant application
    // This method references JsonSerializationDemo for persistence functionality
    // This method references YouTube tutorial for JSwing functions: https://www.youtube.com/watch?v=5o3fMLPY7qY
    // This method references logo/graphic guidance here: https://www.javacodex.com/More-Examples/2/1
    public RestaurantAppGUI() throws IOException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frameSetUp();

        model = new DefaultListModel<>();
        display = new JLabel();
        display.setFont(new Font("Arial", Font.PLAIN, 18));

        logo = ImageIO.read(new File("./data/new-logo-black.png"));
        logoLabel = new JLabel(new ImageIcon(logo));
        logoLabel.setSize(new Dimension(320,320));

        initialize();

        setUpControlPanelSplitPane();
        frame.add(controlPanelSplitPane, BorderLayout.WEST);

        setUpResultsViewSplitPane();
        frame.add(resultsViewSplitPane, BorderLayout.CENTER);

        frame.setVisible(true);

    }

    // Helper method to set up JFrame
    // This method references WindowListener guidance:
    //          https://stackoverflow.com/questions/12210972/setdefaultcloseoperation-to-show-a-jframe-instead
    // MODIFIES: This
    // EFFECTS: Creates and sets up JFrame settings as desired
    private void frameSetUp() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame();
        frame.setSize(new Dimension(1500,1000));
        frame.setTitle("Restaurant Roulette");

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    // Helper method to print EventLog to console
    // This method references AlarmSystem
    // EFFECTS: Prints EventLog of Events to console
    @Override
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    // Helper method to set up left side of GUI
    // This method references vertical split pane guidance here:
    //          http://www.java2s.com/Tutorial/Java/0240__Swing/Createatopbottomsplitpane.htm
    // MODIFIES: This
    // EFFECTS: Sets up vertical split pane Control Panel with Button Panel on top
    //          and Restaurant Roulette Panel on bottom
    private void setUpControlPanelSplitPane() {
        controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        controlPanel.setLayout(new GridLayout(3, 1));
        controlPanel.setSize(new Dimension(500,1000));
        addButtonPanel();

        restaurantRoulettePanel = new JPanel();
        restaurantRoulettePanel.setBorder(BorderFactory.createEmptyBorder(40,10,10,40));
        restaurantRoulettePanel.setLayout(new GridLayout(5,10));
        restaurantRoulettePanel.add(new JButton(new RestaurantRouletteAction()));
        restaurantRoulettePanel.setSize(new Dimension(500,1000));

        controlPanelSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                controlPanel,restaurantRoulettePanel);
    }

    // Helper method to set up Results View split pane (right side of GUI)
    // This method references YouTube tutorial for JList: https://www.youtube.com/watch?v=KOI1WbkKUpQ
    // MODIFIES: This
    // EFFECTS: Sets up horizontal split pane Results View with list of all restaurants in list and results display
    //          Enables users to view restaurant details when a restaurant is selected
    private void setUpResultsViewSplitPane() {
        results = new JPanel();
        results.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        results.setLayout(new GridLayout(1, 1));
        results.setSize(750,1000);

        restListOptions = new JList(model);
        restListOptions.getSelectionModel().addListSelectionListener(e -> {
            Restaurant rest = (Restaurant) restListOptions.getSelectedValue();
            try {
                display.setText("<html> Name: " + rest.getRestName() + "<br/> Cuisine: " + rest.getCuisineType()
                        + "<br/> Neighbourhood: " + rest.getNeighbourhood() + "<br/> Has Patio?: "
                        + rest.getHasPatio() + "<br/> Price Range: " + rest.getPriceRange());
            } catch (NullPointerException exp) {
                display.setText("");
            }
        });
        results.add(display);

        resultsViewSplitPane = new JSplitPane();
        resultsViewSplitPane.setLeftComponent(new JScrollPane(restListOptions));
        resultsViewSplitPane.setRightComponent(results);
    }

    // This method references AlarmSystem
    // MODIFIES: This, controlPanel
    // EFFECTS: Creates function buttons and adds them to the Control Panel
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.setLayout(new GridLayout(4, 2));
        buttonPanel.add(new JButton(new AddRestaurantAction()));
        buttonPanel.add(new JButton(new RemoveRestaurantAction()));
        buttonPanel.add(new JButton(new LoadDataAction()));
        buttonPanel.add(new JButton(new SaveDataAction()));
        controlPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    // MODIFIES: This
    // EFFECTS: Initializes a new RestaurantList with one Restaurant in it
    // This method references the TellerApp
    private void initialize() {
        restList = new RestaurantList();
//        rest = new Restaurant("ahn and chi", "vietnamese",
//                "mount pleasant", "yes", "$$");
//        restList.addRestaurant(rest);
//        model.addElement(rest);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        display.add(logoLabel);
    }

    // Add Restaurant Button Functionality
    private class AddRestaurantAction extends AbstractAction {

        //  Sets button Name
        AddRestaurantAction() {
            super("Add Restaurant");
        }

        // This method references http://www.java2s.com/Tutorials/Java/Swing_How_to/JOptionPane/
        //                          Create_Multiple_input_in_JOptionPane_showInputDialog.htm
        // MODIFIES: This
        // EFFECTS: Takes in user input for all parameters relating to the new Restaurant, initializes
        //          add Restaurant function if not already added, and displays confirmation or failed message
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField nameInput = new JTextField(100);
            JTextField cuisineInput = new JTextField(100);
            JTextField neighbourhoodInput = new JTextField(100);
            JTextField patioInput = new JTextField(3);
            JTextField priceInput = new JTextField(4);

            JPanel newRestDialogBox = new JPanel();
            setUpNewRestDialog(nameInput, cuisineInput, neighbourhoodInput, patioInput, priceInput, newRestDialogBox);

            int result = JOptionPane.showConfirmDialog(null, newRestDialogBox,
                    "Please Enter Restaurant Details", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                if (modifyListsAddRest(nameInput, cuisineInput, neighbourhoodInput, patioInput, priceInput)) {
                    display.add(logoLabel);
                    display.setText(nameInput.getText() + " has been added!");
                } else {
                    display.add(logoLabel);
                    display.setText(nameInput.getText() + " is already in the list "
                            + "- sounds like you need to eat there ASAP!");
                }
            }
        }

        // MODIFIES: newRestDialogBox
        // EFFECTS: Sets up dialog box for the user to input new Restaurant details
        private void setUpNewRestDialog(JTextField nameInput, JTextField cuisineInput, JTextField neighbourhoodInput,
                                        JTextField patioInput, JTextField priceInput, JPanel newRestDialogBox) {
            newRestDialogBox.setLayout(new BoxLayout(newRestDialogBox,BoxLayout.Y_AXIS));

            newRestDialogBox.add(logoLabel);
            newRestDialogBox.add(new JLabel("Restaurant Name:"));
            newRestDialogBox.add(nameInput);
            newRestDialogBox.add(Box.createVerticalStrut(15)); // a spacer
            newRestDialogBox.add(new JLabel("Cuisine Type:"));
            newRestDialogBox.add(cuisineInput);
            newRestDialogBox.add(Box.createVerticalStrut(15)); // a spacer
            newRestDialogBox.add(new JLabel("Neighbourhood:"));
            newRestDialogBox.add(neighbourhoodInput);
            newRestDialogBox.add(Box.createVerticalStrut(15)); // a spacer
            newRestDialogBox.add(new JLabel("Patio? (yes/no):"));
            newRestDialogBox.add(patioInput);
            newRestDialogBox.add(Box.createVerticalStrut(15)); // a spacer
            newRestDialogBox.add(new JLabel("Price Range?: "));
            newRestDialogBox.add(priceInput);
        }

        // MODIFIES: restList, restListOptions
        // EFFECTS: If restName does not already exist in the list, this method updates restList (console)
        //      and restListOptions (GUI) for new Restaurant input and returns true if successfully added;
        //      else, it is a duplicate and will return false
        private Boolean modifyListsAddRest(JTextField nameInput, JTextField cuisineInput, JTextField neighbourhoodInput,
                                        JTextField patioInput, JTextField priceInput) {
            Restaurant rest = new Restaurant(nameInput.getText(), cuisineInput.getText(),
                    neighbourhoodInput.getText(), patioInput.getText(), priceInput.getText());
            restList.addRestaurant(rest);

            Boolean duplicateRest = false;
            Integer restListOptionsSize = model.getSize();
            for (int i = 0; i < restListOptionsSize; i++) {
                if (nameInput.getText().equals(model.get(i).getRestName())) {
                    duplicateRest = true;
                }
            }
            if (!duplicateRest) {
                model.addElement(rest);
            }
            return !duplicateRest;
        }
    }

    // Remove Restaurant Button Functionality
    private class RemoveRestaurantAction extends AbstractAction {

        //  Sets button Name
        RemoveRestaurantAction() {
            super("Remove Restaurant");
        }

        // MODIFIES: restList, restListOptions
        // EFFECTS: Removes restaurant from restList and display based on user selection
        //          and displays confirmation message
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedName = restListOptions.getSelectedValue().toString();
            Integer selectedIndex = restListOptions.getSelectedIndex();

            restListOptions.clearSelection();
            restList.removeRestaurant(selectedName);
            model.removeElementAt(selectedIndex);

            System.out.println(selectedName + " has been removed from the list.");
            display.add(logoLabel);
            display.setText(selectedName + " has been removed!");
        }
    }

    // Restaurant Roulette Functionality (randomize function)
    // Prints a random Restaurant name and details within the restList
    private class RestaurantRouletteAction extends AbstractAction {

        // Sets button name
        RestaurantRouletteAction() {
            super("Play Restaurant Roulette");
        }

        // MODIFIES: display
        // EFFECTS: when button is clicked, displays a Restaurant name and details
        //          based on randomly selected Restaurant index
        public void actionPerformed(ActionEvent e) {
            int randomizeMax = restList.size();
            int r = (int) (Math.random() * randomizeMax);
            String randRestName = restList.viewRestName(r);
            System.out.println("Your random restaurant pick is: " + randRestName + "!");
            display.add(logoLabel);
            display.setText("Your random restaurant pick is: " + randRestName + "!");
            display.setText("<html> Your random restaurant pick is: " + randRestName + "!"
                    + "<br/>" + "<br/> Cuisine: " + restList.viewCuisineType(randRestName)
                    + "<br/> Neighbourhood: " + restList.viewNeighbourhood(randRestName)
                    + "<br/> Has Patio?: " + restList.viewHasPatio(randRestName)
                    + "<br/> Price Range: " + restList.viewPriceRange(randRestName));
        }
    }

    // Loads restList from file
    // This method references the JsonSerializationDemo
    private class LoadDataAction extends AbstractAction {

        // Sets button name
        LoadDataAction() {
            super("Load data");
        }

        // MODIFIES: restList, restListOptions, display
        // EFFECTS: loads restList from file, adds to restListOptions and displays confirmation message
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                restList = jsonReader.read();

                model.clear();
                for (int i = 0; i < restList.size(); i++) {
                    model.addElement(restList.getRest(i));
                }

                System.out.println("Loaded restaurant list from " + JSON_STORE);
                display.add(logoLabel);
                display.setText("Loaded restaurant list!");
            } catch (IOException exp) {
                System.out.println("Unable to read from file: " + JSON_STORE);
                display.add(logoLabel);
                display.setText("Unable to read from file");
            }
        }
    }

    // Saves the restList to file
    // This method references the JsonSerializationDemo
    private class SaveDataAction extends AbstractAction {

        // Sets button name
        SaveDataAction() {
            super("Save data");
        }

        // MODIFIES: this
        // EFFECTS: Saves the restList to file and displays confirmation message
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(restList);
                jsonWriter.close();

                System.out.println("Saved restaurant list to " + JSON_STORE);
                display.add(logoLabel);
                display.setText("Saved restaurant list!");
            } catch (FileNotFoundException exp) {
                System.out.println("Unable to write to file: " + JSON_STORE);
                display.add(logoLabel);
                display.setText("Unable to write to file: " + JSON_STORE);
            }
        }
    }

}
