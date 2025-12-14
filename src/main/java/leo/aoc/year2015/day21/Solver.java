package leo.aoc.year2015.day21;

import java.util.List;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private record Weapon(int cost, int damage) {}

  private record Armor(int cost, int armor) {}

  private record Ring(int cost, int damage, int armor) {}

  private List<Weapon> weapons =
      List.of(
          new Weapon(8, 4),
          new Weapon(10, 5),
          new Weapon(25, 6),
          new Weapon(40, 7),
          new Weapon(74, 8));

  private List<Armor> armors =
      List.of(
          new Armor(0, 0),
          new Armor(13, 1),
          new Armor(31, 2),
          new Armor(53, 3),
          new Armor(75, 4),
          new Armor(102, 5));

  private List<Ring> rings =
      List.of(
          new Ring(0, 0, 0),
          new Ring(0, 0, 0),
          new Ring(25, 1, 0),
          new Ring(50, 2, 0),
          new Ring(100, 3, 0),
          new Ring(20, 0, 1),
          new Ring(40, 0, 2),
          new Ring(80, 0, 3));

  private final int bossHealth;
  private final int bossDamage;
  private final int bossArmor;

  public Solver(String input) {
    super(input);

    this.bossHealth = Integer.parseInt(input.split("\r?\n")[0].split("\\s+")[2]);
    this.bossDamage = Integer.parseInt(input.split("\r?\n")[1].split("\\s+")[1]);
    this.bossArmor = Integer.parseInt(input.split("\r?\n")[2].split("\\s+")[1]);
  }

  @Override
  public String solvePart1() {
    int bossHP = this.bossHealth;
    int bossDP = this.bossDamage;
    int bossAP = this.bossArmor;

    int playerHP = 100;
    int playerDP = 0;
    int playerAP = 0;

    int minCost = Integer.MAX_VALUE;
    for (Weapon w : this.weapons) {
      for (Armor a : this.armors) {
        for (int i = 0; i < this.rings.size(); i++) {
          for (int j = i + 1; j < this.rings.size(); j++) {

            Ring r1 = this.rings.get(i);
            Ring r2 = this.rings.get(j);

            playerDP = w.damage() + r1.damage() + r2.damage();
            playerAP = a.armor() + r1.armor() + r2.armor();

            while (bossHP >= 0 && playerHP >= 0) {
              // player turn
              bossHP -= Math.max(1, playerDP - bossAP);
              // boss turn
              playerHP -= Math.max(1, bossDP - playerAP);
            }

            // player won
            if (bossHP <= 0) {
              int cost = w.cost() + a.cost() + r1.cost() + r2.cost();
              minCost = Math.min(minCost, cost);
            }

            // reset
            bossHP = this.bossHealth;
            bossDP = this.bossDamage;
            bossAP = this.bossArmor;
            playerHP = 100;
            playerDP = 0;
            playerAP = 0;
          }
        }
      }
    }
    return Integer.toString(minCost);
  }

  @Override
  public String solvePart2() {
    int bossHP = this.bossHealth;
    int bossDP = this.bossDamage;
    int bossAP = this.bossArmor;

    int playerHP = 100;
    int playerDP = 0;
    int playerAP = 0;

    int maxCost = Integer.MIN_VALUE;
    for (Weapon w : this.weapons) {
      for (Armor a : this.armors) {
        for (int i = 0; i < this.rings.size(); i++) {
          for (int j = i + 1; j < this.rings.size(); j++) {

            Ring r1 = this.rings.get(i);
            Ring r2 = this.rings.get(j);

            playerDP = w.damage() + r1.damage() + r2.damage();
            playerAP = a.armor() + r1.armor() + r2.armor();

            while (bossHP >= 0 && playerHP >= 0) {
              // player turn
              bossHP -= Math.max(1, playerDP - bossAP);
              // boss turn
              playerHP -= Math.max(1, bossDP - playerAP);
            }

            // player lost
            if (bossHP > 0) {
              int cost = w.cost() + a.cost() + r1.cost() + r2.cost();
              maxCost = Math.max(maxCost, cost);
            }

            // reset
            bossHP = this.bossHealth;
            bossDP = this.bossDamage;
            bossAP = this.bossArmor;
            playerHP = 100;
            playerDP = 0;
            playerAP = 0;
          }
        }
      }
    }
    return Integer.toString(maxCost);
  }
}
