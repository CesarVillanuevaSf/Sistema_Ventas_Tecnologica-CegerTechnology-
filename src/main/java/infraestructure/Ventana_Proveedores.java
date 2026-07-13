package infraestructure;

import domain.Empleados;
import domain.Proveedores;
import controlador.ProveedorService;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ventana_Proveedores extends javax.swing.JFrame {

    private final Empleados empleado;
    private final ProveedorService proveedorService;
    private DefaultTableModel modeloTabla;
    private Proveedores proveedorSeleccionado = null;

    public Ventana_Proveedores(Empleados empleado) {
        this.empleado = empleado;
        this.proveedorService = presentation.SistemaVentasApplication
                .getBean(ProveedorService.class);

        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(1000, 720);
        this.setResizable(false);

        configurarCabecera();
        configurarFooter();
        cargarTabla();
    }

    // ══════════════════════════════════════════════
    //  CABECERA
    // ══════════════════════════════════════════════
    private void configurarCabecera() {
        panelCabecera.removeAll();
        panelCabecera.setBackground(new Color(114, 145, 226));
        panelCabecera.setPreferredSize(new Dimension(1000, 100));
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

        JLabel lblTitulo = new JLabel("🚚  Proveedores");
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
        panelFooter.setPreferredSize(new Dimension(1000, 50));
        panelFooter.setLayout(new BorderLayout());

        JPanel panelFechaHora = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 12));
        panelFechaHora.setOpaque(false);

        JLabel lblFecha = new JLabel("  📅  " + java.time.LocalDate.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel lblHora = new JLabel("  🕐  --:--:--");
        lblHora.setForeground(Color.WHITE);
        lblHora.setFont(new Font("SansSerif", Font.BOLD, 13));

        new javax.swing.Timer(1000, e -> lblHora.setText("  🕐  " +
                java.time.LocalTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")))).start();

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
    //  TABLA
    // ══════════════════════════════════════════════
    private void cargarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "RUC", "Nombre", "Dirección",
                    "Contacto Vendedor", "Distribución"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProveedores.setModel(modeloTabla);
        tablaProveedores.setRowHeight(28);
        tablaProveedores.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaProveedores.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaProveedores.setSelectionBackground(new Color(114, 145, 226));
        tablaProveedores.setSelectionForeground(Color.WHITE);

        tablaProveedores.getColumnModel().getColumn(0).setMinWidth(0);
        tablaProveedores.getColumnModel().getColumn(0).setMaxWidth(0);

        recargarTabla();

        tablaProveedores.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaProveedores.getSelectedRow();
            if (fila >= 0) {
                Long id = (Long) modeloTabla.getValueAt(fila, 0);
                proveedorSeleccionado = proveedorService.listarProveedores()
                        .stream().filter(p -> p.getIdProveedor().equals(id))
                        .findFirst().orElse(null);
                if (proveedorSeleccionado != null) llenarFormulario(proveedorSeleccionado);
            }
        });
    }

    private void recargarTabla() {
        modeloTabla.setRowCount(0);
        for (Proveedores p : proveedorService.listarProveedores()) {
            modeloTabla.addRow(new Object[]{
                p.getIdProveedor(),
                p.getRuc(),
                p.getNombre(),
                p.getDireccion(),
                p.getContactoVendedor(),
                p.getDistribucion()
            });
        }
    }

    private void llenarFormulario(Proveedores p) {
        txtRuc.setText(p.getRuc());
        txtNombre.setText(p.getNombre());
        txtDireccion.setText(p.getDireccion());
        txtContacto.setText(p.getContactoVendedor());
        txtDistribucion.setText(p.getDistribucion());
    }

    private void limpiarFormulario() {
        txtRuc.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtContacto.setText("");
        txtDistribucion.setText("");
        proveedorSeleccionado = null;
        tablaProveedores.clearSelection();
    }

    // ══════════════════════════════════════════════
    //  ACCIONES CRUD
    // ══════════════════════════════════════════════
    private void agregarProveedor() {
        try {
            Proveedores p = new Proveedores();
            p.setRuc(txtRuc.getText().trim());
            p.setNombre(txtNombre.getText().trim());
            p.setDireccion(txtDireccion.getText().trim());
            p.setContactoVendedor(txtContacto.getText().trim());
            p.setDistribucion(txtDistribucion.getText().trim());

            proveedorService.guardarProveedor(p);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Proveedor agregado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarProveedor() {
        if (proveedorSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un proveedor de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            proveedorSeleccionado.setRuc(txtRuc.getText().trim());
            proveedorSeleccionado.setNombre(txtNombre.getText().trim());
            proveedorSeleccionado.setDireccion(txtDireccion.getText().trim());
            proveedorSeleccionado.setContactoVendedor(txtContacto.getText().trim());
            proveedorSeleccionado.setDistribucion(txtDistribucion.getText().trim());

            proveedorService.guardarProveedor(proveedorSeleccionado);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Proveedor actualizado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProveedor() {
        if (proveedorSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un proveedor de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar al proveedor: "
                + proveedorSeleccionado.getNombre() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            proveedorService.eliminarProveedor(proveedorSeleccionado.getIdProveedor());
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Proveedor eliminado.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void buscarProveedor() {
        String texto = txtBuscar.getText().trim();
        modeloTabla.setRowCount(0);
        List<Proveedores> lista = texto.isEmpty()
                ? proveedorService.listarProveedores()
                : proveedorService.buscarPorNombre(texto);
        for (Proveedores p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getIdProveedor(), p.getRuc(), p.getNombre(),
                p.getDireccion(), p.getContactoVendedor(), p.getDistribucion()
            });
        }
    }

    // ══════════════════════════════════════════════
    //  INIT COMPONENTS
    // ══════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelCabecera  = new JPanel();
        panelFooter    = new JPanel();

        JPanel panelContenido = new JPanel(new BorderLayout(15, 0));
        panelContenido.setBackground(new Color(242, 242, 242));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ── FORMULARIO IZQUIERDA ─────────────────
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Registro de Proveedores",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));
        panelFormulario.setPreferredSize(new Dimension(320, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtRuc          = new JTextField(15);
        txtNombre       = new JTextField(15);
        txtDireccion    = new JTextField(15);
        txtContacto     = new JTextField(15);
        txtDistribucion = new JTextField(15);

        Object[][] campos = {
            {"RUC:",               txtRuc},
            {"Nombre:",            txtNombre},
            {"Dirección:",         txtDireccion},
            {"Contacto Vendedor:", txtContacto},
            {"Distribución:",      txtDistribucion}
        };

        for (int i = 0; i < campos.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            JLabel lbl = new JLabel((String) campos[i][0]);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
            panelFormulario.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            panelFormulario.add((Component) campos[i][1], gbc);
        }

        // Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        panelBotones.setOpaque(false);

        JButton btnAgregar  = crearBoton("+ Agregar",  new Color(46, 139, 87));
        JButton btnEditar   = crearBoton("✎ Editar",   new Color(70, 130, 180));
        JButton btnEliminar = crearBoton("✕ Eliminar", new Color(178, 34, 34));
        JButton btnLimpiar  = crearBoton("↺ Limpiar",  new Color(128, 128, 128));

        btnAgregar.addActionListener(e  -> agregarProveedor());
        btnEditar.addActionListener(e   -> editarProveedor());
        btnEliminar.addActionListener(e -> eliminarProveedor());
        btnLimpiar.addActionListener(e  -> limpiarFormulario());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = campos.length;
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        panelFormulario.add(panelBotones, gbc);

        // ── TABLA DERECHA ────────────────────────
        JPanel panelTabla = new JPanel(new BorderLayout(0, 8));
        panelTabla.setOpaque(false);

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelBuscar.setOpaque(false);
        txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton btnBuscar = crearBoton("🔍 Buscar", new Color(114, 145, 226));
        btnBuscar.addActionListener(e -> buscarProveedor());
        txtBuscar.addActionListener(e -> buscarProveedor());
        panelBuscar.add(new JLabel("Buscar: "));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);

        tablaProveedores = new JTable();
        JScrollPane scroll = new JScrollPane(tablaProveedores);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Listado de Proveedores",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));

        panelTabla.add(panelBuscar, BorderLayout.NORTH);
        panelTabla.add(scroll, BorderLayout.CENTER);

        panelContenido.add(panelFormulario, BorderLayout.WEST);
        panelContenido.add(panelTabla, BorderLayout.CENTER);

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
    private JTextField txtRuc, txtNombre, txtDireccion, txtContacto,
                       txtDistribucion, txtBuscar;
    private JTable tablaProveedores;
}