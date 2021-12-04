package qa.test.pack1;

public class LaunchPoint {

  public static void main(String[] args) {

    //Создаем объекты класса
    Point p1 = new Point(1, 2);
    Point p2 = new Point(9, 7);

    //вычисляем расстояние между точками (способ 1)
    double dis1 = distance(p1, p2);
    System.out.println("Расстояние между точками p1(" + p1.x + ";" + p1.y + ") и p2(" + p2.x + ";" + p2.y + ") = " + dis1);

    //вычисляем расстояние между точками (способ 2)
    double dis2 = p1.distance(p2);
    System.out.println("Расстояние между точками (пункт 4) = " + dis2);
  }

  //Вычисляем расстояние между двумя точками
  public static double distance(Point p1, Point p2) {
    return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
  }
}

