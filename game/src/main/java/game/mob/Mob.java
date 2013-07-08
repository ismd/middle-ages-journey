package game.mob;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Общий класс мобов, не конкретные мобы
 * @author ismd
 */
@Entity
public class Mob implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int lvl;
    private int maxHp;
    private int minDamage;
    private int maxDamage;
    private int maxInWorld;
    private long experience;
    private String image;
    private int strength;
    private int dexterity;
    private int endurance;

    public long getId() {
        return id;
    }

    public Mob setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Mob setName(String name) {
        this.name = name;
        return this;
    }

    public int getLevel() {
        return lvl;
    }

    public Mob setLevel(int level) {
        this.lvl = level;
        return this;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public Mob setMaxHp(int maxHp) {
        this.maxHp = maxHp;
        return this;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public Mob setMinDamage(int minDamage) {
        this.minDamage = minDamage;
        return this;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public Mob setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }

    public int getMaxInWorld() {
        return maxInWorld;
    }

    public Mob setMaxInWorld(int maxInWorld) {
        this.maxInWorld = maxInWorld;
        return this;
    }

    public long getExperience() {
        return experience;
    }

    public Mob setExperience(long experience) {
        this.experience = experience;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Mob setImage(String image) {
        this.image = image;
        return this;
    }

    public int getStrength() {
        return strength;
    }

    public Mob setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public int getDexterity() {
        return dexterity;
    }

    public Mob setDexterity(int dexterity) {
        this.dexterity = dexterity;
        return this;
    }

    public int getEndurance() {
        return endurance;
    }

    public Mob setEndurance(int endurance) {
        this.endurance = endurance;
        return this;
    }
}
