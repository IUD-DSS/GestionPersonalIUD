package com.iudigital.funcionarios.dao;

import com.iudigital.funcionarios.config.ConnectionConfig;
import com.iudigital.funcionarios.domain.Funcionario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    private static final String SQL_SELECT_ALL =
        "SELECT id_funcionario, tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento " +
        "FROM funcionarios ORDER BY id_funcionario";

    private static final String SQL_SELECT_BY_ID =
        "SELECT id_funcionario, tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento " +
        "FROM funcionarios WHERE id_funcionario = ?";

    private static final String SQL_INSERT =
        "INSERT INTO funcionarios (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE funcionarios SET tipo_identificacion=?, numero_identificacion=?, nombres=?, apellidos=?, estado_civil=?, sexo=?, direccion=?, telefono=?, fecha_nacimiento=? " +
        "WHERE id_funcionario=?";

    private static final String SQL_DELETE =
        "DELETE FROM funcionarios WHERE id_funcionario=?";

    private Funcionario map(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(rs.getInt("id_funcionario"));
        f.setTipoIdentificacion(rs.getString("tipo_identificacion"));
        f.setNumeroIdentificacion(rs.getInt("numero_identificacion"));
        f.setNombres(rs.getString("nombres"));
        f.setApellidos(rs.getString("apellidos"));
        f.setEstadoCivil(rs.getString("estado_civil"));
        f.setSexo(rs.getString("sexo"));
        f.setDireccion(rs.getString("direccion"));
        f.setTelefono(rs.getString("telefono"));
        Date d = rs.getDate("fecha_nacimiento");
        f.setFechaNacimiento(d != null ? d.toLocalDate() : null);
        return f;
    }

    public List<Funcionario> findAll() throws SQLException {
        try (Connection cn = ConnectionConfig.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            List<Funcionario> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    public Funcionario findById(int id) throws SQLException {
        try (Connection cn = ConnectionConfig.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public int insert(Funcionario f) throws SQLException {
        try (Connection cn = ConnectionConfig.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, f.getTipoIdentificacion());
            ps.setInt(2, f.getNumeroIdentificacion());
            ps.setString(3, f.getNombres());
            ps.setString(4, f.getApellidos());
            ps.setString(5, f.getEstadoCivil());
            ps.setString(6, f.getSexo());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            if (f.getFechaNacimiento() != null) {
                ps.setDate(9, Date.valueOf(f.getFechaNacimiento()));
            } else {
                ps.setNull(9, Types.DATE);
            }
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    f.setIdFuncionario(id);
                    return id;
                }
            }
        }
        return -1;
        }
    
    public void update(Funcionario f) throws SQLException {
        try (Connection cn = ConnectionConfig.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, f.getTipoIdentificacion());
            ps.setInt(2, f.getNumeroIdentificacion());
            ps.setString(3, f.getNombres());
            ps.setString(4, f.getApellidos());
            ps.setString(5, f.getEstadoCivil());
            ps.setString(6, f.getSexo());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            if (f.getFechaNacimiento() != null) {
                ps.setDate(9, Date.valueOf(f.getFechaNacimiento()));
            } else {
                ps.setNull(9, Types.DATE);
            }
            ps.setInt(10, f.getIdFuncionario());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection cn = ConnectionConfig.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
