package sudokubotu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.io.File;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Neisius
 */
public class Main extends javax.swing.JFrame {

    private SDKBoard currentBoard;
    private boolean editMode = false;
	private SDKMask lastMask;

    /** Creates new form MainWindow */
    public Main() {
        currentBoard = new SDKBoard();
        initComponents();
        initBoard();
    }

    private void checkBoard() {
        updateValues();
        Set<Point> invalid = currentBoard.conflictedSquares();
        for (int row = 0; row < currentBoard.getN(); row++) {
            for (int col = 0; col < currentBoard.getN(); col++) {
                if (invalid.contains(new Point(row, col)) && currentBoard.isSquareLocked(row, col)) {
                    SDKSquares[row][col].setBackground(Color.RED);
                } else if (invalid.contains(new Point(row, col))) {
                    SDKSquares[row][col].setBackground(Color.PINK);
                } else if (currentBoard.isSquareLocked(row, col)) {
                    SDKSquares[row][col].setBackground(Color.GRAY);
                } else {
                    SDKSquares[row][col].setBackground(Color.WHITE);
                }
            }
        }

        // winning banner
        if (currentBoard.isSolved()) {
            System.out.println("Solved!");
            for (int row = 0; row < currentBoard.getN(); row++) {
                for (int col = 0; col < currentBoard.getN(); col++) {
                    SDKSquares[row][col].setBackground(Color.GREEN);
                }
            }
        }
    }

    private void solveBoard() {
        clearBoard();
        SudokuBot solver = new SudokuBot(currentBoard.getBaseBoard());
        currentBoard = solver.getSolution();
        redrawBoard();
    }

    private void generateBoard() {
        startEdit();


        String[] options = {"Last Remaining (Easy-1.0)"
                , "Hidden Single (Easy-1.2)"
                , "Direct Hidden Pair (Hard-2.0)"
                , "Naked Single (Hard-2.3)"
                /*, "Direct Hidden Triplet (2.5)"*/ };


        Object answer = JOptionPane.showInputDialog(rootPane, "Generate Difficulty\n\nWith Rule: ", "Generate Difficulty", JOptionPane.PLAIN_MESSAGE, null, options, 0);


        SDKBoard generated = SDKBoard.generateSolvedBoard();
        SDKMask mask = null;
        lastMask = null;
        
		try {
	        if (options[0] == answer) {
	            mask = LastRemainingMaskFactory.createMaskForBoard(generated, generated.getN() * generated.getN());
	        } else if (options[1] == answer) {
	            mask = HiddenSingleMaskFactory.createMaskForBoard(generated, generated.getN() * generated.getN());
	        } else if (options[2] == answer) {
	        	mask = DirectHiddenPairMaskFactory.createMaskForBoard(generated, generated.getN() * generated.getN());
	        	
	        } else if (options[3] == answer) {
	            mask = NakedSingleMaskFactory.createMaskForBoard(generated, generated.getN() * generated.getN());
	        } /* else if (options[4] == answer) {
	            mask = DirectHiddenTripletMaskFactory.createMaskForBoard(generated, generated.getN() * generated.getN());
	        }*/
	    } catch (MaxRunException e) {
	    	generated = SDKBoard.generateSolvedBoard();
	    	SDKBoard itBoard = new SDKBoard(generated.copyBoard());
	    	for(int i = 0; i < 10; i++) {
	    		mask = LastRemainingMaskFactory.createMaskForBoard(itBoard, generated.getN()*generated.getN());
	    		itBoard = mask.applyTo(itBoard);
	    	}
	    	lastMask = (SDKMask) e.v;
	    }
        
        if (mask != null) {
            clearBoard();
            currentBoard = mask.applyTo(generated);
        }
        else {
        	System.out.println("mask is null");
        }
        endEdit();
        initBoard();
        redrawBoard();
    }

