package MemoryAllocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstFitGUI {
    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("First Fit Memory Allocation");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create input panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel processesLabel = new JLabel("Process Sizes (comma-separated):");
        JTextField processesField = new JTextField();
        JLabel memoryLabel = new JLabel("Memory Blocks (comma-separated):");
        JTextField memoryField = new JTextField();
        JButton allocateButton = new JButton("Allocate");
        JTextArea resultArea = new JTextArea();

        panel.add(processesLabel);
        panel.add(processesField);
        panel.add(memoryLabel);
        panel.add(memoryField);
        panel.add(allocateButton);

        // Add components to frame
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Event Handling
        allocateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get process sizes
                    String[] processSizesStr = processesField.getText().split(",");
                    int[] processes = new int[processSizesStr.length];
                    for (int i = 0; i < processSizesStr.length; i++) {
                        processes[i] = Integer.parseInt(processSizesStr[i].trim());
                    }

                    // Get memory blocks
                    String[] memoryBlocksStr = memoryField.getText().split(",");
                    int[] memory = new int[memoryBlocksStr.length];
                    for (int i = 0; i < memoryBlocksStr.length; i++) {
                        memory[i] = Integer.parseInt(memoryBlocksStr[i].trim());
                    }

                    // Call First Fit logic
                    String result = allocateFirstFit(processes, memory);
                    resultArea.setText(result);
                } catch (Exception ex) {
                    resultArea.setText("Invalid input! Please try again.");
                }
            }
        });

        frame.setVisible(true);
    }

    // First Fit Algorithm for Multiple Processes
    public static String allocateFirstFit(int[] processes, int[] memory) {
        StringBuilder result = new StringBuilder("Memory Allocation:\n");

        // Array to track remaining memory in each partition
        int[] remainingMemory = memory.clone();

        for (int p = 0; p < processes.length; p++) {
            boolean allocated = false;

            for (int m = 0; m < remainingMemory.length; m++) {
                if (remainingMemory[m] >= processes[p]) {
                    result.append("Process of size ")
                            .append(processes[p])
                            .append(" allocated to partition of size ")
                            .append(memory[m])
                            .append("\n");

                    remainingMemory[m] -= processes[p]; // Reduce the size of the memory block
                    allocated = true;
                    break;
                }
            }

            if (!allocated) {
                result.append("Process of size ")
                        .append(processes[p])
                        .append(" could not be allocated.\n");
            }
        }

        // Add remaining memory details
        result.append("Remaining partitions: [");
        for (int i = 0; i < remainingMemory.length; i++) {
            result.append(remainingMemory[i]);
            if (i < remainingMemory.length - 1) {
                result.append(", ");
            }
        }
        result.append("]\n");

        return result.toString();
    }
}
