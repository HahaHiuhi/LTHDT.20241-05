package sim;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = -4155452838523112843L;
    static JTextField fieldP;
    static JTextField fieldH;
    static JTextField fieldC;
    JLabel currentPlanLabel;
    JLabel birthDeathLabel;
    static int birthDeathModifier = 0;

    public ControlPanel() {
        
    	setLayout(new GridLayout(12, 1, 5, 5));

        // Labels and text fields for P, H, and C
        JLabel labelP = new JLabel("Plant:");
        fieldP = new JTextField("0");
        JLabel labelH = new JLabel("Herbivore:");
        fieldH = new JTextField("0");
        JLabel labelC = new JLabel("Carnivore:");
        fieldC = new JTextField("0");
        currentPlanLabel = new JLabel("Current Plan: Custom");
        birthDeathLabel = new JLabel("Birth and Death: Balanced");

        labelP.setHorizontalAlignment(JTextField.CENTER);
        labelH.setHorizontalAlignment(JTextField.CENTER);
        labelC.setHorizontalAlignment(JTextField.CENTER);

        // Add key listeners to the text fields
        fieldP.addKeyListener(new FieldListener());
        fieldH.addKeyListener(new FieldListener());
        fieldC.addKeyListener(new FieldListener());

        // Control buttons
       
        JButton helpButton = new JButton("Help");
        JButton choosePlanButton = new JButton("Choose plan ...");
        JButton birthDeathButton = new JButton("Change Birth/Death Rate ...");

        // Add components to the control panel
        add(labelP);
        add(fieldP);
        add(labelH);
        add(fieldH);
        add(labelC);
        add(fieldC);
        add(choosePlanButton);
        add(currentPlanLabel);  
        add(birthDeathButton);
        add(birthDeathLabel);  // Add it to your panel or layout
        add(helpButton);

        setVisible(true);
        
        
        birthDeathButton.addActionListener(new ActionListener(){
            int choose = 0;
            @Override
            public void actionPerformed(ActionEvent e){
                String[][] plan = { { "0", "Balanced"}, { "20", "Higher birth rate"}, { "-20", "Higher death rate"} };

               birthDeathModifier =  Integer.parseInt(plan[choose][0]);
               birthDeathLabel.setText("Birth and Death: " + plan[choose][1]);
                choose = (choose + 1) % 3;
            }
        });
        
        // Help button event listener
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ControlPanel.this, "Help:\nStart - Start the simulation\nPause - Pause the simulation\nResume - Resume the simulation\nQuit - Quit the application\nPlants get energy from sunlight\nHerbivores consume plants\nCarnivores consume herbivores\nThe predators only get 10% energy of their preys");
            }
        });
      

        // Choose plan button event listener
        choosePlanButton.addActionListener(new ActionListener(){
            int choose = 0;
            @Override
            public void actionPerformed(ActionEvent e){
                String[][] plan = { { "100","10","1", "Balanced"}, { "0","100","1", "No plant"}, { "100","10","0", "No Carnivore"} };

                fieldP.setText(plan[choose][0]);
                fieldH.setText(plan[choose][1]);
                fieldC.setText(plan[choose][2]);
                currentPlanLabel.setText("Current plan: " + plan[choose][3]);
                choose = (choose + 1) % 3;
            }
        });

    }

    public class FieldListener extends KeyAdapter implements ActionListener {
        @Override
        public void keyReleased(KeyEvent e) {
            // Get the source of the event (the specific text field that triggered the event)
            Object source = e.getSource();
            
            // Only allow numbers in the text fields
            char keyChar = e.getKeyChar();

            // Check if the key pressed is not a digit and not backspace
            

            // If the text field is empty after a key event, set it to "0"
            if (source == fieldP || source == fieldH || source == fieldC) {
                JTextField field = (JTextField) source;
                String curr = field.getText();
                System.out.println(curr);
                // If the field is empty, set it to "0"
                if (!Character.isDigit(keyChar) && keyChar != KeyEvent.VK_BACK_SPACE) {
                	if(field.getCaretPosition() == 1)  field.setText("0");
                	else field.setText(curr.substring(0, curr.length() - 1));; // Consume the event to prevent invalid input
                }
                if (field.getText().isEmpty()) {
                    field.setText("0");
                } 
                // If the user starts typing, the field should show the new number
                else if (field.getText().equals("0" + keyChar) && Character.isDigit(keyChar) ){
                    field.setText(Character.toString(keyChar));
                }
            }

            // Update the label if any of the fields have text
            if (fieldP.getText().isEmpty() || fieldH.getText().isEmpty() || fieldC.getText().isEmpty()) {
                currentPlanLabel.setText("Current plan: Custom");
            } 
        }

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			JTextField field = (JTextField) source;
			field.setCaretPosition(field.getText().length());
			
		}
    }



    public static void main(String[] args) {
        new ControlPanel();
    }
}
