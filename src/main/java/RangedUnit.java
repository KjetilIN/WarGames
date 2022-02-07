public class RangedUnit extends Unit{

    protected RangedUnit(String name, int health){
        super(name,health,15,8 );
    }

    @Override
    public int getAttackBonus() {
        return 0;
    }

    @Override
    public int getResistBonus() {
        return 0;
    }
}
