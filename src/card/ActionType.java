package card;

public enum ActionType {
    PICKAXE,
    LAMP,
    CART,
    PICKAXE_BLOCK,
    LAMP_BLOCK,
    CART_BLOCK,
    ROCKFALL,
    MAP;

    public static ActionType getNoBlockType(ActionType type) {
        if (type == PICKAXE_BLOCK)
            return PICKAXE;
        else if (type == LAMP_BLOCK)
            return LAMP;
        else if (type == CART_BLOCK)
            return CART;
        else
            return type;
    }
}
