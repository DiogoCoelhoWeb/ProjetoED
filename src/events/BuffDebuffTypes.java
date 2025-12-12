package events;

public enum BuffDebuffTypes {
    LOSE_TURN,
    GO_BACK;

    /**
     * Converts a given BuffDebuffTypes enum value to its corresponding string representation.
     *
     * @param type the BuffDebuffTypes value to be converted to a string
     * @return the string representation of the provided BuffDebuffTypes value,
     *         or an empty string if the type does not match any predefined values
     */
    public String toString(BuffDebuffTypes type) {
        switch (type) {
            case LOSE_TURN:
                return "Lose Turn";
            case GO_BACK:
                return "Go Back";
        }
        return "";
    }

    /**
     * Returns the opposite BuffDebuffType based on the provided string representation
     * of the type. Accepts inputs such as "lose turn" (case insensitive).
     *
     * @param type the string representation of the BuffDebuffType to determine
     *             the opposite of; must not be null
     * @return the opposite BuffDebuffType as an enum value
     * @throws IllegalArgumentException if the input string is null
     */
    public static BuffDebuffTypes getOpposite(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        String temp = type.toLowerCase().replaceAll("\\s+","");
        if (temp.equals("loseturn")) {
            return LOSE_TURN;
        } else {
            return GO_BACK;
        }
    }

}
