import java.awt.BorderLayout; 
import java.awt.Component;
import java.awt.EventQueue; 
import java.awt.Font;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon; 
import javax.swing.JFrame;
import javax.swing.JLabel; 
import javax.swing.JList;
import javax.swing.JPanel; 
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder; 
import javax.swing.JComboBox;

public class CBox extends JFrame{
	
	private JPanel contentPane;	private JLabel picture;
	private JComboBox comboBox;	private JComboBox comboBox2; private ImageIcon[] images;
	private String[] petStrings = {"Bear", "bird", "cat", "fox", "puppy", "rabbit", "sheep"};
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CBox frame = new CBox();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public CBox() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
        
		images = new ImageIcon[petStrings.length];
        Integer[] intArray = new Integer[petStrings.length];
        for (int i = 0; i < petStrings.length; i++) {
            intArray[i] = new Integer(i);
            images[i] = createImageIcon("images/" + petStrings[i] + ".gif");
            if (images[i] != null) {
                images[i].setDescription(petStrings[i]);
            }
        }
		
        // ComboBox 1 (with custom renderer)
        comboBox = new JComboBox(intArray);
		ComboBoxRenderer renderer= new ComboBoxRenderer();
		comboBox.setRenderer(renderer);
		comboBox.setMaximumRowCount(3);
		comboBox.setSelectedIndex(4);
        comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox)arg0.getSource();
				int petName = (int)cb.getSelectedItem();
				ImageIcon icon = createImageIcon("images/" + petStrings[petName] + ".gif");
				picture.setIcon(icon);
				picture.setToolTipText("A drawing of a " + petStrings[petName].toLowerCase());
				if (icon != null) {
					picture.setText(null);
				} else {
					picture.setText("Image not found");
				}
			}
		});
		contentPane.add(comboBox, BorderLayout.NORTH);
		
		// ComboBox 2 (editable)
		comboBox2 = new JComboBox(petStrings);
		comboBox2.setSelectedIndex(4);
       	comboBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox)arg0.getSource();
				String petName = (String)cb.getSelectedItem();
		        
				ImageIcon icon = createImageIcon("images/" + petName + ".gif");
				picture.setIcon(icon);
				picture.setToolTipText("A drawing of a " + petName.toLowerCase());
				if (icon != null) {
					picture.setText(null);
				} else {
					picture.setText("Image not found");
				}
			}
		});
		contentPane.add(comboBox2, BorderLayout.SOUTH);
		comboBox2.setEditable(true);
		
		picture = new JLabel("");
		ImageIcon icon = createImageIcon("images/" + petStrings[comboBox.getSelectedIndex()] + ".gif");
		picture.setIcon(icon);
		picture.setToolTipText("A drawing of a " + petStrings[comboBox.getSelectedIndex()].toLowerCase());
		contentPane.add(picture, BorderLayout.CENTER);
	}
	
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = CBox.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    // Custom renderer for the ComboBox
    class ComboBoxRenderer extends JLabel implements ListCellRenderer {
    	private Font uhOhFont;
    	
    	public ComboBoxRenderer() {
    		setOpaque(true);
    		setHorizontalAlignment(CENTER);
    		setVerticalAlignment(CENTER);
    	}
    	
    	public Component getListCellRendererComponent(JList list,
                    								  Object value,
                    								  int index,
                    								  boolean isSelected,
                    								  boolean cellHasFocus) {
    		int selectedIndex = ((Integer)value).intValue();

    		if (isSelected) {
    			setBackground(list.getSelectionBackground());
    			setForeground(list.getSelectionForeground());
    		} else {
    			setBackground(list.getBackground());
    			setForeground(list.getForeground());
    		}
    		
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
    	
    	protected void setUhOhText(String uhOhText, Font normalFont) {
    		if (uhOhFont == null) { //lazily create this font
    			uhOhFont = normalFont.deriveFont(Font.ITALIC);
    		}
    		setFont(uhOhFont);
    		setText(uhOhText);
    	}
    }	
 }