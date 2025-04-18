package application;

import model.dao.DaoFactory;
import model.dao.DaoFactoryDepartment;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import modelDaoImpl.DepartmentDaoJDBC;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactoryDepartment.createDepartmentDao();

        System.out.println("\n=== Test 1: DepartmentInsert ===");
        Department department = new Department(null,"Jogar");
        departmentDao.insert(department);
        System.out.println(department);

        System.out.println("=== Test 2: FindByIdDepartment ===");
        Department departmentDaoById = departmentDao.findById(1);
        System.out.println(departmentDaoById);

        System.out.println("\n=== Test 3: DepartmentUpdate ===");
        department = departmentDao.findById(4);
        department.setName("Ler");
        departmentDao.update(department);
        System.out.println("Update completed");


        System.out.println("\n=== Test 4: DepartmentDelete ===");
        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        departmentDao.deletById(id);
        System.out.println("Delete complete");

        System.out.println("\n=== Test 5: DepartmentFindAll ===");
        List<Department> list = departmentDao.findAll();
        list = departmentDao.findAll();
        for (Department obj : list){
            System.out.println(obj);
        }

    }
}
