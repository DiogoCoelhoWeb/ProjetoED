package rooms;

public enum RoomType {
    ENTRANCE_HALL,
    ROOM,
    TREASURE_ROOM;

    public String toString() {
        switch (this) {
            case ENTRANCE_HALL:
                return "Entrance Hall";
            case ROOM:
                return "Room";
            case TREASURE_ROOM:
                return "Treasure Room";
            default:
                return "Unknown";
        }
    }
}
