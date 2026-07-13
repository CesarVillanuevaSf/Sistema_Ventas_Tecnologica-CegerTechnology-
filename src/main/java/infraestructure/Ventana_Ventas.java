package infraestructure;

import controlador.ClienteService;
import controlador.ProductoService;
import controlador.VentaService;
import domain.Detalle_Ventas;
import domain.Ventas;
import domain.Empleados;
import domain.Cliente;
import domain.Producto;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ventana_Ventas extends javax.swing.JFrame {

    private final Empleados empleado;
    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;
    private DefaultTableModel modeloVentas;
    private DefaultTableModel modeloDetalle;
    private List<Detalle_Ventas> detallesActuales = new ArrayList<>();

    public Ventana_Ventas(Empleados empleado) {
        this.empleado = empleado;
        this.ventaService = presentation.SistemaVentasApplication
                .getBean(VentaService.class);
        this.clienteService = presentation.SistemaVentasApplication
                .getBean(ClienteService.class);
        this.productoService = presentation.SistemaVentasApplication
                .getBean(ProductoService.class);

        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(1100, 720);
        this.setResizable(false);

        configurarCabecera();
        configurarFooter();
        cargarTablaVentas();
        cargarTablaDetalle();
        cargarCombos();
    }

    // ══════════════════════════════════════════════
    //  CABECERA
    // ══════════════════════════════════════════════
    private void configurarCabecera() {
        panelCabecera.removeAll();
        panelCabecera.setBackground(new Color(114, 145, 226));
        panelCabecera.setPreferredSize(new Dimension(1100, 100));
        panelCabecera.setLayout(new BorderLayout(15, 0));

        JLabel lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(220, 90));
        java.net.URL urlLogo = getClass().getResource("/imagenes/Logo-Empresa.png");
        if (urlLogo != null) {
            Image img = new ImageIcon(urlLogo).getImage()
                    .getScaledInstance(200, 85, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }
        panelCabecera.add(lblLogo, BorderLayout.WEST);

        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        panelCentro.setOpaque(false);

        JButton btnRegresar = new JButton("← Regresar");
        btnRegresar.setBackground(new Color(255, 255, 255, 60));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> {
            new Menu_Principal(empleado).setVisible(true);
            this.dispose();
        });

        JLabel lblTitulo = new JLabel("💰  Ventas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        panelCentro.add(btnRegresar);
        panelCentro.add(lblTitulo);
        panelCabecera.add(panelCentro, BorderLayout.CENTER);

        JPanel panelSaludo = new JPanel(new GridLayout(2, 1, 0, 4));
        panelSaludo.setOpaque(false);

        JLabel lblHola = new JLabel("Hola, " + empleado.getNombre()
                + " " + empleado.getApellido());
        lblHola.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblHola.setForeground(Color.WHITE);
        lblHola.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel lblRol = new JLabel(empleado.getRol());
        lblRol.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblRol.setForeground(Color.WHITE);
        lblRol.setHorizontalAlignment(SwingConstants.RIGHT);

        panelSaludo.add(lblHola);
        panelSaludo.add(lblRol);

        JPanel wrapperSaludo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 32));
        wrapperSaludo.setOpaque(false);
        wrapperSaludo.add(panelSaludo);
        panelCabecera.add(wrapperSaludo, BorderLayout.EAST);

        panelCabecera.revalidate();
        panelCabecera.repaint();
    }

    // ══════════════════════════════════════════════
    //  FOOTER
    // ══════════════════════════════════════════════
    private void configurarFooter() {
        panelFooter.removeAll();
        panelFooter.setBackground(new Color(105, 135, 222));
        panelFooter.setPreferredSize(new Dimension(1100, 50));
        panelFooter.setLayout(new BorderLayout());

        JPanel panelFechaHora = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 12));
        panelFechaHora.setOpaque(false);

        JLabel lblFecha = new JLabel("  📅  " + java.time.LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel lblHora = new JLabel("  🕐  --:--:--");
        lblHora.setForeground(Color.WHITE);
        lblHora.setFont(new Font("SansSerif", Font.BOLD, 13));

        new javax.swing.Timer(1000, e -> lblHora.setText("  🕐  " +
                java.time.LocalTime.now().format(
                        DateTimeFormatter.ofPattern("HH:mm:ss")))).start();

        panelFechaHora.add(lblFecha);
        panelFechaHora.add(lblHora);
        panelFooter.add(panelFechaHora, BorderLayout.WEST);

        JLabel lblCopyright = new JLabel(
                "© 2025 Ceger Technology. Todos los derechos reservados.  ");
        lblCopyright.setForeground(Color.WHITE);
        lblCopyright.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panelFooter.add(lblCopyright, BorderLayout.EAST);

        panelFooter.revalidate();
        panelFooter.repaint();
    }

    // ══════════════════════════════════════════════
    //  TABLAS
    // ══════════════════════════════════════════════
    private void cargarTablaVentas() {
        modeloVentas = new DefaultTableModel(
                new String[]{"ID", "Comprobante", "N° Comprobante",
                    "Cliente", "Subtotal", "IGV", "Total", "Estado", "Fecha"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaVentas.setModel(modeloVentas);
        tablaVentas.setRowHeight(28);
        tablaVentas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaVentas.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaVentas.setSelectionBackground(new Color(114, 145, 226));
        tablaVentas.setSelectionForeground(Color.WHITE);
        tablaVentas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaVentas.getColumnModel().getColumn(0).setMaxWidth(0);

        recargarTablaVentas();

        // Al seleccionar venta → cargar detalles
        tablaVentas.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaVentas.getSelectedRow();
            if (fila >= 0) {
                Long id = (Long) modeloVentas.getValueAt(fila, 0);
                modeloDetalle.setRowCount(0);
                for (Detalle_Ventas d : ventaService.listarDetalles(id)) {
                    modeloDetalle.addRow(new Object[]{
                        d.getProducto() != null ? d.getProducto().getNombre() : "",
                        d.getCantidad(),
                        d.getPVentaUnitario(),
                        d.getCantidad() * d.getPVentaUnitario()
                    });
                }
            }
        });
    }

    private void recargarTablaVentas() {
        modeloVentas.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Ventas v : ventaService.listarVentas()) {
            modeloVentas.addRow(new Object[]{
                v.getCodVenta(),
                v.getComprobantePago(),
                v.getNumeroComprobante(),
                v.getCliente() != null
                        ? v.getCliente().getNombre() + " " + v.getCliente().getApellido() : "",
                String.format("S/ %.2f", v.getSubtotal()),
                String.format("S/ %.2f", v.getIgv()),
                String.format("S/ %.2f", v.getTotal()),
                v.getEstado(),
                v.getFechaHora() != null ? v.getFechaHora().format(fmt) : ""
            });
        }
    }

    private void cargarTablaDetalle() {
        modeloDetalle = new DefaultTableModel(
                new String[]{"Producto", "Cantidad", "P. Unit.", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaDetalle.setModel(modeloDetalle);
        tablaDetalle.setRowHeight(25);
        tablaDetalle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaDetalle.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaDetalle.setSelectionBackground(new Color(114, 145, 226));
        tablaDetalle.setSelectionForeground(Color.WHITE);
    }

    private void cargarCombos() {
        // Clientes
        cmbCliente.removeAllItems();
        cmbCliente.addItem(null);
        for (Cliente c : clienteService.listarClientes()) cmbCliente.addItem(c);
        cmbCliente.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> l,
                    Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                setText(v instanceof Cliente
                        ? ((Cliente)v).getNombre() + " " + ((Cliente)v).getApellido()
                        : "Seleccione...");
                return this;
            }
        });

        // Productos
        cmbProducto.removeAllItems();
        cmbProducto.addItem(null);
        for (Producto p : productoService.listarProductos()) cmbProducto.addItem(p);
        cmbProducto.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> l,
                    Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                setText(v instanceof Producto ? ((Producto)v).getNombre() : "Seleccione...");
                return this;
            }
        });

        // Al seleccionar producto → precio automático
        cmbProducto.addActionListener(e -> {
            Producto p = (Producto) cmbProducto.getSelectedItem();
            if (p != null) {
                txtPrecioUnitario.setText(String.valueOf(p.getPrecioVenta()));
                lblStock.setText("Stock: " + p.getStockActual());
            } else {
                txtPrecioUnitario.setText("");
                lblStock.setText("Stock: --");
            }
        });
    }

    // ══════════════════════════════════════════════
    //  ACCIONES
    // ══════════════════════════════════════════════
    private void agregarProductoDetalle() {
        Producto p = (Producto) cmbProducto.getSelectedItem();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            double precio = Double.parseDouble(txtPrecioUnitario.getText().trim());

            if (cantidad <= 0 || precio <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Cantidad y precio deben ser mayores a cero.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Detalle_Ventas detalle = new Detalle_Ventas();
            detalle.setProducto(p);
            detalle.setCantidad(cantidad);
            detalle.setPVentaUnitario(precio);
            detallesActuales.add(detalle);

            modeloDetalle.addRow(new Object[]{
                p.getNombre(), cantidad, precio, cantidad * precio
            });

            actualizarTotales();
            txtCantidad.setText("");
            cmbProducto.setSelectedIndex(0);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Verifique cantidad y precio.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTotales() {
        double subtotal = detallesActuales.stream()
                .mapToDouble(d -> d.getCantidad() * d.getPVentaUnitario()).sum();
        double igv = subtotal * 0.18;
        double total = subtotal + igv;
        lblSubtotal.setText(String.format("Subtotal: S/ %.2f", subtotal));
        lblIgv.setText(String.format("IGV (18%%): S/ %.2f", igv));
        lblTotal.setText(String.format("TOTAL: S/ %.2f", total));
    }

    private void registrarVenta() {
        try {
            Cliente cliente = (Cliente) cmbCliente.getSelectedItem();
            String comprobante = (String) cmbComprobante.getSelectedItem();
            String nroComprobante = txtNroComprobante.getText().trim();

            if (cliente == null || comprobante.equals("Seleccione...")) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione cliente y tipo de comprobante.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Ventas venta = new Ventas();
            venta.setCliente(cliente);
            venta.setEmpleado(empleado);
            venta.setComprobantePago(comprobante);
            venta.setNumeroComprobante(nroComprobante);

            ventaService.registrarVenta(venta, detallesActuales);
            recargarTablaVentas();
            limpiarVenta();
            JOptionPane.showMessageDialog(this,
                    "Venta registrada correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void anularVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una venta de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de anular esta venta?",
                "Confirmar anulación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Long id = (Long) modeloVentas.getValueAt(fila, 0);
                ventaService.anularVenta(id);
                recargarTablaVentas();
                modeloDetalle.setRowCount(0);
                JOptionPane.showMessageDialog(this,
                        "Venta anulada correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarVenta() {
        detallesActuales.clear();
        modeloDetalle.setRowCount(0);
        cmbCliente.setSelectedIndex(0);
        cmbComprobante.setSelectedIndex(0);
        txtNroComprobante.setText("");
        txtCantidad.setText("");
        txtPrecioUnitario.setText("");
        lblSubtotal.setText("Subtotal: S/ 0.00");
        lblIgv.setText("IGV (18%): S/ 0.00");
        lblTotal.setText("TOTAL: S/ 0.00");
        lblStock.setText("Stock: --");
    }

    // ══════════════════════════════════════════════
    //  INIT COMPONENTS
    // ══════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelCabecera  = new JPanel();
        panelFooter    = new JPanel();

        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBackground(new Color(242, 242, 242));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // ── PANEL IZQUIERDA: NUEVA VENTA ─────────
        JPanel panelNuevaVenta = new JPanel(new BorderLayout(0, 8));
        panelNuevaVenta.setPreferredSize(new Dimension(380, 0));
        panelNuevaVenta.setOpaque(false);

        // Formulario cabecera venta
        JPanel panelFormVenta = new JPanel(new GridBagLayout());
        panelFormVenta.setBackground(Color.WHITE);
        panelFormVenta.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Nueva Venta",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cmbCliente      = new JComboBox<>();
        cmbComprobante  = new JComboBox<>(
                new String[]{"Seleccione...", "BOLETA", "FACTURA", "TICKET"});
        txtNroComprobante = new JTextField(15);

        Object[][] camposVenta = {
            {"Cliente:",      cmbCliente},
            {"Comprobante:",  cmbComprobante},
            {"N° Comprobante:", txtNroComprobante}
        };
        for (int i = 0; i < camposVenta.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            JLabel lbl = new JLabel((String) camposVenta[i][0]);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            panelFormVenta.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            panelFormVenta.add((Component) camposVenta[i][1], gbc);
        }

        // Formulario agregar producto
        JPanel panelFormProducto = new JPanel(new GridBagLayout());
        panelFormProducto.setBackground(Color.WHITE);
        panelFormProducto.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Agregar Producto",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 13),
                new Color(114, 145, 226)));

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 8, 5, 8);
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        cmbProducto      = new JComboBox<>();
        txtCantidad      = new JTextField(10);
        txtPrecioUnitario = new JTextField(10);
        txtPrecioUnitario.setEditable(false);
        txtPrecioUnitario.setBackground(new Color(230, 230, 230));
        lblStock = new JLabel("Stock: --");
        lblStock.setForeground(new Color(46, 139, 87));
        lblStock.setFont(new Font("SansSerif", Font.BOLD, 12));

        Object[][] camposProducto = {
            {"Producto:",    cmbProducto},
            {"Cantidad:",    txtCantidad},
            {"P. Unitario:", txtPrecioUnitario},
            {"",             lblStock}
        };
        for (int i = 0; i < camposProducto.length; i++) {
            gbc2.gridx = 0; gbc2.gridy = i; gbc2.weightx = 0;
            JLabel lbl = new JLabel((String) camposProducto[i][0]);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            panelFormProducto.add(lbl, gbc2);
            gbc2.gridx = 1; gbc2.weightx = 1.0;
            panelFormProducto.add((Component) camposProducto[i][1], gbc2);
        }

        JButton btnAgregarDetalle = crearBoton("+ Agregar Producto",
                new Color(70, 130, 180));
        btnAgregarDetalle.addActionListener(e -> agregarProductoDetalle());
        gbc2.gridx = 0; gbc2.gridy = camposProducto.length;
        gbc2.gridwidth = 2;
        panelFormProducto.add(btnAgregarDetalle, gbc2);

        // Tabla detalle
        tablaDetalle = new JTable();
        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Detalle de Venta",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 13),
                new Color(114, 145, 226)));
        scrollDetalle.setPreferredSize(new Dimension(0, 150));

        // Totales
        JPanel panelTotales = new JPanel(new GridLayout(3, 1, 0, 3));
        panelTotales.setBackground(new Color(240, 245, 255));
        panelTotales.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        lblSubtotal = new JLabel("Subtotal: S/ 0.00");
        lblIgv      = new JLabel("IGV (18%): S/ 0.00");
        lblTotal    = new JLabel("TOTAL: S/ 0.00");

        lblSubtotal.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblIgv.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotal.setForeground(new Color(46, 139, 87));

        panelTotales.add(lblSubtotal);
        panelTotales.add(lblIgv);
        panelTotales.add(lblTotal);

        // Botones finales
        JPanel panelBotonesVenta = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        panelBotonesVenta.setOpaque(false);
        JButton btnRegistrar = crearBoton("✔ Registrar Venta", new Color(46, 139, 87));
        JButton btnLimpiar   = crearBoton("↺ Limpiar", new Color(128, 128, 128));
        btnRegistrar.addActionListener(e -> registrarVenta());
        btnLimpiar.addActionListener(e   -> limpiarVenta());
        panelBotonesVenta.add(btnRegistrar);
        panelBotonesVenta.add(btnLimpiar);

        panelNuevaVenta.add(panelFormVenta,    BorderLayout.NORTH);
        panelNuevaVenta.add(panelFormProducto, BorderLayout.CENTER);
        panelNuevaVenta.add(scrollDetalle,     BorderLayout.SOUTH);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);
        panelSur.add(panelTotales,     BorderLayout.CENTER);
        panelSur.add(panelBotonesVenta, BorderLayout.SOUTH);

        // ── PANEL DERECHA: HISTORIAL ─────────────
        JPanel panelHistorial = new JPanel(new BorderLayout(0, 8));
        panelHistorial.setOpaque(false);

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelBuscar.setOpaque(false);
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton btnBuscar = crearBoton("🔍 Buscar", new Color(114, 145, 226));
        JButton btnAnular = crearBoton("✕ Anular Venta", new Color(178, 34, 34));
        btnBuscar.addActionListener(e -> {
            String txt = txtBuscar.getText().trim();
            modeloVentas.setRowCount(0);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            List<Ventas> lista = txt.isEmpty()
                    ? ventaService.listarVentas()
                    : ventaService.buscarPorCliente(txt);
            for (Ventas v : lista) {
                modeloVentas.addRow(new Object[]{
                    v.getCodVenta(), v.getComprobantePago(),
                    v.getNumeroComprobante(),
                    v.getCliente() != null ? v.getCliente().getNombre()
                            + " " + v.getCliente().getApellido() : "",
                    String.format("S/ %.2f", v.getSubtotal()),
                    String.format("S/ %.2f", v.getIgv()),
                    String.format("S/ %.2f", v.getTotal()),
                    v.getEstado(),
                    v.getFechaHora() != null ? v.getFechaHora().format(fmt) : ""
                });
            }
        });
        btnAnular.addActionListener(e -> anularVenta());
        panelBuscar.add(new JLabel("Buscar cliente: "));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);
        panelBuscar.add(btnAnular);

        tablaVentas = new JTable();
        JScrollPane scrollVentas = new JScrollPane(tablaVentas);
        scrollVentas.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Historial de Ventas",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));

        panelHistorial.add(panelBuscar,   BorderLayout.NORTH);
        panelHistorial.add(scrollVentas,  BorderLayout.CENTER);

        // ── ENSAMBLE FINAL ───────────────────────
        JPanel panelIzquierda = new JPanel(new BorderLayout(0, 8));
        panelIzquierda.setOpaque(false);
        panelIzquierda.add(panelNuevaVenta, BorderLayout.CENTER);
        panelIzquierda.add(panelSur,        BorderLayout.SOUTH);

        panelContenido.add(panelIzquierda, BorderLayout.WEST);
        panelContenido.add(panelHistorial,  BorderLayout.CENTER);

        panelPrincipal.add(panelCabecera,  BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(panelFooter,    BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        pack();
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ══════════════════════════════════════════════
    //  VARIABLES
    // ══════════════════════════════════════════════
    private JPanel panelPrincipal, panelCabecera, panelFooter;
    private JComboBox<Cliente> cmbCliente;
    private JComboBox<Producto> cmbProducto;
    private JComboBox<String> cmbComprobante;
    private JTextField txtNroComprobante, txtCantidad, txtPrecioUnitario, txtBuscar;
    private JLabel lblSubtotal, lblIgv, lblTotal, lblStock;
    private JTable tablaVentas, tablaDetalle;
}