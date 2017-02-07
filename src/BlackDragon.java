
public class BlackDragon extends Character{

	public BlackDragon(String name, int baseLevel, int classLevel, int HP,
			int mana, int maxHP, int maxMana, int ATK, int DEF, int SPD,
			int MGC, int LUK) {
		
		// Load stats
		this.name = name;
		this.baseLevel = baseLevel;
		this.classLevel = classLevel;
		this.HP = HP;
		this.mana = mana;
		this.maxHP = maxHP;
		this.maxMana = maxMana;
		this.ATK = ATK;
		this.DEF = DEF;
		this.SPD = SPD;
		this.MGC = MGC;
		this.LUK = LUK;
		
		// Load spells
		mainSpells[0] = "Black Fire";
		equipment[0] = new Equipment("Normal Sword", "Cut", "Slash", "Stab", "Mega Ultra Death Strike of the Extreme Phoenix Dragon God");
	}
	
	public void castSpell(int spellNumber){
		System.out.println("Casting spell "+mainSpells[spellNumber]);
		
	}
	
}
