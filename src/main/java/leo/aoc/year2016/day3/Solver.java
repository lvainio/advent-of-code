package leo.aoc.year2016.day3;

import java.util.ArrayList;
import java.util.List;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private record Triangle(int a, int b, int c) {
    public boolean isValidTriangle() {
      return a + b > c && a + c > b && b + c > a;
    }
  }

  private List<Triangle> triangles;

  public Solver(String input) {
    super(input);

    this.triangles =
        input
            .lines()
            .map(
                line -> {
                  String[] sideLengths = line.trim().split("\\s+");
                  int a = Integer.parseInt(sideLengths[0]);
                  int b = Integer.parseInt(sideLengths[1]);
                  int c = Integer.parseInt(sideLengths[2]);
                  return new Triangle(a, b, c);
                })
            .toList();
  }

  @Override
  public String solvePart1() {
    return Long.toString(this.triangles.stream().filter(Triangle::isValidTriangle).count());
  }

  @Override
  public String solvePart2() {
    List<Triangle> newTriangles = new ArrayList<>();
    for (int i = 0; i < this.triangles.size(); i += 3) {
      Triangle t1 = this.triangles.get(i);
      Triangle t2 = this.triangles.get(i + 1);
      Triangle t3 = this.triangles.get(i + 2);
      newTriangles.add(new Triangle(t1.a(), t2.a(), t3.a()));
      newTriangles.add(new Triangle(t1.b(), t2.b(), t3.b()));
      newTriangles.add(new Triangle(t1.c(), t2.c(), t3.c()));
    }
    return Long.toString(newTriangles.stream().filter(Triangle::isValidTriangle).count());
  }
}
