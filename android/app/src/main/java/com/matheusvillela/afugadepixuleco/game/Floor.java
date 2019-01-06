package com.matheusvillela.afugadepixuleco.game;

public enum Floor {
    ZERO(0, 0),
    FIRST(1, 13),
    SECOND(2, 31),
    THIRD(3, 49),
    FOURTH(4, 67);
    int id;
    float height;
    Floor(int id, float height) {
        this.id = id;
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public static Floor getFloorById(int id) {
        for(Floor floor : Floor.values()) {
            if(floor.id == id){
                return floor;
            }
        }
        return null;
    }
}
