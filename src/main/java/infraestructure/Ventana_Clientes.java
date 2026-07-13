package infraestructure;

import controlador.ClienteService;
import domain.Cliente;
import domain.Empleados;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ventana_Clientes extends javax.swing.JFrame {

    private final Empleados empleado;
    private final ClienteService clienteService;
    private DefaultTableModel modeloTabla;
    private Cliente clienteSeleccionado = null;

    public Ventana_Clientes(Empleados empleado) {
        this.empleado       = empleado;
        this.clienteService = presentation.SistemaVentasApplication
                .getBean(ClienteService.class);

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

        JLabel lblTitulo = new JLabel("👥  Clientes");
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
                new String[]{"ID", "Nombre", "Apellido", "DNI",
                    "Dirección", "Teléfono", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaClientes.setModel(modeloTabla);
        tablaClientes.setRowHeight(28);
        tablaClientes.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaClientes.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaClientes.setSelectionBackground(new Color(114, 145, 226));
        tablaClientes.setSelectionForeground(Color.WHITE);

        // Ocultar columna ID
        tablaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tablaClientes.getColumnModel().getColumn(0).setMaxWidth(0);

        recargarTabla();

        // Al seleccionar fila → llenar formulario
        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if (fila >= 0) {
                Long id = (Long) modeloTabla.getValueAt(fila, 0);
                clienteSeleccionado = clienteService.listarClientes()
                        .stream().filter(c -> c.getIdCLiente().equals(id))
                        .findFirst().orElse(null);
                if (clienteSeleccionado != null)
                    llenarFormulario(clienteSeleccionado);
            }
        });
    }

    private void recargarTabla() {
        modeloTabla.setRowCount(0);
        for (Cliente c : clienteService.listarClientes()) {
            modeloTabla.addRow(new Object[]{
                c.getIdCLiente(),
                c.getNombre(),
                c.getApellido(),
                c.getDni(),
                c.getDireccion(),
                c.getTelefono(),
                c.getEmail()
            });
        }
    }

    private void llenarFormulario(Cliente c) {
        txtNombre.setText(c.getNombre());
        txtApellido.setText(c.getApellido());
        txtDni.setText(c.getDni());
        txtDireccion.setText(c.getDireccion());
        txtTelefono.setText(c.getTelefono());
        txtEmail.setText(c.getEmail());
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        clienteSeleccionado = null;
        tablaClientes.clearSelection();
    }

    // ══════════════════════════════════════════════
    //  ACCIONES CRUD
    // ══════════════════════════════════════════════
    private void agregarCliente() {
        try {
            Cliente c = new Cliente();
            c.setNombre(txtNombre.getText().trim());
            c.setApellido(txtApellido.getText().trim());
            c.setDni(txtDni.getText().trim());
            c.setDireccion(txtDireccion.getText().trim());
            c.setTelefono(txtTelefono.getText().trim());
            c.setEmail(txtEmail.getText().trim());

            clienteService.guardarCliente(c);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Cliente registrado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarCliente() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un cliente de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            clienteSeleccionado.setNombre(txtNombre.getText().trim());
            clienteSeleccionado.setApellido(txtApellido.getText().trim());
            clienteSeleccionado.setDni(txtDni.getText().trim());
            clienteSeleccionado.setDireccion(txtDireccion.getText().trim());
            clienteSeleccionado.setTelefono(txtTelefono.getText().trim());
            clienteSeleccionado.setEmail(txtEmail.getText().trim());

            clienteService.guardarCliente(clienteSeleccionado);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Cliente actualizado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un cliente de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar al cliente: "
                + clienteSeleccionado.getNombre() + " "
                + clienteSeleccionado.getApellido() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                clienteService.eliminarCliente(clienteSeleccionado.getIdCLiente());
                recargarTabla();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this,
                        "Cliente eliminado.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarCliente() {
        String texto = txtBuscar.getText().trim();
        modeloTabla.setRowCount(0);
        List<Cliente> lista = texto.isEmpty()
                ? clienteService.listarClientes()
                : clienteService.buscarPorNombre(texto);
        for (Cliente c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getIdCLiente(), c.getNombre(), c.getApellido(),
                c.getDni(), c.getDireccion(), c.getTelefono(), c.getEmail()
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
                "Registro de Clientes",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));
        panelFormulario.setPreferredSize(new Dimension(320, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        txtNombre    = new JTextField(15);
        txtApellido  = new JTextField(15);
        txtDni       = new JTextField(15);
        txtDireccion = new JTextField(15);
        txtTelefono  = new JTextField(15);
        txtEmail     = new JTextField(15);

        Object[][] campos = {
            {"Nombre:",    txtNombre},
            {"Apellido:",  txtApellido},
            {"DNI:",       txtDni},
            {"Dirección:", txtDireccion},
            {"Teléfono:",  txtTelefono},
            {"Email:",     txtEmail}
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
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 6, 6));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        JButton btnAgregar  = crearBoton("+ Agregar",  new Color(46, 139, 87));
        JButton btnEditar   = crearBoton("✎ Editar",   new Color(70, 130, 180));
        JButton btnEliminar = crearBoton("✕ Eliminar", new Color(178, 34, 34));
        JButton btnLimpiar  = crearBoton("↺ Limpiar",  new Color(128, 128, 128));

        btnAgregar.addActionListener(e  -> agregarCliente());
        btnEditar.addActionListener(e   -> editarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
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
        btnBuscar.addActionListener(e -> buscarCliente());
        txtBuscar.addActionListener(e -> buscarCliente());
        panelBuscar.add(new JLabel("Buscar: "));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);

        tablaClientes = new JTable();
        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Listado de Clientes",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));

        panelTabla.add(panelBuscar, BorderLayout.NORTH);
        panelTabla.add(scroll,      BorderLayout.CENTER);

        panelContenido.add(panelFormulario, BorderLayout.WEST);
        panelContenido.add(panelTabla,      BorderLayout.CENTER);

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
    private JTextField txtNombre, txtApellido, txtDni, txtDireccion,
                       txtTelefono, txtEmail, txtBuscar;
    private JTable tablaClientes;
}
