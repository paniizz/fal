package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class FaalHafezApp {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("فال حافظ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setLayout(new BorderLayout());

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Create and add the labels
        JLabel label1 = new JLabel("نیت کنید ...", SwingConstants.CENTER);
        label1.setFont(new Font("Serif", Font.PLAIN, 24));
        mainPanel.add(label1, gbc);

        gbc.gridy++;
        JLabel label2 = new JLabel("در صورت اتمام نیت خود، روی دکمه‌ی زیر کلیک کنید.", SwingConstants.CENTER);
        label2.setFont(new Font("Serif", Font.PLAIN, 18));
        mainPanel.add(label2, gbc);

        // Create and add the button
        gbc.gridy++;
        JButton button = new JButton("دریافت فال");
        button.setFont(new Font("Serif", Font.PLAIN, 18));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Send HTTP request and get response
                    String response = sendHTTPRequest();
                    // Display the response in a new window
                    showResponseWindow(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(button, gbc);

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }

    private static String sendHTTPRequest() throws Exception {
        String url = "https://faal.spclashers.workers.dev/api";
        @SuppressWarnings("deprecation")
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        // Optional: set HTTP method to GET
        httpClient.setRequestMethod("GET");

        // Get the response
        int responseCode = httpClient.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                // حذف \r از inputLine
                inputLine = inputLine.replace("\r", "");
                response.append(inputLine);
            }
            in.close();

            // Print the JSON response for debugging
            System.out.println(response.toString());

            // Return the response as a string
            return response.toString();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    private static void showResponseWindow(String response) {
        // Parse the JSON response
        JSONObject jsonResponse = new JSONObject(response);

        // Extract poem and interpretation
        String poem = jsonResponse.optString("Poem", "No poem available").replace("\\n", "\n").replace("\\r", "");
        String interpretation = jsonResponse.optString("Interpretation", "No interpretation available");

        // Create a new frame to display the response
        JFrame responseFrame = new JFrame("فال حافظ");
        responseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        responseFrame.setSize(600, 700);
        responseFrame.setLayout(new BorderLayout());

        // Create text areas to display the poem and interpretation
        JTextArea poemArea = new JTextArea(poem);
        poemArea.setLineWrap(true);
        poemArea.setWrapStyleWord(true);
        poemArea.setEditable(false);

        JTextArea interpretationArea = new JTextArea(interpretation);
        interpretationArea.setLineWrap(true);
        interpretationArea.setWrapStyleWord(true);
        interpretationArea.setEditable(false);

        // Add labels
        JLabel poemLabel = new JLabel("شعر");
        poemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel interpretationLabel = new JLabel("تفسیر");
        interpretationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add text areas and labels to the frame
        JPanel poemPanel = new JPanel(new BorderLayout());
        poemPanel.add(poemLabel, BorderLayout.NORTH);
        poemPanel.add(new JScrollPane(poemArea), BorderLayout.CENTER);

        JPanel interpretationPanel = new JPanel(new BorderLayout());
        interpretationPanel.add(interpretationLabel, BorderLayout.NORTH);
        interpretationPanel.add(new JScrollPane(interpretationArea), BorderLayout.CENTER);

        responseFrame.add(poemPanel, BorderLayout.CENTER);
        responseFrame.add(interpretationPanel, BorderLayout.SOUTH);

        // Show the response frame
        responseFrame.setVisible(true);
    }
}
