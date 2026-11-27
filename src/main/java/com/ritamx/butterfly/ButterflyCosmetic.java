package com.ritamx.butterfly;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class ButterflyCosmetic extends JavaPlugin {
    private final Set<UUID> activePlayers = new HashSet<>();
    private BukkitTask particleTask;

    @Override
    public void onEnable() {
        // Use the real Bukkit scheduler API directly; no hand-written API stubs.
        particleTask = getServer().getScheduler().runTaskTimer(this, this::tickEffects, 0L, 2L);
        getLogger().info("ButterflyCosmetic 1.1.0 enabled.");
    }

    @Override
    public void onDisable() {
        if (particleTask != null) {
            particleTask.cancel();
            particleTask = null;
        }
        activePlayers.clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        UUID id = player.getUniqueId();
        if (activePlayers.remove(id)) {
            player.sendMessage("Butterfly effect disabled.");
        } else {
            activePlayers.add(id);
            player.sendMessage("Butterfly effect enabled.");
        }
        return true;
    }

    private void tickEffects() {
        for (UUID id : Set.copyOf(activePlayers)) {
            Player player = getServer().getPlayer(id);
            if (player == null || !player.isOnline()) {
                activePlayers.remove(id);
                continue;
            }
            Location base = player.getLocation().add(0.0, 1.15, 0.0);
            double phase = (System.currentTimeMillis() % 1600L) / 1600.0 * Math.PI * 2.0;
            double bob = Math.sin(phase) * 0.08;
            Location center = base.clone().add(0.0, bob, 0.0);

            // Small decorative butterfly silhouette made from particles.
            // Particle visibility is handled by the server for nearby players.
            spawnWing(player, center, -1.0, phase);
            spawnWing(player, center, 1.0, phase);
            player.getWorld().spawnParticle(Particle.END_ROD, center, 1, 0.015, 0.015, 0.015, 0.0);
        }
    }

    private void spawnWing(Player player, Location center, double side, double phase) {
        double flap = 0.75 + 0.25 * Math.sin(phase * 2.0);
        for (int i = 0; i < 4; i++) {
            double x = side * (0.12 + i * 0.10);
            double y = (0.18 + i * 0.025) * flap;
            double z = Math.sin(phase + i * 0.55) * 0.035;
            Location point = center.clone().add(x, y, z);
            player.getWorld().spawnParticle(Particle.END_ROD, point, 1, 0.01, 0.01, 0.01, 0.0);
        }
        Location lower = center.clone().add(side * 0.22, -0.12 * flap, 0.0);
        player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, lower, 1, 0.01, 0.01, 0.01, 0.0);
    }
}
