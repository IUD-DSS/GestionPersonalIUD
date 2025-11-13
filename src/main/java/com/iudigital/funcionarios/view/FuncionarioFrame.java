package com.iudigital.funcionarios.view;

import com.iudigital.funcionarios.dao.FuncionarioDao;
import com.iudigital.funcionarios.domain.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FuncionarioFrame extends JFrame {

    private final FuncionarioDao dao = new FuncionarioDao();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID","Tipo documento","Documento","Nombres","Apellidos","Estado civil","Sexo","Dirección","Teléfono","Nacimiento"}, 0
    );
    private final JTable table = new JTable(model);

    private final JTextField txtId = new JTextField();
    private final JComboBox<String> cbTipo = new JComboBox<>(new String[]{"CC","CE","PASAPORTE","NIT"});
    private final JTextField txtNumero = new JTextField();
    private final JTextField txtNombres = new JTextField();
    private final JTextField txtApellidos = new JTextField();
    private final JTextField txtEstado = new JTextField();
    private final JComboBox<String> cbSexo = new JComboBox<>(new String[]{"M","F","O"});
    private final JTextField txtDireccion = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtNacimiento = new JTextField(); // yyyy-MM-dd

    private final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FuncionarioFrame() {
        super("Gestión de Funcionarios");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        // ===== PANEL IZQUIERDO (Formulario) =====
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BorderLayout(5,5));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Datos del funcionario"));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font fuente = new Font("Segoe UI", Font.PLAIN, 13);

        JLabel[] labels = {
                new JLabel("ID:"), new JLabel("Tipo de documento:"),
                new JLabel("Documento:"), new JLabel("Nombres:"),
                new JLabel("Apellidos:"), new JLabel("Estado civil:"),
                new JLabel("Sexo:"), new JLabel("Dirección:"),
                new JLabel("Teléfono:"), new JLabel("Nacimiento (yyyy-MM-dd):")
        };
        for (JLabel l : labels) l.setFont(fuente);

        Component[] fields = {txtId, cbTipo, txtNumero, txtNombres, txtApellidos,
                txtEstado, cbSexo, txtDireccion, txtTelefono, txtNacimiento};
        for (Component c : fields) c.setFont(fuente);

        txtId.setEditable(false);

        // Colocar los elementos en el GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; form.add(labels[0], gbc);
        gbc.gridx = 1; form.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy++; form.add(labels[1], gbc);
        gbc.gridx = 1; form.add(cbTipo, gbc);

        gbc.gridx = 0; gbc.gridy++; form.add(labels[2], gbc);
        gbc.gridx = 1; form.add(txtNumero, gbc);

        gbc.gridx = 0; gbc.gridy++; form.add(labels[3], gbc);
        gbc.gridx = 1; form.add(txtNombres, gbc);

        gbc.gridx = 0; gbc.gridy++; form.add(labels[4], gbc);
        gbc.gridx = 1; form.add(txtApellidos, gbc);

        gbc.gridx = 0; gbc.gridy++; form.add(labels[5], gbc);
        gbc.gridx = 1; form.add(txtEstado, gbc);

        gbc.gridx = 0; gbc.gridy++; form.add(labels[6], gbc);
        gbc.gridx = 1; form.add(cbSexo, gbc);

        gbc.gridx = 0; gbc.gridy++; form.add(labels[7], gbc);
        gbc.gridx = 1; form.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy++; form.add(labels[8], gbc);
        gbc.gridx = 1; form.add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy++; form.add(labels[9], gbc);
        gbc.gridx = 1; form.add(txtNacimiento, gbc);

        panelIzquierdo.add(form, BorderLayout.CENTER);

        // ===== PANEL BOTONES (debajo del formulario) =====
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnNuevo = new JButton("🧹 Nuevo");
        JButton btnActualizar = new JButton("✏️ Actualizar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnRefrescar = new JButton("🔄 Refrescar");

        Font fuenteBtn = new Font("Segoe UI", Font.BOLD, 13);
        JButton[] botones = {btnNuevo, btnActualizar, btnEliminar, btnRefrescar};
        for (JButton b : botones) {
            b.setFont(fuenteBtn);
            b.setPreferredSize(new Dimension(120, 35));
            buttons.add(b);
        }
        panelIzquierdo.add(buttons, BorderLayout.SOUTH);

        // ===== PANEL DERECHO (Tabla) =====
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de funcionarios"));

        // ===== Agregar ambos paneles a la ventana =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, scrollPane);
        splitPane.setDividerLocation(420);
        splitPane.setResizeWeight(0.4);
        add(splitPane, BorderLayout.CENTER);

        // ===== ACCIONES =====
        btnNuevo.addActionListener(this::onNuevo);
        btnActualizar.addActionListener(this::onActualizar);
        btnEliminar.addActionListener(this::onEliminar);
        btnRefrescar.addActionListener(e -> cargarTabla());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(String.valueOf(model.getValueAt(row, 0)));
                cbTipo.setSelectedItem(model.getValueAt(row, 1));
                txtNumero.setText(String.valueOf(model.getValueAt(row, 2)));
                txtNombres.setText(String.valueOf(model.getValueAt(row, 3)));
                txtApellidos.setText(String.valueOf(model.getValueAt(row, 4)));
                txtEstado.setText(String.valueOf(model.getValueAt(row, 5)));
                cbSexo.setSelectedItem(model.getValueAt(row, 6));
                txtDireccion.setText(String.valueOf(model.getValueAt(row, 7)));
                txtTelefono.setText(String.valueOf(model.getValueAt(row, 8)));
                txtNacimiento.setText(String.valueOf(model.getValueAt(row, 9)));
            }
        });

        cargarTabla();
    }

    private void onNuevo(ActionEvent e) {
        txtId.setText("");
        cbTipo.setSelectedIndex(0);
        txtNumero.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEstado.setText("");
        cbSexo.setSelectedIndex(0);
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtNacimiento.setText("");
        table.clearSelection();
    }

    private Funcionario collectForm(boolean requireId) {
        Funcionario f = new Funcionario();
        if (requireId) {
            if (txtId.getText().isBlank()) throw new IllegalArgumentException("Seleccione un registro.");
            f.setIdFuncionario(Integer.parseInt(txtId.getText().trim()));
        }
        f.setTipoIdentificacion((String) cbTipo.getSelectedItem());
        f.setNumeroIdentificacion(Integer.parseInt(txtNumero.getText().trim()));
        f.setNombres(txtNombres.getText().trim());
        f.setApellidos(txtApellidos.getText().trim());
        f.setEstadoCivil(txtEstado.getText().trim());
        f.setSexo((String) cbSexo.getSelectedItem());
        f.setDireccion(txtDireccion.getText().trim());
        f.setTelefono(txtTelefono.getText().trim());
        if (!txtNacimiento.getText().isBlank()) {
            f.setFechaNacimiento(LocalDate.parse(txtNacimiento.getText().trim(), F));
        } else {
            f.setFechaNacimiento(null);
        }
        return f;
    }

    private void onActualizar(ActionEvent e) {
        try {
            boolean nuevo = txtId.getText().isBlank();
            Funcionario f = collectForm(!nuevo);
            if (nuevo) {
                dao.insert(f);
                JOptionPane.showMessageDialog(this, "Funcionario registrado correctamente.");
            } else {
                dao.update(f);
                JOptionPane.showMessageDialog(this, "Funcionario actualizado correctamente.");
            }
            cargarTabla();
            onNuevo(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEliminar(ActionEvent e) {
        try {
            if (txtId.getText().isBlank()) throw new IllegalArgumentException("Seleccione un registro.");
            int ok = JOptionPane.showConfirmDialog(this, "¿Eliminar el registro seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                dao.delete(Integer.parseInt(txtId.getText().trim()));
                JOptionPane.showMessageDialog(this, "Registro eliminado.");
                cargarTabla();
                onNuevo(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        try {
            List<Funcionario> list = dao.findAll();
            model.setRowCount(0);
            for (Funcionario f : list) {
                model.addRow(new Object[]{
                        f.getIdFuncionario(),
                        f.getTipoIdentificacion(),
                        f.getNumeroIdentificacion(),
                        f.getNombres(),
                        f.getApellidos(),
                        f.getEstadoCivil(),
                        f.getSexo(),
                        f.getDireccion(),
                        f.getTelefono(),
                        f.getFechaNacimiento() != null ? f.getFechaNacimiento().toString() : ""
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

