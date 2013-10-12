
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * @author khanov
 *
 */
public class CustomComboBoxDemo extends JPanel {
	
	private JPanel pnlComboBoxes; // A panel to display different ComboBoxes (uses CardLayout)
	final static String[] comboBoxTypes = {"Choose ComboBox...", "Simple JComboBox", "Editable JComboBox", "Multi-Selection JList", "JComboBox with Custom Renderer"};
	
	ImageIcon[] images;
    String[] petStrings = {"Bird", "Cat", "Dog", "Rabbit", "Pig"};
    String[] plantsStrings = { "Flower", "Palm", "Vine", "Bulb"};
 
    public CustomComboBoxDemo() {
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	
    	// Creates the menu
    	JComboBox comboBoxMenu = new JComboBox(comboBoxTypes);
    	comboBoxMenu.addItemListener(new ItemListener() {
    		public void itemStateChanged(ItemEvent evt) {
    			CardLayout cl = (CardLayout)(pnlComboBoxes.getLayout());
    			cl.show(pnlComboBoxes, (String)evt.getItem());
    		}
    	});
        add(comboBoxMenu);
        
        // Creates different ComboBoxes ("cards" for the CardLayout)
        JPanel card1 = createSimpleComboBoxCard();
        JPanel card2 = createEditableComboBoxCard();
        JPanel card3 = createMultiSelectionListCard();
        JPanel card4 = createComboBoxWithCustomRendererCard();
        
        // Creates a panel to display different ComboBoxes (uses CardLayout, shows "cards")
        pnlComboBoxes = new JPanel(new CardLayout(0, 0));
        pnlComboBoxes.add(new JPanel(), comboBoxTypes[0]); // Blank
        pnlComboBoxes.add(card1, comboBoxTypes[1]); // Simple ComboBox
        pnlComboBoxes.add(card2, comboBoxTypes[2]); // Editable ComboBox
        pnlComboBoxes.add(card3, comboBoxTypes[3]); // Multiple selection List
        pnlComboBoxes.add(card4, comboBoxTypes[4]); // ComboBox with images inside it
        add(pnlComboBoxes);
    }    
    
    
    /**
     * Creates a simple JComboBox with the ability to set
     * current selected index, using the TextField and Button. 
     * @return JPanel - a "card" to display in the CardLayout
     */
    private JPanel createSimpleComboBoxCard() {
    	final JComboBox simpleComboBox = new JComboBox(petStrings);
        simpleComboBox.setSelectedIndex(2);
        
        // A TextField and Button to submit new selected index
        final JTextField textField = new JTextField();
        textField.setColumns(10);
        JButton submitButton = new JButton("Set New Selected Index");
        submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int newIndex = Integer.parseInt(textField.getText());
				if (newIndex > petStrings.length - 1) {
					System.out.println("Error: invalid input."); // handle "out of bound" error
					return;
				}
				simpleComboBox.setSelectedIndex(newIndex);
				textField.setText(null); // clear text after submission
			}
        });
        
        // A container to hold the TextField and Button
        JPanel submissionPanel = new JPanel();
        submissionPanel.add(textField);
        submissionPanel.add(submitButton);
        
        // Packing everything up into a card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.add(simpleComboBox);
        card.add(submissionPanel);
    	return card;
    }
    
    private JPanel createEditableComboBoxCard() {
    	JComboBox editableComboBox = new JComboBox(plantsStrings);
        editableComboBox.setEditable(true);
        
        JPanel card = new JPanel();
        card.add(editableComboBox);
        return card;
    }
    
    private JPanel createMultiSelectionListCard() {
    	JList multiSelectionList = new JList(plantsStrings);
        
        JPanel card = new JPanel();
        card.add(multiSelectionList);
        return card;
    }
    
    private JPanel createComboBoxWithCustomRendererCard() {
    	//Load the pet images and create an array of indexes.
        images = new ImageIcon[petStrings.length];
        Integer[] intArray = new Integer[petStrings.length];
        for (int i = 0; i < petStrings.length; i++) {
            intArray[i] = new Integer(i);
            images[i] = createImageIcon("images/" + petStrings[i] + ".gif");
            if (images[i] != null) {
                images[i].setDescription(petStrings[i]);
            }
        }
        
        JComboBox comboBoxWithCustomRenderer = new JComboBox(intArray);
        comboBoxWithCustomRenderer.setMaximumRowCount(3);
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        comboBoxWithCustomRenderer.setRenderer(renderer);
        
        JPanel card = new JPanel();
        card.add(comboBoxWithCustomRenderer);
        return card;
    }
    

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = CustomComboBoxDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CustomComboBoxDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new CustomComboBoxDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    /*
     * A renderer for a combo box must implement the ListCellRenderer interface. 
     * A combo box's editor must implement ComboBoxEditor.
     */
    class ComboBoxRenderer extends JLabel implements ListCellRenderer {
        private Font uhOhFont;

        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        /*
         * This method finds the image and text corresponding
         * to the selected value and returns the label, set up
         * to display the text and image.
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            //Get the selected index. (The index param isn't
            //always valid, so just use the value.)
            int selectedIndex = ((Integer)value).intValue();

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            //Set the icon and text.  If icon was null, say so.
            ImageIcon icon = images[selectedIndex];
            String pet = petStrings[selectedIndex];
            setIcon(icon);
            if (icon != null) {
                setText(pet);
                setFont(list.getFont());
            } else {
                setUhOhText(pet + " (no image available)", list.getFont());
            }

            return this;
        }

        //Set the font and text when no image was found.
        protected void setUhOhText(String uhOhText, Font normalFont) {
            if (uhOhFont == null) { //lazily create this font
                uhOhFont = normalFont.deriveFont(Font.ITALIC);
            }
            setFont(uhOhFont);
            setText(uhOhText);
        }
    }
}
