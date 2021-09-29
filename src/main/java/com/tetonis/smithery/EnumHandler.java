package com.tetonis.smithery;

import net.minecraft.util.IStringSerializable;

public class EnumHandler {
    public static enum Molds implements IStringSerializable{
        NONE("none", 0),
        PICKAXE("pickaxe", 1),
        PICKAXE_FILLED_STONE("pickaxe_filled_stone", 2),
        PICKAXE_FILLED_STONE_PROGRESS("pickaxe_filled_stone_progress", 3),
        PICKAXE_HEAD_STONE("pickaxe_head_stone", 4),
        PICKAXE_HEAD_STONE_PROGRESS("pickaxe_head_stone_progress", 4);

        private int ID;
        private String name;

        private Molds(String name, int ID){
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String toString() {
            return getName();
        }


        @Override
        public String getName() {
            return this.name;
        }

        public int getID() {
            return ID;
        }


    }
}
