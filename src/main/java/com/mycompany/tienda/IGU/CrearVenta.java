package com.mycompany.tienda.IGU;

import com.mycompany.tienda.LOGICA.*;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.BorderLayout;     // IMPORTAR explícitamente BorderLayout
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CrearVenta extends javax.swing.JFrame {

    private LogicaController control = new LogicaController();
    private List<Producto> productos = new ArrayList<>();
    private java.util.List<ProductoVendido> productosEnVenta = new ArrayList<>();
    private java.util.List<Categoria> listaCategorias;

    public CrearVenta() {
        initComponents();
pnlRemito.setLayout(new BoxLayout(pnlRemito, BoxLayout.Y_AXIS));
        setLocationRelativeTo(null);
        setTitle("Nueva Venta");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Cargar categorías y poner el listener
        cargarCategorias();

        cmbCategoria.addActionListener(e -> {
            Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();
            if (categoria != null) {
                productos = control.traerProductosDeCategoria(categoria);
                cargarTablaProductos(productos);
            }
        });
    }

    private void cargarCategorias() {
        listaCategorias = control.CategoriasAll();
        cmbCategoria.setModel(new DefaultComboBoxModel<>(listaCategorias.toArray(new Categoria[0])));

        cmbCategoria.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Categoria) {
                    setText(((Categoria) value).getDescripcion());
                } else {
                    setText("");
                }
                return this;
            }
        });
    }

    private void cargarTablaProductos(List<Producto> productos) {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nombre", "Precio", "Stock", "Cantidad", "Agregar"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4;  // Cantidad y botón
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) {
                    return Double.class;
                }
                if (columnIndex == 2 || columnIndex == 3) {
                    return Integer.class;
                }
                return String.class;
            }
        };

        for (Producto p : productos) {
            modelo.addRow(new Object[]{p.getNombre(), p.getPrecio(), p.getStock(), 1, "Agregar"});
        }

        tablaProductos.setModel(modelo);

        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tablaProductos.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void agregarProductoAlPanel(Producto producto, int cantidad) {
        for (ProductoVendido pv : productosEnVenta) {
            if (pv.getProducto().getId().equals(producto.getId())) {
                pv.setCantidad(pv.getCantidad() + cantidad);
                actualizarPanelRemito();
                actualizarTotal();
                return;
            }
        }
        productosEnVenta.add(new ProductoVendido(producto, cantidad));
        actualizarPanelRemito();
        actualizarTotal();
    }

    private void actualizarPanelRemito() {
        pnlRemito.removeAll();

        for (ProductoVendido pv : productosEnVenta) {
            double subtotal = pv.getCantidad() * pv.getProducto().getPrecio();

            JPanel fila = new JPanel();
            fila.setLayout(new BoxLayout(fila, BoxLayout.X_AXIS));
            fila.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // para separación visual

            JLabel lblNombre = new JLabel(pv.producto.getNombre());
            JLabel lblPrecio = new JLabel(" $" + pv.producto.getPrecio());
            JLabel lblCantidad = new JLabel(" x" + pv.cantidad);
            JLabel lblSubtotal = new JLabel(" = $" + pv.producto.getPrecio() * pv.cantidad);

// Establecer tamaños preferidos para mejor alineación
            lblNombre.setPreferredSize(new Dimension(200, 20));
            lblPrecio.setPreferredSize(new Dimension(100, 20));
            lblCantidad.setPreferredSize(new Dimension(80, 20));
            lblSubtotal.setPreferredSize(new Dimension(100, 20));

            fila.add(lblNombre);
            fila.add(lblPrecio);
            fila.add(lblCantidad);
            fila.add(lblSubtotal);

            pnlRemito.add(fila);
        }

        pnlRemito.revalidate();
        pnlRemito.repaint();
    }

    private void actualizarTotal() {
        double total = 0;
        for (ProductoVendido pv : productosEnVenta) {
            total += pv.getCantidad() * pv.getProducto().getPrecio();
        }
        lblTotal.setText("Total: $" + total);
    }

    private class ProductoVendido {

        private Producto producto;
        private int cantidad;

        public ProductoVendido(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public Producto getProducto() {
            return producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }

    private class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText("Agregar");
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private boolean clicked;
        private int filaSeleccionada;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Agregar");
            button.setOpaque(true);

            // Aquí manejamos el click del botón:
            button.addActionListener(e -> {
                // Tomamos la fila seleccionada:
                Producto producto = productos.get(filaSeleccionada);
                int cantidad = 1;
                try {
                    cantidad = Integer.parseInt(tablaProductos.getValueAt(filaSeleccionada, 3).toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida");
                    // Cancelamos la edición
                    fireEditingCanceled();
                    return;
                }

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "Cantidad debe ser mayor a cero");
                    fireEditingCanceled();
                    return;
                } else if (producto.getStock() >= cantidad) {
                    agregarProductoAlPanel(producto, cantidad);
                } else {
                    JOptionPane.showMessageDialog(null, "Stock insuficiente");
                    fireEditingCanceled();
                    return;
                }

                clicked = true;
                // Para terminar la edición y que la tabla refresque
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            filaSeleccionada = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Agregar";
        }

        @Override
        public boolean stopCellEditing() {
            // Aquí no hacemos nada extra porque la acción ya se disparó en el listener del botón
            return super.stopCellEditing();
        }

    }
    private void finalizarVenta() {
    try {
        if (productosEnVenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos para vender.");
            return;
        }

        // Transformar productosEnVenta a la lista requerida para lógica
        List<LogicaController.ProductoCantidad> listaVenta = new ArrayList<>();

        for (ProductoVendido pv : productosEnVenta) {
            listaVenta.add(new LogicaController.ProductoCantidad(pv.getProducto(), pv.getCantidad()));
        }

        // Llamar a la lógica para crear la venta
        control.crearVenta(listaVenta);

        JOptionPane.showMessageDialog(this, "Venta realizada con éxito!");

        // Limpiar panel y lista
        productosEnVenta.clear();
        actualizarPanelRemito();
        actualizarTotal();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al crear la venta: " + ex.getMessage());
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        cmbCategoria = new javax.swing.JComboBox<com.mycompany.tienda.LOGICA.Categoria>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pnlRemito = new javax.swing.JPanel();

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
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Precio", "Stock", "Cantidad", "Agregar"
            }
        ));
        jScrollPane1.setViewportView(tablaProductos);

        jLabel1.setText("Venta");

        jLabel2.setText("Categoria");

        jLabel3.setText("Productos");

        jButton1.setText("Confirmar Venta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        pnlRemito.setLayout(new javax.swing.BoxLayout(pnlRemito, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane3.setViewportView(pnlRemito);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal)
                .addGap(77, 77, 77))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)))
                        .addComponent(jButton1)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         try {
        finalizarVenta();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al realizar la venta: " + ex.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(CrearVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrearVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrearVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Categoria> cmbCategoria;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlRemito;
    private javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables
}
