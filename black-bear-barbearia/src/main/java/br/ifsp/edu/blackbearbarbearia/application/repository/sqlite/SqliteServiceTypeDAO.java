package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Type;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceTypeDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteServiceTypeDAO implements ServiceTypeDAO {
    final SqliteTypeDAO typeDAO = new SqliteTypeDAO();

    @Override
    public void create(Integer serviceId, List<Type> types) {
        String sql = """
                INSERT INTO serviceType(
                    serviceId,
                    typeId
                ) VALUES (?, ?)
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            for(Type type : types) {
                final Optional<Integer> typeId = typeDAO.findId(type);

                stmt.setInt(1, serviceId);
                stmt.setInt(2, typeId.get());

                stmt.executeUpdate();
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean update(Integer serviceId, List<Type> types) {
        deleteByServiceId(serviceId);
        create(serviceId, types);
        return findByServiceId(serviceId).equals(types);
    }

    @Override
    public Boolean deleteByServiceId(Integer serviceId) {
        String sql = """
                DELETE FROM serviceType WHERE serviceId = ?
                """;
        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
            stmt.setInt(1, serviceId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findByServiceId(serviceId).isEmpty();
    }

    @Override
    public List<Type> findByServiceId(Integer serviceId) {
        String sql = "SELECT * FROM serviceType WHERE serviceId = ?";
        List<Type> types = new ArrayList<>();

        try {
            final PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);

            stmt.setInt(1, serviceId);

            final ResultSet result = stmt.executeQuery();

            while(result.next()){
                final Integer typeId = result.getInt("typeId");

                Type type = typeDAO.findOne(typeId).get();

                types.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return types;
    }
}
