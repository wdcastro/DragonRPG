
public class BlackDragon extends Character{

	public BlackDragon(String name, int level, int HP,
			int mana, int maxHP, int maxMana, int ATK, int DEF,
			int MGC_ATK, int MGC_DEF, int MGC_HEAL) {
		
		// Load stats
		this.name = name;
		this.level = level;
		this.HP = HP;
		this.mana = mana;
		this.maxHP = maxHP;
		this.maxMana = maxMana;
		this.ATK = ATK;
		this.DEF = DEF;
		this.MGC_ATK = MGC_ATK;
		this.MGC_DEF = MGC_DEF;
		this.MGC_HEAL = MGC_HEAL;
		
		// Load spells
		mainSpells[0] = "Black Fire";
		mainSpells[1] = "Wrath of Darkness";
		mainSpells[2] = "Destructive Blaze";
		mainSpells[3] = "Mega Ultra Death Strike of the Extreme Phoenix Dragon God";
		equipment[0] = new Equipment("Normal Sword", "Cut", "Slash", "Stab", "Mega Ultra Death Strike of the Extreme Phoenix Dragon God");
	}
	
	public void castSpell(int spellNumber){
		System.out.println("Casting spell "+mainSpells[spellNumber]);
		
	}
	
}
