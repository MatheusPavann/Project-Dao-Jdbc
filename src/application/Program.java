package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {


        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== Test 1: FindById ===");
        Seller seller = sellerDao.findById(7);
        System.out.println(seller);

        System.out.println("\n=== Test 2: SellerFindByDepartment ===");
        Department department = new Department(2,null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list){
            System.out.println(obj);
        }

        System.out.println("\n=== Test 3: SellerFindAll ===");
         list = sellerDao.findAll();
        for (Seller obj : list){
            System.out.println(obj);
        }


    }
}