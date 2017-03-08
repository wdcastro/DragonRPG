
public class WhiteDragon extends Character{
	
	public WhiteDragon(String name, int level, int HP,
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
		mainSpells[0] = "White Shield";
		mainSpells[1] = "Holy Light";
		mainSpells[2] = "Repel";
		mainSpells[3] = "Amazing Next Level Holy Magic of the Egyptian God Ra";
		equipment[0] = new Equipment("Normal Staff", "Hit", "Sweep", "Poke", "Amazing Next Level Holy Magic of the Egyptian God Ra" );
	}
	
	public void castSpell(int spellNumber){
		System.out.println("Casting spell "+mainSpells[spellNumber]);
	}

}
