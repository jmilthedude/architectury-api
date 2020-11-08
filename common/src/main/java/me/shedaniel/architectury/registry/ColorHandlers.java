/*
 * Copyright 2020 shedaniel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.shedaniel.architectury.registry;

import me.shedaniel.architectury.ArchitecturyPopulator;
import me.shedaniel.architectury.Populatable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

@Environment(EnvType.CLIENT)
public final class ColorHandlers {
    private ColorHandlers() {}
    
    @Populatable
    private static final Impl IMPL = null;
    
    public static void registerItemColors(ItemColor color, ItemLike... items) {
        IMPL.registerItemColors(color, items);
    }
    
    public static void registerBlockColors(BlockColor color, Block... blocks) {
        IMPL.registerBlockColors(color, blocks);
    }
    
    public interface Impl {
        void registerItemColors(ItemColor color, ItemLike... items);
        
        void registerBlockColors(BlockColor color, Block... blocks);
    }
    
    static {
        ArchitecturyPopulator.populate(ColorHandlers.class);
    }
}