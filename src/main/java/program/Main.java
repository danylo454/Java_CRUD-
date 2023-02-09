package program;

import jdk.jfr.Category;
import models.Question;
import models.QuestionItem;
import models.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HiberSessionUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        gameTest();


    }
    private static void gameTest(){
        Scanner in = new Scanner(System.in);
        String action = "";
        do {

            System.out.println("=============== Head Menu ================");
            System.out.print("1)Play Test\n2)Add Question\n3)Remove Question\n4)Edit Question\n5)Show Questions\n0)Exit\nEnter: ");
            action = in.nextLine();
            if (action.equals("1")){
                StartGameTests();
            }
            else if (action.equals("2")){
                addQuestion();
            } else if (action.equals("3")){
                removeQuestion();
            }else if (action.equals("4")){
                editQuestion();
            }else if (action.equals("5")){
                showQuestions();
            }

        }while (!action.equals("0"));
    }
    private static void removeQuestion(){
        try {
            Scanner in = new Scanner(System.in);
            Session context = HiberSessionUtil.getSessionFactory().openSession();
            Transaction tx = context.beginTransaction();
            Query query = context.createQuery("FROM Question");
            List<Question> listQuestion = query.list();

            System.out.println("============= RemoveQuestion ================");
            for (Question question: listQuestion) {
                System.out.println("Id: " + question.getId() + ") Назва: " + question.getName());
            }
            System.out.println("Введіть id питання якого хочете видалити:");
            System.out.print("Enter: ");
            int idC = in.nextInt();

            Query tempQuery = context.createQuery("FROM QuestionItem WHERE question_id= '"+idC+"'");
            List<QuestionItem> listQuestionItems = tempQuery.list();
            for (QuestionItem q: listQuestionItems) {

                context.remove(q);

            }
            Question question = context.get(Question.class,idC);
            context.remove(question);




            tx.commit();
            context.close();


        } catch (Exception ex) {
            System.out.println("Exception removeQuestion: " + ex.getMessage());
        }
    }
    private  static  void showQuestions(){
        Session context = HiberSessionUtil.getSessionFactory().openSession();
        Query query = context.createQuery("FROM Question");
        List<Question> listQuestion = query.list();
        int i = 1;
        for (Question q : listQuestion){
            System.out.println(i + ") "+q.getName());
            i++;
        }



        context.close();
    }
    private static void editQuestion(){
        try{
            Scanner in = new Scanner(System.in);
            Session context = HiberSessionUtil.getSessionFactory().openSession();
            Transaction tx = context.beginTransaction();
            Query query = context.createQuery("FROM Question");
            List<Question> listQuestion = query.list();
            System.out.println("============= Edit Question ================");
            for (Question question: listQuestion) {
                System.out.println("Id: " + question.getId() + " ) Назва: " + question.getName());
            }
            System.out.print("Ведіть Id питання якого хочете змінити: ");

            int idQuestion = Integer.parseInt(in.nextLine());
            Question editQuestion = context.get(Question.class,idQuestion);
            System.out.println("=============================================");
            System.out.println("Старе питання: " +editQuestion.getName());
            System.out.print("Ведіть нове питання: ");
            String newNameQuestion = in.nextLine();
            editQuestion.setName(newNameQuestion);
            context.save(editQuestion);
            Query tempQuery = context.createQuery("FROM QuestionItem WHERE question_id= '"+editQuestion.getId()+"'");
            List<QuestionItem> listQuestionItems = tempQuery.list();
            System.out.println("=============================================");

                for (QuestionItem q: listQuestionItems) {
                    System.out.println("Id: "+ q.getId() + " Відповідь: " + q.getText() +"   Is_true: " + q.isTrue());
                }
                System.out.println("Ведіть id відповіді яку хочете змінити");
                int idItemQuestion = Integer.parseInt(in.nextLine());
                QuestionItem editQuestionItem = context.get(QuestionItem.class,idItemQuestion);
                System.out.println("Ведіть нову відповідь");
                String newName = in.nextLine();
                editQuestionItem.setText(newName);
                System.out.println("Відповіть є => Правильна - 1, Невірна - 0");
                System.out.print("Введетіть =>: ");
                boolean isTrue = Byte.parseByte(in.nextLine())==1 ? true : false;
                editQuestionItem.setTrue(isTrue);
                context.save(editQuestionItem);



            tx.commit();
            context.close();


        }catch (Exception ex){

        }
    }
    private static void addQuestion(){
        try{
            Session context = HiberSessionUtil.getSessionFactory().openSession();
            Transaction tx = context.beginTransaction();
            Scanner in = new Scanner(System.in);
            System.out.print("Вкажіть назву питання: ");
            String questionText = in.nextLine();
            Question q = new Question();
            q.setName(questionText);
            context.save(q);
            String action = "";
            do {
                System.out.print("Варіант відповіді: ");
                String text = in.nextLine();
                System.out.println("Правильна - 1, Невірна - 0");
                System.out.print("Введетіть =>: ");
                boolean isTrue = Byte.parseByte(in.nextLine())==1 ? true : false;
                QuestionItem qi = new QuestionItem();
                qi.setText(text);
                qi.setTrue(isTrue);
                qi.setQuestion(q);
                context.save(qi);
                System.out.println("0). = Вихід\n1). = Додати наступний варіант\n");
                System.out.print("==>:");
                action = in.nextLine();


            }while (!action.equals("0"));




            tx.commit();
            context.close();;
        }catch (Exception ex){
            System.out.print(ex.getMessage());
        }

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
    public static void StartGameTests(){
        try {
            Scanner in = new Scanner(System.in);
            Session context = HiberSessionUtil.getSessionFactory().openSession();
            Transaction tx = context.beginTransaction();
            Query query = context.createQuery("FROM Question");
            List<Question> listQuestion = query.list();
            int mark = 0;
            int countFalse = 0;

            for (Question question:listQuestion) {
                System.out.println("=============================");
                Query tempQuery = context.createQuery("FROM QuestionItem WHERE question_id= '"+question.getId()+"'");
                List<QuestionItem> listQuestionItems = tempQuery.list();
                System.out.println("Питання: " + question.getName());
                for (QuestionItem questionItem:listQuestionItems){
                    System.out.println(questionItem.getText());
                    System.out.println("Відповіть є => Вірна - 1, Невірна - 0");
                    System.out.print("Введетіть =>: ");
                    boolean isTrue = Byte.parseByte(in.nextLine())==1 ? true : false;
                    if (isTrue == true && questionItem.isTrue() == true){

                        mark += 12;
                        System.out.println(mark);
                    }
                }

            }
            int exampleArraySize = listQuestion.size();
            double markForTest = mark / exampleArraySize ;
            System.out.println(exampleArraySize);
            System.out.println(mark);
            System.out.println("Твоя оцінка за тест: " +markForTest);






        }catch (Exception ex){

        }

    }

}
