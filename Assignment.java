import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// INTERFACE
// Contributed by Siti Nur Farah Maisarah (106387)
// Defines the contract for any encoder class.
interface Encodable {
    boolean checkStringValidity(String inputText);
    int countCharacters(String inputText);
    int generateShift();
    String applyCipher(String inputText, int shift);
    void encode();
    String getResultText();
}

// Contributed by Nur Syukrinah (97717) - implements Encodable interface
class Encoded implements Encodable {

    private String inputText;
    private int charCount;      // Stores non-space count after encode()
    private String resultText;  // Stores encoded result after encode()
    private final String groupID = "G02/CS-G07";

    // Default constructor
    // Contributed by Siti Nur Farah Maisarah (106387)
    public Encoded() {}

    // Constructor with input
    // Contributed by Siti Nur Farah Maisarah (106387)
    public Encoded(String inputText) {
        this.inputText = inputText;
    }

    // Getter for groupID
    // Contributed by Bong Ming Meng (103541)
    public String getGroupID() {
        return groupID;
    }

    // Getter for charCount
    // Returns stored non-space character count after encode() is called
    // Contributed by Nur Syukrinah (97717)
    public int getCharCount() {
        return charCount;
    }

    // VALIDATION
    // Contributed by Siti Nur Farah Maisarah (106387)
    // Checks whether the input string contains only:
    //   - Lowercase letters (a-z)
    //   - Digits (0-9)
    //   - Spaces
    // Returns true if all characters are valid, false otherwise.
    @Override
    public boolean checkStringValidity(String inputText) {
        if (inputText == null || inputText.isEmpty()) {
            return false;
        }

        for (int i = 0; i < inputText.length(); i++) {
            char ch = inputText.charAt(i);
            if (!((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == ' ')) {
                return false;
            }
        }
        return true;
    }

    // COUNT CHARACTERS
    // Contributed by Nur Syukrinah (97717)
    // Counts only alphanumeric (non-space) characters in the input.
    @Override
    public int countCharacters(String inputText) {
        int count = 0;

        for (int i = 0; i < inputText.length(); i++) {
            char ch = inputText.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                count++;
            }
        }
        return count;
    }

    // GENERATE SHIFT
    // Contributed by Nashrur Aisyha Hani (102776)
    // Derives a consistent group-specific shift value from the hardcoded
    // groupID using Java's built-in hashCode() method.
    // Math.abs() ensures non-negative value.
    // (% 10) + 1 constrains result to range [1, 10].
    // Same groupID always produces same groupShift — output is traceable.
    @Override
    public int generateShift() {
        int groupShift = (Math.abs(groupID.hashCode()) % 10) + 1;
        return groupShift;
    }

    // APPLY CIPHER
    // Contributed by Bong Ming Meng (103541)
    // Applies a Caesar-style shift cipher to the input string:
    //   - Lowercase letters shifted within a-z (mod 26)
    //   - Digits shifted within 0-9 (mod 10)
    //   - Spaces preserved unchanged
    // Formula for letters : (c - 'a' + shift) % 26 + 'a'
    // Formula for digits  : (c - '0' + shift) % 10 + '0'
    @Override
    public String applyCipher(String inputText, int shift) {
        if (inputText == null || inputText.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < inputText.length(); i++) {
            char c = inputText.charAt(i);

            if (c >= 'a' && c <= 'z') {
                char encoded = (char) ((c - 'a' + shift) % 26 + 'a');
                result.append(encoded);
            }

            else if (c >= '0' && c <= '9') {
                char encoded = (char) ((c - '0' + shift) % 10 + '0');
                result.append(encoded);
            }

            else if (c == ' ') {
                result.append(' ');
            }
        }
        return result.toString();
    }

    // Returns final shift value for display purposes
    // Uses stored this.charCount — call encode() first
    // Contributed by Bong Ming Meng (103541)
    public int getFinalShift() {
        return generateShift() + this.charCount;
    }

    // Returns encoded result after encode() has been called
    // Contributed by Bong Ming Meng (103541)
    @Override
    public String getResultText() {
        return resultText;
    }

    // MAIN ENCODE METHOD
    // Contributed by All Members
    // Orchestrates the full encoding pipeline:
    //   1. Validate input
    //   2. Count non-space characters — stored into this.charCount field
    //   3. Generate group-specific shift using hashCode()
    //   4. Compute final shift = groupShift + charCount
    //   5. Apply cipher and store into this.resultText field
    @Override
    public void encode() {
        // Step 1: Validate
        if (!checkStringValidity(inputText)) {
            resultText = "INVALID INPUT";
            return;
        }

        // Step 2: Count and store into class field
        this.charCount = countCharacters(inputText);

        // Step 3: Generate fixed shift from groupID
        int groupShift = generateShift();

        // Step 4: Compute final shift
        int finalShift = groupShift + this.charCount;

        // Step 5: Apply cipher and store result
        resultText = applyCipher(inputText, finalShift);
    }
}

// PUBLIC MAIN CLASS
// Contributed by All Members
// Builds and displays the Java Swing GUI.
public class Assignment {

    public static void main(String[] args) {

        JFrame frame = new JFrame("String Encoder");
        frame.setSize(420, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // INPUT PANEL
        // Contributed by Siti Nur Farah Maisarah (106387)
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        JLabel inputLabel = new JLabel("Enter text to encode:");
        JTextField inputField = new JTextField();
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        frame.add(inputPanel, BorderLayout.NORTH);

        // OUTPUT DISPLAY AREA
        // Contributed by Nur Syukrinah (97717) and Nashrur Aisyah Hani (102776)
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.add(scrollPane, BorderLayout.CENTER);

        // ENCODE BUTTON & LOGIC
        // Contributed by Bong Ming Meng (103541)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton encodeButton = new JButton("Encode");
        buttonPanel.add(encodeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for the Encode button
        // Steps:
        //   1. Validate input
        //   2. Call encode() to run full pipeline
        //   3. Read stored results via getters
        //   4. Display output
        // NOTE: groupShift is intentionally NOT displayed per assignment spec
        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String input = inputField.getText();

                // Use Encodable interface type - demonstrates polymorphism
                Encodable obj = new Encoded(input);

                // Step 1: Validate
                if (!obj.checkStringValidity(input)) {
                    displayArea.setText("ERROR: Invalid input.\n" 
                    + "Please enter only lowercase letters (a-z), \n"
                    + "digits (0-9), and spaces.");
                    return;
                }

                // Step 2: Run full encode pipeline
                // Sets this.charCount and this.resultText internally
                obj.encode();

                // Step 3: Cast to Encoded to access class-specific getters
                Encoded enc   = (Encoded) obj;
                int charCount  = enc.getCharCount();
                int groupShift = obj.generateShift();
                int finalShift = groupShift + charCount;
                String result  = obj.getResultText();

                // Step 4: Display output
                displayArea.setText(
                    "Non-space characters : " + charCount  + "\n" +
                    "Final shift : " + finalShift + "\n" +
                    "Output : " +
                    result + "\n\n" +
                    "Encoding completed successfully!"
                );
            }
        });

        frame.setVisible(true);
    }
}
