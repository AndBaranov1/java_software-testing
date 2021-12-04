package qa.test.pack1;

public class Point {

  //точки на плоскости
  public double x;
  public double y;

  //Конструктор с параметрами x, y
  public Point(double x, double y) {
    //вызов конструктора с параметрами x и y
    this.x = x;
    this.y = y;
  }

  //Вычисляем расстояние между двумя точками (пункт 4)
  public double distance(Point p2) {
    return Math.sqrt(Math.pow(p2.x - this.x, 2) + Math.pow(p2.y - this.y, 2));
  }

}