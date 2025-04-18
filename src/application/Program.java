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


        System.out.println("\n=== Test 4: SellerInsert ===");
        Seller newSeller = new Seller(null,"Jessika","jessika@gmail.com",new Date(),4000.0,department);
        sellerDao.insert(newSeller);
        System.out.println("New id = " + newSeller.getId());

        System.out.println("\n=== Test 5: SellerUpdate ===");
        seller = sellerDao.findById(1);
        seller.setName("Paulo plinio");
        sellerDao.update(seller);
        System.out.println("Update completed");

    }
}