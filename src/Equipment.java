
public class Equipment {
	private String name;
	private String spell_0;
	private String spell_1;
	private String spell_2;
	private String spell_3;
	
	public Equipment(String name, String spell_0, String spell_1, String spell_2, String spell_3){
			this.name = name;
			this.spell_0 = spell_0;
			this.spell_1 = spell_1;
			this.spell_2 = spell_2;
			this.spell_3 = spell_3;
	}
	

	public String getSpell(int number){
		switch (number) {
		case 0:
			return spell_0;
		case 1:
			return spell_1;
		case 2:
			return spell_2;
		case 3:
			return spell_3;
		default:
			System.err.println("ERROR: getSpell asking for invalid number slot " + number);
			return null;
		}
	}
	
	public void setSpell(int number, String name){
		switch (number) {
		case 0:
			spell_0 = name;
		case 1:
			spell_1 = name;
		case 2:
			spell_2 = name;
		case 3:
			spell_3 = name;
		default:
			System.err.println("ERROR: setSpell is invalid, " + number + ", " + name);
		}
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
