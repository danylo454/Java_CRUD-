package program;

import jdk.jfr.Category;
import models.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HiberSessionUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Добавлення Ролі
//        Scanner in = new Scanner(System.in);
//        System.out.println("Enter name role: ");
//        String name = in.nextLine();
//        addNewCategory(name);

        //Видалення Категорії
//        Scanner in =  new Scanner(System.in);
//        System.out.println("Enter id role that you want to delete: ");
//        int idC = in.nextInt();
//        removeCategory(idC);

//        Редагування Категорії
//        editCategory();

        //Виведення Категорій
        ShowCategory();


    }

    public static void editCategory() {
        try {
            Session context = HiberSessionUtil.getSessionFactory().openSession();
            Transaction tx = context.beginTransaction();
            Scanner in = new Scanner(System.in);


            Query queue = context.createQuery("FROM Role");
            List<Role> roleList = queue.list();
            for (Role role : roleList) {
                System.out.println("ID: " + role.getId() + " Name: " + role.getName());
            }
            System.out.println("Enter Category id: ");
            int idC = Integer.parseInt(in.nextLine());
            System.out.println("Enter new category name: ");
            String idCN = in.nextLine();

            Role role = (Role) context.get(Role.class, idC);
            role.setName(idCN);
            context.update(role);
            tx.commit();

            context.close();


        } catch (Exception ex) {
            System.out.println("Exception addNewCategory: " + ex.getMessage());
        }

    }

    public static void removeCategory(int idC) {
        try {
            Session context = HiberSessionUtil.getSessionFactory().openSession();
            Transaction tx = context.beginTransaction();
            Role role = (Role) context.get(Role.class, idC);
            context.remove(role);
            tx.commit();
            context.close();

            System.out.println("Successfully removed!");

        } catch (Exception ex) {
            System.out.println("Exception removeCategory: " + ex.getMessage());
        }

    }

    public static void addNewCategory(String newCategory) {
        try {
            Session context = HiberSessionUtil.getSessionFactory().openSession();
            Role role = new Role();
            role.setName(newCategory);
            context.save(role);
            System.out.println("Role: " + newCategory + " Added successfully !");
            context.close();
        } catch (Exception ex) {
            System.out.println("Exception addNewCategory: " + ex.getMessage());
        }

    }

    public static void ShowCategory() {
        try {
            Session context = HiberSessionUtil.getSessionFactory().openSession();
            Query queue = context.createQuery("FROM Role");
            List<Role> roleList = queue.list();
            for (Role role : roleList) {
                System.out.println("ID: " + role.getId() + " Name: " + role.getName());
            }
            context.close();


        } catch (Exception ex) {
            System.out.println("Exception addNewCategory: " + ex.getMessage());
        }

    }
}
