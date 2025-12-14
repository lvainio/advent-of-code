package leo.aoc.year2015.day22;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private static final Effect SHIELD = new Effect("shield", 6, 7, 0, 0);

  private static final Effect POISON = new Effect("poison", 6, 0, 3, 0);

  private static final Effect RECHARGE = new Effect("recharge", 5, 0, 0, 101);

  private static final List<Spell> SPELLS =
      List.of(
          new Spell(53, 4, 0, null),
          new Spell(73, 2, 2, null),
          new Spell(113, 0, 0, SHIELD),
          new Spell(173, 0, 0, POISON),
          new Spell(229, 0, 0, RECHARGE));

  private final Boss originalBoss;

  public Solver(String input) {
    super(input);

    int bossHealth = Integer.parseInt(input.split("\r?\n")[0].split("\\s+")[2]);
    int bossDamage = Integer.parseInt(input.split("\r?\n")[1].split("\\s+")[1]);
    this.originalBoss = new Boss(bossHealth, bossDamage);
  }

  @Override
  public String solvePart1() {
    Player player = new Player(50, 500, 0, 0);
    Boss boss = new Boss(this.originalBoss.health(), this.originalBoss.damage());
    State state = new State(true, new HashSet<>(), player, boss);
    return Integer.toString(findMinCost(state, false));
  }

  @Override
  public String solvePart2() {
    Player player = new Player(50, 500, 0, 0);
    Boss boss = new Boss(this.originalBoss.health(), this.originalBoss.damage());
    State state = new State(true, new HashSet<>(), player, boss);
    return Integer.toString(findMinCost(state, true));
  }

  private int findMinCost(State state, boolean hardMode) {
    PriorityQueue<State> pq =
        new PriorityQueue<>(Comparator.comparingInt(s -> s.player().manaSpent()));
    pq.add(state);

    while (!pq.isEmpty()) {
      State currentState = pq.poll();

      if (isBossDead(currentState)) {
        return currentState.player().manaSpent();
      }

      Player player = currentState.player();

      int newPlayerHealthHardMode = player.health();
      if (currentState.isPlayerTurn() && hardMode) {
        newPlayerHealthHardMode--;
      }

      Player newPlayer =
          new Player(newPlayerHealthHardMode, player.mana(), player.armor(), player.manaSpent());

      State newState =
          new State(
              currentState.isPlayerTurn(), currentState.effects(), newPlayer, currentState.boss());

      if (isPlayerDead(newState)) {
        continue;
      }

      newState = applyStartOfTurnEffects(newState);

      if (isBossDead(newState)) {
        return newState.player().manaSpent();
      }

      if (newState.isPlayerTurn()) {
        for (Spell spell : SPELLS) {
          if (canAffordSpell(newState, spell) && canCastSpell(newState, spell)) {
            State nextState = applyPlayerTurn(newState, spell);
            pq.add(nextState);
          }
        }
      } else {
        State nextState = applyBossTurn(newState);
        pq.add(nextState);
      }
    }
    return Integer.MAX_VALUE;
  }

  private boolean canAffordSpell(State state, Spell spell) {
    return state.player().mana() >= spell.cost();
  }

  private boolean canCastSpell(State state, Spell spell) {
    if (spell.effect() == null) {
      return true;
    }
    boolean hasEffect = false;
    for (Effect effect : state.effects()) {
      if (spell.effect().id().equals(effect.id())) {
        hasEffect = true;
      }
    }
    return !hasEffect;
  }

  private State applyStartOfTurnEffects(State state) {
    HashSet<Effect> effects = state.effects();
    HashSet<Effect> newEffects = new HashSet<>();

    Player player = state.player();
    Boss boss = state.boss();

    int newArmor = 0;
    int newMana = player.mana();
    int newBossHealth = boss.health();

    for (Effect effect : effects) {
      newArmor += effect.armorIncrease();
      newMana += effect.manaIncrease();
      newBossHealth -= effect.damage();

      int turns = effect.turns() - 1;
      if (turns > 0) {
        newEffects.add(
            new Effect(
                effect.id(),
                turns,
                effect.armorIncrease(),
                effect.damage(),
                effect.manaIncrease()));
      }
    }

    Player newPlayer = new Player(player.health(), newMana, newArmor, player.manaSpent());
    Boss newBoss = new Boss(newBossHealth, boss.damage());

    return new State(state.isPlayerTurn(), newEffects, newPlayer, newBoss);
  }

  private boolean isPlayerDead(State state) {
    return state.player().health() <= 0;
  }

  private boolean isBossDead(State state) {
    return state.boss().health() <= 0;
  }

  private State applyBossTurn(State state) {
    Player player = state.player();
    Boss boss = state.boss();
    int newPlayerHealth = player.health() - (Math.max(1, boss.damage() - player.armor()));
    Player newPlayer =
        new Player(newPlayerHealth, player.mana(), player.armor(), player.manaSpent());
    return new State(true, state.effects(), newPlayer, state.boss());
  }

  public State applyPlayerTurn(State state, Spell spell) {
    Player player = state.player();
    Boss boss = state.boss();

    int newPlayerHealth = player.health();
    int newBossHealth = boss.health();

    HashSet<Effect> newEffects = new HashSet<>(state.effects());

    int newMana = player.mana() - spell.cost();
    int newManaSpent = player.manaSpent() + spell.cost();

    if (spell.effect() == null) {
      newPlayerHealth += spell.heal();
      newBossHealth -= spell.damage();
    } else {
      newEffects.add(spell.effect());
    }

    Player newPlayer = new Player(newPlayerHealth, newMana, player.armor(), newManaSpent);
    Boss newBoss = new Boss(newBossHealth, boss.damage());

    return new State(false, newEffects, newPlayer, newBoss);
  }
}
