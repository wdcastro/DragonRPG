abstract public class Character {
	// maybe abstract class, make hero obj, enemy obj etc

	String name;
	protected int baseLevel;
	protected int classLevel;
	protected int HP;
	protected int mana;
	protected int maxHP;
	protected int maxMana;
	protected int ATK;
	protected int DEF;
	protected int SPD;
	protected int MGC;
	protected int LUK;
	protected int cooldownTimer = 0;
	protected Equipment[] equipment = new Equipment[6];
	protected boolean isShielded = false;
	private String[] totalSpells = new String[16];
	protected String[] mainSpells = new String[4];
	private String[] auras = new String[4];

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getHP() {
		return HP;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public int getMana() {
		return mana;
	}

	public int getATK() {
		return ATK;
	}

	public int getDEF() {
		return DEF;
	}

	public int getSPD() {
		return SPD;
	}

	public int getMGC() {
		return MGC;
	}

	public int getLUK() {
		return LUK;
	}

	/**
	 * Character takes damage specified by parameter
	 * 
	 * @param amount
	 *            - damage to take
	 */
	public void takeDamage(int amount) { // can check for death in battlemanager progressFight(), it'll use an if check anyway. using an if check with takeDamage still is one cpu tick

		System.out.println(getName() + " takes " + amount + " damage.");
		HP -= amount;
		if (HP <= 0) {
			HP = 0;
			System.out.println("HP is 0. " + getName() + " has died.");
		}
		System.out.println("Remaining HP is "+HP);
	}

	/**
	 * Heal the character for specified amount of health
	 * 
	 * @param amount
	 *            - amount to heal
	 */
	public void healHP(int amount) {
		HP += amount;
		if (HP > maxHP) {
			HP = maxHP;
		}
	}

	public void useMana(int amount) {
		mana -= amount;
	}

	public void gainStat(String stat, int amount) {
		switch (stat) {
		case "maxHP":
			maxHP += amount;
			break;
		case "maxMana":
			maxMana += amount;
			break;
		case "ATK":
			ATK += amount;
			break;
		case "DEF":
			DEF += amount;
			break;
		case "SPD":
			SPD += amount;
			break;
		case "MGC":
			MGC += amount;
			break;
		case "LUK":
			LUK += amount;
			break;
		default:
			break;
		}
	}
	
	public void loseStat(String stat, int amount) {
		if(amount <= 0){
			return;
		}
		switch (stat) {
		case "maxHP":
			maxHP -= amount;
			if(maxHP < 1){
				maxHP = 1;
			}
			break; 
		case "maxMana":
			maxMana -= amount;
			if(maxMana < 1){
				maxMana = 1;
			}
			break;
		case "ATK":
			ATK -= amount;
			if(ATK < 0){
				ATK = 0;
			}
			break;
		case "DEF":
			DEF -= amount;
			if(DEF < 0){
				DEF = 0;
			}
			break;
		case "SPD":
			SPD -= amount;
			if(SPD < 0){
				SPD = 0;
			}
			break;
		case "MGC":
			MGC -= amount;
			if(MGC < 0){
				MGC = 0;
			}
			break;
		case "LUK":
			LUK -= amount;
			if(LUK < 0){
				LUK = 0;
			}
			break;
		default:
			break;
		}
	}
	
	public void getAttacked(Character attacker, String spell, int damage){
		if(isShielded){
			damage = 0;
			System.out.println(getName()+" was shielded.");
		}
		takeDamage(damage);
	}
	
	public int getCooldown(){
		return cooldownTimer;
	}
	
	public Equipment[] getEquipment(){
		return equipment;
	}
	
	public String[] getSpells(){
		return mainSpells;
	}
	
	public void setShield(boolean shield){
		isShielded = shield;
	}
	
	public boolean isDead(){
		return HP == 0;
	}

}