    private void clearBoard() {
        // clear to blank board
        if (editBoard.isSelected()) {
            currentBoard.createBoard(currentBoard.getN());
            initBoard();
            BoardBack.updateUI();
        } else {
            // clear to game board
            for (int row = 0; row < currentBoard.getN(); row++) {
                for (int col = 0; col < currentBoard.getN(); col++) {
                    if (currentBoard.setSquareValue(row, col, 0)) {
                        SDKSquares[row][col].setBackground(Color.white);
                    } else {
                        SDKSquares[row][col].setBackground(Color.GRAY);
                    }
                }
            }
        }
        redrawBoard();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileBrowser = new javax.swing.JFileChooser();
        clearButton = new javax.swing.JButton();
        solveButton = new javax.swing.JButton();
        checkButton = new javax.swing.JButton();
        scrollBackPane = new javax.swing.JScrollPane();
        BoardBack = new javax.swing.JPanel();
        generateButton = new javax.swing.JButton();
        rankButton = new javax.swing.JButton();
        editButton = new javax.swing.JToggleButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newBoard = new javax.swing.JMenuItem();
        openBoard = new javax.swing.JMenuItem();
        saveBoard = new javax.swing.JMenuItem();
        exitSDK = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        clearBoard = new javax.swing.JMenuItem();
        solveBoard = new javax.swing.JMenuItem();
        checkBoard = new javax.swing.JMenuItem();
        editBoard = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SuDoKuBoTu");
        setMinimumSize(new java.awt.Dimension(800, 500));

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        solveButton.setText("Solve");
        solveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solveButtonActionPerformed(evt);
            }
        });

        checkButton.setText("Check");
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
            }
        });

        scrollBackPane.setMinimumSize(new java.awt.Dimension(0, 0));
        scrollBackPane.setPreferredSize(new java.awt.Dimension(0, 0));

        BoardBack.setMinimumSize(new java.awt.Dimension(300, 300));
        BoardBack.setPreferredSize(new java.awt.Dimension(300, 300));
        BoardBack.setLayout(new java.awt.GridLayout(3, 3));
        scrollBackPane.setViewportView(BoardBack);

        generateButton.setText("Generate");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        rankButton.setText("Rank");
        rankButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rankButtonActionPerformed(evt);
            }
        });

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");

        newBoard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newBoard.setText("New Board");
        newBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBoardActionPerformed(evt);
            }
        });
        fileMenu.add(newBoard);

        openBoard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openBoard.setText("Open Board");
        openBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openBoardActionPerformed(evt);
            }
        });
        fileMenu.add(openBoard);

        saveBoard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveBoard.setText("Save Board");
        saveBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBoardActionPerformed(evt);
            }
        });
        fileMenu.add(saveBoard);

        exitSDK.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitSDK.setText("Exit");
        exitSDK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitSDKActionPerformed(evt);
            }
        });
        fileMenu.add(exitSDK);

        jMenuBar1.add(fileMenu);

        editMenu.setText("Edit");

        clearBoard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        clearBoard.setText("Clear Board");
        clearBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBoardActionPerformed(evt);
            }
        });
        editMenu.add(clearBoard);

        solveBoard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        solveBoard.setText("Solve Board");
        solveBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solveBoardActionPerformed(evt);
            }
        });
        editMenu.add(solveBoard);

        checkBoard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        checkBoard.setText("Check Board");
        checkBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoardActionPerformed(evt);
            }
        });
        editMenu.add(checkBoard);

        editBoard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        editBoard.setText("Edit Board");
        editBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBoardActionPerformed(evt);
            }
        });
        editMenu.add(editBoard);

        jMenuBar1.add(editMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(clearButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rankButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(solveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editButton)
                .addGap(292, 292, 292))
            .addComponent(scrollBackPane, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(solveButton)
                    .addComponent(generateButton)
                    .addComponent(checkButton)
                    .addComponent(rankButton)
                    .addComponent(editButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollBackPane, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearBoard();
    }//GEN-LAST:event_clearButtonActionPerformed

	private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkButtonActionPerformed
            checkBoard();
    }//GEN-LAST:event_checkButtonActionPerformed

    private void clearBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBoardActionPerformed
        clearBoard();
    }//GEN-LAST:event_clearBoardActionPerformed

    private void checkBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoardActionPerformed
        checkBoard();
    }//GEN-LAST:event_checkBoardActionPerformed

    private void saveBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBoardActionPerformed
        saveSDKBoard();
    }//GEN-LAST:event_saveBoardActionPerformed

    private void openBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openBoardActionPerformed
        loadSDKBoard();
    }//GEN-LAST:event_openBoardActionPerformed

    private void exitSDKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitSDKActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitSDKActionPerformed

    private void newBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBoardActionPerformed

        int numOfOptions = 6;
        Integer[] options = new Integer[numOfOptions];
        for (int i = 0; i < numOfOptions; i++) {
            options[i] = (i + 2) * (i + 2);
        }
        Object answer = JOptionPane.showInputDialog(rootPane, "New Board Size\n\nWidth: ", "New Board", JOptionPane.PLAIN_MESSAGE, null, options, currentBoard.getN());
        int newN = 9;
        for (Integer opt : options) {
            if (opt == answer) {
                newN = opt;

            }
        }

        if (answer != null) {
            currentBoard.createBoard(newN);
            initBoard();
        }

    }//GEN-LAST:event_newBoardActionPerformed

    private void editBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBoardActionPerformed
        //enable edit mode lock squares on switch
        if (editMode) {
            endEdit();
        } else {
            startEdit();
        }
    }//GEN-LAST:event_editBoardActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        generateBoard();
    }//GEN-LAST:event_generateButtonActionPerformed

    private void solveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solveButtonActionPerformed
        solveBoard();
    }//GEN-LAST:event_solveButtonActionPerformed

    private void solveBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solveBoardActionPerformed
        solveBoard();
    }//GEN-LAST:event_solveBoardActionPerformed

    private void rankButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rankButtonActionPerformed
        rankBoard();
    }//GEN-LAST:event_rankButtonActionPerformed

    private void rankBoard() {
        SDKAnalysis rank = new SDKAnalysis(currentBoard);
        rank.setMask(lastMask);
        String rankMesg = "Hardest Rule: " + rank.getHardestRule() +
                        "\nDifficulty:         " + rank.getRank();
        JOptionPane.showMessageDialog(rootPane, rankMesg, "Board Rank", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(rankMesg);
//=======
//
//        // TODO popup rank
//        System.out.println("hardest rule: " + rank.getHardestRule());
//        System.out.println("difficulty: " + rank.getRank());
//>>>>>>> 222afc96c2be42d952e643bcbf6367f0346e0ae7
    }

	private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
            // edit mode button
            if (editMode) {
                endEdit();
            } else {
                startEdit();
            }
    }//GEN-LAST:event_editButtonActionPerformed

    private void SDKSquaresFocusGained(java.awt.event.FocusEvent evt) {
        // Select text when box clicked
        ((JTextField) evt.getComponent()).selectAll();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BoardBack;
    private javax.swing.JMenuItem checkBoard;
    private javax.swing.JButton checkButton;
    private javax.swing.JMenuItem clearBoard;
    private javax.swing.JButton clearButton;
    private javax.swing.JCheckBoxMenuItem editBoard;
    private javax.swing.JToggleButton editButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitSDK;
    private javax.swing.JFileChooser fileBrowser;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton generateButton;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem newBoard;
    private javax.swing.JMenuItem openBoard;
    private javax.swing.JButton rankButton;
    private javax.swing.JMenuItem saveBoard;
    private javax.swing.JScrollPane scrollBackPane;
    private javax.swing.JMenuItem solveBoard;
    private javax.swing.JButton solveButton;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JPanel[] SDKZones;
    private javax.swing.JTextField[][] SDKSquares;

    private void initBoard() {
        initZones();
        initSquares();
        BoardBack.updateUI();
    }

    private void initZones() {
        SDKZones = new JPanel[currentBoard.getN()];

        BoardBack.removeAll();
        BoardBack.setLayout(new java.awt.GridLayout(currentBoard.getZonesInRow(), currentBoard.getZonesInRow()));
        for (int i = 0; i < currentBoard.getN(); i++) {
            SDKZones[i] = new javax.swing.JPanel();
            SDKZones[i].setLayout(new java.awt.GridLayout(currentBoard.getZonesInRow(), currentBoard.getZonesInRow()));
            SDKZones[i].setBorder(BorderFactory.createRaisedBevelBorder());
            SDKZones[i].setMaximumSize(new Dimension(32767, 32767));
            BoardBack.add(SDKZones[i]);
        }
    }

    private void initSquares() {
        SDKSquares = new JTextField[currentBoard.getN()][currentBoard.getN()];
        for (JPanel zone : SDKZones) {
            zone.removeAll();
        }

        for (int row = 0; row < currentBoard.getN(); row++) {
            for (int col = 0; col < currentBoard.getN(); col++) {
                SDKSquares[row][col] = new JTextField();
                SDKSquares[row][col].setText("");//currentBoard.getBoardValue(row, col).toString()
                SDKSquares[row][col].setHorizontalAlignment(JTextField.CENTER);
                SDKSquares[row][col].setFont(new Font("TimesRoman", Font.BOLD, 18));
                SDKSquares[row][col].setMinimumSize(new Dimension(80, 80));
                SDKSquares[row][col].setMaximumSize(new Dimension(160, 160));
                SDKSquares[row][col].setSize(100, 100);

                SDKSquares[row][col].addFocusListener(new java.awt.event.FocusAdapter() {

                    public void focusGained(java.awt.event.FocusEvent evt) {
                        SDKSquaresFocusGained(evt);
                    }
                });

                if (currentBoard.isSquareLocked(row, col)) {
                    SDKSquares[row][col].setEditable(false);
                    SDKSquares[row][col].setBackground(Color.GRAY);
                } else {
                    SDKSquares[row][col].setEditable(true);
                    SDKSquares[row][col].setBackground(Color.WHITE);
                }


                SDKZones[currentBoard.identifyZone(row, col)].add(SDKSquares[row][col]);
            }
        }
    }

    // reload values from board
    private void redrawBoard() {
        for (int row = 0; row < currentBoard.getN(); row++) {
            for (int col = 0; col < currentBoard.getN(); col++) {
                SDKSquares[row][col].setText(
                        currentBoard.getSquareValue(row, col) == 0
                        ? ""
                        : currentBoard.getSquareValue(row, col).toString());//hide zeros as blanks
            }
        }
    }

    // Update the current board with values from GUI
    private void updateValues() {
        for (int row = 0; row < currentBoard.getN(); row++) {
            for (int col = 0; col < currentBoard.getN(); col++) {
                try {
                    if (!SDKSquares[row][col].getText().equals("")) {
                        currentBoard.setSquareValue(row, col, Integer.valueOf(SDKSquares[row][col].getText()));
                    } else {
                        currentBoard.setSquareValue(row, col, 0);
                    }
                } catch (NumberFormatException e) {
                    // invalid input handling
                }
                SDKSquares[row][col].setText(
                        currentBoard.getSquareValue(row, col) == 0
                        ? ""
                        : currentBoard.getSquareValue(row, col).toString());//hide zeros as blanks

            }
        }
    }

    private void saveSDKBoard() {
        int returnVal = fileBrowser.showSaveDialog(Main.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileBrowser.getSelectedFile();
            //This is where a real application would open the file.
            System.out.print("Save file selected: " + file.getName() + "." + "\n");

            // ask to overwrite
            boolean write = true;
            if (file.exists()) {
                write = (JOptionPane.showConfirmDialog(rootPane, "File Exists!\n\nOverwrite?", "Overwrite?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
                        ? true
                        : false);
            }

            if (write) {
                updateValues();
                System.out.print("Writng to file: " + file.getName() + "." + "\n");
                currentBoard.saveBoard(file);
            } else {
                System.out.print("Writng cancelled: " + file.getName() + "." + "\n");
            }

        } else {
            System.out.print("Save command cancelled by user." + "\n");
        }
    }

    private void loadSDKBoard() {
        int returnVal = fileBrowser.showOpenDialog(Main.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileBrowser.getSelectedFile();

            System.out.print("Opening: " + file.getName() + "." + "\n");

            currentBoard.loadBoard(file);

            for (JPanel panel : SDKZones) {
                panel.removeAll();
            }

            for (int i = 0; i < currentBoard.getZonesInRow(); i++) {
                SDKZones[i].setLayout(new java.awt.GridLayout(currentBoard.getZonesInRow(), currentBoard.getZonesInRow()));
            }
            initBoard();
            redrawBoard();
        } else {
            System.out.print("Open command cancelled by user." + "\n");
        }
    }

    private void endEdit() {
        updateValues();
        if (currentBoard.conflictedSquares().isEmpty()) {
            for (int row = 0; row < currentBoard.getN(); row++) {
                for (int col = 0; col < currentBoard.getN(); col++) {
                    if (currentBoard.getSquareValue(row, col) == 0) {
                        currentBoard.setSquareLock(row, col, false);
                    } else {
                        currentBoard.setSquareLock(row, col, true);
                    }
                }
            }
            initBoard();
            redrawBoard();
            solveButton.setEnabled(true);
            editBoard.setSelected(false);
            editButton.setSelected(false);
            editMode = false;
        } else {
            checkBoard();
            solveButton.setEnabled(false);
            editBoard.setSelected(true);
            editButton.setSelected(true);
        }
    }

    private void startEdit() {
        updateValues();
        if (currentBoard.conflictedSquares().isEmpty()) {
            for (int row = 0; row < currentBoard.getN(); row++) {
                for (int col = 0; col < currentBoard.getN(); col++) {
                    currentBoard.setSquareLock(row, col, false);
                }
            }
            initBoard();
            redrawBoard();
            solveButton.setEnabled(false);
            editBoard.setSelected(true);
            editButton.setSelected(true);
            editMode = true;
        } else {
            checkBoard();
            solveButton.setEnabled(true);
            editBoard.setSelected(false);
            editButton.setSelected(false);
        }
    }
}
