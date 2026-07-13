package infraestructure;

import domain.Empleados;
import controlador.EmpleadoService;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MantenedorEmpleados extends javax.swing.JFrame {

    private final Empleados empleadoActual;
    private final EmpleadoService empleadoService;
    private DefaultTableModel modeloTabla;
    private Empleados empleadoSeleccionado = null;

    public MantenedorEmpleados(Empleados empleadoActual) {
        this.empleadoActual = empleadoActual;
        this.empleadoService = presentation.SistemaVentasApplication
                .getBean(EmpleadoService.class);

        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(1000, 720);
        this.setResizable(false);

        configurarCabecera();
        configurarFooter();
        cargarTabla();
    }

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
            new Menu_Principal(empleadoActual).setVisible(true);
            this.dispose();
        });

        JLabel lblTitulo = new JLabel("👤  Empleados");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        panelCentro.add(btnRegresar);
        panelCentro.add(lblTitulo);
        panelCabecera.add(panelCentro, BorderLayout.CENTER);

        JPanel panelSaludo = new JPanel(new GridLayout(2, 1, 0, 4));
        panelSaludo.setOpaque(false);

        JLabel lblHola = new JLabel("Hola, " + empleadoActual.getNombre()
                + " " + empleadoActual.getApellido());
        lblHola.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblHola.setForeground(Color.WHITE);
        lblHola.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel lblRol = new JLabel(empleadoActual.getRol());
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

        new javax.swing.Timer(1000, e -> lblHora.setText("  🕐  "
                + java.time.LocalTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")))).start();

        panelFechaHora.add(lblFecha);
        panelFechaHora.add(lblHora);
        panelFooter.add(panelFechaHora, BorderLayout.WEST);

        JLabel lblCopyright = new JLabel(
                "© 2026 Ceger Technology. Todos los derechos reservados.  ");
        lblCopyright.setForeground(Color.WHITE);
        lblCopyright.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panelFooter.add(lblCopyright, BorderLayout.EAST);

        panelFooter.revalidate();
        panelFooter.repaint();
    }

    private void cargarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Apellido", "DNI",
                    "Correo", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaEmpleados.setModel(modeloTabla);
        tablaEmpleados.setRowHeight(28);
        tablaEmpleados.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaEmpleados.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaEmpleados.setSelectionBackground(new Color(114, 145, 226));
        tablaEmpleados.setSelectionForeground(Color.WHITE);

        tablaEmpleados.getColumnModel().getColumn(0).setMinWidth(0);
        tablaEmpleados.getColumnModel().getColumn(0).setMaxWidth(0);

        recargarTabla();

        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaEmpleados.getSelectedRow();
            if (fila >= 0) {
                Long id = (Long) modeloTabla.getValueAt(fila, 0);
                empleadoSeleccionado = empleadoService.listarEmpleados()
                        .stream().filter(emp -> emp.getIdEmpleado().equals(id))
                        .findFirst().orElse(null);
                if (empleadoSeleccionado != null)
                    llenarFormulario(empleadoSeleccionado);
            }
        });
    }

    private void recargarTabla() {
        modeloTabla.setRowCount(0);
        for (Empleados emp : empleadoService.listarEmpleados()) {
            modeloTabla.addRow(new Object[]{
                emp.getIdEmpleado(),
                emp.getNombre(),
                emp.getApellido(),
                emp.getDni(),
                emp.getCorreoElectronico(),
                emp.getRol()
            });
        }
    }

    private void llenarFormulario(Empleados emp) {
        txtNombre.setText(emp.getNombre());
        txtApellido.setText(emp.getApellido());
        txtDni.setText(emp.getDni());
        txtCorreo.setText(emp.getCorreoElectronico());
        cmbRol.setSelectedItem(emp.getRol());
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtCorreo.setText("");
        cmbRol.setSelectedIndex(0);
        empleadoSeleccionado = null;
        tablaEmpleados.clearSelection();
    }

    private void editarEmpleado() {
        if (empleadoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un empleado de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            empleadoSeleccionado.setNombre(txtNombre.getText().trim());
            empleadoSeleccionado.setApellido(txtApellido.getText().trim());
            empleadoSeleccionado.setDni(txtDni.getText().trim());
            empleadoSeleccionado.setCorreoElectronico(txtCorreo.getText().trim());
            empleadoSeleccionado.setRol((String) cmbRol.getSelectedItem());
            empleadoService.guardarEmpleado(empleadoSeleccionado);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Empleado actualizado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEmpleado() {
        if (empleadoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un empleado de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (empleadoSeleccionado.getIdEmpleado()
                .equals(empleadoActual.getIdEmpleado())) {
            JOptionPane.showMessageDialog(this,
                    "No puede eliminar su propia cuenta.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar a: " + empleadoSeleccionado.getNombre()
                + " " + empleadoSeleccionado.getApellido() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            empleadoService.eliminarEmpleado(empleadoSeleccionado.getIdEmpleado());
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Empleado eliminado.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cambiarRol() {
        if (empleadoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un empleado de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String nuevoRol = (String) cmbRol.getSelectedItem();
            empleadoService.cambiarRol(empleadoSeleccionado.getIdEmpleado(), nuevoRol);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Rol actualizado a: " + nuevoRol, "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetearPassword() {
        if (empleadoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un empleado de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Resetear contraseña de " + empleadoSeleccionado.getNombre()
                + "? La nueva contraseña será su DNI.",
                "Confirmar reset", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                empleadoService.resetearPassword(empleadoSeleccionado.getIdEmpleado());
                JOptionPane.showMessageDialog(this,
                        "Contraseña reseteada al DNI del empleado.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarEmpleado() {
        String texto = txtBuscar.getText().trim();
        modeloTabla.setRowCount(0);
        List<Empleados> lista = texto.isEmpty()
                ? empleadoService.listarEmpleados()
                : empleadoService.buscarPorNombre(texto);
        for (Empleados emp : lista) {
            modeloTabla.addRow(new Object[]{
                emp.getIdEmpleado(), emp.getNombre(), emp.getApellido(),
                emp.getDni(), emp.getCorreoElectronico(), emp.getRol()
            });
        }
    }

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
                "Gestión de Empleados",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));
        panelFormulario.setPreferredSize(new Dimension(320, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre   = new JTextField(15);
        txtApellido = new JTextField(15);
        txtDni      = new JTextField(15);
        txtCorreo   = new JTextField(15);
        cmbRol      = new JComboBox<>(new String[]{
            "PENDIENTE", "EMPLEADO", "SERVICIO TECNICO", "ADMINISTRADOR"});

        Object[][] campos = {
            {"Nombre:",   txtNombre},
            {"Apellido:", txtApellido},
            {"DNI:",      txtDni},
            {"Correo:",   txtCorreo},
            {"Rol:",      cmbRol}
        };

        for (int i = 0; i < campos.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            JLabel lbl = new JLabel((String) campos[i][0]);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
            panelFormulario.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            panelFormulario.add((Component) campos[i][1], gbc);
        }

        JLabel lblNota = new JLabel(
                "<html><i>* Para agregar use el Registro del Login.</i></html>");
        lblNota.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblNota.setForeground(new Color(150, 150, 150));
        gbc.gridx = 0; gbc.gridy = campos.length;
        gbc.gridwidth = 2;
        panelFormulario.add(lblNota, gbc);

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 6, 6));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        JButton btnEditar   = crearBoton("✎ Editar",         new Color(70, 130, 180));
        JButton btnEliminar = crearBoton("✕ Eliminar",        new Color(178, 34, 34));
        JButton btnRol      = crearBoton("⚙ Cambiar Rol",     new Color(153, 101, 21));
        JButton btnReset    = crearBoton("🔑 Resetear Clave", new Color(128, 0, 128));
        JButton btnLimpiar  = crearBoton("↺ Limpiar",         new Color(128, 128, 128));

        btnEditar.addActionListener(e   -> editarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnRol.addActionListener(e      -> cambiarRol());
        btnReset.addActionListener(e    -> resetearPassword());
        btnLimpiar.addActionListener(e  -> limpiarFormulario());

        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRol);
        panelBotones.add(btnReset);

        gbc.gridx = 0; gbc.gridy = campos.length + 1;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        gbc.gridy = campos.length + 2;
        panelFormulario.add(btnLimpiar, gbc);

        // ── TABLA DERECHA ────────────────────────
        JPanel panelTabla = new JPanel(new BorderLayout(0, 8));
        panelTabla.setOpaque(false);

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelBuscar.setOpaque(false);
        txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton btnBuscar = crearBoton("🔍 Buscar", new Color(114, 145, 226));
        btnBuscar.addActionListener(e -> buscarEmpleado());
        txtBuscar.addActionListener(e -> buscarEmpleado());
        panelBuscar.add(new JLabel("Buscar: "));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);

        tablaEmpleados = new JTable();
        JScrollPane scroll = new JScrollPane(tablaEmpleados);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Listado de Empleados",
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
    private JTextField txtNombre, txtApellido, txtDni, txtCorreo, txtBuscar;
    private JComboBox<String> cmbRol;
    private JTable tablaEmpleados;
}