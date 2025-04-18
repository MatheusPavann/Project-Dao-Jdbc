package model.dao;

import db.DB;
import modelDaoImpl.DepartmentDaoJDBC;

public class DaoFactoryDepartment {

    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());

    }
}
