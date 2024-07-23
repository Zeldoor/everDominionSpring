package com.generation.dominion.service;

import java.util.Random;

import com.generation.dominion.dto.TroopDTO;

public class TroopGenerator {
    private static final String[] ROLES = {"Fighter", "Tank", "Bard", "Healer"};
    private static final int MIN_DAMAGE = 10;
    private static final int MAX_DAMAGE = 100;
    private static final int MIN_HEALTH = 50;
    private static final int MAX_HEALTH = 200;

    public static TroopDTO generateRandomTroop() {
        Random random = new Random();

        String role = ROLES[random.nextInt(ROLES.length)];
        int minimumDamage = random.nextInt(MAX_DAMAGE - MIN_DAMAGE + 1) + MIN_DAMAGE;
        int maximumDamage = random.nextInt(MAX_DAMAGE - minimumDamage + 1) + minimumDamage;
        int health = random.nextInt(MAX_HEALTH - MIN_HEALTH + 1) + MIN_HEALTH;

        return new TroopDTO(role, minimumDamage, maximumDamage, health);
    }
}
