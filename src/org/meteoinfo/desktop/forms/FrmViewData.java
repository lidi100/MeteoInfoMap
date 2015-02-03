/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteoinfo.desktop.forms;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.meteoinfo.data.GridData;
import org.meteoinfo.data.StationData;
import org.meteoinfo.data.XYListDataset;
import org.meteoinfo.global.MIMath;
import org.meteoinfo.table.RowHeaderTable;
import org.meteoinfo.projection.ProjectionInfo;

/**
 *
 * @author yaqiang
 */
public class FrmViewData extends javax.swing.JFrame {

    private double _missingValue;
    private String _dataType = "GridData";
    private Object _data;
    private ProjectionInfo _projInfo;
    private String[] _colNames;

    public void setMissingValue(double value) {
        _missingValue = value;
    }

    public void setGridData(GridData value) {
        _data = value;
        _dataType = "GridData";
        this.jButton_ToStation.setEnabled(true);

        GridData gData = (GridData) _data;
        int xNum = gData.getXNum();
        int yNum = gData.getYNum();
        Object[][] tData = new Object[yNum][xNum];
        _colNames = new String[xNum];
        for (int i = 0; i < xNum; i++) {
            _colNames[i] = String.valueOf(i);
        }

        double min = gData.getMaxMinValue()[1];
        int dNum = MIMath.getDecimalNum(min);
        String dFormat = "%1$." + String.valueOf(dNum) + "f";

        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                tData[i][j] = String.format(dFormat, gData.data[i][j]);
            }
        }

        DefaultTableModel model = new DefaultTableModel(tData, _colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.jTable1.setModel(model);
        this.jScrollPane1.setRowHeaderView(new RowHeaderTable(this.jTable1, 40, true));
    }

    public void setStationData(StationData value) {
        _data = value;
        _dataType = "StationData";
        this.jButton_ToStation.setEnabled(false);

        StationData sData = (StationData) _data;
        double min = sData.getMinValue();
        int dNum = MIMath.getDecimalNum(min);
        String dFormat = "%1$." + String.valueOf(dNum) + "f";

        int yNum = sData.getStNum();
        //int xNum = sData.data.length + 1;
        int xNum = 4;
        Object[][] tData = new Object[yNum][xNum];
        for (int i = 0; i < xNum; i++) {
            if (i == 0) {
                for (int j = 0; j < yNum; j++) {
                    tData[j][i] = sData.stations.get(j);
                }
            } else if (i <= 2) {
                for (int j = 0; j < yNum; j++) {
                    //tData[j][i] = String.format("%1$.2f", sData.data[i - 1][j]);
                    tData[j][i] = String.valueOf(sData.data[j][i - 1]);
                }
            } else {
                for (int j = 0; j < yNum; j++) {
                    tData[j][i] = String.format(dFormat, sData.data[j][i - 1]);
                }
            }
        }

        DefaultTableModel model = new DefaultTableModel(tData, _colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.jTable1.setModel(model);
        this.jScrollPane1.setRowHeaderView(new RowHeaderTable(this.jTable1, 40));
    }

    public void setXYData(XYListDataset value) {
        _data = value;
        _dataType = "XYData";
        this.jButton_ToStation.setEnabled(false);

        double min = value.getY(0, 0);
        int dNum = MIMath.getDecimalNum(min);
        String dFormat = "%1$." + String.valueOf(dNum) + "f";

        int yNum = value.getItemCount();
        int xNum = value.getSeriesCount() * 2;
        _colNames = new String[xNum];
        for (int i = 0; i < xNum / 2; i++) {
            _colNames[i * 2] = value.getSeriesKey(i) + "_X";
            _colNames[i * 2 + 1] = value.getSeriesKey(i) + "_Y";
        }
        Object[][] tData = new Object[yNum][xNum];
        for (int i = 0; i < xNum / 2; i++) {
            for (int j = 0; j < yNum; j++) {
                tData[j][i * 2] = value.getX(i, j);
                tData[j][i * 2 + 1] = value.getY(i, j);
            }
        }

        DefaultTableModel model = new DefaultTableModel(tData, _colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.jTable1.setModel(model);
        this.jScrollPane1.setRowHeaderView(new RowHeaderTable(this.jTable1, 40));
    }

    public void setProjectionInfo(ProjectionInfo value) {
        _projInfo = value;
    }

    /**
     * Creates new form FrmViewData
     */
    public FrmViewData() {
        initComponents();

        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("/org/meteoinfo/desktop/resources/MeteoInfo_1_16x16x8.png"));
            this.setIconImage(image);
        } catch (Exception e) {
        }
        this.jButton_Stat.setEnabled(false);
        this.jButton_Chart.setEnabled(false);
    }

    /**
     * Constructor
     *
     * @param colNames
     */
    public FrmViewData(String[] colNames) {
        this();
        _colNames = colNames;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton_Save = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton_ToStation = new javax.swing.JButton();
        jButton_Stat = new javax.swing.JButton();
        jButton_Chart = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jButton_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/meteoinfo/desktop/resources/Disk_1_16x16x8.png"))); // NOI18N
        jButton_Save.setToolTipText("Save File");
        jButton_Save.setFocusable(false);
        jButton_Save.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_Save.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton_Save);
        jToolBar1.add(jSeparator1);

        jButton_ToStation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/meteoinfo/desktop/resources/TSB_NewPoint.Image.png"))); // NOI18N
        jButton_ToStation.setToolTipText("To Station Data");
        jButton_ToStation.setFocusable(false);
        jButton_ToStation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_ToStation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton_ToStation);

        jButton_Stat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/meteoinfo/desktop/resources/Statictics.png"))); // NOI18N
        jButton_Stat.setToolTipText("Statistics");
        jButton_Stat.setFocusable(false);
        jButton_Stat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_Stat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton_Stat);

        jButton_Chart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/meteoinfo/desktop/resources/chart.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/meteoinfo/desktop/bundle/Bundle_FrmMeteoData"); // NOI18N
        jButton_Chart.setToolTipText(bundle.getString("FrmMeteoData.jButton_1DPlot.toolTipText")); // NOI18N
        jButton_Chart.setFocusable(false);
        jButton_Chart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_Chart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_Chart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ChartActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_Chart);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ChartActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Under developing!");
    }//GEN-LAST:event_jButton_ChartActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmViewData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmViewData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmViewData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmViewData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmViewData().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Chart;
    private javax.swing.JButton jButton_Save;
    private javax.swing.JButton jButton_Stat;
    private javax.swing.JButton jButton_ToStation;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}