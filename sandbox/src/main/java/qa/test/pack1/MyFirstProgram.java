package qa.test.pack1;

public class MyFirstProgram {

    public static void main(String[] args) {
        hello("world");
        hello("user");
        hello("Andrey");

        Square s =new Square(5);
        System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

        Rectangel r = new Rectangel(4, 6);
        System.out.println("Площадь прямоугольника со сторонами " + r.a +  " и " + r.b + " = " + r.area());
    }
    public static void hello(String somebody) {
        System.out.println("Hello, " + somebody + "!");
    }





}