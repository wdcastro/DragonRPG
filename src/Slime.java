
public class Slime extends Character {
	
	/*
	 * String name;
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
	 */
	
	public Slime(){
		name = "Slime";
		baseLevel = 1;
		classLevel = 1;
		HP = 10;
		mana = 0;
		maxHP = 10;
		maxMana = 10;
		ATK = 1;
		DEF = 1;
		MGC_ATK = 1;
		MGC_DEF = 1;
		MGC_HEAL = 1;
		cooldownTimer = 0;
		mainSpells = new String[] {"Splash", "Blob", "Tackle", "Shapeshift"};
		
	}

}
