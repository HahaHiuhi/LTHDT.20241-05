// package sim;

// import java.awt.BorderLayout;

// import javax.swing.*;

// public class Test {
//     public static void main(String[] args) {
//         // Create a new world with 30x30 grid
//         World myWorld = new World();
        
//         // Create the view to render the world
//         WorldView myView = new WorldView(myWorld);

//         // Create a controller to manage the simulation
//         WorldController myController = new WorldController(myWorld, myView);

//         // Set up the frame
//         JFrame frame = new JFrame("Organism Simulation");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.getContentPane().setLayout(new BorderLayout()); // Use BorderLayout for flexibility
//         frame.getContentPane().add(myView, BorderLayout.CENTER); // Add view in the center
//         frame.pack();
//         frame.setVisible(true);

//         // Start the simulation
//         myController.startSimulation();
//     }
// }

package sim;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        // Tạo thế giới mô phỏng
        World myWorld = new World();
        WorldView myView = new WorldView(myWorld);
        WorldController myController = new WorldController(myWorld, myView);

        // Tạo JFrame chính
        JFrame frame = new JFrame("Organism Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel bên trái: điều khiển và hiển thị số liệu P, H, C
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(10, 1, 5, 5));

        // Các nhãn và ô nhập liệu cho P, H, C
        JLabel labelP = new JLabel("P:");
        JTextField fieldP = new JTextField("0");
        JLabel labelH = new JLabel("H:");
        JTextField fieldH = new JTextField("0");
        JLabel labelC = new JLabel("C:");
        JTextField fieldC = new JTextField("0");

        fieldP.setEditable(true);
        fieldH.setEditable(true);
        fieldC.setEditable(true);

        // Nút điều khiển
        JButton startButton = new JButton("Start Simulation");
        JButton pauseButton = new JButton("Pause Simulation");
        JButton resumeButton = new JButton("Resume Simulation");
        JButton helpButton = new JButton("Help");
        JButton quitButton = new JButton("Quit");

        // Thêm các thành phần vào controlPanel
        controlPanel.add(labelP);
        controlPanel.add(fieldP);
        controlPanel.add(labelH);
        controlPanel.add(fieldH);
        controlPanel.add(labelC);
        controlPanel.add(fieldC);
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        controlPanel.add(helpButton);
        controlPanel.add(quitButton);

        // Panel bên phải: Stats
        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setBorder(BorderFactory.createTitledBorder("Stats"));

        // Thêm các thành phần vào frame
        frame.add(controlPanel, BorderLayout.WEST);
        frame.add(myView, BorderLayout.CENTER);
        frame.add(statsArea, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);

        // Sự kiện cho các nút
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int plants = Integer.parseInt(fieldP.getText());
                int herbivores = Integer.parseInt(fieldH.getText());
                int carnivores = Integer.parseInt(fieldC.getText());
                myController.startSimulation(plants, herbivores, carnivores);
                statsArea.append("Simulation started...\n");

                // Thêm Timer để cập nhật các số liệu P, H, C theo thời gian thực
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Cập nhật số liệu P, H, C từ World
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {

                                // Cập nhật thống kê trong statsArea
                                statsArea.setText("Plants: " + myWorld.getPlantsCount() + "\n" +
                                                   "Herbivores: " + myWorld.getHerbivoresCount() + "\n" +
                                                   "Carnivores: " + myWorld.getCarnivoresCount() + "\n");
                            }
                        });
                    }
                });
                timer.start();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myController.pauseSimulation();
                statsArea.append("Simulation stopped...\n");
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myController.resumeSimulation();
                statsArea.append("Simulation resume");
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Help:\nStart - Bắt đầu mô phỏng\nPause - Dừng mô phỏng\nResume - Tiếp tục mô phỏng\nQuit - Thoát chương trình");
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hiển thị hộp thoại xác nhận khi nhấn nút Quit
                int result = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to quit?", 
                "Confirm Exit", 
                JOptionPane.YES_NO_OPTION);

                // Nếu người dùng chọn Yes, thoát ứng dụng
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}
