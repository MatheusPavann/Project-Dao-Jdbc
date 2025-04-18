package modelDaoImpl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {


    private Connection conn;
    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("INSERT INTO department\n" +
                            "(Name)\n" +
                            "VALUES\n" +
                            "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());


            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE department " +
                            "SET Name = ? " +
                            "WHERE Id = ?");


            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deletById(Integer id) {

        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("DELETE FROM department\n " +
                    "WHERE Id = ?");

            st.setInt(1,id);
            st.executeUpdate();
        }catch (SQLException e ){
            throw new DbException("Voce nao pode excluir esse id pois esta vinculado a algum vendedor");
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department WHERE Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt("Id"));
                dep.setName(rs.getString("Name"));
                return dep;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setDepartment(dep);
        return obj;
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT d.Id as DepId, d.Name as DepName, " +
                            "s.Id as SellerId, s.Name as SellerName, s.Email, s.BirthDate, s.BaseSalary " +
                            "FROM department d " +
                            "LEFT JOIN seller s ON d.Id = s.DepartmentId " +
                            "ORDER BY d.Name, s.Name");

            rs = st.executeQuery();

            Map<Integer, Department> map = new HashMap<>();
            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                int depId = rs.getInt("DepId");

                Department dep = map.get(depId);
                if (dep == null) {
                    dep = new Department();
                    dep.setId(depId);
                    dep.setName(rs.getString("DepName"));
                    dep.setSellers(new ArrayList<>());
                    map.put(depId, dep);
                    list.add(dep);
                }

                if (rs.getObject("SellerId") != null) {
                    Seller seller = new Seller();
                    seller.setId(rs.getInt("SellerId"));
                    seller.setName(rs.getString("SellerName"));
                    seller.setEmail(rs.getString("Email"));
                    seller.setBirthDate(rs.getDate("BirthDate"));
                    seller.setBaseSalary(rs.getDouble("BaseSalary"));
                    seller.setDepartment(dep);
                    dep.getSellers().add(seller);
                }
            }

            return list;

        } catch (SQLException e) {
            throw new DbException("Error fetching departments with sellers: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
