/*
 * This file is part of architectury.
 * Copyright (C) 2020, 2021, 2022 architectury
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package dev.architectury.test;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.test.debug.ConsoleMessageSink;
import dev.architectury.test.debug.MessageSink;
import dev.architectury.test.debug.client.ClientOverlayMessageSink;
import dev.architectury.test.entity.TestEntity;
import dev.architectury.test.events.DebugEvents;
import dev.architectury.test.gamerule.TestGameRules;
import dev.architectury.test.item.TestBlockInteractions;
import dev.architectury.test.networking.TestModNet;
import dev.architectury.test.particle.TestParticles;
import dev.architectury.test.registry.TestRegistries;
import dev.architectury.test.registry.client.TestKeybinds;
import dev.architectury.test.tags.TestTags;
import dev.architectury.test.trade.TestTrades;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MinecartRenderer;

public class TestMod {
    public static final MessageSink SINK = EnvExecutor.getEnvSpecific(() -> ClientOverlayMessageSink::new, () -> ConsoleMessageSink::new);
    public static final String MOD_ID = "architectury_test";
    
    public static void initialize() {
        DebugEvents.initialize();
        TestRegistries.initialize();
        TestGameRules.init();
        TestTags.initialize();
        TestTrades.init();
        TestParticles.initialize();
        TestModNet.initialize();
        TestBlockInteractions.init();
        EnvExecutor.runInEnv(Env.CLIENT, () -> TestMod.Client::initializeClient);
    }
    
    @Environment(EnvType.CLIENT)
    public static class Client {
        @Environment(EnvType.CLIENT)
        public static void initializeClient() {
            ClientLifecycleEvent.CLIENT_STARTED.register((client) -> SINK.accept("Client started!"));
            ClientLifecycleEvent.CLIENT_STOPPING.register((client) -> SINK.accept("Client stopping!"));
            ClientTickEvent.CLIENT_LEVEL_POST.register(level -> level.addParticle(TestParticles.TEST_PARTICLE.get(), 0, 0, 0, 0, 0, 0));
            TestKeybinds.initialize();
            TestModNet.initializeClient();
            EntityRendererRegistry.register(() -> TestEntity.TYPE, context ->
                    new MinecartRenderer<>(context, ModelLayers.MINECART));
        }
    }
}
